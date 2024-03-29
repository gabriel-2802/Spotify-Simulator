package app.command.types.admin;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.utils.Enums;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * AddUser command class.
 */
@Getter
@Setter
public class  AddUser extends Command {

        private String username;
        private String city;
        private String type;
        private int age;

        /**
         * This method is used to execute the command
         * @return ObjectNode
         */
        @Override
        public ObjectNode execute() {
                Enums.UserType userType = switch (getType()) {
                        case "artist" -> Enums.UserType.ARTIST;
                        case "host" -> Enums.UserType.HOST;
                        default -> Enums.UserType.USER;
                };
                Admin admin = Admin.getInstance();
                String message = admin.addUser(getUsername(),
                        getAge(), getCity(), userType);

                return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();
        }
}
