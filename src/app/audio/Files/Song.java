package app.audio.Files;

import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represents a song
 */
@Getter
public final class Song extends AudioFile {
    private final String album;
    private final ArrayList<String> tags;
    private final String lyrics;
    private final String genre;
    private final Integer releaseYear;
    private final String artist;
    private Integer likes;

    public Song(final SongInput songInput) {
        super(songInput.getName(), songInput.getDuration());
        this.album = songInput.getAlbum();
        this.tags = songInput.getTags();
        this.lyrics = songInput.getLyrics();
        this.genre = songInput.getGenre();
        this.releaseYear = songInput.getReleaseYear();
        this.artist = songInput.getArtist();
        this.likes = 0;
        owner = artist;
    }
    public Song(final String name, final Integer duration, final String album,
                final ArrayList<String> tags, final String lyrics, final String genre,
                final Integer releaseYear, final String artist) {
        super(name, duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.likes = 0;
        owner = artist;
    }

    /**
     * overrides the method which checks if the song's albummatches the album
     * @param givenAlbum the album name
     * @return true if the song's album matches the album
     */
    @Override
    public boolean matchesAlbum(final String givenAlbum) {
        return this.getAlbum().equalsIgnoreCase(givenAlbum);
    }

    /**
     * overrides the method which checks if the song's tags match the tags
     * @param tagss the tags
     * @return true if the song's tags match the tags
     */
    @Override
    public boolean matchesTags(final ArrayList<String> tagss) {
        List<String> songTags = new ArrayList<>();
        for (String tag : this.getTags()) {
            songTags.add(tag.toLowerCase());
        }

        for (String tag : tagss) {
            if (!songTags.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * overrides the method which checks if the song's lyrics match the lyrics
     * @param lyricss the lyrics
     * @return true if the song's lyrics match the lyrics
     */
    @Override
    public boolean matchesLyrics(final String lyricss) {
        return this.getLyrics().toLowerCase().contains(lyricss.toLowerCase());
    }

    /**
     * overrides the method which checks if the song's genre matches the genre
     * @param genree the genre
     * @return true if the song's genre matches the genre
     */
    @Override
    public boolean matchesGenre(final String genree) {
        return this.getGenre().equalsIgnoreCase(genree);
    }

    /**
     * overrides the method which checks if the song's artist matches the artist
     * @param artistt the artist
     * @return true if the song's artist matches the artist
     */
    @Override
    public boolean matchesArtist(final String artistt) {
        return this.getArtist().equalsIgnoreCase(artistt);
    }

    /**
     * overrides the method which checks if the song's release year matches the release year
     * @param releaseYearr the release year
     * @return true if the song's release year matches the release year
     */
    @Override
    public boolean matchesReleaseYear(final String releaseYearr) {
        return filterByYear(this.getReleaseYear(), releaseYearr);
    }

    /**
     * the method checks if the song is from a time period
     * @param year the year
     * @param query the time period
     * @return true if the song matches the description
     */
    private static boolean filterByYear(final int year, final String query) {
        if (query.startsWith("<")) {
            return year < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return year > Integer.parseInt(query.substring(1));
        } else {
            return year == Integer.parseInt(query);
        }
    }

    /**
     * the method increments the number of likes
     */
    public void like() {
        likes++;
    }

    /**
     * the method decrements the number of likes
     */
    public void dislike() {
        likes--;
    }

    /**
     * overrides the toString method
     * @return the string representation of the song
     */
    @Override
    public String toString() {
        return getName() + " - " + getArtist();
    }
}
