package app.audio.Collections;

import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

/**
 * class used for outputting an album
 */
@Getter
public class AlbumOutput {
    private final String name;
    private final ArrayList<String> songs;
    public AlbumOutput(final Album album) {
        this.name = album.getName();
        this.songs = new ArrayList<>();

        for (Song song : album.getSongs()) {
            songs.add(song.getName());
        }
    }
}
