package app.audio.Files;

import lombok.Getter;

/**
 * class that represents an episode
 */
@Getter
public final class Episode extends AudioFile {
    private final String description;

    public Episode(final String name,
                   final Integer duration, final String description, final String owner) {
        super(name, duration);
        this.description = description;
        this.owner = owner;
    }

    /**
     * overrides the toString method
     * @return the string representation of the episode
     */
    @Override
    public String toString() {
        return getName() + " - " + description;
    }
}
