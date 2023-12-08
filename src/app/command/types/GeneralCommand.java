package app.command.types;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * General command interface
 */
public interface GeneralCommand {
    /**
     * This method is used to execute the command
     * @return ObjectNode
     */
    ObjectNode execute();
}
