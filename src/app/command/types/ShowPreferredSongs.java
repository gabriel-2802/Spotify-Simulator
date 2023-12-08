package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * This class is used to show preferred songs
 */
@Getter
@Setter
public class  ShowPreferredSongs extends Command {

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
        List<String> songs = user.showPreferredSongs();
        return new OutputBuilder<String>().setCommand(getCommand(), getUsername(),
                getTimestamp()).setResult(songs).build();
        }
}
