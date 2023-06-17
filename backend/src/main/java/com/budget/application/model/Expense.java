package com.budget.application.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expense_id")
    private Long expenseID;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "`value`")
    private Double value;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Tag.class)
    @JoinTable(name = "expenses_tags", joinColumns = {
            @JoinColumn(name = "expense_id", referencedColumnName = "expense_id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", nullable = false, updatable = false) })
    private List<Tag> tags;

    private String formattedDate;

    public Expense() {
    }

    public Long getExpenseID() {
        return this.expenseID;
    }

    public void setExpsenseID(Long expenseID) {
        this.expenseID = expenseID;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
}
