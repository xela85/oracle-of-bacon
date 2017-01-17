package com.serli.oracle.of.bacon.repository;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by xela on 17/01/17.
 */
public class ElasticSearchRepositoryTest {

    private ElasticSearchRepository repository;

    @Before
    public void setup() {
        repository = new ElasticSearchRepository();
    }


    @Test
    public void testDeNiro() throws IOException {
        List<String> names = repository.getActorsSuggests("De Niro");
        for (String name : names) {
            assertTrue("The result must contain the string 'De Niro'", name.toLowerCase().contains("de") || name.toLowerCase().contains("niro"));
        }

    }

    @Test
    public void testSize() throws IOException {
        List<String> names = repository.getActorsSuggests("a");
        assertEquals(5, names.size());
    }


    @Test
    public void testNoResult() throws IOException {
        assertEquals(0, repository.getActorsSuggests("This request should have no result").size());
    }

}
