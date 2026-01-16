package jade.product.shortifycrawler.global.exception;

public class GeminiTemporaryException extends GeminiException {

    public GeminiTemporaryException(String message) {
        super(ErrorCode.GEMINI_TEMPORARY, message);
    }

    public GeminiTemporaryException(String message, Throwable cause) {
        super(ErrorCode.GEMINI_TEMPORARY, message, cause);
    }
}
