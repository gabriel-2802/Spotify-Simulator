package app.player;

import lombok.Getter;

@Getter
public class PodcastBookmark {
    private final String name;
    private final String owner;
    private final int id;
    private final int timestamp;

    public PodcastBookmark(final String name, final int id, final int timestamp, final String owner) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "PodcastBookmark{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }
}
