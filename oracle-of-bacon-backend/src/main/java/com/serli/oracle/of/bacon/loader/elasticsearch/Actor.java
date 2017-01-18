package com.serli.oracle.of.bacon.loader.elasticsearch;

import io.searchbox.annotations.JestId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xela on 17/01/17.
 */
public class Actor {

    @JestId
    private String name;

    public SuggestionParam name_suggest;


    public Actor(String name) {
        List<String> suggestions = new ArrayList<>();
        String[] tokens = name.replaceAll("'|,", "").toLowerCase().split("\\s|,");
        for (int i = 0; i < tokens.length; i++) {
            suggestions.add(tokens[i]);
            for (int j = 0; j < tokens.length; j++) {
                if(i != j) {
                    suggestions.add(tokens[i] + " " + tokens[j]);
                    suggestions.add(tokens[i] + ", " + tokens[j]);
                }
            }
        }
        this.name_suggest = new SuggestionParam(suggestions);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SuggestionParam getName_suggest() {
        return name_suggest;
    }

    public void setName_suggest(SuggestionParam name_suggest) {
        this.name_suggest = name_suggest;
    }
}
