package app.command.types.users.pagination;

import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PreviousPage extends Command {
    private String username;

    @Override
    public ObjectNode execute() {
        System.out.println("PreviousPage");
        return null;
    }
}
