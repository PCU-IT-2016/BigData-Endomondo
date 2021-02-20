package process;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import process.utils.Workout;
import process.utils.base.Files;

import java.io.File;
import java.io.IOException;

public class NearestNeighborsProcess
{
    public static class TokenizerMapper extends Mapper<Object, Text, Text, NullWritable>
    {
        //baca user id dan sport yang akan di prediksi
        private String line = Files.ReadLine("C:\\Users\\Sutik\\IdeaProjects\\BigData-Endomondo-master\\Big Data\\EndomondoPrediction\\input\\userid.txt");
        private String PredictUserId = line.substring(0, line.indexOf(","));
        private String PredictSport = line.substring(line.indexOf(",") + 1, line.length());

        private Text outputKey = new Text();
        private NullWritable nullWritable = NullWritable.get();
        private JSONParser parser = new JSONParser();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            String val = value.toString().replace("'", "\"");

            try
            {
                Workout wo = new Workout();
                JSONObject temp = (JSONObject) parser.parse(val);

                wo.decode(temp);

                //pisahkan data yang ingin di prediksi dan yang menjadi dataset
                //dengan membuang data yang memiliki user id yang sama dan
                //mengambil yang memiliki sport yang sama
                if(wo.getUserId() != Long.parseLong(PredictUserId))
                {
                    if(wo.getSport().equals(PredictSport))
                    {
                        outputKey.set(wo.toJson().toJSONString());
                    }
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }

    public class NearestNeighborsReducer extends Reducer<Text,IntWritable,Text,NullWritable>
    {
        //hitung jarak antar objek dengan yang rata2 objet yang diprediksi

        private Text result = new Text();
        private NullWritable out = NullWritable.get();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
        {
            context.write(result, out);
        }
    }

    public void run(String inputPath, String outputPath) throws Exception
    {
        // handles "path already exist" exception.
        try
        {
            FileUtils.cleanDirectory(new File(outputPath));
            FileUtils.deleteDirectory(new File(outputPath));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Nearest Neighbors");

        job.setJarByClass(NearestNeighborsProcess.class);

        job.setMapperClass(NearestNeighborsProcess.TokenizerMapper.class);
        job.setReducerClass(NearestNeighborsProcess.NearestNeighborsReducer.class);

        // MAPPER KEY & VALUE CLASS
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);

    }
}
