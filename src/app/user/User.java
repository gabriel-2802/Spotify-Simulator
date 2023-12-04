package app.user;

import app.Admin;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pagination.*;
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
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    @Setter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Getter
    private Enums.Connection connectionStatus;
    @Getter
    private Enums.UserType type;

    public void setPage(Page page) {
        this.page = page;
    }

    @Getter
    @Setter
    private Page page; // the page the user is currently on

    public User(String username, int age, String city) {
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

    public User(String username, int age, String city, Enums.UserType type) {
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
        this.type = type;
        page = new HomePage(this);
    }

    public ArrayList<String> search(Filters filters, String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;

        ArrayList<String> results = new ArrayList<>();

        switch (type) {
            case "song", "album", "playlist", "podcast":
                List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

                for (LibraryEntry libraryEntry : libraryEntries) {
                    results.add(libraryEntry.getName());
                }
            case "artist", "host":
                List<User> users = searchBar.searchCreators(type, filters.getName());

                for (User user : users) {
                    results.add(user.getUsername());
                }
        }
        return results;
    }

    public String select(int itemNumber) {
        if (!lastSearched)
            return "Please conduct a search before making a selection.";

        lastSearched = false;

        switch (searchBar.getLastSearchType()) {
            case "song", "album", "playlist", "podcast":
                LibraryEntry selected = searchBar.select(itemNumber);

                if (selected == null)
                    return "The selected ID is too high.";

                return "Successfully selected %s.".formatted(selected.getName());
            case "host":
                User selectedUser = searchBar.selectCreator(itemNumber);

                if (selectedUser == null) {
                    return "The selected ID is too high.";
                } else {
                    page = new HostPage((Host)selectedUser);
                }

                return "Successfully selected %s's page.".formatted(selectedUser.getUsername());
            case "artist":
                User selectedArtist = searchBar.selectCreator(itemNumber);

                if (selectedArtist == null) {
                    return "The selected ID is too high.";
                } else {
                    page = new ArtistPage((Artist)selectedArtist);
                }

                return "Successfully selected %s's page.".formatted(selectedArtist.getUsername());
            default:
                return "Invalid search type.";
        }
    }

    public String load() {
        if (searchBar.getLastSelected() == null)
            return "Please select a source before attempting to load.";

        if (!searchBar.getLastSearchType().equals("song") && ((AudioCollection)searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }
        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());

        searchBar.clearSelection();
        player.pause();

        return "Playback loaded successfully.";
    }

    public String playPause() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to pause or resume playback.";

        player.pause();

        if (player.getPaused())
            return "Playback paused successfully.";
        else
            return "Playback resumed successfully.";
    }

    public String repeat() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before setting the repeat status.";

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch(repeatMode) {
            case NO_REPEAT -> repeatStatus = "no repeat";
            case REPEAT_ONCE -> repeatStatus = "repeat once";
            case REPEAT_ALL -> repeatStatus = "repeat all";
            case REPEAT_INFINITE -> repeatStatus = "repeat infinite";
            case REPEAT_CURRENT_SONG -> repeatStatus = "repeat current song";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    public String shuffle(Integer seed) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before using the shuffle function.";

        if (!player.getType().equals("playlist") && !player.getType().equals("album"))
            return "The loaded source is not a playlist or an album.";

        player.shuffle(seed);

        if (player.getShuffle())
            return "Shuffle function activated successfully.";
        return "Shuffle function deactivated successfully.";
    }

    public String forward() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to forward.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipNext();

        return "Skipped forward successfully.";
    }

    public String backward() {
        if (player.getCurrentAudioFile() == null)
            return "Please select a source before rewinding.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipPrev();

        return "Rewound successfully.";
    }

    public String like() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before liking or unliking.";

        if (!player.getType().equals("song") && !player.getType().equals("playlist") && !player.getType().equals("album"))
            return "Loaded source is not a song.";

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

    public String next() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        player.next();

        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        return "Skipped to next track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }

    public String prev() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before returning to the previous track.";

        player.prev();

        return "Returned to previous track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }

    public String createPlaylist(String name, int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name)))
            return "A playlist with the same name already exists.";


        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    public String addRemoveInPlaylist(int Id) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before adding to or removing from the playlist.";

        if (player.getType().equals("podcast"))
            return "The loaded source is not a song.";

        if (Id > playlists.size())
            return "The specified playlist does not exist.";

        Playlist playlist = playlists.get(Id - 1);

        if (playlist.containsSong((Song)player.getCurrentAudioFile())) {
            playlist.removeSong((Song)player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song)player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    public String switchPlaylistVisibility(Integer playlistId) {
        if (playlistId > playlists.size())
            return "The specified playlist ID is too high.";

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    // TODO: follow albums?
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null)
            return "Please select a source before following or unfollowing.";

        if (!type.equals("playlist"))
            return "The selected source is not a playlist.";

        Playlist playlist = (Playlist)selection;

        if (playlist.getOwner().equals(username))
            return "You cannot follow or unfollow your own playlist.";

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

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

    public void simulateTime(int time) {
        player.simulatePlayer(time);
    }
    public String switchConnectionStatus() {
       if (connectionStatus == Enums.Connection.ONLINE) {
           connectionStatus = Enums.Connection.OFFLINE;
       } else {
           connectionStatus = Enums.Connection.ONLINE;
       }

       player.switchConnectionStatus();
        return username + " has changed status successfully.";
    }

    public void updatePage() {
        page.updatePage();
    }


    public String addAlbum(String name, int releaseYear, String description, String owner, ArrayList<SongInput> songsInput) {
        return username + " is not an artist.";
    }

    public String addMerch(Merch merch) {
        return username + " is not an artist.";
    }

    public String addEvent(Event event) {
        return username + " is not an artist.";
    }

    public String addPodcast(String name, String owner, List<EpisodeInput> episodeInputs) {
        return username + " is not a host.";
    }

    /* functions returns the audio colelction that the user is listening to */
    public AudioFile listeningTo() {
        if (player.getCurrentAudioFile() != null) {
            return player.getCurrentAudioFile();
        }

        return null;
    }

    public void deleteData() {
        for (Playlist playlist : followedPlaylists) {
            playlist.decreaseFollowers();
        }
        followedPlaylists.clear();

        for (Song song : likedSongs) {
            song.dislike();
        }
        likedSongs.clear();

        Admin.removePlaylistsData(username);
    }

    public List<PodcastBookmark> getBookmarks() {
        return player.getBookmarks();
    }

    public String addAnnouncement(String name, String message) {
        return username + " is not a host.";
    }
    public String removeAnnouncement(String name) {
        return username + " is not a host";
    }

    public ArrayList<AlbumOutput> showAlbums() {
        throw new UnsupportedOperationException("Not supported by this user type");
    }

    public ArrayList<PodcastOutput> showPodcasts() {
        throw new UnsupportedOperationException("Not supported by this user type");
    }

    public String removeAlbum(String name) {
        return username + " is not an artist.";
    }

    public String removePodcast(String name) {
        return username + " is not a host.";
    }

    public void removePodcastBookmark(String name) {
        player.removePodcastBookmark(name);
    }

    public String changePage(String pageName) {
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

    public String removeEvent(String name) {
        return username + " is not an artist.";
    }
    public void removeSongsFromPlaylistByArtist(String artist) {
        System.out.println("Removing songs by " + artist + " from " + username + "'s playlists.");
        System.out.println("->nr of playlists: " + playlists.size() + " from " + username);
        for (Playlist playlist : playlists) {
            System.out.println("----playlist: " + playlist.getName() + " from " + username);
            playlist.removeSongsByArtist(artist);
        }
        System.out.println("->nr of playlists: " + playlists.size() + " from " + username);
        for (Playlist playlist : followedPlaylists) {
            System.out.println("---playlist: " + playlist.getName() + " from " + username);
            playlist.removeSongsByArtist(artist);
        }
    }


}
