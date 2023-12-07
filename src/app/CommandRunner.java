package app;

import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.PodcastOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.User;
import app.user.utils.Event;
import app.user.utils.Merch;
import app.utils.Enums;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

public final class CommandRunner {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private CommandRunner() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * runs search command
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();
        if (user == null) {
            return userNonexistent(commandInput);
        }

        ArrayList<String> results = new ArrayList<>();
        String message;
        if (user.getConnectionStatus() == Enums.Connection.ONLINE) {
            results = user.search(filters, type);
            message = "Search returned " + results.size() + " results";
        } else {
            message = commandInput.getUsername() + " is offline.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * runs command for a user to select media or an content creator they previously searched
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to load a media or an content creator they previously selected
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to play or pause a media they previously loaded
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to repeat a media they previously loaded
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to shuffle a media they previously loaded
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to forward a media they previously loaded
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to backward a media they previously loaded
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to like a song they previously loaded
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.like();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to move to the next audio file
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to move to the previous audio file
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to create a playlist
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.createPlaylist(commandInput.getPlaylistName(),
                commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to add a song to a playlist
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to switch the visibility of a playlist
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to show the playlists of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * runs the command to follow a playlist
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return userError(commandInput);
        }

        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to show the status of a user's player
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * runs the command to show the liked songs of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * runs the command to show the preferred genre of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * runs the command to show the top 5 most liked songs on the platform
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * runs the command to show the top 5 most followed playlists on the platform
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * runs the command to switch the connection status of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!(user.getType() == Enums.UserType.USER)) {
            message = commandInput.getUsername() + " is not a normal user.";
        } else {
            message = user.switchConnectionStatus();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * in case of an error, the other commands invoke this method
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode userError(final CommandInput commandInput) {

        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (user.getConnectionStatus() == Enums.Connection.OFFLINE) {
            message = commandInput.getUsername() + " is offline.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * in case of a non-existent user, the other commands invoke this method
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode userNonexistent(final CommandInput commandInput) {
        String message = "The username " + commandInput.getUsername() + " doesn't exist.";

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to show the online users
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    /**
     * runs the command to add a new user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        Enums.UserType userType = switch (commandInput.getType()) {
            case "artist" -> Enums.UserType.ARTIST;
            case "host" -> Enums.UserType.HOST;
            default -> Enums.UserType.USER;
        };
        String message = Admin.addUser(commandInput.getUsername(),
                commandInput.getAge(), commandInput.getCity(), userType);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to add a new album
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        String message = user.addAlbum(commandInput.getName(),
                    commandInput.getReleaseYear(), commandInput.getDescription(),
                    commandInput.getUsername(), commandInput.getSongs());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs the command to show the albums of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        ArrayList<AlbumOutput> albums = user.showAlbums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * runs the command to print the current page of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }
        String message;
        if (user.getConnectionStatus() == Enums.Connection.OFFLINE) {
            message = commandInput.getUsername() + " is offline.";
        } else {
            user.updatePage();
             message = user.getPage().toString();
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs the command to add a new merch
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        Merch newMerch = new Merch(commandInput.getName(),
                commandInput.getPrice(), commandInput.getDescription());
        String message = user.addMerch(newMerch);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs the command to add a new event
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        Event newEvent = new Event(commandInput.getName(),
                commandInput.getDate(), commandInput.getDescription());
        String message = user.addEvent(newEvent);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs the command to show all the users
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> users = Admin.getAllUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(users));

        return objectNode;
    }

    /**
     * runs the command to delete a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        String message = Admin.deleteUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * runs command for a user to add a podcast
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        String message = user.addPodcast(commandInput.getName(),
                commandInput.getUsername(), commandInput.getEpisodes());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs command for a user to add an announcement
     * @param command the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode addAnnouncement(final CommandInput command) {
        User user = Admin.getUser(command.getUsername());
        if (user == null) {
            return userNonexistent(command);
        }

        String message = user.addAnnouncement(command.getName(), command.getDescription());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", command.getCommand());
        objectNode.put("user", command.getUsername());
        objectNode.put("timestamp", command.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs command for a user to remove an announcement
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        String message = user.removeAnnouncement(commandInput.getName());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs command for a user to show all podcasts of an user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        ArrayList<PodcastOutput> albums = user.showPodcasts();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * runs command for a user to remove an album
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }
        String message = user.removeAlbum(commandInput.getName());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs command for a user to remove a podcast
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        String message = user.removePodcast(commandInput.getName());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs the command to change the page of a user
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        String message = user.changePage(commandInput.getNextPage());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs the command to remove an event
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return userNonexistent(commandInput);
        }

        String message = user.removeEvent(commandInput.getName());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * runs the command to show top 5 most liked albums on the platform
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    /**
     * runs the command to show top 5 most liked artists on the platform
     * @param commandInput the command
     * @return the result of the command as an ObjectNode
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> artists = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(artists));

        return objectNode;
    }
}
