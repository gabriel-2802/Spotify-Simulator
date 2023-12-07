package app.searchBar;

import app.audio.LibraryEntry;
import app.user.Artist;
import app.user.Host;
import app.user.User;

import java.util.ArrayList;
import java.util.List;
/**
 * Filter utilities
 */
public final class FilterUtils {
    private FilterUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Filters the given entries by name
     * @param entries the entries
     * @param name the name
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByName(final List<LibraryEntry> entries,
                                                  final String name) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (entry.matchesName(name)) {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Filters the given entries by album
     * @param entries the entries
     * @param album the album
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByAlbum(final List<LibraryEntry> entries,
                                                   final String album) {
        return filter(entries, entry -> entry.matchesAlbum(album));
    }

    /**
     * Filters the given entries by tags
     * @param entries the entries
     * @param tags the tags
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByTags(final List<LibraryEntry> entries,
                                                  final ArrayList<String> tags) {
        return filter(entries, entry -> entry.matchesTags(tags));
    }

    /**
     * Filters the given entries by lyrics
     * @param entries the entries
     * @param lyrics the lyrics
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByLyrics(final List<LibraryEntry> entries,
                                                    final String lyrics) {
        return filter(entries, entry -> entry.matchesLyrics(lyrics));
    }

    /**
     * Filters the given entries by genre
     * @param entries the entries
     * @param genre the genre
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByGenre(final List<LibraryEntry> entries,
                                                   final String genre) {
        return filter(entries, entry -> entry.matchesGenre(genre));
    }

    /**
     * Filters the given entries by artist
     * @param entries the entries
     * @param artist the artist
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByArtist(final List<LibraryEntry> entries,
                                                    final String artist) {
        return filter(entries, entry -> entry.matchesArtist(artist));
    }

    /**
     * Filters the given entries by release year
     * @param entries the entries
     * @param releaseYear the release year
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByReleaseYear(final List<LibraryEntry> entries,
                                                         final String releaseYear) {
        return filter(entries, entry -> entry.matchesReleaseYear(releaseYear));
    }

    /**
     * Filters the given entries by owner
     * @param entries the entries
     * @param user the user
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByOwner(final List<LibraryEntry> entries,
                                                   final String user) {
        return filter(entries, entry -> entry.matchesOwner(user));
    }

    /**
     * Filters the given entries by playlist visibility
     * @param entries the entries
     * @param user the user
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByPlaylistVisibility(final List<LibraryEntry> entries,
                                                                final String user) {
        return filter(entries, entry -> entry.isVisibleToUser(user));
    }

    /**
     * Filters the given entries by followers
     * @param entries the entries
     * @param followers the followers
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByFollowers(final List<LibraryEntry> entries,
                                                       final String followers) {
        return filter(entries, entry -> entry.matchesFollowers(followers));
    }

    /**
     * Filters the given entries by description
     * @param entries the entries
     * @param description the description
     * @return the filtered entries
     */
    public static List<LibraryEntry> filterByDescription(final List<LibraryEntry> entries,
                                                         final String description) {
        return filter(entries, entry -> entry.matchesDescription(description));
    }

    /**
     * Filters the given entries by criteria
     * @param entries the entries
     * @param criteria the criteria
     * @return the filtered entries
     */
    private static List<LibraryEntry> filter(final List<LibraryEntry> entries,
                                             final FilterCriteria criteria) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (criteria.matches(entry)) {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Filters the given artists by name
     * @param artists the artists
     * @param artist the artist
     * @return the filtered artists
     */
    public static List<User> filterArtists(final List<Artist> artists,
                                           final String artist) {
        List<User> result = new ArrayList<>();
        for (User user : artists) {
            if (user.getUsername().toLowerCase().startsWith(artist.toLowerCase())) {
                result.add(user);
            }
        }
        return result;
    }

    /**
     * Filters the given hosts by name
     * @param hosts the hosts
     * @param host the host
     * @return the filtered hosts
     */
    public static List<User> filterHosts(final List<Host> hosts, final String host) {
        List<User> result = new ArrayList<>();
        for (User user : hosts) {
            if (user.getUsername().toLowerCase().startsWith(host.toLowerCase())) {
                result.add(user);
            }
        }
        return result;
    }

    /**
     * functional interface for filtering
     */
    @FunctionalInterface
    private interface FilterCriteria {
        boolean matches(LibraryEntry entry);
    }
}
