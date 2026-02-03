package Friday;

/**
 * Represents a custom exception used by the Friday application
 * to indicate user-facing errors and invalid operations.
 */
public class FridayException extends Exception {
    public FridayException(String msg) {
        super(msg);
    }
}
