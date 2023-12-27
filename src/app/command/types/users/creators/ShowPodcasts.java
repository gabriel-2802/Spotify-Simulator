package app.command.types.users.creators;
import app.Admin;
import app.audio.Collections.PodcastOutput;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to show podcasts
 */
@Getter
@Setter
public class  ShowPodcasts extends Command {

    private String username;
    private int playlistId;

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
        public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        if (user == null) {
            return new OutputBuilder().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        List<PodcastOutput> podcasts = user.showPodcasts();
        return new OutputBuilder().setCommand(getCommand(), getUsername(),
                getTimestamp()).setPodcastResult(podcasts).build();
        }
}
