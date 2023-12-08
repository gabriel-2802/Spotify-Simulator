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
public class  GetAllUsers extends Command {

            /**
            * This method is used to execute the command
            * @return ObjectNode
            */
    @Override
        public ObjectNode execute() {
        List<String> results = Admin.getAllUsers();
        return new OutputBuilder<String>().setShowCommand(getCommand(), getTimestamp(),
                results).build();
        }
}
