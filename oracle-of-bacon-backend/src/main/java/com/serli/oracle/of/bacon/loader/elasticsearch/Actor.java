package com.serli.oracle.of.bacon.loader.elasticsearch;

import io.searchbox.annotations.JestId;

/**
 * Created by xela on 17/01/17.
 */
public class Actor {

    @JestId
    private String name;

    public Actor(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
