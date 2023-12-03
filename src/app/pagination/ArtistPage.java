package app.pagination;

import app.user.User;

public class ArtistPage implements Page {
    private final User artist;

    public ArtistPage(User artist) {
        this.artist = artist;
    }

    @Override
    public void updatePage() {

    }
}
