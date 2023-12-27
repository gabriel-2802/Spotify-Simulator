package app.command.types.users;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to change the page
 */
@Getter
@Setter
public class  ChangePage extends Command {

        private String username;
        private String nextPage;

        /**
         * execute method for the addAlbum command
         * @return ObjectNode
         */
        @Override
        public ObjectNode execute() {
                User user = Admin.getUser(getUsername());
                if (user == null) {
                        return new OutputBuilder().setNonUserCommand(getCommand(), getTimestamp(),
                                getUsername()).build();
                }

                String message = user.changePage(getNextPage());
                return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();

        }
}
