package BIA.Business.Impact.Analysis.Exception;

public class NotSufficientRightsException extends RuntimeException {
    public NotSufficientRightsException() {
        super("You don't have enough rights to perform this operation");
    }

    public NotSufficientRightsException(String message) {
        super(message);
    }
}
