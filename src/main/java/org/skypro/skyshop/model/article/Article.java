package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.Searchable;
import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Article title cannot be null or blank");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Article content cannot be null or blank");
        }
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // ... остальные методы ...
}
