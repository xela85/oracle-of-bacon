package com.serli.oracle.of.bacon.repository;

import com.serli.oracle.of.bacon.loader.elasticsearch.Actor;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ElasticSearchRepository {

    private final JestClient jestClient;

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
        Search search = new Search.Builder("{\n" +
                "    \"size\": 5,\n" +
                "    \"query\": {\n" +
                "        \"match\" : {\n" +
                "            \"name\" : {\n" +
                "              \"query\": \"" + searchQuery + "\",\n" +
                "              \"operator\": \"and\",\n" +
                "              \"fuzziness\": \"AUTO\"\n" +
                "            }\n" +
                "            \n" +
                "        }\n" +
                "    }\n" +
                "}").build();
        List<SearchResult.Hit<Actor, Void>> hits = jestClient.execute(search).getHits(Actor.class);
        return hits.stream().map(h -> h.source).map(Actor::getName).collect(toList());
    }


}
