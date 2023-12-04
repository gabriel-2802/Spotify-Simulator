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
import app.utils.Enums;
import fileio.input.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Admin {
    @Getter
    private static List<User> users = new ArrayList<>();
    public static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    @Getter
    private static List<Artist> artists = new ArrayList<>();
    @Getter
    private static List<Host> hosts = new ArrayList<>();
    @Getter
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp = 0;

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription(), podcastInput.getOwner()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }

    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getConnectionStatus().equals(Enums.Connection.ONLINE) && user.getType() == Enums.UserType.USER) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    public static String addUser(String username, int age, String city, Enums.UserType userType) {
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
        switch (userType) {
            case ARTIST -> artists.add((Artist) newUser);
            case HOST -> hosts.add((Host) newUser);
        }

        return "The username " + username + " has been added successfully.";
    }

    public static void addAlbum(Album album) {
        albums.add(album);
        songs.addAll(album.getSongs());
    }

    public static ArrayList<String> getAllUsers() {
        ArrayList<String> allUsers = new ArrayList<>();
        for (User user : users) {
           if (user.getType() == Enums.UserType.USER) {
               allUsers.add(user.getUsername());
           }
        }

        for (Artist artist : artists) {
            allUsers.add(artist.getUsername());
        }

        for (Host host : hosts) {
            allUsers.add(host.getUsername());
        }
        return allUsers;
    }

    private static boolean validUserDelete(String username) {
        for (User user : users) {

                AudioFile userSource = user.listeningTo();
                if (userSource != null && userSource.getOwner().equals(username)) {
                    return false;
                }
                if (user.getPage().getOwner().equals(username) && !user.getUsername().equals(username)) {
                    return false;
                }
        }
        return true;
    }

    public static void removeHostData(String username) {

    }
    public static void removePlaylistsData(String username) {
        for (User user : users) {
            user.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner().equals(username));
            user.getPlaylists().removeIf(playlist -> playlist.getOwner().equals(username));
        }
    }

    public static void removeArtistData(String username) {
        songs.removeIf(song -> song.getArtist().equals(username));
        for (User user : users) {
            user.getLikedSongs().removeIf(song -> song.getArtist().equals(username));
            user.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner().equals(username));
        }
        albums.removeIf(album -> album.getOwner().equals(username));

    }

    public static String deleteUser(String username) {
        User user = getUser(username);
        if (user == null) {
            return "User " + username + " doesn't exist.";
        }

        if (!validUserDelete(username)) {
            return username + " can't be deleted.";
        }

        user.deleteData();
        users.remove(user);
        if (user.getType() == Enums.UserType.ARTIST) {
            artists.remove((Artist) user);
        } else if (user.getType() == Enums.UserType.HOST) {
            hosts.remove((Host) user);
        }
        return username + " was successfully deleted.";
    }



}
