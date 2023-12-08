package app.command.types;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "command", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddAlbum.class, name = "addAlbum"),
        @JsonSubTypes.Type(value = AddAnnouncement.class, name = "addAnnouncement"),
        @JsonSubTypes.Type(value = AddEvent.class, name = "addEvent"),
        @JsonSubTypes.Type(value = AddMerch.class, name = "addMerch"),
        @JsonSubTypes.Type(value = AddPodcast.class, name = "addPodcast"),
        @JsonSubTypes.Type(value = AddRemoveInPlaylist.class, name = "addRemoveInPlaylist"),
        @JsonSubTypes.Type(value = AddUser.class, name = "addUser"),
        @JsonSubTypes.Type(value = Backward.class, name = "backward"),
        @JsonSubTypes.Type(value = ChangePage.class, name = "changePage"),
        @JsonSubTypes.Type(value = Command.class, name = "command"),
        @JsonSubTypes.Type(value = CreatePlaylist.class, name = "createPlaylist"),
        @JsonSubTypes.Type(value = DeleteUser.class, name = "deleteUser"),
        @JsonSubTypes.Type(value = Error.class, name = "error"),
        @JsonSubTypes.Type(value = GeneralCommand.class, name = "generalCommand"),
        @JsonSubTypes.Type(value = GetAllUsers.class, name = "getAllUsers"),
        @JsonSubTypes.Type(value = GetOnlineUsers.class, name = "getOnlineUsers"),
        @JsonSubTypes.Type(value = GetTop5Albums.class, name = "getTop5Albums"),
        @JsonSubTypes.Type(value = GetTop5Artists.class, name = "getTop5Artists"),
        @JsonSubTypes.Type(value = GetTop5Playlists.class, name = "getTop5Playlists"),
        @JsonSubTypes.Type(value = GetTop5Songs.class, name = "getTop5Songs"),
        @JsonSubTypes.Type(value = Like.class, name = "like"),
        @JsonSubTypes.Type(value = Load.class, name = "load"),
        @JsonSubTypes.Type(value = Next.class, name = "next"),
        @JsonSubTypes.Type(value = PlayPause.class, name = "playPause"),
        @JsonSubTypes.Type(value = Prev.class, name = "prev"),
        @JsonSubTypes.Type(value = PrintCurrentPage.class, name = "printCurrentPage"),
        @JsonSubTypes.Type(value = RemoveAlbum.class, name = "removeAlbum"),
        @JsonSubTypes.Type(value = RemoveAnnouncement.class, name = "removeAnnouncement"),
        @JsonSubTypes.Type(value = RemoveEvent.class, name = "removeEvent"),
        @JsonSubTypes.Type(value = RemovePodcast.class, name = "removePodcast"),
        @JsonSubTypes.Type(value = Repeat.class, name = "repeat"),
        @JsonSubTypes.Type(value = Search.class, name = "search"),
        @JsonSubTypes.Type(value = Select.class, name = "select"),
        @JsonSubTypes.Type(value = ShowAlbums.class, name = "showAlbums"),
        @JsonSubTypes.Type(value = ShowPlaylists.class, name = "showPlaylists"),
        @JsonSubTypes.Type(value = ShowPodcasts.class, name = "showPodcasts"),
        @JsonSubTypes.Type(value = ShowPreferredSongs.class, name = "showPreferredSongs"),
        @JsonSubTypes.Type(value = Shuffle.class, name = "shuffle"),
        @JsonSubTypes.Type(value = Status.class, name = "status"),
        @JsonSubTypes.Type(value = SwitchConnectionStatus.class, name = "switchConnectionStatus"),
        @JsonSubTypes.Type(value = SwitchVisibility.class, name = "switchVisibility"),
        @JsonSubTypes.Type(value = Follow.class, name = "follow"),
        @JsonSubTypes.Type(value = Forward.class, name = "forward")
})
@Data
public abstract class Command implements GeneralCommand {
    protected String command;
    protected int timestamp;
    public Command() {

    }
    @Override
    public abstract ObjectNode execute();
}
