package jade.product.shortifycrawler.global.exception;

public abstract class GeminiException extends ShortifyException {

    protected GeminiException(ErrorCode code, String message) {
        super(code, message);
    }

    protected GeminiException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
