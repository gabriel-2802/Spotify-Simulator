package app.user.utils;

import lombok.Data;

/**
 * Merch class
 */
@Data
public class Merch {
    private String name;
    private int price;
    private String description;

    public Merch(final String name, final int price, final String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /**
     * Returns a string representation of the merch
     * @return a string representation of the merch
     */
    @Override
    public String toString() {
        return name + " - " + price + ":\n\t" + description;
    }
}
