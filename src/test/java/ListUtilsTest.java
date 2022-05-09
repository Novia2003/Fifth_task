import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ListUtilsTest {
    private final String inputFileName;
    private final String outputFileName;

    public ListUtilsTest(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    @Parameterized.Parameters
    public static List<String[]> cases() {
        return Arrays.asList(new String[]{"src/test/resources/inputForTextWithQuotationMarks.txt",
                        "src/test/resources/outputForTextWithQuotationMarks.txt"},
                new String[]{"src/test/resources/inputForTextWithDashAndHyphen.txt",
                        "src/test/resources/outputForTextWithDashAndHyphen.txt"},
                new String[]{"src/test/resources/inputForTextWithOtherPunctuationMarks.txt",
                        "src/test/resources/outputForTextWithOtherPunctuationMarks.txt"},
                new String[]{"src/test/resources/inputForTextWithNumbers.txt",
                        "src/test/resources/outputForTextWithNumbers.txt"},
                new String[]{"src/test/resources/inputForPlainText.txt",
                        "src/test/resources/outputForPlainText.txt"});
    }

    @Test
    public void readWordsForTreeFromFile() throws FileNotFoundException {
        List<String> list = ListUtils.readWordsForTreeFromFile(inputFileName);
        List<String> expectedList = ListUtils.readWordsFromOutputFile(outputFileName);

        Assert.assertEquals(expectedList, list);
    }
}