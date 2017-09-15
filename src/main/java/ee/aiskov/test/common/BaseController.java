package ee.aiskov.test.common;

import ee.aiskov.test.common.exception.NoResourceFoundException;
import org.slf4j.Logger;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

public class BaseController {
    private static final Logger LOGGER = getLogger(BaseController.class);

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(CannotCreateTransactionException.class)
    public void serviceUnavailable() {
        LOGGER.error("Database connection lost.");
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public void notFoundHandler() {
        LOGGER.debug("Resource not found");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public void badRequestHandler() {
        LOGGER.debug("Bad request");
    }
}
