package jade.product.shortifycrawler.global.exception;

public abstract class ShortifyException extends RuntimeException {

    private final ErrorCode errorCode;

    protected ShortifyException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected ShortifyException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public boolean needAlert() {
        return errorCode.needAlert();
    }
}
