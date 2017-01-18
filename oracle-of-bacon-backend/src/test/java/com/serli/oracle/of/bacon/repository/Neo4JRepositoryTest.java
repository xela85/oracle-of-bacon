package com.serli.oracle.of.bacon.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ferrilata on 17/01/17.
 */
public class Neo4JRepositoryTest {

    @Test
    public void getConnectionsToKevinBacon() throws Exception {
        Neo4JRepository repo = new Neo4JRepository();

        List<?> goodResult = repo.getConnectionsToKevinBacon("Pacino, Al (I)");
        List<?> badResult = repo.getConnectionsToKevinBacon("bateau");

        assertNotEquals("Returned List for good query shouldn't be empty", 0, goodResult.size());
        assertEquals("Returned List for bad query should be empty", 0, badResult.size());
        assertEquals("Should be 9 for Pacino, Al (I)", 9, goodResult.size());
        assertEquals("Should be 13 for Niro, Chel", 13, repo.getConnectionsToKevinBacon("Niro, Chel").size());
        assertEquals("Should be 5 for Hanks, Tom", 5, repo.getConnectionsToKevinBacon("Hanks, Tom").size());
    }

}