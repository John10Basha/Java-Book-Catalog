
public class UnknownGenreException extends Exception {
	
	 public UnknownGenreException(String message, String record, String fileName) {
	        super(message + ": " + record + " in file " + fileName);
	    }

}
