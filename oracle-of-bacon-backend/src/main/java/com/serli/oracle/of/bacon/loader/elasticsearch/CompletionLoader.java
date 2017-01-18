package com.serli.oracle.of.bacon.loader.elasticsearch;

import com.mongodb.BulkWriteRequestBuilder;
import com.serli.oracle.of.bacon.repository.ElasticSearchRepository;
import com.serli.oracle.of.bacon.utils.Json;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Suggest;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

        recreateIndex(client);

        createMapping(client);


        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            bufferedReader.lines()
                    .forEach(line -> {
                        String name = line.replaceAll("\"", "");
                        Actor element = new Actor(name);
                        Index index = new Index.Builder(element).build();
                        builder.get().addAction(index);

                        if(count.incrementAndGet() % 10000 == 0) {
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

    private static void createMapping(JestClient client) throws IOException {
        PutMapping putMapping = new PutMapping.Builder("actors", "actor",
                Json.obj()
                        .add("actor", Json.obj()
                                .add("properties", Json.obj()
                                        .add("name", Json.obj()
                                                .add("type", "string"))
                                        .add("name_suggest", Json.obj()
                                                .add("type", "completion"))))
                        .toString()).build();
        System.out.println(client.execute(putMapping).getErrorMessage());
    }

    private static void recreateIndex(JestClient client) throws IOException {
        client.execute(new DeleteIndex.Builder("actors").build());
        client.execute(new CreateIndex.Builder("actors")
                .settings(Json.obj()
                        .add("settings", Json.obj()
                                .add("analysis", Json.obj()
                                        .add("analyser", "english")))
                        .toString()
                ).build());
    }


    private static Bulk.Builder createBuilder() {
        return new Bulk.Builder()
                .defaultIndex("actors")
                .defaultType("actor");
    }

}
