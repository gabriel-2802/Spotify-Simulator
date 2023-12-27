package app.command.types.users.search;
import app.command.OutputBuilder;
import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import app.Admin;
import app.user.User;

/**
 * This class is used to select a song
 */
@Getter
@Setter
public class  Select extends Command {

    private String username;
    private int itemNumber;

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

        String message = user.select(getItemNumber());
        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();

    }
}
