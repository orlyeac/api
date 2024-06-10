package com.tuxpoli.customer.infrastructure.persistence.jpa;

import com.tuxpoli.common.domain.LabourLink;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
public class CustomerJPAEntity implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "labour_link",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private LabourLink labourLink;

    @Column(
            name = "company",
            nullable = true
    )
    private String company;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active;

    @Column(
            name = "authority",
            nullable = false
    )
    private String authority;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    public CustomerJPAEntity() {

    }

    public CustomerJPAEntity(
            Long id,
            String name,
            String email,
            String password,
            LabourLink labourLink,
            String company,
            Boolean active,
            String authority,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.labourLink = labourLink;
        this.company = company;
        this.active = active;
        this.authority = authority;
        this.createdAt = createdAt;
    }

    public CustomerJPAEntity(
            String name,
            String email,
            String password,
            LabourLink labourLink,
            String company,
            Boolean active,
            String authority,
            LocalDateTime createdAt
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.labourLink = labourLink;
        this.company = company;
        this.active = active;
        this.authority = authority;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LabourLink getLabourLink() {
        return labourLink;
    }

    public void setLabourLink(LabourLink labourLink) {
        this.labourLink = labourLink;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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
        CustomerJPAEntity that = (CustomerJPAEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && labourLink == that.labourLink && Objects.equals(company, that.company) && Objects.equals(active, that.active) && Objects.equals(authority, that.authority) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, labourLink, company, active, authority, createdAt);
    }

    @Override
    public String toString() {
        return "CustomerJPAEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", labourLink=" + labourLink +
                ", company='" + company + '\'' +
                ", active=" + active +
                ", authority='" + authority + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
