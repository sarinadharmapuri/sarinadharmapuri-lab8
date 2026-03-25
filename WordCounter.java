
public class WordCounter {

    public int processText(StringBuffer text, String stopword) {
        Pattern regex = Pattern.compile("your regular expression here");
        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            System.out.println("I just found the word: " + regexMatcher.group());
        } 

        // if stopword was not found, invalidstopwordexception

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