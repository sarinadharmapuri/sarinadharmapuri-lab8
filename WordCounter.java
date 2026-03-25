import java.util.regex.*;

public class WordCounter {

    public int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        Pattern regex = Pattern.compile("\\w+");
        Matcher regexMatcher = regex.matcher(text);
        int count = 0;
        boolean stopwordFound = false;


        while (regexMatcher.find()) {
            System.out.println("I just found the word: " + regexMatcher.group());

            if (stopword != null) {
                if (regexMatcher.find().equals(stopword)) {
                    stopwordFound = true;
                    break;
                }
            }
            count++;
        } 

        // if stopword was not found, invalidstopwordexception
        if (stopwordFound == false && stopword != null) {
            throw new InvalidStopwordException("Stopword not found");
        }

        if (count < 5) {
            throw new TooSmallText("Text is less than 5 words");
        }

        return count;
        // check if count > 5, if not TooSmallText exception
    }

    public StringBuffer processFile(String input) throws EmptyFileException {
        // check if file can be opened, if not user re-enters filename
        BufferedReader reader;
        
        while (reader == null) {
            try {
                reader = new BufferedReader(new FileReader(input));
            } catch (IOException e) {
                System.out.println("File cannot be opened, please re-enter the filename");

            }
        }

        // if file is empty, empty file exception

        StringBuffer result = new StringBuffer();
        
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append(System.lineSeperator());
            }

            if (line == null) {
                throw new EmptyFileException(input + "is empty");
            }
            return result;
        }
    }

    public static void main(String[] args) {
        
    }

}