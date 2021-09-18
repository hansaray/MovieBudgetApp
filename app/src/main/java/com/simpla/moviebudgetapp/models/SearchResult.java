package com.simpla.moviebudgetapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * @param name- MovieName
     * @param id- MovieId
     */
    public SearchResult(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter and Setter Functions
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
