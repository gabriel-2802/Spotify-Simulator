package app.pagination;

import app.audio.Collections.Album;
import app.pagination.visitors.PageVisitor;
import app.user.Artist;
import app.user.User;
import app.user.utils.Event;
import app.user.utils.Merch;
import lombok.Getter;
import java.util.ArrayList;

/**
 * Page for an artist
 */
@Getter
public class ArtistPage extends Page {
    private final User artist;
    private ArrayList<String> albums;
    private ArrayList<String> merch;
    private ArrayList<String> events;

    public ArtistPage(final User artist) {
        this.artist = artist;
        owner = artist.getUsername();
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();
    }

    /**
     * Clears the page
     */
    @Override
    public void clearPage() {
        albums.clear();
        merch.clear();
        events.clear();
    }
    
    @Override
    public void updatePage() {
        clearPage();

        for (Merch merch : ((Artist) getArtist()).getMerch()) {
            getMerch().add(merch.toString());
        }

        for (Album album : ((Artist) getArtist()).getAlbums()) {
            getAlbums().add(album.getName());
        }

        for (Event event : ((Artist) getArtist()).getEvents()) {
            getEvents().add(event.toString());
        }
    }

    /**
     * Returns a string representation of the page
     * @return a string representation of the page
     */
    @Override
    public String toString() {
        return "Albums:\n\t[" + String.join(", ", albums) + "]\n\n"
                + "Merch:\n\t[" + String.join(", ", merch) + "]\n\n"
                + "Events:\n\t[" + String.join(", ", events) + "]";
    }
}
