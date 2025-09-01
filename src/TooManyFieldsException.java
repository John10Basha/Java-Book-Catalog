
public class TooManyFieldsException extends Exception {
	
	public TooManyFieldsException(String message, String record, String fileName) {
		super(message + ": " + record + " in file " + fileName);
	}

}
