package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
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
public class Artist extends Creator {
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Merch> merch;
    @Getter
    private ArrayList<Event> events;

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        type = Enums.UserType.ARTIST;
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
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
        Admin admin = Admin.getInstance();
        admin.addAlbum(newAlbum);
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
     * @param merchh the merchandise
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
        Admin.removeUserData(this);
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

        Admin admin = Admin.getInstance();
        return admin.removeAlbum(this, albumToRemove);
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
