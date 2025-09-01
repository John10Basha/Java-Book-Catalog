
public class TooFewFieldsException extends Exception {
	
	public TooFewFieldsException(String message, String record, String fileName) {
        super(message + ": " + record + " in file " + fileName);
    }


}
