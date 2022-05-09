import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        SimpleTree tree = new SimpleTree();
        tree.insertWordsOfTextFromFile("src/main/resources/input.txt");
        tree.printTree();
    }
}