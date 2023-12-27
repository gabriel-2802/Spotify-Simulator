package app.command.types.users;

import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BuyMerch extends Command {
    private String name;
    private String username;

    @Override
    public ObjectNode execute() {
        System.out.println("BuyMerch");
        return null;
    }
}
