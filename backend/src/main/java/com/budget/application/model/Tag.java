package com.budget.application.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long tagID;

    @Column(name = "name", unique = true, length = 30)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Expense> expenses = new ArrayList<Expense>();

    public Tag(String name) {
        super();
        this.name = name;
    }

    public Tag() {
    }

    public Long getTagID() {
        return this.tagID;
    }

    public void setTagID(Long tagID) {
        this.tagID = tagID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Expense> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
