package app.searchBar.searchStrategies;

import app.Admin;
import app.audio.LibraryEntry;
import app.searchBar.FilterUtils;
import app.searchBar.Filters;
import app.searchBar.SearchBarV2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to search for playlists
 */
public class PlaylistSearchStrategy implements SearchStrategy<LibraryEntry> {
    /**
     * This method is used to search for the given filters
     * @param filters the filters
     * @return the results
     */
    @Override
    public List<LibraryEntry> search(final Filters filters, final List<String> names,
                                     final String user) {
        Admin admin = Admin.getInstance();
        List<LibraryEntry> entries = new ArrayList<>(admin.getPlaylists());

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

        if (entries.size() > SearchBarV2.MAX_RESULTS) {
            entries = entries.subList(0, SearchBarV2.MAX_RESULTS);
        }

        for (LibraryEntry entry : entries) {
            names.add(entry.getName());
        }

        return entries;
    }
}
