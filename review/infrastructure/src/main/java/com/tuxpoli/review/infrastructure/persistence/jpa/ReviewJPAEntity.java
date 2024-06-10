package com.tuxpoli.review.infrastructure.persistence.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "review"
)
public class ReviewJPAEntity {

    @Id
    @SequenceGenerator(
            name = "review_id_seq",
            sequenceName = "review_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_id_seq"
    )
    private Long id;

    @Column(
            name = "opinion",
            nullable = false
    )
    private String opinion;

    @Column(
            name = "author_id",
            nullable = false
    )
    private Long authorId;

    @Column(
            name = "author_name",
            nullable = false
    )
    private String authorName;

    @Column(
            name = "author_labour_link",
            nullable = false
    )
    private String authorLabourLink;

    @Column(
            name = "author_company",
            nullable = true
    )
    private String authorCompany;

    @Column(
            name = "publishable",
            nullable = false
    )
    private Boolean publishable;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    public ReviewJPAEntity() {
    }

    public ReviewJPAEntity(
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
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorLabourLink = authorLabourLink;
        this.authorCompany = authorCompany;
        this.publishable = publishable;
        this.createdAt = createdAt;
    }

    public ReviewJPAEntity(
            String opinion,
            Long authorId,
            String authorName,
            String authorLabourLink,
            String authorCompany,
            Boolean publishable,
            LocalDateTime createdAt
    ) {
        this.opinion = opinion;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorLabourLink = authorLabourLink;
        this.authorCompany = authorCompany;
        this.publishable = publishable;
        this.createdAt = createdAt;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLabourLink() {
        return authorLabourLink;
    }

    public void setAuthorLabourLink(String authorLabourLink) {
        this.authorLabourLink = authorLabourLink;
    }

    public String getAuthorCompany() {
        return authorCompany;
    }

    public void setAuthorCompany(String authorCompany) {
        this.authorCompany = authorCompany;
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
        ReviewJPAEntity that = (ReviewJPAEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(opinion, that.opinion) && Objects.equals(authorId, that.authorId) && Objects.equals(authorName, that.authorName) && authorLabourLink == that.authorLabourLink && Objects.equals(authorCompany, that.authorCompany) && Objects.equals(publishable, that.publishable) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opinion, authorId, authorName, authorLabourLink, authorCompany, publishable, createdAt);
    }

    @Override
    public String toString() {
        return "ReviewJPAEntity{" +
                "id=" + id +
                ", opinion='" + opinion + '\'' +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", authorLabourLink=" + authorLabourLink +
                ", authorCompany='" + authorCompany + '\'' +
                ", publishable=" + publishable +
                ", createdAt=" + createdAt +
                '}';
    }
}
