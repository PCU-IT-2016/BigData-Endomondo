import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import process.analysis.GetRouteProcess;
import process.analysis.UniqueSportCountProcess;
import process.preprocessing.CleanFeatureProcess;
import process.preprocessing.RemoveUnknownProcess;
import process.utils.Workout;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        // Get route from recommendations (output from python)
        GetRouteProcess process = new GetRouteProcess();
        process.run("input/endomondo", "output/route");
    }
}