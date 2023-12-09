package app.searchBar;

import app.audio.LibraryEntry;
import app.searchBar.searchStrategies.SearchStrategy;
import app.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * the second version of the search bar, designed to with Factory and Strategy patterns
 */
public class SearchBarV2 {
    public static final int MAX_RESULTS = 5;
    private List<?> results;
    private final String user;
    @Getter
    private String lastSearchType;

    @Getter
    private LibraryEntry lastSelectedMedia;
    @Getter
    private User lastSelectedUser;

    public SearchBarV2(final String user) {
        this.results = new ArrayList<>();
        this.user = user;
    }

    /**
     * clears the selection
     */
    public void clearSelection() {
        lastSelectedMedia = null;
        lastSelectedUser = null;
        lastSearchType = null;
    }

    /**
     * searches for the given filters
     * @param filters the filters
     * @param type the type of the search
     * @return the names of the results
     */
    public List<String> search(final Filters filters, final String type) {
        SearchStrategy<?> strategy = SearchStrategyFactory.getStrategy(type);
        List<String> names = new ArrayList<>();
        results = strategy.search(filters, names, user);
        lastSearchType = type;
        return names;
    }

    /**
     * selects the given index
     * @param index the index
     * @return the name of the selected media or content creator
     */
    public boolean select(final int index) {
        if (results.size() < index || index < 0) {
            results.clear();
            return false;
        }

        return switch (lastSearchType) {
            case "song", "album", "playlist", "podcast" -> {
                lastSelectedMedia = (LibraryEntry) results.get(index - 1);
                results.clear();
                yield true;
            }
            case "artist", "host" -> {
                lastSelectedUser = (User) results.get(index - 1);
                results.clear();
                yield true;
            }
            default -> {
                results.clear();
                yield false;
            }
        };
    }
}
