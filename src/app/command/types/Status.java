package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.player.PlayerStats;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to get the status of a user
 */
@Getter
@Setter
public class  Status extends Command {

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

        PlayerStats stats = user.getPlayerStats();
        return new OutputBuilder().setCommand(getCommand(), getUsername(),
                getTimestamp()).setStats(stats).build();

        }
}
