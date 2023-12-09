package app.user;

import app.Admin;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.PodcastOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pagination.ArtistPage;
import app.pagination.HomePage;
import app.pagination.HostPage;
import app.pagination.LikedContentPage;
import app.pagination.Page;
import app.pagination.visitors.UpdateVisitor;
import app.pagination.visitors.PageVisitor;
import app.player.Player;
import app.player.PlayerStats;
import app.player.PodcastBookmark;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.user.utils.Event;
import app.user.utils.Merch;
import app.utils.Enums;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Getter
    private final String username;
    @Getter
    private final int age;
    @Getter
    private final String city;
    @Getter
    private final ArrayList<Playlist> playlists;
    @Getter
    @Setter
    private ArrayList<Song> likedSongs;
    @Getter
    private final ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Getter
    private Enums.Connection connectionStatus;
    @Getter
    protected Enums.UserType type;
    @Getter
    @Setter
    private Page page; // the page the user is currently on

    public User(final String username, final int age, final
    String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        connectionStatus = Enums.Connection.ONLINE;
        type = Enums.UserType.USER;
        page = new HomePage(this);
    }

    /**
     * searches for the given filters and type
     * @param filters the filters
     * @param typee the type of the search
     * @return list of results as strings
     */
    public ArrayList<String> search(final Filters filters, final String typee) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;

        ArrayList<String> results = new ArrayList<>();

        switch (typee) {
            case "song", "album", "playlist", "podcast":
                List<LibraryEntry> libraryEntries = searchBar.search(filters, typee);

                for (LibraryEntry libraryEntry : libraryEntries) {
                    results.add(libraryEntry.getName());
                }
                break;
            case "artist", "host":
                List<User> users = searchBar.searchCreators(typee, filters.getName());

                for (User user : users) {
                    results.add(user.getUsername());
                }
                break;
            default:
                break;
        }
        return results;
    }

    /**
     * selects the given item number
     * @param itemNumber the item number
     * @return a message about the success of the operation
     */
    public String select(final int itemNumber) {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        switch (searchBar.getLastSearchType()) {
            case "song", "album", "playlist", "podcast":
                LibraryEntry selected = searchBar.select(itemNumber);

                if (selected == null) {
                    return "The selected ID is too high.";
                }

                return "Successfully selected %s.".formatted(selected.getName());
            case "host":
                User selectedUser = searchBar.selectCreator(itemNumber);

                if (selectedUser == null) {
                    return "The selected ID is too high.";
                } else {
                    page = new HostPage(selectedUser);
                }

                return "Successfully selected %s's page.".formatted(selectedUser.getUsername());
            case "artist":
                User selectedArtist = searchBar.selectCreator(itemNumber);

                if (selectedArtist == null) {
                    return "The selected ID is too high.";
                } else {
                    page = new ArtistPage(selectedArtist);
                }

                return "Successfully selected %s's page.".formatted(selectedArtist.getUsername());
            default:
                return "Invalid search type.";
        }
    }

    /**
     * loads the selected source
     * @return a message about the success of the operation
     */
    public String load() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
                && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }
        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());

        searchBar.clearSelection();
        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * plays or pauses the playback
     * @return a message about the success of the operation
     */
    public String playPause() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * sets the repeat mode
     * @return a message about the success of the operation
     */
    public String repeat() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        repeatStatus = switch (repeatMode) {
            case NO_REPEAT -> "no repeat";
            case REPEAT_ONCE -> "repeat once";
            case REPEAT_ALL -> "repeat all";
            case REPEAT_INFINITE -> "repeat infinite";
            case REPEAT_CURRENT_SONG -> "repeat current song";
            default -> "";
        };

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * sets the shuffle mode on or off
     * @param seed the seed for the shuffle
     * @return a message about the success of the operation
     */
    public String shuffle(final Integer seed) {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * forwards the playback
     * @return a message about the success of the operation
     */
    public String forward() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * rewinds the playback
     * @return a message about the success of the operation
     */
    public String backward() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * likes the current song
     * @return a message about the success of the operation
     */
    public String like() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * moves to next song
     * @return a message about the success of the operation
     */
    public String next() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * moves to previous song
     * @return a message about the success of the operation
     */
    public String prev() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * creates a playlist
     * @param name the name of the playlist
     * @param timestamp the timestamp of the playlist
     * @return a message about the success of the operation
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }


        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * add or remove a song from a playlist
     * @param id the id of the playlist
     * @return a message about the success of the operation
     */
    public String addRemoveInPlaylist(final int id) {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * switches the visibility of a playlist
     * @param playlistId the id of the playlist
     * @return a message about the success of the operation
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }

        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * shows all playlists
     * @return a list of playlists
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * follows the current playlist
     * @return a message about the success of the operation
     */
    public String follow() {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        LibraryEntry selection = searchBar.getLastSelected();
        String typee = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!typee.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * returns the stats of the player
     * @return the stats of the player
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * shows all liked songs
     * @return a list of liked songs
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * shows the preffered genre of the user
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * simulates the time
     * @param time the time to be simulated
     */
    public void simulateTime(final int time) {
        player.simulatePlayer(time);
    }

    /**
     * switches the connection status
     * @return a message about the success of the operation
     */
    public String switchConnectionStatus() {
       if (connectionStatus == Enums.Connection.ONLINE) {
           connectionStatus = Enums.Connection.OFFLINE;
       } else {
           connectionStatus = Enums.Connection.ONLINE;
       }

       player.switchConnectionStatus();
        return username + " has changed status successfully.";
    }

    /**
     * updates the page of the user
     */
    public void updatePage() {
        PageVisitor updateVisitor = new UpdateVisitor();
        page.acceptVisitor(updateVisitor);
    }

    /**
     * adds an album
     * @param name the name of the album
     * @param releaseYear the release year of the album
     * @param description the description of the album
     * @param owner the owner of the album
     * @param songsInput the songs of the album
     * @return a message about the success of the operation
     */
    public String addAlbum(final String name, final int releaseYear,
                           final String description, final String owner,
                           final ArrayList<SongInput> songsInput) {
        return username + " is not an artist.";
    }

    /**
     * adds a merch
     * @param merch the merch to be added
     * @return a message about the success of the operation
     */
    public String addMerch(final Merch merch) {
        return username + " is not an artist.";
    }

    /**
     * adds an event
     * @param event the event to be added
     * @return  a message about the success of the operation
     */
    public String addEvent(final Event event) {
        return username + " is not an artist.";
    }

    /**
     * adds a podcast
     * @param name the name of the podcast
     * @param owner the owner of the podcast
     * @param episodeInputs the episodes of the podcast
     * @return a message about the success of the operation
     */
    public String addPodcast(final String name, final String owner,
                             final List<EpisodeInput> episodeInputs) {
        return username + " is not a host.";
    }


    /**
     * return the current audio file being listened to by the user
     * * @return the song or episode being listened to
     */
    public AudioFile listeningToFile() {
        if (player.getCurrentAudioFile() != null) {
            return player.getCurrentAudioFile();
        }

        return null;
    }

    /**
     * returns the current audio collection being listened to by the user
     * @return the playlist,album or podcast being listened to
     */
    public AudioCollection listeningToCollection() {
        if (player.getCurrentAudioFile() != null) {
            return player.getCurrentAudioCollection();
        }

        return null;
    }

    /**
     * deletes the data of the user
     */
    public void deleteData() {
        for (Playlist playlist : followedPlaylists) {
            playlist.decreaseFollowers();
        }
        followedPlaylists.clear();

        for (Song song : likedSongs) {
            song.dislike();
        }
        likedSongs.clear();

        Admin.removeUserData(this);
    }

    /**
     * get the bookmarks of the user
     * @return the bookmarks
     */
    public List<PodcastBookmark> getBookmarks() {
        return player.getBookmarks();
    }

    /**
     * adds an announcement
     * @param name of the announcement
     * @param message of the announcement
     * @return a message about the success of the operation
     */
    public String addAnnouncement(final String name, final String message) {
        return username + " is not a host.";
    }

    /**
     * removes an announcement
     * @param name of the announcement
     * @return a message about the success of the operation
     */
    public String removeAnnouncement(final String name) {
        return username + " is not a host";
    }

    /**
     * shows all albums
     * @return a list of albums
     */
    public ArrayList<AlbumOutput> showAlbums() {
        throw new UnsupportedOperationException("Not supported by this user type");
    }

    /**
     * shows all podcasts
     * @return a list of podcasts
     */
    public ArrayList<PodcastOutput> showPodcasts() {
        throw new UnsupportedOperationException("Not supported by this user type");
    }

    /**
     * removes an album
     * @param name of the album
     * @return a message about the success of the operation
     */
    public String removeAlbum(final String name) {
        return username + " is not an artist.";
    }

    /**
     * removes a podcast
     * @param name of the podcast
     * @return a message about the success of the operation
     */
    public String removePodcast(final String name) {
        return username + " is not a host.";
    }

    /**
     * removes a podcast bookmark
     * @param name of the podcast bookmark
     */
    public void removePodcastBookmark(final String name) {
        player.removePodcastBookmark(name);
    }

    /**
     * changes the page of the user
     * @param pageName the name of the page
     * @return a message about the success of the operation
     */
    public String changePage(final String pageName) {
        if (connectionStatus.equals(Enums.Connection.OFFLINE)) {
            return username + " is offline.";
        }
        return switch (pageName) {
            case "Home":
                page = new HomePage(this);
                yield getUsername() + " accessed Home successfully.";
            case "LikedContent":
                page = new LikedContentPage(this);
                yield getUsername() + " accessed LikedContent successfully.";
            default:
                yield getUsername() + " is trying to access a non-existent page.";
        };
    }

    /**
     * removes an event
     * @param name of the event
     * @return a message about the success of the operation
     */
    public String removeEvent(final String name) {
        return username + " is not an artist.";
    }
}
