package com.gregIturnquist.hackingspringboot.reactive;

import org.springframework.data.annotation.Id;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class Item {

    private @Id
    String id;
    private String name;
    private String Description;
    private double price;

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 && Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(Description, item.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, Description);
    }

    public Item(String name, String description, double price) {
        this.name = name;
        this.price = price;
        Description = description;
    }

    public Item(String id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        Description = description;
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
