package app.pagination;

import app.pagination.visitors.PageVisitor;
import app.user.User;
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

    /**
     * Accepts a visitor
     * @param visitor the visitor
     */
    @Override
    public void acceptVisitor(final PageVisitor visitor) {
        visitor.visit(this);
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
