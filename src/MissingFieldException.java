
public class MissingFieldException extends Exception {
	
	public MissingFieldException(String message, String record, String fileName) {
        super(message + ": " + record + " in file " + fileName);
    }

}
