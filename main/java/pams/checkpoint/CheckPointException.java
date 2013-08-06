package pams.checkpoint;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-8-3
 * Time: ÉÏÎç11:23
 * To change this template use File | Settings | File Templates.
 */
public class CheckPointException extends Exception {
    private static final long serialVersionUID = 1L;

    public CheckPointException() {
        super();
    }
    public CheckPointException(String message) {
        super(message);
    }
    public CheckPointException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
    public CheckPointException(Throwable rootCause) {
        super(rootCause);
    }
    public Throwable getRootCause() {
        return getCause();
    }
}
