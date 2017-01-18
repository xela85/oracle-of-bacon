package com.serli.oracle.of.bacon.repository;

import com.serli.oracle.of.bacon.loader.elasticsearch.Actor;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Suggest;
import io.searchbox.core.SuggestResult;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ElasticSearchRepository {

    private final JestClient jestClient;

    private final static String SUGGESTION_NAME = "my-suggestion";

    public ElasticSearchRepository() {
        jestClient = createClient();

    }

    public static JestClient createClient() {
        JestClient jestClient;
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200")
                .multiThreaded(true)
                .readTimeout(60000)
                .build());

        jestClient = factory.getObject();
        return jestClient;
    }

    public List<String> getActorsSuggests(String searchQuery) throws IOException {

        Suggest suggest = new Suggest.Builder("{\n" +
                "  \"my-suggestion\": {\n" +
                "    \"text\": \"" + searchQuery + "\",\n" +
                "    \"completion\": {\n" +
                "      \"field\": \"name_suggest\",\n" +
                "      \"size\": 10\n" +
                "    }\n" +
                "  }\n" +
                "}").addIndex("actors").build();

        SuggestResult result = jestClient.execute(suggest);
        return result.getSuggestions(SUGGESTION_NAME).stream()
                .flatMap(s -> s.options.stream())
                .map(t -> t.get("_id").toString()).collect(toList());
    }


}
