package com.example.prashant.monolith.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict=false)
public class Results {

    @SerializedName("results")
    @Expose
    private List<Results> results = null;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

}