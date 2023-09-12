import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// A basic file reader for reading the highs core
public class FileRead {

    public String retrieveDataFromFile(String fileName)
    {
        String data = "";

        // Attempt to read from file and build concatenated String
        try {
            Scanner fileIn = new Scanner(new FileReader(fileName));

            while(fileIn.hasNext())
            {
                data += fileIn.nextLine();
            }

            fileIn.close();

        } catch(IOException e)
        {
            System.out.println(e);
        }
        return data;
    }
}
