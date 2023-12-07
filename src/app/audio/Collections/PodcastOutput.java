package app.audio.Collections;

import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;

/**
 * class used for outputting a podcast
 */
@Getter
public class PodcastOutput {
    private String name;
    private ArrayList<String> episodes;

    public PodcastOutput(final Podcast podcast) {
        this.name = podcast.getName();
        this.episodes = new ArrayList<>();
        for (Episode episode : podcast.getEpisodes()) {
            episodes.add(episode.getName());
        }
    }
}
