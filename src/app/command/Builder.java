package app.command;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Builder interface for JSON
 */
public interface Builder {
    /**
     * This method is used to build the JSON object NODE
     * @return ObjectNode
     */
    ObjectNode build();
}
