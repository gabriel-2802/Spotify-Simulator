package app.command.types.users.search;
import app.Admin;
import app.command.OutputBuilder;
import app.command.types.Command;
import app.searchBar.Filters;
import app.user.User;
import app.utils.Enums;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import fileio.input.FiltersInput;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to search
 */
@Getter
@Setter
public class  Search extends Command {

    private String username;;
    private FiltersInput filters;
    private String type;

    public Search() {
    }

    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    @Override
    public ObjectNode execute() {
        User user = Admin.getUser(getUsername());
        Filters searchFilters = new Filters(getFilters());
        String searchType = getType();
        if (user == null) {
            return new OutputBuilder().setNonUserCommand(getCommand(), getTimestamp(),
                    getUsername()).build();
        }

        List<String> results = new ArrayList();
        String message;
        if (user.getConnectionStatus() == Enums.Connection.ONLINE) {
            results = user.search(searchFilters, searchType);
            message = "Search returned " + results.size() + " results";
        } else {
            message = getUsername() + " is offline.";
        }

        OutputBuilder builder = new OutputBuilder().
                setMessageCommand(getCommand(), getUsername(),
                getTimestamp(), message);
        builder.setSearchResults(results);

        return builder.build();
    }

}
