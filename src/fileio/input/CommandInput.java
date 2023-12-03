package fileio.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public final class CommandInput {
    private String command;
    private String username;
    private Integer timestamp;
    private String type; // song / playlist / podcast //adduser
    private FiltersInput filters; // pentru search
    private Integer itemNumber; // pentru select
    private Integer repeatMode; // pentru repeat
    private Integer playlistId; // pentru add/remove song
    private String playlistName; // pentru create playlist
    private Integer seed; // pentru shuffle
    private Integer age; // for add user
    private String city; // for add user
    private ArrayList<SongInput> songs; //for add album
    private String name; // for add album
    private String description; // for add album
    private Integer releaseYear; // for add album
    private String date; // for add event
    private Integer price; //for add merch
    private ArrayList<EpisodeInput> episodes; // for add podcast
    private String nextPage; // for next page




    public CommandInput() {
    }

    @Override
    public String toString() {
        return "CommandInput{" +
                "command='" + command + '\'' +
                ", username='" + username + '\'' +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", filters=" + filters +
                ", itemNumber=" + itemNumber +
                ", repeatMode=" + repeatMode +
                ", playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                ", seed=" + seed +
                '}';
    }
}
