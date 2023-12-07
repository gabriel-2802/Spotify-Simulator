package app.audio.Collections;

import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

/** Class that represents an album. *
 * An album is a collection of songs.
 **/
public class Album extends AudioCollection {
    private int releaseYear;
    private String description;
    @Getter
    private ArrayList<Song> songs;
    public Album(final String name, final String owner, final int releaseYear,
                 final String description, final ArrayList<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    /**
     * the method computes the number of songs
     * @return the number of songs
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * the method returns the song at a given index
     * @param index the index of the song
     * @return the song at the given index
     */
    @Override
    public Song getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * checks if the album matches the description
     * @param givenDescription the description to be checked
     */
    @Override
    public boolean matchesDescription(final String givenDescription) {
        return this.description.toLowerCase().startsWith(givenDescription.toLowerCase());
    }

    /**
     * the function computes the number of likes of the album
     * @return the number of likes of the album
     */
    public int likes() {
        int likes = 0;
        for (Song song : songs) {
            likes += song.getLikes();
        }
        return likes;
    }

    /**
     * the function checks if the album contains a song by an artist
     * @param name the creator
     * @return true if the album contains a song by the artist
     */
    @Override
    public boolean containsMediaByCreator(final String name) {
        if (songs.get(0).getArtist().equals(name)) {
            return true;
        }
        return false;
    }

}
