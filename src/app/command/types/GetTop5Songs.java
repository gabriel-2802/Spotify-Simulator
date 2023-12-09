package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to get top 5 songs
 */
@Getter
@Setter
public class  GetTop5Songs extends Command {
    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        Admin admin = Admin.getInstance();
        List<String> songs = admin.getTop5Songs();
        return new OutputBuilder<String>().setShowCommand(getCommand(), getTimestamp(),
                songs).build();

            }
}
