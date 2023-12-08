package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import fileio.input.EpisodeInput;

import java.util.ArrayList;

/**
 * This class is used to add a podcast
 */
@Getter
@Setter
public class  AddPodcast extends Command {
    private String username;
    private String name;
    private ArrayList<EpisodeInput> episodes;

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        if (user == null) {
            return new OutputBuilder<>().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        String message = user.addPodcast(getName(),
                getUsername(), getEpisodes());
        return new OutputBuilder<>().setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message).build();
    }
}
