package app.searchBar.search;

public class SearchStrategyFactory {
    private SearchStrategyFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * instantiates a new search strategy based on the type
     * @param type the type of the search
     * @return the new search strategy
     */
//    public static SearchStrategy createSearchStrategy(final String type) {
//        return switch (type) {
//            case "song" -> new SongSearchStrategy();
//            case "artist" -> new ArtistSearchStrategy();
//            case "host" -> new HostSearchStrategy();
//            case "album" -> new AlbumSearchStrategy();
//            case "playlist" -> new PlaylistSearchStrategy();
//            case "podcast" -> new PodcastSearchStrategy();
//            default -> null;
//        };

}
