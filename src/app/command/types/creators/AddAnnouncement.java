package app.command.types.creators;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to add an announcement
 */
@Getter
@Setter
public class  AddAnnouncement extends Command {
    private String username;
    private String name;
    private String description;

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

        String message = user.addAnnouncement(getName(), getDescription());
        return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }
}
