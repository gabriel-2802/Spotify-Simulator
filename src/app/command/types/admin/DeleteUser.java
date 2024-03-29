package app.command.types.admin;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to delete a user
 */
@Getter
@Setter
public class  DeleteUser extends Command {

    private String username;

    /**
     * execute method for the addAlbum command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        Admin admin = Admin.getInstance();
        String message = admin.deleteUser(getUsername());
        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }
}
