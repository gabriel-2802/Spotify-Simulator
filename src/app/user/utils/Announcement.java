package app.user.utils;

import lombok.Getter;
/**
 * Announcement class
 */
@Getter
public class Announcement {
    private final String name;
    private final String message;

    public Announcement(final String name, final String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * Returns a string representation of the announcement
     * @return a string representation of the announcement
     */
    @Override
    public String toString() {
        return name + ":\n\t" + message + "\n";
    }
}
