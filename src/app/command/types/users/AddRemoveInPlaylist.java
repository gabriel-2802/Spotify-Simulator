package app.command.types.users;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import app.utils.Enums;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to add or remove a song in a playlist
 */
@Getter
@Setter
public class  AddRemoveInPlaylist extends Command {
    private String username;
    private int playlistId;

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        if (user == null || !user.getConnectionStatus().equals(Enums.Connection.ONLINE)) {
            return new OutputBuilder().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        String message = user.addRemoveInPlaylist(getPlaylistId());
        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();

    }
}
