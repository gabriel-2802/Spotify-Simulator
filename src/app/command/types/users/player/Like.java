package app.command.types.users.player;
import app.Admin;
import app.command.types.Command;
import app.user.User;
import app.command.OutputBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
/**
 * This class is used to like a song
 */
@Getter
@Setter
public class  Like extends Command {

    private String username;

    /**
     * execute method for the addAlbum command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        if (user == null) {
            return new OutputBuilder().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        String message = user.like();

        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }
}
