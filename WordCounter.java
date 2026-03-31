import java.util.regex.*;
import java.io.*;

public class WordCounter {

    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher regexMatcher = regex.matcher(text);
        int count = 0;
        boolean stopwordFound = false;


        while (regexMatcher.find()) {
            String word = regexMatcher.group();
            count++;
            System.out.println(count);
            if (stopword != null && word.equals(stopword) && count >= 5) {
                stopwordFound = true;
                break;
            }

        } 

        if (stopword == null) {
            stopwordFound = true;
        }

        if (count < 5) {
            throw new TooSmallText(count);
        }

        // if stopword was not found, invalidstopwordexception
        if (stopwordFound == false) {
            throw new InvalidStopwordException(stopword);
        }

        return count;
        // check if count > 5, if not TooSmallText exception
    }

    public static StringBuffer processFile(String input) throws EmptyFileException {
        // check if file can be opened, if not user re-enters filename
        BufferedReader reader = null;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        
        while (reader == null) {
            try {
                reader = new BufferedReader(new FileReader(input));
            } catch (IOException e) {
                //System.out.println("File cannot be opened, please re-enter the filename");
                try {
                    input = inputReader.readLine();
                } catch (IOException a) {
                    //System.out.println("Error reading file path");
                }

            }
        }

        // if file is empty, empty file exception

        StringBuffer result = new StringBuffer();
        
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            //System.out.println("Error processing the file");
            //throw new EmptyFileException(input + " was enpty");
        } 


        if (result.length() == 0) {
            //return new StringBuffer("");
            throw new EmptyFileException(input);
        }
        
        return result;
    }

    public static void main(String[] args) throws IOException {
        WordCounter counter = new WordCounter();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String option = null;
        // while loop until option 1 or 2 is chosen

        while (option == null || (!option.equals("1") && !option.equals("2"))) {
            System.out.println("To process a file, enter 1 or to process text enter 2");
            option = inputReader.readLine();

        }

        StringBuffer text = new StringBuffer();

        if (option.equals("1")) {
            String path = args[0];

            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                System.out.println(e);
                text = new StringBuffer("");
            }
        } else {
            text = new StringBuffer(args[0]);
        }
        String stopword = null;
        if (args.length > 1) {
            stopword = args[1];
        } 
        int count = 0;

        try {
            count = processText(text, stopword);
            System.out.println("Found " + count + " words.");
        } catch (InvalidStopwordException e) {
            System.out.println(e);
        
        System.out.println("Please provide a new stopword");
        String newStopword = inputReader.readLine();
            try {
            count = 0;
            count = processText(text, newStopword);
            System.out.println("Found " + count + " words.");
            } catch (InvalidStopwordException a) {
            System.out.println(a);
            } catch (TooSmallText b) {
            System.out.println(b);
            }
    } catch (TooSmallText c) {
        System.out.println(c);
    }

    }

}