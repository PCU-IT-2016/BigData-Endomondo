package process.utils.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Files
{
    public static String ReadLine(String path)
    {
        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(path))))
        {
            String line = "";
            while ((line = buffer.readLine()) != null)
            {
                return line;
            }
            return line;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
