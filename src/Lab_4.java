import java.io.FileNotFoundException;
import java.io.IOException;

public class Lab_4 {
    public static void main(String[] args) {

        try{
            CReader c = new CReader();
            c.delComments();
            c.printText();

        }
        catch (FileNotFoundException e) {
            System.out.println("FNF");
        }
        catch (IOException e) {
            System.out.println("IOE");
        }

    }
}
