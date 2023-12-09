package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to get top 5 albums
 */
@Getter
@Setter
public class  GetTop5Albums extends Command {
    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        Admin admin = Admin.getInstance();
        List<String> results = admin.getTop5Albums();
        return new OutputBuilder<String>().setShowCommand(getCommand(), getTimestamp(),
                results).build();
    }
}
