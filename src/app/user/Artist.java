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

public class Artist extends User {
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Merch> merch;
    @Getter
    private ArrayList<Event> events;
    public Artist(String username, int age, String city, Enums.UserType type) {
        super(username, age, city, type);
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
    }

    @Override
    public String addAlbum(String name, int releaseYear, String description, String owner, ArrayList<SongInput> songsInput) {
        ArrayList<Song> songs = new ArrayList<>();
        ArrayList <String> songNames = new ArrayList<>();
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

    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutputs = new ArrayList<>();
        for (Album album : albums) {
            albumOutputs.add(new AlbumOutput(album));
        }
        return albumOutputs;
    }

    @Override
    public String addMerch(Merch merch) {
        for (Merch product : this.merch) {
            if (product.getName().equals(merch.getName())) {
                return getUsername()  + " has merchandise with the same name.";
            }
        }

        if (merch.getPrice() < 0 ) {
            return "Price for merchandise can not be negative.";
        }
        this.merch.add(merch);
        return getUsername() + " has added new merchandise successfully.";
    }

    @Override
    public String addEvent(Event event) {
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

    @Override
    public void deleteData() {
        super.deleteData();
        Admin.removeArtistData(getUsername());
    }

    @Override
    public String removeAlbum(String albumName) {
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

    @Override
    public String changePage(String pagename) {
        throw new UnsupportedOperationException("Artist cannot change page.");
    }

    @Override
    public String removeEvent(String name) {
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

    public int likes() {
        int likes = 0;
        for (Album album : albums) {
            likes += album.likes();
        }
        return likes;
    }
}
