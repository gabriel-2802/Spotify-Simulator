package app.pagination;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pagination.visitors.Visitor;
import app.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Getter
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
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Liked songs:\n\t[" + String.join(", ", songs) + "]\n\n" +
                "Followed playlists:\n\t[" + String.join(", ", playlists) + "]";
    }
}
