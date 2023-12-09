package app.searchBar.searchStrategies;

import app.Admin;
import app.audio.LibraryEntry;
import app.searchBar.FilterUtils;
import app.searchBar.Filters;
import app.searchBar.SearchBarV2;

import java.util.ArrayList;
import java.util.List;

public class SongSearchStrategy implements SearchStrategy<LibraryEntry> {


    /**
     * This method is used to search for the given filters
     * @param filters the filters
     * @return the results
     */
    @Override
    public List<LibraryEntry> search(final Filters filters, final List<String> names,
                                     final String user) {
        List<LibraryEntry> entries = new ArrayList<>(Admin.getSongs());
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

        if (entries.size() > SearchBarV2.MAX_RESULTS) {
            entries = entries.subList(0, SearchBarV2.MAX_RESULTS);
        }

        for (LibraryEntry entry : entries) {
            names.add(entry.getName());
        }

        return entries;
    }
}
