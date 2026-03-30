import java.util.regex.*;
import java.io.*;

public class WordCounter {

    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
        Pattern regex = Pattern.compile("\\w+");
        Matcher regexMatcher = regex.matcher(text);
        int count = 0;
        boolean stopwordFound = false;


        while (regexMatcher.find()) {
            System.out.println("I just found the word: " + regexMatcher.group());

            count++;

            if (stopword != null) {
                if (regexMatcher.group().equals(stopword)) {
                    stopwordFound = true;
                    break;
                }
            }
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

    public static StringBuffer processFile(String input) throws EmptyFileException {
        // check if file can be opened, if not user re-enters filename
        BufferedReader reader = null;
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        
        while (reader == null) {
            try {
                reader = new BufferedReader(new FileReader(input));
            } catch (IOException e) {
                System.out.println("File cannot be opened, please re-enter the filename");
                try {
                    input = inputReader.readLine();
                } catch (IOException a) {
                    System.out.println("Error reading file path");
                }

            }
        }

        // if file is empty, empty file exception

        StringBuffer result = new StringBuffer();
        
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append(System.lineSeparator());
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error processing the file");
        }
            if (result.length() == 0) {
                throw new EmptyFileException(input + "is empty");
            }
            return result;
    }

    public static void main(String[] args) throws IOException {
        WordCounter counter = new WordCounter();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String option = null;
        // while loop until option 1 or 2 is chosen

        while (option == null) {
            System.out.println("To process a file, enter 1 or to process text enter 2");
            option = inputReader.readLine();

            if (option.equals("1") || option.equals("2")) {
                break;
            } else {
                System.out.println("Please enter option again");
            }
        }

        System.out.println("If applicable, enter a stopword");
        String stopword = inputReader.readLine();
        StringBuffer fileText = new StringBuffer();

        if (option.equals("1")) {
            String input = args[0];

            try {
                fileText = counter.processFile(input);
            } catch (EmptyFileException e) {
                System.out.println(e);
                fileText = new StringBuffer("");
            }
        }


        if (option.equals("2")) {
            System.out.println("Please enter the text that will be processed");
            StringBuffer inputText = new StringBuffer(inputReader.readLine());

            int count = 0;

            try {
                count = counter.processText(inputText, stopword);
                System.out.println("The word count was: " + count);
            } catch (InvalidStopwordException e) {
                System.out.println(e);
                System.out.println("Please enter a new stopword");
                String newStopword = inputReader.readLine();

                try {
                count = counter.processText(inputText, newStopword);
                System.out.println("The word count was: " + count);
                } catch (InvalidStopwordException a) {
                System.out.println(a);
                } catch (TooSmallText b) {
                System.out.print(b);
                } 
            } catch (TooSmallText c) {
                System.out.println(c);
            }
        }

        // then ask for stopword
            // process text or file, raise necessary exceptions
        // if invalidstopwordexception is found, only provide one chance to reenter stopword
    }

}