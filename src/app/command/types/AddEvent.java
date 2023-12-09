package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import app.user.utils.Event;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * AddEvent command class.
 */
@Getter
@Setter
public class  AddEvent extends Command {

        private String username;
        private String name;
        private String description;
        private String date;

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

                Event newEvent = new Event(getName(),
                        getDate(), getDescription());
                String message = user.addEvent(newEvent);

                return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();
        }
}
