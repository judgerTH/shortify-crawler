package jade.product.shortifycrawler.global.exception;

public class SummaryInvalidException extends ShortifyException {

    public SummaryInvalidException(String message) {
        super(ErrorCode.SUMMARY_INVALID, message);
    }
}
