package app.pagination;

import app.audio.Collections.Album;
import app.pagination.visitors.Visitor;
import app.user.Artist;
import app.user.User;
import app.user.utils.Event;
import app.user.utils.Merch;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public class ArtistPage extends Page {
    private final User artist;
    private ArrayList<String> albums;
    private ArrayList<String> merch;
    private ArrayList<String> events;

    public ArtistPage(User artist) {
        this.artist = artist;
        owner = artist.getUsername();
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
    }

    @Override
    public void clearPage() {
        albums.clear();
        merch.clear();
        events.clear();
    }
    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Albums:\n\t[" + String.join(", ", albums) + "]\n\n" +
                "Merch:\n\t[" + String.join(", ", merch) + "]\n\n" +
                "Events:\n\t[" + String.join(", ", events) + "]";
    }
}
