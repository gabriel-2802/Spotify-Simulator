package app.command.types.users.creators;
import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;

/**
 * This class is used to remove an event
 */
@Getter
@Setter
public class  RemoveEvent extends Command {

    private String username;
    private String name;

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        if (user == null) {
            return new OutputBuilder().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        String message = user.removeEvent(getName());
        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }
}
