package app.searchBar.search;

import app.Admin;
import app.audio.LibraryEntry;
import app.searchBar.FilterUtils;
import app.searchBar.Filters;
import app.searchBar.SearchBar;

import java.util.ArrayList;
import java.util.List;

/**
 * class for searching songs
 */
public class SongSearch implements SearchStrategy {
    @Override
    public List<String> search(Filters filters) {
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
        return null;
    }

}
