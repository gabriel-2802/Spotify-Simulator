package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to get all users
 */
@Getter
@Setter
public class  GetOnlineUsers extends Command {
    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        Admin admin = Admin.getInstance();
        List<String> onlineUsers = admin.getOnlineUsers();
        return new OutputBuilder().setShowCommand(getCommand(), getTimestamp(),
                onlineUsers).build();
    }
}
