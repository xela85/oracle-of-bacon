package com.serli.oracle.of.bacon.utils;

import com.google.gson.JsonObject;

/**
 * Created by xela on 18/01/17.
 */
public class Json {

    private JsonObject json;

    public Json() {
        this.json = new JsonObject();
    }

    public static Json obj() {
        return new Json();
    }


    public Json add(String id, Json value) {
        json.add(id, value.json);
        return this;
    }

    public Json add(String id, String value) {
        json.addProperty(id, value);
        return this;
    }

    public Json add(String id, Number number) {
        json.addProperty(id, number);
        return this;
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
