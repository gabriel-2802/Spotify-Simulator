package app.pagination;

import app.audio.Collections.Album;
import app.user.Artist;
import app.user.User;
import app.user.utils.Event;
import app.user.utils.Merch;

import java.util.ArrayList;

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
    public void updatePage() {
        clearPage();

        for (Merch merch : ((Artist)artist).getMerch()) {
            this.merch.add(merch.toString());
        }

        for (Album album : ((Artist)artist).getAlbums()) {
            albums.add(album.getName());
        }

        for (Event event : ((Artist)artist).getEvents()) {
            events.add(event.toString());
        }
    }

    @Override
    public String toString() {
        return "Albums:\n\t[" + String.join(", ", albums) + "]\n\n" +
                "Merch:\n\t[" + String.join(", ", merch) + "]\n\n" +
                "Events:\n\t[" + String.join(", ", events) + "]";
    }
}
