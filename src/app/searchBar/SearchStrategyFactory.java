package app.searchBar;

import app.searchBar.searchStrategies.AlbumSearchStrategy;
import app.searchBar.searchStrategies.ArtistSearchStrategy;
import app.searchBar.searchStrategies.HostSearchStrategy;
import app.searchBar.searchStrategies.PlaylistSearchStrategy;
import app.searchBar.searchStrategies.PodcastSearchStrategy;
import app.searchBar.searchStrategies.SearchStrategy;
import app.searchBar.searchStrategies.SongSearchStrategy;

/**
 * This class is a factory used to get the search strategy
 */
public final class SearchStrategyFactory {

    private SearchStrategyFactory() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * This method is used to get the search strategy
     * @param type the type of the search
     * @return the search strategy
     */
    public static SearchStrategy<?> getStrategy(final String type) {
        return switch (type) {
            case "song" -> new SongSearchStrategy();
            case "playlist" -> new PlaylistSearchStrategy();
            case "album" -> new AlbumSearchStrategy();
            case "podcast" -> new PodcastSearchStrategy();
            case "artist" -> new ArtistSearchStrategy();
            case "host" -> new HostSearchStrategy();
            default -> throw new IllegalArgumentException("Unexpected value for search: " + type);
        };
    }
}
