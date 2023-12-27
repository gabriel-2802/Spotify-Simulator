package app.command.types.users;

import app.command.types.Command;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UpdateRecommendations extends Command {
    private String username;
    private String recommendationType;

    @Override
    public ObjectNode execute() {
        System.out.println("UpdateRecommendations");
        return null;
    }
}
