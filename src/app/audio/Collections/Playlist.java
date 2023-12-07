package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

/**
 * The type Playlist.
 */
@Getter
public final class Playlist extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private int timestamp;

    /**
     * Instantiates a new Playlist.
     *
     * @param name  the name
     * @param owner the owner
     */
    public Playlist(final String name, final String owner) {
        this(name, owner, 0);
    }

    /**
     * Instantiates a new Playlist.
     *
     * @param name      the name
     * @param owner     the owner
     * @param timestamp the timestamp
     */
    public Playlist(final String name, final String owner, final int timestamp) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
        this.timestamp = timestamp;
    }

    /**
     * Contains song boolean.
     *
     * @param song the song
     * @return the boolean
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * Add song.
     *
     * @param song the song
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Remove song.
     *
     * @param song the song
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * Remove song.
     *
     * @param index the index
     */
    public void removeSong(final int index) {
        songs.remove(index);
    }

    /**
     * Switch visibility.
     */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    /**
     * Increase followers.
     */
    public void increaseFollowers() {
        followers++;
    }

    /**
     * Decrease followers.
     */
    public void decreaseFollowers() {
        followers--;
    }

    /**
     * Gets number of tracks.
     *
     * @return the number of tracks
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Gets track by index.
     *
     * @param index the index
     * @return the track by index
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * the method checks if the playlist is visible to a user
     * @param user the user
     * @return true if the playlist is visible to the user, false otherwise
     */
    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }

    /**
     * the method checks if the playlist has a given number of followers
     * @param followerNum the follower numbers
     * @return true if the playlist has the given number of followers, false otherwise
     */
    @Override
    public boolean matchesFollowers(final String followerNum) {
        return filterByFollowersCount(this.getFollowers(), followerNum);
    }

    /**
     * the method checks if the playlist has a given number of followers
     * @param count the follower numbers
     * @param query the query
     * @return true if the playlist has the given number of followers, false otherwise
     */
    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }

    /**
     * the method computes the total number of likes of the playlist
     * @return the total number of likes of the playlist
     */
        public int totalLikes() {
        int total = 0;
        for (Song song : songs) {
            total += song.getLikes();
        }
        return total;
    }

    /**
     * overridden the toString method
     * @return the string representation of the playlist
     */
    @Override
    public String toString() {
        return getName() + " - " + getOwner();
    }

    /**
     * the method removes all the songs by a given artist
     * @param artist the artist
     **/
    public void removeSongsByArtist(final String artist) {
        songs.removeIf(song -> song.getArtist().equals(artist));
    }

    /**
     * the method removes all the songs from a given album
     * @param album the album name
     **/
    public void removeSongsByAlbum(final String album) {
        songs.removeIf(song -> song.getAlbum().equals(album));
    }

    /**
     * the method checks if the playlist contains a song by a given artist
     * @param artist the artist
     * @return true if the playlist contains a song by the artist, false otherwise
     */
    @Override
    public boolean containsMediaByCreator(final String artist) {
        for (Song song : songs) {
            if (song.getArtist().equals(artist)) {
                return true;
            }
        }
        return false;
    }

}

