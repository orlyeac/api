package com.tuxpoli.notification.infrastructure.persistence.jpa;

import com.tuxpoli.notification.domain.model.NotificationKind;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "notification"
)
public class NotificationJPAEntity {

    @Id
    @SequenceGenerator(
            name = "notification_id_seq",
            sequenceName = "notification_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_id_seq"
    )
    private Long id;

    @Column(
            name = "to_id",
            nullable = false
    )
    private Long toId;

    @Column(
            name = "to_email",
            nullable = false
    )
    private String toEmail;

    @Column(
            name = "to_name",
            nullable = false
    )
    private String toName;

    @Column(
            name = "kind",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private NotificationKind kind;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    public NotificationJPAEntity() {
    }

    public NotificationJPAEntity(
            Long id,
            Long toId,
            String toEmail,
            String toName,
            NotificationKind kind,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.toId = toId;
        this.toEmail = toEmail;
        this.toName = toName;
        this.kind = kind;
        this.createdAt = createdAt;
    }

    public NotificationJPAEntity(
            Long toId,
            String toEmail,
            String toName,
            NotificationKind kind,
            LocalDateTime createdAt
    ) {
        this.toId = toId;
        this.toEmail = toEmail;
        this.toName = toName;
        this.kind = kind;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public NotificationKind getKind() {
        return kind;
    }

    public void setKind(NotificationKind kind) {
        this.kind = kind;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
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
        NotificationJPAEntity that = (NotificationJPAEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(toId, that.toId) && Objects.equals(toEmail, that.toEmail) && Objects.equals(toName, that.toName) && Objects.equals(kind, that.kind) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toId, toEmail, toName, kind, createdAt);
    }

    @Override
    public String toString() {
        return "NotificationJPAEntity{" +
                "id=" + id +
                ", toId=" + toId +
                ", toEmail='" + toEmail + '\'' +
                ", toName='" + toName + '\'' +
                ", kind=" + kind +
                ", createdAt=" + createdAt +
                '}';
    }
}
