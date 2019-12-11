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

import java.io.File;
import java.io.IOException;

public class GetRouteProcess {
    public static class TokenizerMapper extends Mapper<Object, Text, Text, NullWritable> {

        private Text outputKey = new Text();
        private NullWritable out = NullWritable.get();
        private JSONParser parser = new JSONParser();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            Workout workout = new Workout();
            String val = value.toString().replace("'", "\"");

            try {
                JSONObject obj = (JSONObject) parser.parse(val);
                workout.decode(obj);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            outputKey.set(workout.getGender());
            context.write(outputKey, out);
        }
    }

    public static class CountGenderReducer extends Reducer<Text,IntWritable,Text,NullWritable> {
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

        job.setJarByClass(UniqueGenderCountProcess.class);

        job.setMapperClass(UniqueGenderCountProcess.TokenizerMapper.class);
        job.setReducerClass(UniqueGenderCountProcess.CountGenderReducer.class);

        // MAPPER KEY & VALUE CLASS
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
    }
}
