package app.command.types.users.creators;
import app.Admin;
import app.audio.Collections.AlbumOutput;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to show albums
 */
@Getter
@Setter
public class  ShowAlbums extends Command {

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

        List<AlbumOutput> albums = user.showAlbums();
        return new OutputBuilder().setCommand(getCommand(), getUsername(),
                getTimestamp()).setAlbumResult(albums).build();

    }
}
