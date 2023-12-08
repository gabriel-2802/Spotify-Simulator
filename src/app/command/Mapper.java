package app.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

/**
 * Mapper class for JSON
 */
@Getter
public final class Mapper {
    public static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    private Mapper() {
        throw new IllegalStateException("Utility class");
    }
}
