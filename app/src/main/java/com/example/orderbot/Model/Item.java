package com.example.orderbot.Model;

public class Item {
    private int id, quantity, stock, price, cardID, categoryID;

    public Item(int id, int quantity, int stock, int price, int cardID, int categoryID) {
        this.id = id;
        this.quantity = quantity;
        this.stock = stock;
        this.price = price;
        this.cardID = cardID;
        this.categoryID = categoryID;
    }

    public Item(int id, int quantity, int stock, int price, int cardID, String image_link) {
        this.id = id;
        this.quantity = quantity;
        this.stock = stock;
        this.price = price;
        this.cardID = cardID;
    }

    public Item(int id, int quantity, int stock, int price, int cardID_link) {
        this.id = id;
        this.quantity = quantity;
        this.stock = stock;
        this.price = price;
        this.cardID = cardID;
    }

    public Item() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
