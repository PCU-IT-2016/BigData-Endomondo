package process.analysis;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetRouteProcess {
    public static class TokenizerMapper extends Mapper<Object, Text, Text, NullWritable> {

        private Text outputKey = new Text();
        private NullWritable out = NullWritable.get();
        private JSONParser parser = new JSONParser();

        private ArrayList<JSONObject> recommendations = new ArrayList<>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);

            try
            {
                File file=new File("input/recommendation/recommendation.json");    //creates a new file instance
                FileReader fr=new FileReader(file);   //reads the file
                BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
                String line = "";
                while((line=br.readLine())!=null)
                {
                    line = line.replace("'", "\"");
                    try {
                        JSONObject obj = (JSONObject) parser.parse(line);
                        this.recommendations.add(obj);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                fr.close();    //closes the stream and release the resources
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            Workout workout = new Workout();
            String val = value.toString().replace("'", "\"");

            try {
                JSONObject obj = (JSONObject) parser.parse(val);

                for (JSONObject rec : recommendations) {
                    if (String.valueOf((Long) obj.get("id")).equals((String) rec.get("id"))) {
                        outputKey.set(obj.toJSONString());
                        context.write(outputKey, out);
//                        recommendations.remove(rec);
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static class GetProcessReducer extends Reducer<Text,IntWritable,Text,NullWritable> {
        private Text result = new Text();
        private NullWritable out = NullWritable.get();

        public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, out);
        }
    }

    public void run(String inputPath, String outputPath) throws Exception {
        // handles "path already exist" exception.
        try {
            FileUtils.cleanDirectory(new File(outputPath));
            FileUtils.deleteDirectory(new File(outputPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Count Gender");

        job.setJarByClass(GetRouteProcess.class);

        job.setMapperClass(GetRouteProcess.TokenizerMapper.class);
        job.setReducerClass(GetRouteProcess.GetProcessReducer.class);

        // MAPPER KEY & VALUE CLASS
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
    }
}
