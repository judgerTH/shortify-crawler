package jade.product.shortifycrawler.global.exception;

public class GeminiCriticalException extends GeminiException {

    public GeminiCriticalException(String message) {
        super(ErrorCode.GEMINI_CRITICAL, message);
    }

    public GeminiCriticalException(String message, Throwable cause) {
        super(ErrorCode.GEMINI_CRITICAL, message, cause);
    }
}
