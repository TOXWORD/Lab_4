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
    private boolean hasOp1Star;

    public CReader() throws FileNotFoundException {

        text = new StringBuffer();
        changedText = new StringBuffer();
        hasBr = false;
        hasComm = false;
        hasOp1Star = false;

        File f = new File(pathIn);
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                text.append(sc.nextLine()).append('\n');
            }
        }
    }

    private void hasBracket(int pos){
        char current = 0;
        char preCurrent = 0;
        char afterCurrent = 0;
        char pre2Current = 0;

        try{
            current = text.charAt(pos);
            preCurrent = text.charAt(pos - 1);
            afterCurrent = text.charAt(pos + 1);
            pre2Current = text.charAt(pos - 2);

        }
        catch(Exception e){

        }

        if (current == '"' && preCurrent == '\'' && afterCurrent == '\'') {
            return;
        }
        if (current == '"' && preCurrent == '\\' && pre2Current != '\\') {
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
        char current = 0;
        char afterCurrent = 0;

        try{
            current = text.charAt(pos);
            afterCurrent = text.charAt(pos + 1);
        }
        catch(Exception e){

        }

        if (current == '/' && afterCurrent == '/' && !hasBr) {
            hasComm = true;
            if(pos < text.length() - 2) {
                return pos + 2;
            }
        }
        if (current == '\n') {
            hasComm = false;
        }
        return pos;
    }

    private int oneStarCommented(int pos) {
        char current = 0;
        char afterCurrent = 0;

        try{
            current = text.charAt(pos);
            afterCurrent = text.charAt(pos + 1);
        }
        catch(Exception e){

        }
        if (current == '/' && afterCurrent == '*' && !hasBr) {
            hasOp1Star = true;
            if(pos < text.length() - 1) {
                return pos + 1;
            }
        }
        if (hasOp1Star && current == '*' && afterCurrent == '/') {
            hasOp1Star = false;
            if(pos < text.length() - 2) {
                return pos + 2;
            }
        }
        return pos;
    }

    public void delComments(){
        changedText = new StringBuffer();

        for (int i = 0; i < text.length() - 1; i++) {

            hasBracket(i);
            i = isCommented(i);
            i = oneStarCommented(i);

            if (!(hasComm || hasOp1Star)) {
                changedText.append(text.charAt(i));
            }
        }
    }

    public void printText() throws IOException {

        try (FileWriter f = new FileWriter(pathOut)) {
            f.write(changedText.toString());
        }
    }
}
