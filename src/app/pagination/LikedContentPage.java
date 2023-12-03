package app.pagination;

import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;

public class LikedContentPage implements Page {
        private final User user;
        private ArrayList<String> songs;
        private ArrayList<String> playlists;

        public LikedContentPage(User user) {
                this.user = user;
                this.songs = new ArrayList<>();
                this.playlists = new ArrayList<>();
        }
        @Override
        public void updatePage() {
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
