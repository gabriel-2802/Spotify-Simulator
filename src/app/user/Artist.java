package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.utils.Event;
import app.user.utils.Merch;
import app.utils.Enums;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Artist class
 */
public class Artist extends User {
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Merch> merch;
    @Getter
    private ArrayList<Event> events;
    public Artist(final String username, final int age, final String city,
                  final Enums.UserType type) {
        super(username, age, city, type);
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * adds a new song
     * @param filters the filters
     * @param type the type of the search
     * @return a message about the success of the operation
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

    /**
     * Adds a new album
     * @param name the name of the album
     * @param releaseYear the release year of the album
     * @param description the description of the album
     * @param owner the owner of the album
     * @param songsInput the songs of the album
     * @return a message about the success of the operation
     */
    @Override
    public String addAlbum(final String name, final int releaseYear,
                           final String description, final String owner,
                           final ArrayList<SongInput> songsInput) {
        ArrayList<Song> songs = new ArrayList<>();
        ArrayList<String> songNames = new ArrayList<>();
        for (SongInput songInput : songsInput) {
            songs.add(new Song(songInput));
            songNames.add(songInput.getName());
        }

        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return getUsername() + " has another album with the same name.";
            }
        }

        for (String songName : songNames) {
            int count = Collections.frequency(songNames, songName);
            if (count > 1) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }

        Album newAlbum = new Album(name, owner, releaseYear, description, songs);
        albums.add(newAlbum);
        Admin.addAlbum(newAlbum);
        return getUsername() + " has added new album successfully.";
    }

    /**
     * shows all albums of the artist
     * @return a list of albums
     */
    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutputs = new ArrayList<>();
        for (Album album : albums) {
            albumOutputs.add(new AlbumOutput(album));
        }
        return albumOutputs;
    }

    /**
     * adds merchandise to the artist
     * @param merch the merchandise
     * @return a message about the success of the operation
     */
    @Override
    public String addMerch(final Merch merchh) {
        for (Merch product : this.merch) {
            if (product.getName().equals(merchh.getName())) {
                return getUsername()  + " has merchandise with the same name.";
            }
        }

        if (merchh.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }
        this.merch.add(merchh);
        return getUsername() + " has added new merchandise successfully.";
    }

    /**
     * adds an event to the artist
     * @param event the event
     * @return a message about the success of the operation
     */
    @Override
    public String addEvent(final Event event) {
        for (Event e : events) {
            if (e.getName().equals(event.getName())) {
                return getUsername() + " has another event with the same name.";
            }
        }

        if (!event.hasValidDate()) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }

        events.add(event);
        return getUsername() + " has added new event successfully.";
    }

    /**
     * deletes the data of the artist
     */
    @Override
    public void deleteData() {
        super.deleteData();
        Admin.removeArtistData(getUsername());
    }

    /**
     * removes an album from the artist
     * @param albumName the name of the album
     * @return a message about the success of the operation
     */
    @Override
    public String removeAlbum(final String albumName) {
        Album albumToRemove = null;
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                albumToRemove = album;
                break;
            }
        }

        if (albumToRemove == null) {
            return getUsername() + " doesn't have an album with the given name.";
        }

        return Admin.removeAlbum(this, albumToRemove);
    }

    /**
     * changes the page of the artist
     * @param pageName the name of the page
     * @return a message about the success of the operation
     */
    @Override
    public String changePage(final String pageName) {
        throw new UnsupportedOperationException("Artist cannot change page.");
    }

    /**
     * removes an event from the artist
     * @param name the name of the event
     * @return
     */
    @Override
    public String removeEvent(final String name) {
        Event eventToRemove = null;
        for (Event event : events) {
            if (event.getName().equals(name)) {
                eventToRemove = event;
                break;
            }
        }

        if (eventToRemove == null) {
            return getUsername() + " doesn't have an event with the given name.";
        }

        events.remove(eventToRemove);
        return getUsername() + " deleted the event successfully.";
    }

    /**
     * returns the number of likes from the albums of the artist
     * @return the number of likes
     */
    public int likes() {
        int likes = 0;
        for (Album album : albums) {
            likes += album.likes();
        }
        return likes;
    }
}
