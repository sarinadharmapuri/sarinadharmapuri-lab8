
public class TooSmallText extends Exception {

    public TooSmallText(int inputError) {
        super("Only found " + inputError + " words.");
    }

}