package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import app.utils.Enums;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * Player
 */
public class Player {
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    private boolean paused;
    @Getter
    private PlayerSource source;
    @Getter
    private String type;
    private Enums.Connection connectionStatus;
    @Getter
    private ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();
    private static final int SECONDS = 90;


    public Player() {
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
        connectionStatus = Enums.Connection.ONLINE;
        source = null;
    }

    /**
     * stops the player
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        source = null;
        shuffle = false;
    }

    /**
     * Bookmarks the current podcast
     */
    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark =
                    new PodcastBookmark(source.getAudioCollection().getName(),
                    source.getIndex(), source.getDuration(),
                            ((Podcast) source.getAudioCollection()).getOwner());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    /**
     * Creates a source
     * @param typee the type of the source
     * @param entry the entry
     * @param bookmarks the bookmarks
     * @return the source
     */
    public static PlayerSource createSource(final String typee, final LibraryEntry entry,
                                            final List<PodcastBookmark> bookmarks) {
        if ("song".equals(typee)) {
            return new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
        } else if ("playlist".equals(typee)) {
            return new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
        } else if ("podcast".equals(typee)) {
            return createPodcastSource((AudioCollection) entry, bookmarks);
        } else if ("album".equals(typee)) {
            return new PlayerSource(Enums.PlayerSourceType.ALBUM, (AudioCollection) entry);
        }

        return null;
    }

    /**
     * Creates a podcast source
     * @param collection the collection
     * @param bookmarks the bookmarks
     * @return the podcast source
     */
    private static PlayerSource createPodcastSource(final AudioCollection collection,
                                                    final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }

    /**
     * Sets the source
     * @param entry the entry
     * @param typee the type of the source
     */
    public void setSource(final LibraryEntry entry, final String typee) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        this.type = typee;
        this.source = createSource(typee, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /**
     * sets the source on pause or unpauses it
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Shuffles the source if it is a playlist or an album
     * @param seed the seed
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST
                || source.getType() == Enums.PlayerSourceType.ALBUM) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }

    /**
     * Repeats the source
     * @return the repeat mode
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }

    /**
     * Simulates the player time
     * @param time the time
     */
    public void simulatePlayer(final int time) {
        int myTime = time;
        if (!paused && connectionStatus == Enums.Connection.ONLINE) {
            while (myTime >= source.getDuration()) {
                myTime -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-myTime);
            }
        }
    }

    /**
     * Plays the next song
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    /**
     * Plays the previous song
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    /**
     * Skips the song
     * @param duration the duration
     */
    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     * Skips the next song
     */
    public void skipNext() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(-SECONDS);
        }
    }

    /**
     * Skips the previous song
     */
    public void skipPrev() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(SECONDS);
        }
    }

    /**
     * Returns the current audio file
     * @return the current audio file
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     * return the pause status
     * @return true if the player is paused, false otherwise
     */
    public boolean getPaused() {
        return paused;
    }

    /** returns the shuffle status
     * @return true if the player is shuffled, false otherwise
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     * returns the status of the player
     * @return the status of the player
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }

    /**
     * Switches the connection status
     */
    public void switchConnectionStatus() {
        if (connectionStatus == Enums.Connection.ONLINE) {
            connectionStatus = Enums.Connection.OFFLINE;
        } else {
            connectionStatus = Enums.Connection.ONLINE;
        }
    }

    /**
     * Returns the current audio collection
     * @return the current audio collection
     */
    public AudioCollection getCurrentAudioCollection() {
        if (source == null) {
            return null;
        }
        return source.getAudioCollection();
    }

    /**
     * removes a podcast bookmark
     * @param name the name of the podcast
     */
    public void removePodcastBookmark(final String name) {
        bookmarks.removeIf(bookmark -> bookmark.getName().equals(name));
    }
}
