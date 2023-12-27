package app.command.types.users.creators;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.user.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import fileio.input.SongInput;

import java.util.ArrayList;
/**
 * Class for the addAlbum command
 */
@Getter
@Setter
public class  AddAlbum extends Command {

        private String username;
        private String name;
        private ArrayList<SongInput> songs;
        private String description;
        private int releaseYear;

        /**
         * execute method for the addAlbum command
         */
        @Override
        public ObjectNode execute() {
                User user = Admin.getUser(username);
                if (user == null) {
                     return new OutputBuilder().setNonUserCommand("addAlbum",
                             timestamp, username).build();
                }

                String message = user.addAlbum(getName(), getReleaseYear(),
                        getDescription(), getUsername(), getSongs());
                return new OutputBuilder().setMessageCommand(getCommand(), getUsername(),
                        getTimestamp(), message).build();
        }
}
