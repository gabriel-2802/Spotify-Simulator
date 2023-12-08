package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to add an announcement
 */
@Getter
@Setter
public class  Backward extends Command {

    private String username;

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

        String message = user.backward();
        return new OutputBuilder<>().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
        }
}
