package app.user.utils;

import lombok.Getter;

@Getter
public class Announcement {
    private final String name;
    private final String message;

    public Announcement(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public String toString() {
        return name + ":\n\t" + message + "\n";
    }
}
