package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.Searchable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(\"Article title cannot be null or blank\");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException(\"Article content cannot be null or blank\");
        }
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return toString();
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return \"ARTICLE\";
    }

    @Override
    public String toString() {
        return title + \"\n\" + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
