package app.command;

import app.player.PlayerStats;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * Class used to build the output
 * @param <T> the type of the ArrayList we want to add to the output
 */
public class OutputBuilder<T> implements Builder {
    private final ObjectNode output;
    public OutputBuilder() {
        output = Mapper.OBJ_MAPPER.createObjectNode();
    }

    /**
     * This method is used to set the command, username and timestamp
     * @param command the command
     * @param username the username
     * @param timestamp the timestamp
     * @return OutputBuilder
     */
    public OutputBuilder<T> setCommand(final String command, final String username,
                                       final int timestamp) {
        output.put("command", command);
        output.put("user", username);
        output.put("timestamp", timestamp);
        return this;
    }

    /**
     * This method is used to set the command, username, timestamp and message
     * @param command the command
     * @param username the username
     * @param timestamp the timestamp
     * @param message the message
     * @return OutputBuilder
     */
    public OutputBuilder<T> setMessageCommand(final String command,
                                              final String username, final int timestamp,
                                              final String message) {
        output.put("command", command);
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", message);
        return this;
    }

    /**
     * this method is used to add the search results to the output
     * @param results the search results
     * @return OutputBuilder
     */
    public OutputBuilder<T> setSearchResults(final List<T> results) {
        output.put("results", Mapper.OBJ_MAPPER.valueToTree(results));
        return this;
    }

    /**
     * This method is used to set the command, timestamp and results
     * @param command the command
     * @param timestamp the timestamp
     * @param results the results
     * @return OutputBuilder
     */
    public OutputBuilder<T> setShowCommand(final String command, final int timestamp,
                                           final List<T> results) {
        output.put("command", command);
        output.put("timestamp", timestamp);
        output.put("result", Mapper.OBJ_MAPPER.valueToTree(results));
        return this;
    }

    /**
     * this method is used to set the result field in the output
     * @param results the results
     * @return OutputBuilder
     */
    public OutputBuilder<T> setResult(final List<T> results) {
        output.put("result", Mapper.OBJ_MAPPER.valueToTree(results));
        return this;
    }

    /**
     * This method is used to set the command, timestamp and message for a non existing user
     * @param command the command
     * @param timestamp the timestamp
     * @return OutputBuilder
     */
    public OutputBuilder<T> setNonUserCommand(final String command, final int timestamp,
                                              final String username) {
        output.put("command", command);
        output.put("user", username);
        output.put("timestamp", timestamp);
        output.put("message", "The username " + username + " doesn't exist.");
        return this;
    }

    /**
     * sets the stats field in the output
     * @param stats the stats
     * @return OutputBuilder
     */
    public OutputBuilder<T> setStats(final PlayerStats stats) {
        output.put("stats", Mapper.OBJ_MAPPER.valueToTree(stats));
        return this;
    }

    /**
     * This method is used to return the ObjectNode we built
     * @return ObjectNode we built
     */
    @Override
    public ObjectNode build() {
        return output;
    }

}
