package com.serli.oracle.of.bacon.repository;

import org.bson.Document;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ferrilata on 17/01/17.
 */
public class MongoDbRepositoryTest {
    @Test
    public void getActorByName() throws Exception {
        MongoDbRepository repo = new MongoDbRepository();

        assertFalse("truc should not exist", repo.getActorByName("truc").isPresent());
        assertTrue("Robert De Niro should exist", repo.getActorByName("Robert De Niro").isPresent());
        assertTrue("Jake Gyllenhaal should exist", repo.getActorByName("Jake Gyllenhaal").isPresent());
        assertTrue("Emma Watson should exist", repo.getActorByName("Emma Watson").isPresent());
        assertTrue("Emma Watson should exist", repo.getActorByName("Emma Watson").get() instanceof Document);
    }
}