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
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getConnectionsToKevinBacon() throws Exception {
        Neo4JRepository repo = new Neo4JRepository();

        List<?> goodResult = repo.getConnectionsToKevinBacon("Pacino, Al");
        List<?> badResult = repo.getConnectionsToKevinBacon("bateau");

        assertNotNull("Returned List shouldn't be null", goodResult);
        assertNull("Returned List should be null", badResult);
        assertEquals("Returned List shouldn't be empty", 5, goodResult);
    }

}