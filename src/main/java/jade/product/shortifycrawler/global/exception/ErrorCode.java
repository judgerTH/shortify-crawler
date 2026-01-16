package jade.product.shortifycrawler.global.exception;

public enum ErrorCode {

    // Gemini
    GEMINI_TEMPORARY(false),
    GEMINI_CRITICAL(true),

    // Summary
    SUMMARY_INVALID(true),
    SUMMARY_PROCESS_FAILED(true),

    // Infra
    DB_ERROR(true),
    UNKNOWN(true);

    private final boolean alert;

    ErrorCode(boolean alert) {
        this.alert = alert;
    }

    public boolean needAlert() {
        return alert;
    }
}
