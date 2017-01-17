package com.serli.oracle.of.bacon.loader.elasticsearch;

import com.mongodb.BulkWriteRequestBuilder;
import com.serli.oracle.of.bacon.repository.ElasticSearchRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CompletionLoader {
    private static AtomicInteger count = new AtomicInteger(0);



    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Expecting 1 arguments, actual : " + args.length);
            System.err.println("Usage : completion-loader <actors file path>");
            System.exit(-1);
        }

        String inputFilePath = args[0];
        JestClient client = ElasticSearchRepository.createClient();


        AtomicReference<Bulk.Builder> builder = new AtomicReference<>(createBuilder());
        client.execute(new CreateIndex.Builder("actors").build());

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            bufferedReader.lines()
                    .forEach(line -> {
                        //TODO ElasticSearch insert
                        Actor element = new Actor(line.replaceAll("\"", ""));
                        Index index = new Index.Builder(element).build();
                        builder.get().addAction(index);

                        if(count.incrementAndGet() % 50000 == 0) {
                            try {
                                System.out.println("INSERT");
                                client.execute(builder.get().build());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            builder.set(createBuilder());
                        }

                    });
        }
        try {
            System.out.println("INSERT");
            client.execute(builder.get().build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Inserted total of " + count.get() + " actors");
    }


    private static Bulk.Builder createBuilder() {
        return new Bulk.Builder()
                .defaultIndex("actors")
                .defaultType("actor");
    }

}
