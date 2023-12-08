package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import app.utils.Enums;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to switch the connection status of a user
 */
@Getter
@Setter
public class  SwitchConnectionStatus extends Command {
    private String username;

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        String message;
        if (user == null) {
            message = "The username " + getUsername() + " doesn't exist.";
        } else if (!(user.getType() == Enums.UserType.USER)) {
            message = getUsername() + " is not a normal user.";
        } else {
            message = user.switchConnectionStatus();
        }

        return new OutputBuilder<>().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }
}
