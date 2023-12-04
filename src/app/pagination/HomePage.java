package app.pagination;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends Page{
    private final User user;
    ArrayList <String> songs;
    ArrayList <String>  playlists;


    public HomePage(User user) {
        this.owner = user.getUsername();
        this.user = user;
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        owner = user.getUsername();
    }

    @Override
    public void clearPage() {
        songs.clear();
        playlists.clear();
    }
    @Override
    public void updatePage() {
        clearPage();
        List<Song> likedSongs = user.getLikedSongs();

        if (likedSongs != null) {
            songs.addAll(likedSongs.stream()
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .limit(5)
                    .map(Song::getName)
                    .toList());
        }

        // Update playlists
        List<Playlist> followedPlaylists = user.getFollowedPlaylists();
        if (followedPlaylists != null) {
            playlists.addAll(followedPlaylists.stream()
                    .sorted(Comparator.comparingInt(Playlist::totalLikes).reversed())
                    .limit(5)
                    .map(Playlist::getName)
                    .toList());
        }
    }

    @Override
    public String toString() {
        return "Liked songs:\n\t[" + String.join(", ", songs) + "]\n\n" +
                "Followed playlists:\n\t[" + String.join(", ", playlists) + "]";
    }
}
