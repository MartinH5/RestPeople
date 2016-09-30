package exceptions;

/**
 *
 * @author Martin
 */
public class ValidationErrorException extends Exception {

    /**
     * Creates a new instance of <code>ValidationErrorException</code> without
     * detail message.
     */
    public ValidationErrorException() {
    }

    /**
     * Constructs an instance of <code>ValidationErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ValidationErrorException(String msg) {
        super(msg);
    }
}
