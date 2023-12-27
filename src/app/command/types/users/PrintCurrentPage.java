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
 * This class is used to print the current page
 */
@Getter
@Setter
public class  PrintCurrentPage extends Command {

    private String username;

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

        String message;
        if (user.getConnectionStatus() == Enums.Connection.OFFLINE) {
            message = getUsername() + " is offline.";
        } else {
            user.updatePage();
            message = user.getPage().toString();
        }

        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
        }
}
