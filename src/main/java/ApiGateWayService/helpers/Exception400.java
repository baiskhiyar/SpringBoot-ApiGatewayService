package ApiGateWayService.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exception400 extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(Exception400.class);

    public Exception400(String message) {
        super(message);
        logger.error(message);
    }

    public Exception400(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
