
package exception;

/**
 *
 * @author ongyongen
 */
public class InvalidLoginException extends Exception {


    public InvalidLoginException() {
    }

    public InvalidLoginException(String msg) {
        super(msg);
    }
}