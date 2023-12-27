package app.pagination;

import app.audio.Files.Song;
import app.pagination.visitors.PageVisitor;
import app.user.User;
import lombok.Getter;
import java.util.ArrayList;

@Getter
public class LikedContentPage extends Page {
        private final User user;
        private ArrayList<String> songs;
        private ArrayList<String> playlists;

        public LikedContentPage(final User user) {
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
                for (Song song : getUser().getLikedSongs()) {
                        getSongs().add(song.toString());
                }

                for (app.audio.Collections.Playlist playlist : getUser().getFollowedPlaylists()) {
                        getPlaylists().add(playlist.toString());
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
