package process.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectParser
{
    private String Filename;
    private Gson gson = new Gson();

    public  ObjectParser(String filename)
    {
        this.Filename = filename;
    }

    //convert JSON Object into Java Object
    public ArrayList<Workout> FromJson()
    {
        ArrayList<Workout> workouts = new ArrayList<>();

        String line = "";

        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(this.Filename))))
        {
            while ((line = buffer.readLine()) != null)
            {
                // Convert JSON String to Java Object
                workouts.add(this.gson.fromJson(line, Workout.class));
            }
            return workouts;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
