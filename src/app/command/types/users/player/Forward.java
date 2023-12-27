package app.command.types.users.player;

import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

/**
 * This class is used to forward a message
 */
@Data
public class Forward extends Command {

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

                String message = user.forward();
                return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();
        }
}
