package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to get top 5 artists
 */
@Getter
@Setter
public class  GetTop5Artists extends Command {
    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        List<String> artists = Admin.getTop5Artists();
        return new OutputBuilder<String>().setShowCommand(getCommand(), getTimestamp(),
                artists).build();
    }
}
