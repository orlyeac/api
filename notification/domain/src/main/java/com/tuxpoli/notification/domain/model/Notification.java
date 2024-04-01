package com.tuxpoli.notification.domain.model;

import com.tuxpoli.common.domain.NotificationKind;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {

    private Long id;
    private Long toId;
    private String toEmail;
    private String toName;
    private NotificationKind kind;
    private LocalDateTime createdAt;

    public Notification(
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

    public Notification(
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

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public NotificationKind getKind() {
        return kind;
    }

    public void setKind(NotificationKind kind) {
        this.kind = kind;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static Notification create(
            Long toId,
            String toEmail,
            String toName,
            NotificationKind kind,
            LocalDateTime createdAt
    ) {
        return new Notification(toId, toEmail, toName, kind, createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) && Objects.equals(toId, that.toId) && Objects.equals(toEmail, that.toEmail) && Objects.equals(toName, that.toName) && Objects.equals(kind, that.kind) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toId, toEmail, toName, kind, createdAt);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", toId=" + toId +
                ", toEmail='" + toEmail + '\'' +
                ", toName='" + toName + '\'' +
                ", kind=" + kind +
                ", createdAt=" + createdAt +
                '}';
    }

}
