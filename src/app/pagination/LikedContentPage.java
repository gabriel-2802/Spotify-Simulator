package app.pagination;

import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;

public class LikedContentPage extends Page {
        private final User user;
        private ArrayList<String> songs;
        private ArrayList<String> playlists;

        public LikedContentPage(User user) {
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
                for (Song song : user.getLikedSongs()) {
                        songs.add(song.getName());
                }

                for (app.audio.Collections.Playlist playlist : user.getFollowedPlaylists()) {
                        playlists.add(playlist.getName());
                }
        }

        @Override
        public String toString() {
                return "Liked songs:\n\t[" + String.join(", ", songs) + "]\n\n" +
                        "Followed playlists:\n\t[" + String.join(", ", playlists) + "]";
        }
}
