package app;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import fileio.input.EpisodeInput;
import app.utils.Enums;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * The admin class is a singleton class that stores all the data of the program
 */
public final class Admin {
    private static Admin instance = null;
    @Getter
    private static List<User> users = new ArrayList<>();
    @Getter
    private static List<Song> songs = new ArrayList<>();
    @Getter
    private static List<Podcast> podcasts = new ArrayList<>();
    @Getter
    private static List<Album> albums = new ArrayList<>();
    @Getter
    private static int timestamp = 0;
    private static final int MAX_RESULTS = 5;

    private Admin() {
    }

    /**
     * lazy initialization of the singleton
     * @return the instance of the singleton
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;

    }

    /**
     * adds the data from the input file to the program
     * @param userInputList the input file
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * adds the data from the input file to the program
     * @param songInputList the input file
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * adds the data from the input file to the program
     * @param podcastInputList the input file
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription(), podcastInput.getOwner()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * the method returns all the playlists from the program
     * @return a list of all the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * the method returns all the artists from the program
     * @return a list of all the artists
     */
    public static List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        for (User user : users) {
            if (user.getType() == Enums.UserType.ARTIST) {
                artists.add((Artist) user);
            }
        }
        return artists;
    }

    /**
     * the method returns all the hosts from the program
     * @return a list of all the hosts
     */
    public static List<Host> getHosts() {
        List<Host> hosts = new ArrayList<>();
        for (User user : users) {
            if (user.getType() == Enums.UserType.HOST) {
                hosts.add((Host) user);
            }
        }
        return hosts;
    }

    /**
     * the method returns the user with the given username
     * @param username of the user
     * @return the user with the given username
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * the method updates the timestamp of the program and simulates the time for all the users
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * the method returns top 5 most liked songs
     * @return a list of the top 5 most liked songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= MAX_RESULTS) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * the method returns top 5 most followed playlists
     * @return a list of the top 5 most followed playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= MAX_RESULTS) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * the method resets the data of the program
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * the method returns the online users
     * @return a list of the online users
     */
    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getConnectionStatus().equals(Enums.Connection.ONLINE)
                    && user.getType() == Enums.UserType.USER) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * the methods add a new user in the database
     * @param username of the user
     * @param age of the user
     * @param city of the user
     * @param userType of the user
     * @return a message that the user was added successfully or not
     */
    public static String addUser(final String username, final int age, final String city,
                                 final Enums.UserType userType) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return "The username " + username + " is already taken.";
            }
        }

        User newUser = switch (userType) {
            case ARTIST -> new Artist(username, age, city, userType);
            case HOST -> new Host(username, age, city, userType);
            default -> new User(username, age, city);

        };

        users.add(newUser);
        return "The username " + username + " has been added successfully.";
    }

    /**
     * the methods adds an album in the database
     * @param album to be added
     */
    public static void addAlbum(final Album album) {
        albums.add(album);
        songs.addAll(album.getSongs());
    }

    /**
     * the methods adds a podcast in the database
     * @param podcast to be added
     */
    public static void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * the method returns all the users from the database
     * @return a list of all the names the users
     */
    public static ArrayList<String> getAllUsers() {
        ArrayList<String> allUsers = new ArrayList<>();
        for (User user : users) {
           if (user.getType() == Enums.UserType.USER) {
               allUsers.add(user.getUsername());
           }
        }

        for (Artist artist : getArtists()) {
            allUsers.add(artist.getUsername());
        }

        for (Host host : getHosts()) {
            allUsers.add(host.getUsername());
        }
        return allUsers;
    }

    /**
    * the method checks if a user can be deleted
    * @username of the user to be deleted
    **/
    private static boolean validUserDelete(final String username) {
        for (User user : users) {

                AudioFile userSource = user.listeningToFile();
                if (userSource != null && userSource.getOwner().equals(username)) {
                    return false;
                }

                AudioCollection userCollection = user.listeningToCollection();

                if (userCollection != null && userCollection.getOwner().equals(username)) {
                    return false;
                }

                if (user.getPage().getOwner().equals(username)
                        && !user.getUsername().equals(username)) {
                    return false;
                }
        }
        return true;
    }

    /**
     * the method removes all the data of a user from the platform
     * @param deleteUser  the user
     **/
    public static void removeUserData(final User deleteUser) {
        String username = deleteUser.getUsername();
        // we remove the playlists from the users
        for (User user : users) {
            user.getFollowedPlaylists().removeIf(playlist ->
                    playlist.getOwner().equals(username));
            user.getPlaylists().removeIf(playlist ->
                    playlist.getOwner().equals(username));
        }
    }

    /**
     * the method removes all the data of an artist from the platform
     * @param artist the artist
     */
    public static void removeUserData(final Artist artist) {
        String username = artist.getUsername();
        songs.removeIf(song -> song.getArtist().equals(username));
        for (User user : users) {
            user.getLikedSongs().removeIf(song -> song.getArtist().equals(username));
            user.getFollowedPlaylists().removeIf(playlist ->
                    playlist.getOwner().equals(username));
            for (Playlist playlist: user.getPlaylists()) {
                playlist.removeSongsByArtist(username);
            }

            for (Playlist playlist: user.getFollowedPlaylists()) {
                playlist.removeSongsByArtist(username);
            }

        }

        albums.removeIf(album -> album.getOwner().equals(username));
    }

    /**
     * the method removes all the data of a host from the platform
     * @param host the host
     */
    public static void removeUserData(final Host host) {
        String username = host.getUsername();
        for (User user : users) {
            user.getBookmarks().removeIf(bookmark -> bookmark.getOwner().equals(username));
        }
        podcasts.removeIf(podcast -> podcast.getOwner().equals(username));
    }

    /**
     * the method removes all the data of an user from the platform
     * @username of the user
     * @return a message that the user was deleted successfully or not
     **/
    public static String deleteUser(final String username) {
        User user = getUser(username);
        if (user == null) {
            return "User " + username + " doesn't exist.";
        }

        if (!validUserDelete(username)) {
            return username + " can't be deleted.";
        }

        user.deleteData();
        users.remove(user);
        return username + " was successfully deleted.";
    }

    /**
     * the method checks if media from a certain creator can be deleted
     * @param creatorName of the creator
     * @return true if the media can be deleted, false otherwise
     */
    public static boolean invalidMediaDelete(final String creatorName) {
        for (User user : users) {
            if (user.listeningToFile() != null
                    && user.listeningToFile().getOwner().equals(creatorName)) {
                return true;
            }

            AudioCollection userCollection = user.listeningToCollection();
            if (userCollection != null) {
                if (userCollection.getOwner().equals(creatorName)) {
                    return true;
                }
                if (userCollection.containsMediaByCreator(creatorName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * the method removes all the data of an album from the platform
     * @album to be deleted
     * @return a message that the album was deleted successfully or not
     **/
    public static String removeAlbum(final Artist artist, final Album album) {
        if (invalidMediaDelete(artist.getUsername())) {
            return artist.getUsername() + " can't delete this album.";
        }

        artist.getAlbums().remove(album);
        ArrayList<Song> songsToRemove = album.getSongs();
        songs.removeAll(songsToRemove);

        for (User user : users) {
            user.getLikedSongs().removeIf(Song -> Song.getAlbum().equals(album.getName()));

            for (Playlist playlist : user.getPlaylists()) {
                playlist.removeSongsByAlbum(album.getName());
            }
            for (Playlist playlist : user.getFollowedPlaylists()) {
                playlist.removeSongsByAlbum(album.getName());
            }
        }

        albums.remove(album);
        return artist.getUsername() + " deleted the album successfully.";
    }

    /**
     * the method removes all the data of a podcast from the platform
     * @podcast to be deleted
     * @return a message that the podcast was deleted successfully or not
     **/
    public static String removePodcast(final Host host, final Podcast podcast) {
        if (invalidMediaDelete(host.getUsername())) {
            return host.getUsername() + " can't delete this podcast.";
        }

        host.getPodcasts().remove(podcast);
        podcasts.remove(podcast);
        for (User user : users) {
            user.removePodcastBookmark(podcast.getName());
        }
        return host.getUsername() + " deleted the podcast successfully.";
    }

    /**
     * the method finds top 5 most liked albums
     * @return a list of the top 5 most liked albums
     **/
    public static List<String> getTop5Albums() {
        List<String> topAlbums = new ArrayList<>();
        List<Album> sortedAlbums = new ArrayList<>(albums);
        sortedAlbums.sort(Comparator.comparingInt(Album::likes).
                reversed().thenComparing(Album::getName));

        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= MAX_RESULTS) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * the method finds top 5 most liked artists
     * @return a list of the top 5 most liked artists
     **/
    public static List<String> getTop5Artists() {
        List<String> topArtists = new ArrayList<>();
        List<Artist> sortedArtists = new ArrayList<>(getArtists());
        sortedArtists.sort(Comparator.comparingInt(Artist::likes).reversed());

        int count = 0;
        for (Artist artist : sortedArtists) {
            if (count >= MAX_RESULTS) {
                break;
            }
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }
}
