package app.audio.Collections;

import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

public class Album extends AudioCollection {
    private int releaseYear;
    private String description;
    @Getter
    private ArrayList<Song> songs;
    public Album(String name, String owner, int releaseYear, String description, ArrayList<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public Song getTrackByIndex(int index) {
        return songs.get(index);
    }

    @Override
    public boolean matchesDescription(String description) {
        return this.description.toLowerCase().startsWith(description.toLowerCase());
    }
}
