package com.serli.oracle.of.bacon.repository;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisRepository {

    private static String DEFAULT_FIELD = "searches";

    private Jedis jedis;

    private String searchesField;


    RedisRepository(String searchesField) {
        this.searchesField = searchesField;
        this.jedis = new Jedis("localhost");
    }

    public RedisRepository() {
        this(DEFAULT_FIELD);
    }

    public List<String> getLastTenSearches() {
        return jedis.lrange(searchesField, 0, 9);
    }

    public void addSearchEntry(String search) {
        jedis.lpush(searchesField, search);
    }

    public void clearList() {
        jedis.del(searchesField);
    }



}
