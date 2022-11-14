package ExceptionHandler;

import Util.ErrorWriter;

public class Error extends RuntimeException {

    public Error(String message) {
        super(message);
        new ErrorWriter(message);
    }
}
