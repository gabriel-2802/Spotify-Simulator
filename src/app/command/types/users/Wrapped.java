package app.command.types.users;

import app.command.types.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Wrapped extends Command {
    private String username;

    @Override
    public ObjectNode execute() {
        System.out.println("Wrapped");
        return null;
    }

}
