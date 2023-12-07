package app.pagination.visitors;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Song;
import app.pagination.ArtistPage;
import app.pagination.HomePage;
import app.pagination.HostPage;
import app.pagination.LikedContentPage;
import app.user.Artist;
import app.user.Host;
import app.user.utils.Announcement;
import app.user.utils.Event;
import app.user.utils.Merch;

import java.util.Comparator;
import java.util.List;

/**
 * Visitor that updates the page
 */
public class UpdateVisitor implements PageVisitor {
    private static final int MAX_RESULTS = 5;
    /**
     * Updates the page
     * @param artistPage the page to be updated
     */
    @Override
    public void visit(final ArtistPage artistPage) {
        artistPage.clearPage();

        for (Merch merch : ((Artist) artistPage.getArtist()).getMerch()) {
            artistPage.getMerch().add(merch.toString());
        }

        for (Album album : ((Artist) artistPage.getArtist()).getAlbums()) {
            artistPage.getAlbums().add(album.getName());
        }

        for (Event event : ((Artist) artistPage.getArtist()).getEvents()) {
            artistPage.getEvents().add(event.toString());
        }
    }

    /**
     * Updates the page
     * @param likePage the page to be updated
     */

    @Override
    public void visit(final LikedContentPage likePage) {
        likePage.clearPage();
        for (Song song : likePage.getUser().getLikedSongs()) {
            likePage.getSongs().add(song.toString());
        }

        for (app.audio.Collections.Playlist playlist : likePage.getUser().getFollowedPlaylists()) {
            likePage.getPlaylists().add(playlist.toString());
        }
    }

    /**
     * Updates the page
     * @param homePage the page to be updated
     */
    @Override
    public void visit(final HomePage homePage) {
        homePage.clearPage();
        List<Song> likedSongs = homePage.getUser().getLikedSongs();

        if (likedSongs != null) {
            homePage.getSongs().addAll(likedSongs.stream()
                    .sorted(Comparator.comparingInt(Song::getLikes).reversed())
                    .limit(MAX_RESULTS)
                    .map(Song::getName)
                    .toList());
        }

        List<Playlist> followedPlaylists = homePage.getUser().getFollowedPlaylists();
        if (followedPlaylists != null) {
            homePage.getPlaylists().addAll(followedPlaylists.stream()
                    .sorted(Comparator.comparingInt(Playlist::totalLikes).reversed())
                    .limit(MAX_RESULTS)
                    .map(Playlist::getName)
                    .toList());
        }

    }

    /**
     * Updates the page
     * @param hostPage the page to be updated
     */
    @Override
    public void visit(final HostPage hostPage) {
        hostPage.clearPage();

        for (Podcast podcast: ((Host) hostPage.getHost()).getPodcasts()) {
            hostPage.getPodcasts().add(podcast.toString());
        }

        for (Announcement announcement: ((Host) hostPage.getHost()).getAnnouncements()) {
            hostPage.getAnnouncements().add(announcement.toString());
        }
    }
}
