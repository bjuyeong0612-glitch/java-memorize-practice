package exception;

public class InvalidPaymentDayException extends RuntimeException {
	public InvalidPaymentDayException(String message) {
		super(message);
	}
}
