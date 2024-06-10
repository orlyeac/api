package com.tuxpoli.review.domain.model;

import java.util.Objects;

public class Author {

    private Long id;
    private String name;
    private String labourLink;
    private String company;

    public Author(Long id, String name, String labourLink, String company) {
        this.id = id;
        this.name = name;
        this.labourLink = labourLink;
        this.company = company;
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

    public String getLabourLink() {
        return labourLink;
    }

    public void setLabourLink(String labourLink) {
        this.labourLink = labourLink;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(labourLink, author.labourLink) && Objects.equals(company, author.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, labourLink, company);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", labourLink='" + labourLink + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
