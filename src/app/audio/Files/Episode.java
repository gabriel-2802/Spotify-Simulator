package app.audio.Files;

import lombok.Getter;

@Getter
public final class Episode extends AudioFile {
    private final String description;

    public Episode(String name, Integer duration, String description, String owner) {
        super(name, duration);
        this.description = description;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return getName() + " - " + description;
    }
}
