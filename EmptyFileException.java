import java.io.*;


public class EmptyFileException extends IOException {

    public EmptyFileException(String errorMessage) {
        super(errorMessage);
    }
}