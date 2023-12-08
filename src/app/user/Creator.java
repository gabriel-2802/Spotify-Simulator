package app.user;

import app.audio.Collections.PlaylistOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.utils.Enums;

import java.util.ArrayList;

/**
 * Creator class
 * since normal user commands are not supported by hosts and artists, this class ensures
 * that the commands are not executed by the inheritor classes
 */
public abstract class Creator extends User {
    public Creator() {
        super();
    }

    public Creator(final String username, final int age, final String city,
                   final Enums.UserType type) {
        super(username, age, city, type);
    }

    /**
     * searches for the given filters and type
     * @param filters the filters
     * @param type the type of the search
     * @return list of results as strings
     */
    @Override
    public ArrayList<String> search(final Filters filters, final String type) {
        throw new UnsupportedOperationException("unsupported search");
    }

    /**
     * selects media
     * @param itemNumber the number of the item
     */
    @Override
    public String select(final int itemNumber) {
        throw new UnsupportedOperationException("unsupported select");
    }

    /**
     * loads the selected audio file
     * @return a message about the success of the operation
     */
    @Override
    public String load() {
        throw new UnsupportedOperationException("unsupported load");
    }

    /**
     * plays pause on the audio file
     * @return a message about the success of the operation
     */
    @Override
    public String playPause() {
        throw new UnsupportedOperationException("unsupported playPause");
    }

    /**
     * repeats the media
     * @return a message about the success of the operation
     */
    @Override
    public String repeat() {
        throw new UnsupportedOperationException("unsupported repeat");
    }

    /**
     * shuffles the media
     * @param seed the seed for the shuffle
     * @return a message about the success of the operation
     */
    @Override
    public String shuffle(final Integer seed) {
        throw new UnsupportedOperationException("unsupported shuffle");
    }

    /**
     * forwards the playback
     * @return a message about the success of the operation
     */
    @Override
    public String forward() {
        throw new UnsupportedOperationException("unsupported forward");
    }

    /**
     * rewinds the playback
     * @return a message about the success of the operation
     */
    @Override
    public String backward() {
        throw new UnsupportedOperationException("unsupported backward");
    }

    /**
     * likes the current song
     * @return a message about the success of the operation
     */
    @Override
    public String like() {
        throw new UnsupportedOperationException("unsupported like");
    }

    /**
     * moves to next song
     * @return a message about the success of the operation
     */
    @Override
    public String next() {
        throw new UnsupportedOperationException("unsupported next");
    }

    /**
     * moves to previous song
     * @return a message about the success of the operation
     */
    @Override
    public String prev() {
        throw new UnsupportedOperationException("unsupported prev");
    }

    /**
     * creates a playlist
     * @param name the name of the playlist
     * @param timestamp the timestamp of the playlist
     * @return a message about the success of the operation
     */
    @Override
    public String createPlaylist(final String name, final int timestamp) {
        throw new UnsupportedOperationException("unsupported createPlaylist");
    }

    /**
     * add or remove a song from a playlist
     * @param id the id of the playlist
     * @return a message about the success of the operation
     */
    @Override
    public String addRemoveInPlaylist(final int id) {
        throw new UnsupportedOperationException("unsupported addRemoveInPlaylist");
    }

    /**
     * switches the visibility of a playlist
     * @param playlistId the id of the playlist
     * @return a message about the success of the operation
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        throw new UnsupportedOperationException("unsupported switchPlaylistVisibility");
    }

    /**
     * shows all playlists
     * @return a list of playlists
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        throw new UnsupportedOperationException("unsupported showPlaylists");
    }

    /**
     * follows the current playlist
     * @return a message about the success of the operation
     */
    @Override
    public String follow() {
        throw new UnsupportedOperationException("unsupported follow");
    }

    /**
     * returns the stats of the player
     * @return the stats of the player
     */
    @Override
    public PlayerStats getPlayerStats() {
        throw new UnsupportedOperationException("unsupported getPlayerStats");
    }

    /**
     * shows all liked songs
     * @return a list of liked songs
     */
    @Override
    public ArrayList<String> showPreferredSongs() {
        throw new UnsupportedOperationException("unsupported showPreferredSongs");
    }

    /**
     * shows the preffered genre of the user
     * @return the preferred genre
     */
    @Override
    public String getPreferredGenre() {
        throw new UnsupportedOperationException("unsupported getPreferredGenre");
    }
}
