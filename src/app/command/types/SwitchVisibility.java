package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to switch the visibility of a playlist
 */
@Getter
@Setter
public class  SwitchVisibility extends Command {
    private String username;
    private int playlistId;

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        if (user == null) {
            return new OutputBuilder<>().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        String message = user.switchPlaylistVisibility(getPlaylistId());
        return new OutputBuilder<>().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }

}
