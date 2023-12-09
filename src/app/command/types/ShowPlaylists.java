package app.command.types;
import app.Admin;
import app.audio.Collections.PlaylistOutput;
import app.command.OutputBuilder;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to show playlists
 */
@Getter
@Setter
public class  ShowPlaylists extends Command {

    private String username;

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

        List<PlaylistOutput> playlists = user.showPlaylists();
        return new OutputBuilder().setCommand(getCommand(), getUsername(),
                getTimestamp()).setPlaylistResult(playlists).build();
    }
}
