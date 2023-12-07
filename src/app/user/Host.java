package app.user;

import app.Admin;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.Podcast;
import app.audio.Collections.PodcastOutput;
import app.audio.Files.Episode;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.utils.Announcement;
import app.utils.Enums;
import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Host extends User {
    private ArrayList<Podcast> podcasts;
    private ArrayList<Announcement> announcements;

    public Host(final String username, final int age, final String city,
                final Enums.UserType type) {
        super(username, age, city, type);
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    /**
     * adds a new song
     * @param filters the filters
     * @param type the type of the search
     * @return a message about the success of the operation
     */
    @Override
    public ArrayList<String> search(final Filters filters, final String type) {
        throw new UnsupportedOperationException("unsupported search");
    }

    /**
     * selects media
     * @param itemNumber the number of the item
     */
    @Override
    public String select(final int itemNumber) {
        throw new UnsupportedOperationException("unsupported select");
    }

    /**
     * loads the selected audio file
     * @return a message about the success of the operation
     */
    @Override
    public String load() {
        throw new UnsupportedOperationException("unsupported load");
    }

    /**
     * plays pause on the audio file
     * @return a message about the success of the operation
     */
    @Override
    public String playPause() {
        throw new UnsupportedOperationException("unsupported playPause");
    }

    /**
     * repeats the media
     * @return a message about the success of the operation
     */
    @Override
    public String repeat() {
        throw new UnsupportedOperationException("unsupported repeat");
    }

    /**
     * shuffles the media
     * @param seed the seed for the shuffle
     * @return a message about the success of the operation
     */
    @Override
    public String shuffle(final Integer seed) {
        throw new UnsupportedOperationException("unsupported shuffle");
    }

    /**
     * forwards the playback
     * @return a message about the success of the operation
     */
    @Override
    public String forward() {
        throw new UnsupportedOperationException("unsupported forward");
    }

    /**
     * rewinds the playback
     * @return a message about the success of the operation
     */
    @Override
    public String backward() {
        throw new UnsupportedOperationException("unsupported backward");
    }

    /**
     * likes the current song
     * @return a message about the success of the operation
     */
    @Override
    public String like() {
        throw new UnsupportedOperationException("unsupported like");
    }

    /**
     * moves to next song
     * @return a message about the success of the operation
     */
    @Override
    public String next() {
        throw new UnsupportedOperationException("unsupported next");
    }

    /**
     * moves to previous song
     * @return a message about the success of the operation
     */
    @Override
    public String prev() {
        throw new UnsupportedOperationException("unsupported prev");
    }

    /**
     * creates a playlist
     * @param name the name of the playlist
     * @param timestamp the timestamp of the playlist
     * @return a message about the success of the operation
     */
    @Override
    public String createPlaylist(final String name, final int timestamp) {
        throw new UnsupportedOperationException("unsupported createPlaylist");
    }

    /**
     * add or remove a song from a playlist
     * @param id the id of the playlist
     * @return a message about the success of the operation
     */
    @Override
    public String addRemoveInPlaylist(final int id) {
        throw new UnsupportedOperationException("unsupported addRemoveInPlaylist");
    }

    /**
     * switches the visibility of a playlist
     * @param playlistId the id of the playlist
     * @return a message about the success of the operation
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        throw new UnsupportedOperationException("unsupported switchPlaylistVisibility");
    }

    /**
     * shows all playlists
     * @return a list of playlists
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        throw new UnsupportedOperationException("unsupported showPlaylists");
    }

    /**
     * follows the current playlist
     * @return a message about the success of the operation
     */
    @Override
    public String follow() {
        throw new UnsupportedOperationException("unsupported follow");
    }

    /**
     * returns the stats of the player
     * @return the stats of the player
     */
    @Override
    public PlayerStats getPlayerStats() {
        throw new UnsupportedOperationException("unsupported getPlayerStats");
    }

    /**
     * shows all liked songs
     * @return a list of liked songs
     */
    @Override
    public ArrayList<String> showPreferredSongs() {
        throw new UnsupportedOperationException("unsupported showPreferredSongs");
    }

    /**
     * shows the preffered genre of the user
     * @return the preferred genre
     */
    @Override
    public String getPreferredGenre() {
        throw new UnsupportedOperationException("unsupported getPreferredGenre");
    }

    /**
     * deletes the data of the host from the platform
     */
    @Override
    public void deleteData() {
        super.deleteData();
        podcasts.clear();
        Admin.removeHostData(getUsername());
    }

    /**
     * add a new podcast
     * @param name the name of the podcast
     * @param owner the owner of the podcast
     * @param episodeInputs the episodes of the podcast
     * @return
     */
    @Override
    public String addPodcast(final String name, final String owner,
                             final List<EpisodeInput> episodeInputs) {
        List<Episode> episodes = new ArrayList<>();
        List<String> episodeNames = new ArrayList<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription(), owner));
            episodeNames.add(episodeInput.getName());
        }

        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return getUsername() + " has another podcast with the same name.";
            }
        }

        for (String episodeName : episodeNames) {
            int count = Collections.frequency(episodeNames, episodeName);
            if (count > 1) {
                return getUsername() + " has the same episode in this podcast.";
            }
        }

        Podcast newPodcast = new Podcast(name, owner, episodes);
        podcasts.add(newPodcast);
        Admin.addPodcast(newPodcast);
        return getUsername() + " has added new podcast successfully.";
    }

    /**
     * adds a new announcement
     * @param name the name of the announcement
     * @param message the message of the announcement
     * @return a message about the success of the operation
     */
    @Override
    public String addAnnouncement(final String name, final String message) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return getUsername() + " has already added an announcement with this name.";
            }
        }

        Announcement newAnnouncement = new Announcement(name, message);
        announcements.add(newAnnouncement);
        return getUsername() + " has successfully added new announcement.";
    }

    /**
     * removes an announcement
     * @param name the name of the announcement
     * @return a message about the success of the operation
     */
    @Override
    public String removeAnnouncement(final String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                announcements.remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    /**
     * shows all podcasts of the host
     * @return a list of podcasts
     */
    @Override
    public ArrayList<PodcastOutput> showPodcasts() {
        ArrayList<PodcastOutput> podcastOutputs = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }
        return podcastOutputs;
    }

    /**
     * removes a podcast
     * @param name the name of the podcast
     * @return a message about the success of the operation
     */
    @Override
    public String removePodcast(final String name) {
        Podcast podcastToRemove = null;
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                podcastToRemove = podcast;
                break;
            }
        }

        if (podcastToRemove == null) {
            return getUsername() + " doesn't have a podcast with the given name.";
        }

        return Admin.removePodcast(this, podcastToRemove);
    }

    /**
     * changes the page of the host
     * @param pageName the name of the page
     * @return a message about the success of the operation
     */
    @Override
    public String changePage(final String pageName) {
        throw new UnsupportedOperationException("Host cannot change page.");
    }
}
