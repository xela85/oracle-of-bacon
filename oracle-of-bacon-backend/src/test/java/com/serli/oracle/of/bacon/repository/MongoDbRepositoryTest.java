package com.serli.oracle.of.bacon.repository;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ferrilata on 17/01/17.
 */
public class MongoDbRepositoryTest {

    private MongoDbRepository repo;

    @Before
    public void setup() { repo = new MongoDbRepository();
    }

    @Test
    public void testNotExist() throws Exception {
        assertFalse("truc should not exist", repo.getActorByName("truc").isPresent());
    }

    @Test
    public void testDeNiro() throws Exception {
        assertTrue("Robert De Niro should exist", repo.getActorByName("Robert De Niro").isPresent());
    }

    @Test
    public void testGyllenhaal() throws Exception {
        assertTrue("Jake Gyllenhaal should exist", repo.getActorByName("Jake Gyllenhaal").isPresent());
    }

    @Test
    public void testWatson() throws Exception {
        assertTrue("Emma Watson should exist", repo.getActorByName("Emma Watson").isPresent());
    }

    @Test
    public void testReturnType() throws Exception {
        assertTrue("Return type should be Document", repo.getActorByName("Emma Watson").get() instanceof Document);
    }
}