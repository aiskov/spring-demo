package ee.aiskov.test.common.exception;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class NoResourceFoundException extends RuntimeException {
    public NoResourceFoundException(String id, Class<?> entityType) {
        super(format("Resource with id %s not found %s", id,
                ofNullable(entityType).map(Class::getSimpleName).orElse("UNKNOWN")));
    }
}
