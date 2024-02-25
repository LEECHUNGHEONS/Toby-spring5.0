package main.vol1_chlee.ch7.sqlservice.exception;

public class SqlRetrievalFailureException extends RuntimeException {
	public SqlRetrievalFailureException() {
		super();
	}

	public SqlRetrievalFailureException(String message) {
		super(message);
	}

	public SqlRetrievalFailureException(Throwable cause) {
		super(cause);
	}

	public SqlRetrievalFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
