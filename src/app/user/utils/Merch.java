package app.user.utils;

import lombok.Data;

@Data
public class Merch {
    private String name;
    private int price;
    private String description;

    public Merch(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String toString() {
        return name + " - " + price;
    }
}
