package com.serli.oracle.of.bacon.repository;

import com.serli.oracle.of.bacon.loader.elasticsearch.Actor;
import com.serli.oracle.of.bacon.utils.Json;
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

/**
 * Configure an ElasticSearch REST client, and enable the user to search for an actor in the database
 */
public class ElasticSearchRepository {

    private final JestClient jestClient;

    private final static String SUGGESTION_NAME = "my-suggestion";

    public ElasticSearchRepository() {
        jestClient = createClient();

    }

    /**
     * Create and configure an elastic search client connected to the actor database
     * @return configrued <a href="https://github.com/searchbox-io/Jest">Jest</a> client
     */
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

    /**
     * Get actor suggestion for a string given by the user.
     * Several operations are done on the data, enabling to match with every firstName and lastName order
     * @param searchQuery user query
     * @return list of actors matching the query
     * @throws IOException occurs if an error is catched by the JEST client
     */
    public List<String> getActorsSuggests(String searchQuery) throws IOException {




        Suggest suggest = new Suggest.Builder(Json.obj()
                .add("my-suggestion", Json.obj()
                        .add("text", searchQuery)
                        .add("completion", Json.obj()
                                .add("field", "name_suggest")
                                .add("size", 10)))
                .toString())
                .addIndex("actors").build();

        SuggestResult result = jestClient.execute(suggest);
        return result.getSuggestions(SUGGESTION_NAME).stream()
                .flatMap(s -> s.options.stream())
                .map(t -> t.get("_id").toString()).collect(toList());
    }


}
