package model.entity;

import model.entity.enums.EditionTheme;

public class Edition {
    private Long id;
    private String name;
    private EditionTheme theme;
    private Double price;
    private String description;

    public Edition() {
        theme = null;
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

    public EditionTheme getTheme() {
        return theme;
    }

    public void setTheme(EditionTheme theme) {
        this.theme = theme;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
