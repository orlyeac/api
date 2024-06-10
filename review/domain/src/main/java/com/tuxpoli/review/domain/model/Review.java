package com.tuxpoli.review.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Review {

    private Long id;

    private String opinion;

    private Author author;

    private Boolean publishable;

    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(
            Long id,
            String opinion,
            Long authorId,
            String authorName,
            String authorLabourLink,
            String authorCompany,
            Boolean publishable,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.opinion = opinion;
        this.author = new Author(authorId, authorName, authorLabourLink, authorCompany);
        this.publishable = publishable;
        this.createdAt = createdAt;
    }

    public Review(
            String opinion,
            Long authorId,
            String authorName,
            String authorLabourLink,
            String authorCompany,
            Boolean publishable,
            LocalDateTime createdAt
    ) {
        this.opinion = opinion;
        this.author = new Author(authorId, authorName, authorLabourLink, authorCompany);
        this.publishable = publishable;
        this.createdAt = createdAt;
    }

    public Review(
            Long id,
            String opinion,
            Author author,
            Boolean publishable,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.opinion = opinion;
        this.author = author;
        this.publishable = publishable;
        this.createdAt = createdAt;
    }

    public Review(
            String opinion,
            Author author,
            Boolean publishable,
            LocalDateTime createdAt
    ) {
        this.opinion = opinion;
        this.author = author;
        this.publishable = publishable;
        this.createdAt = createdAt;
    }

    public static Review create(
            String opinion,
            Author author,
            Boolean publishable,
            LocalDateTime createdAt
    ) {
        return new Review(opinion, author, publishable,  createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Boolean getPublishable() {
        return publishable;
    }

    public void setPublishable(Boolean publishable) {
        this.publishable = publishable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review that = (Review) o;
        return Objects.equals(id, that.id) && Objects.equals(opinion, that.opinion) && Objects.equals(author, that.author) && Objects.equals(publishable, that.publishable) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opinion, author, publishable, createdAt);
    }

    @Override
    public String toString() {
        return "ReviewJPAEntity{" +
                "id=" + id +
                ", opinion='" + opinion + '\'' +
                ", author=" + author +
                ", publishable=" + publishable +
                ", createdAt=" + createdAt +
                '}';
    }
}
