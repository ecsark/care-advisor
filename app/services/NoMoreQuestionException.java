package services;

/**
 * User: ecsark
 * Date: 2/15/15
 * Time: 22:55
 */
public class NoMoreQuestionException extends Exception {
    public NoMoreQuestionException() {
    }

    public NoMoreQuestionException(String s) {

    }

    public NoMoreQuestionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMoreQuestionException(Throwable cause) {
        super(cause);
    }
}
