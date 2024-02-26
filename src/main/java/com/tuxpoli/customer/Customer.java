package com.tuxpoli.customer;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Customer implements UserDetails {

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
            nullable = false
    )
    private String name;

    @Column(
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
            name = "authority",
            nullable = false
    )
    private String authority;

    public Customer() {

    }

    public Customer(Long id, String name, String email, String password, LabourLink labourLink, String company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.labourLink = labourLink;
        this.company = company;
        this.authority = "ROLE_USER";
    }

    public Customer(String name, String email, String password, LabourLink labourLink, String company) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.labourLink = labourLink;
        this.company = company;
        this.authority = "ROLE_USER";
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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && labourLink == customer.labourLink && Objects.equals(company, customer.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, labourLink, company);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", labourLink=" + labourLink +
                ", company='" + company + '\'' +
                ", authority='" + authority + '\'' +
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
