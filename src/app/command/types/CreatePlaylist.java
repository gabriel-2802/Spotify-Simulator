package app.command.types;
import app.Admin;
import app.command.OutputBuilder;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to create a playlist
 */
@Getter
@Setter
public class  CreatePlaylist extends Command {

        private String username;
        private String playlistName;

        /**
         * execute method for the addAlbum command
         * @return ObjectNode
         */
        @Override
        public ObjectNode execute() {
                User user = Admin.getUser(getUsername());
                if (user == null) {
                        return new OutputBuilder<>().setNonUserCommand(getCommand(), getTimestamp(),
                                getUsername()).build();
                }

                String message = user.createPlaylist(getPlaylistName(),
                        getTimestamp());

                return new OutputBuilder<>().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();
        }
}
