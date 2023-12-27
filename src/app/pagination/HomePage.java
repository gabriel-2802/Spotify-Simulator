package app.pagination;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pagination.visitors.PageVisitor;
import app.user.User;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class HomePage extends Page {
    private final User user;
    private ArrayList<String> songs;
    private ArrayList<String>  playlists;
    private static final int MAX_RESULTS = 5;

    public HomePage(final User user) {
        this.owner = user.getUsername();
        this.user = user;
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
        owner = user.getUsername();
    }

    /**
     * Clears the page
     */
    @Override
    public void clearPage() {
        songs.clear();
        playlists.clear();
    }
    
    @Override
    public void updatePage() {
        clearPage();
        List<Song> likedSongs = getUser().getLikedSongs();

        if (likedSongs != null) {
            getSongs().addAll(likedSongs.stream()
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .limit(MAX_RESULTS)
                    .map(Song::getName)
                    .toList());
        }

        List<Playlist> followedPlaylists = getUser().getFollowedPlaylists();
        if (followedPlaylists != null) {
            getPlaylists().addAll(followedPlaylists.stream()
                    .sorted(Comparator.comparingInt(Playlist::totalLikes).reversed())
                    .limit(MAX_RESULTS)
                    .map(Playlist::getName)
                    .toList());
        }
    }

    /**
     * Returns a string representation of the page
     * @return a string representation of the page
     */
    @Override
    public String toString() {
        return "Liked songs:\n\t[" + String.join(", ", songs) + "]\n\n"
                + "Followed playlists:\n\t[" + String.join(", ", playlists) + "]";
    }
}
