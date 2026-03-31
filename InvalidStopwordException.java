
public class InvalidStopwordException extends Exception {

    public InvalidStopwordException(String inputError) {
        super("Couldn't find stopword: " + inputError);
    }

}