package app.searchBar;


import app.Admin;
import app.audio.LibraryEntry;
import app.user.User;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * Search bar
 */
public class SearchBar {
    private List<LibraryEntry> results;
    private List<User> userResults;
    private final String user;
    private static final Integer MAX_RESULTS = 5;
    @Getter
    private String lastSearchType;

    @Getter
    private LibraryEntry lastSelected;
    private User lastSelectedUser;

    public SearchBar(final String user) {
        this.results = new ArrayList<>();
        this.user = user;
    }

    /**
     * clears the selection
     */
    public void clearSelection() {
        lastSelected = null;
        lastSelectedUser = null;
        lastSearchType = null;
    }

    /**
     * searches media for the given filters and type
     * @param filters the filters
     * @param type the type of the media
     * @return the results
     */
    public List<LibraryEntry> search(final Filters filters, final String type) {
        List<LibraryEntry> entries;

        switch (type) {
            case "song":
                entries = new ArrayList<>(Admin.getSongs());

                if (filters.getName() != null) {
                    entries = FilterUtils.filterByName(entries, filters.getName());
                }

                if (filters.getAlbum() != null) {
                    entries = FilterUtils.filterByAlbum(entries, filters.getAlbum());
                }

                if (filters.getTags() != null) {
                    entries = FilterUtils.filterByTags(entries, filters.getTags());
                }

                if (filters.getLyrics() != null) {
                    entries = FilterUtils.filterByLyrics(entries, filters.getLyrics());
                }

                if (filters.getGenre() != null) {
                    entries = FilterUtils.filterByGenre(entries, filters.getGenre());
                }

                if (filters.getReleaseYear() != null) {
                    entries = FilterUtils.filterByReleaseYear(entries, filters.getReleaseYear());
                }

                if (filters.getArtist() != null) {
                    entries = FilterUtils.filterByArtist(entries, filters.getArtist());
                }

                break;
            case "playlist":
                entries = new ArrayList<>(Admin.getPlaylists());

                entries = FilterUtils.filterByPlaylistVisibility(entries, user);

                if (filters.getName() != null) {
                    entries = FilterUtils.filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = FilterUtils.filterByOwner(entries, filters.getOwner());
                }

                if (filters.getFollowers() != null) {
                    entries = FilterUtils.filterByFollowers(entries, filters.getFollowers());
                }

                break;
            case "podcast":
                entries = new ArrayList<>(Admin.getPodcasts());

                if (filters.getName() != null) {
                    entries = FilterUtils.filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = FilterUtils.filterByOwner(entries, filters.getOwner());
                }

                break;
            case "album":
                entries = new ArrayList<>(Admin.getAlbums());
                if (filters.getName() != null) {
                    entries = FilterUtils.filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = FilterUtils.filterByOwner(entries, filters.getOwner());
                }

                if (filters.getDescription() != null) {
                    entries = FilterUtils.filterByDescription(entries, filters.getDescription());
                }
                break;
            default:
                entries = new ArrayList<>();
        }

        while (entries.size() > MAX_RESULTS) {
            entries.remove(entries.size() - 1);
        }

        this.results = entries;
        this.lastSearchType = type;
        return this.results;
    }

    /**
     * searches creators for the given type and name
     * @param type the type of the creator
     * @param name the name of the creator
     * @return the results
     */
    public List<User> searchCreators(final String type, final String name) {
        List<User> entries =  switch (type) {
            case "artist" -> FilterUtils.filterArtists(Admin.getArtists(), name);
            case "host" -> FilterUtils.filterHosts(Admin.getHosts(), name);
            default -> new ArrayList<>();
        };

        while (entries.size() > MAX_RESULTS) {
            entries.remove(entries.size() - 1);
        }

        this.userResults = entries;
        this.lastSearchType = type;
        return this.userResults;
    }

    /**
     * selects the given item number from the results
     * @param itemNumber the item number
     * @return the selected item
     */
    public User selectCreator(final Integer itemNumber) {
        if (this.userResults.size() < itemNumber) {
            results.clear();
            userResults.clear();

            return null;
        } else {
            lastSelectedUser =  this.userResults.get(itemNumber - 1);
            userResults.clear();
            return lastSelectedUser;
        }
    }

    /**
     * selects the given item number from the results
     * @param itemNumber the item number
     * @return the selected item
     */
    public LibraryEntry select(final Integer itemNumber) {
        if (this.results.size() < itemNumber) {
            results.clear();
            userResults.clear();

            return null;
        } else {
            lastSelected =  this.results.get(itemNumber - 1);
            results.clear();

            return lastSelected;
        }
    }
}
