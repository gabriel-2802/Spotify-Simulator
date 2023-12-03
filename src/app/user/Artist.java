package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
import app.utils.Enums;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.Collections;

public class Artist extends User {
    private ArrayList<Album> albums;
    public Artist(String username, int age, String city, Enums.UserType type) {
        super(username, age, city, type);
        albums = new ArrayList<>();
    }

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
}
