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

    private Neo4JRepository repo;

    @Before
    public void setup() { repo = new Neo4JRepository(); }

    @Test
    public void testNotEmpty() throws Exception {

        assertNotEquals("Returned List for good query shouldn't be empty",
                0,
                repo.getConnectionsToKevinBacon("Pacino, Al (I)").size());
    }

    @Test
    public void testEmpty() throws Exception {
        assertEquals("Returned List for bad query should be empty",
                0,
                repo.getConnectionsToKevinBacon("bateau").size());
    }

    @Test
    public void testShouldBeFive() throws Exception {
        assertEquals("Should be 5 for Hanks, Tom", 5, repo.getConnectionsToKevinBacon("Hanks, Tom").size());
    }

    @Test
    public void testShouldBeNine() throws Exception {
        assertEquals("Should be 9 for Pacino, Al (I)",
                9,
                repo.getConnectionsToKevinBacon("Pacino, Al (I)").size());
    }

    @Test
    public void testShouldBeThirteen() throws Exception {
        assertEquals("Should be 13 for Niro, Chel", 13, repo.getConnectionsToKevinBacon("Niro, Chel").size());
    }

}