package com.serli.oracle.of.bacon.repository;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by xela on 17/01/17.
 */
public class RedisRepositoryTest {

    private RedisRepository redisRepository;


    @Before
    public void setup() {
        redisRepository = new RedisRepository("test");
        redisRepository.clearList();
    }


    @Test
    public void testLessThan10Entries() {
        for (int i = 0; i < 100; i++) {
            redisRepository.addSearchEntry("request_" + i);
        }
        assertTrue(redisRepository.getLastTenSearches().size() == 10);
    }

    @Test
    public void testClear() {
        for (int i = 0; i < 100; i++) {
            redisRepository.addSearchEntry("request_" + i);
        }
        redisRepository.clearList();
        assertEquals(redisRepository.getLastTenSearches().size(), 0);
    }

    @Test
    public void testOrder() {
        redisRepository.addSearchEntry("a");
        redisRepository.addSearchEntry("b");
        redisRepository.addSearchEntry("c");

        assertEquals(redisRepository.getLastTenSearches(), Arrays.asList("c", "b", "a"));
    }


}
