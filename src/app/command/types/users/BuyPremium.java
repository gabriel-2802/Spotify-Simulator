package app.command.types.users;

import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BuyPremium extends Command {
    private String username;

    @Override
    public ObjectNode execute() {
        System.out.println("BuyPremium");
        return null;
    }
}
