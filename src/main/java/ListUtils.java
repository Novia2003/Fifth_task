import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListUtils {

    public static List<String> readWordsForTreeFromFile(String fileName) throws FileNotFoundException {
        List<String> words;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            words = new ArrayList<>();
            scanner.useDelimiter("(\\s-\\s|[,.?!:;()'\"]|\\s|\\d)+");

            while (scanner.hasNext()) {
                words.add(scanner.next());
            }
        }
        return words;
    }

    public static List<String> readWordsFromOutputFile(String fileName) throws FileNotFoundException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            lines = new ArrayList<>();
            scanner.useDelimiter("\\s");

            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        }
        return lines;
    }
}

