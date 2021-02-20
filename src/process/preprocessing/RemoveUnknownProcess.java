package process.preprocessing;

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
import process.analysis.UniqueGenderCountProcess;

import java.io.File;
import java.io.IOException;

public class RemoveUnknownProcess {
    public static class TokenizerMapper extends Mapper<Object, Text, Text, NullWritable> {

        private Text outputKey = new Text();
        private NullWritable out = NullWritable.get();
        private JSONParser parser = new JSONParser();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String val = value.toString().replace("'", "\"");
            try {
                JSONObject obj = (JSONObject) parser.parse(val);
                if (!obj.get("gender").toString().equals("unknown")){
                    outputKey.set(val);
                    context.write(outputKey, out);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static class RemoveUnknownReducer extends Reducer<Text,IntWritable,Text,NullWritable> {
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

        Job job = Job.getInstance(conf, "Removes unknown gender");

        job.setJarByClass(UniqueGenderCountProcess.class);

        job.setMapperClass(RemoveUnknownProcess.TokenizerMapper.class);
        job.setReducerClass(RemoveUnknownProcess.RemoveUnknownReducer.class);

        // MAPPER KEY & VALUE CLASS
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.waitForCompletion(true);
    }
}
