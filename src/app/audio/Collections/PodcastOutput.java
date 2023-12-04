package app.audio.Collections;

import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class PodcastOutput {
    private String name;
    private ArrayList<String> episodes;

    public PodcastOutput(Podcast podcast) {
        this.name = podcast.getName();
        this.episodes = new ArrayList<>();
        for (Episode episode : podcast.getEpisodes()) {
            episodes.add(episode.getName());
        }
    }
}
