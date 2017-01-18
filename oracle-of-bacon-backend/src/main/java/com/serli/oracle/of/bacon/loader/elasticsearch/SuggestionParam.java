package com.serli.oracle.of.bacon.loader.elasticsearch;

import java.util.List;

/**
 * Created by xela on 18/01/17.
 */
public class SuggestionParam {

    private List<String> input;

    public SuggestionParam(List<String> input) {
        this.input = input;
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }
}
