package app.command.types.creators;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import app.user.utils.Merch;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * AddMerch command class.
 */
@Getter
@Setter
public class  AddMerch extends Command {

        private String username;
        private String name;
        private String description;
        private int price;

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

                Merch newMerch = new Merch(getName(),
                        getPrice(), getDescription());
                String message = user.addMerch(newMerch);

                return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();
        }
}
