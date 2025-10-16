package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SearchResult {
    private final String id;
    private final String name;
    private final String contentType;

    @JsonCreator
    public SearchResult(@JsonProperty("id") String id, 
                       @JsonProperty("name") String name, 
                       @JsonProperty("contentType") String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(
            searchable.getId().toString(),
            searchable.getName(),
            searchable.getContentType()
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }
}
