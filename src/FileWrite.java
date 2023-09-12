import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// A basic file writer to write the high score
public class FileWrite {

    public void writeContentsToFile(String fileName, String data, boolean appendData)
    {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName, appendData));

            writer.println(data);

            writer.close();

        } catch(IOException e)
        {
            System.out.println("" + e);
        }
    }
}
