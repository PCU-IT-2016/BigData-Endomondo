package process.utils;

import com.google.gson.Gson;

import java.io.*;

public class ObjectParser
{
    private String Filename;

    public ObjectParser()
    {
        this.Filename = "";
    }

    public ObjectParser(String filename)
    {
        this.Filename = filename;
    }

    //count object/line in json file text
    private int CountObject()
    {
        if (this.Filename.trim() == "")
        {
            return -1;
        }

        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(this.Filename))))
        {
            String line = "";
            int counter = 0;
            while ((line = buffer.readLine()) != null)
            {
                counter++;
            }
            return counter;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    //convert JSON Object into Java Object
    public Workout[] FromJson()
    {
        if(this.Filename.trim() == "")
        {
            return null;
        }

        Gson gson = new Gson();
        Workout[] workouts = new Workout[this.CountObject()];
        int counter = 0;

        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(this.Filename))))
        {
            String line = "";
            while ((line = buffer.readLine()) != null)
            {
                // Convert JSON String to Java Object
                workouts[counter] = gson.fromJson(line, Workout.class);
                counter++;
            }
            return workouts;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //convert JSON Object into Java Object
    public Workout FromJson(String json_object)
    {
        Gson gson = new Gson();
        Workout workout;

        try {
            workout = gson.fromJson(json_object, Workout.class);
            return workout;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //convert JSON Object into Java Object
    public void SplitSport(String sport, String output_path)
    {
        Gson gson = new Gson();
        Workout workouts = new Workout();

        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(this.Filename))))
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_path, true)))
            {
                String line = "";
                while ((line = buffer.readLine()) != null)
                {
                    // Convert JSON String to Java Object
                    workouts = gson.fromJson(line, Workout.class);

                    if(workouts.getSport().equals(sport) && !workouts.getGender().equals("unknown"))
                    {
                        writer.newLine();
                        writer.write(line);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
