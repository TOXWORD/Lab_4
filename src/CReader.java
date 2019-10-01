import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CReader {

    private StringBuffer text;
    private StringBuffer changedText;
    private String pathIn = "input.txt";
    private String pathOut = "output.txt";

    private boolean hasBr;
    private boolean hasComm;
    boolean hasOp1Star;

    CReader() throws FileNotFoundException {

        text = new StringBuffer();
        changedText = new StringBuffer();
        hasBr = false;
        hasComm = false;
        hasOp1Star = false;

        File f = new File(pathIn);
        Scanner sc = new Scanner(f);

        while (sc.hasNextLine()) {
            text.append(sc.nextLine()).append('\n');
        }

        sc.close();

    }

    private void hasBracket(int pos) {
        char current = text.charAt(pos);

        if (current == '"' && text.charAt(pos - 1) == '\'' && text.charAt(pos + 1) == '\'') {
            return;
        }
        if (current == '"' && text.charAt(pos - 1) == '\\' && text.charAt(pos - 2) != '\\') {
            return;
        }
        if (current == '"' && hasComm) {
            return;
        }

        if (current == '"') {
            hasBr = !hasBr;
        }
    }

    private int isCommented(int pos) {
        char current = text.charAt(pos);

        if (current == '/' && text.charAt(pos + 1) == '/' && !hasBr) {
            hasComm = true;
            return pos + 2;
        }
        if (current == '\n') {
            hasComm = false;
        }
        return pos;
    }

    private int oneStarCommented(int pos) {
        char current = text.charAt(pos);

        if (current == '/' && text.charAt(pos + 1) == '*' && !hasBr) {
            hasOp1Star = true;
            return pos + 1;
        }
        if (hasOp1Star && current == '*' && text.charAt(pos + 1) == '/') {
            hasOp1Star = false;
            return pos + 2;
        }

        return pos;
    }

    public void delComments() {

        changedText = new StringBuffer();
        boolean hasOp2Star = false;

        char firstS;
        char curS;

        for (int i = 0; i < text.length() - 1; i++) {

            hasBracket(i);
            i = isCommented(i);
            i = oneStarCommented(i);

            curS = text.charAt(i);

            if (!(hasComm || hasOp1Star)) {
                changedText.append(curS);
            }
        }
    }

    public void printText() throws IOException {

        FileWriter f = new FileWriter(pathOut);
        f.write(changedText.toString());
        f.close();
    }

}
