package com.example.orderbot.Model;

import java.util.List;

public class Card {
    private int id;
    private String name, category, image, about;
    private List<Item> items;

    public Card(int id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public Card(int id, String name, String about, String category, String image, List<Item> items) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.category = category;
        this.image = image;
        this.items = items;
    }

    public Card() {}

    public void setId(int id) { this.id = id;}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
