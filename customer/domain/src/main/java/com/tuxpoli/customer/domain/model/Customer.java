package com.tuxpoli.customer.domain.model;

import com.tuxpoli.common.domain.LabourLink;
import com.tuxpoli.common.domain.exception.WithoutChangeException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Customer {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LabourLink labourLink;
    private String company;
    private Boolean active;
    private String authority;
    private LocalDateTime createdAt;

    public Customer(
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

    public Customer(
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

    public String getPassword() {
        return password;
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

    public static Customer create(
            String name,
            String email,
            String password,
            LabourLink labourLink,
            String company,
            Boolean active,
            String authority,
            LocalDateTime createdAt
    ) {
        return new Customer(
                name,
                email,
                password,
                labourLink,
                company,
                active,
                authority,
                createdAt
        );
    }

    public Customer update(
            String name,
            String email,
            LabourLink labourLink,
            String company
    ) {
        boolean modified = false;
        if (
                name != null &&
                        !name.equals(this.getName())
        ) {
            this.setName(name);
            modified = true;
        }
        if (
                email != null &&
                        !email.equals(this.getEmail())
        ) {
            this.setEmail(email);
            modified = true;
        }
        if (
                labourLink != null &&
                        !labourLink.equals(this.getLabourLink())
        ) {
            this.setLabourLink(labourLink);
            modified = true;
        }
        if (
                company != null &&
                        !company.equals(this.getCompany())
        ) {
            this.setCompany(company);
            modified = true;
        }
        if (!modified) {
            throw new WithoutChangeException(
                    "update without change attempted"
            );
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && labourLink == customer.labourLink && Objects.equals(company, customer.company) && Objects.equals(active, customer.active) && Objects.equals(authority, customer.authority) && Objects.equals(createdAt, customer.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, labourLink, company, active, authority, createdAt);
    }

    @Override
    public String toString() {
        return "Customer{" +
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
}
