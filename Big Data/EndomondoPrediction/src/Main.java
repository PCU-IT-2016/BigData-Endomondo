import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import process.analysis.UniqueSportCountProcess;
import process.preprocessing.CleanFeatureProcess;
import process.preprocessing.RemoveUnknownProcess;
import process.utils.Workout;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        CleanFeatureProcess cleanFeatureProcess = new CleanFeatureProcess();
        cleanFeatureProcess.run("input/real", "output/clean_feature");
    }
}
