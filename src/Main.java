import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import process.NearestNeighborsProcess;
import process.analysis.UniqueSportCountProcess;
import process.preprocessing.CleanFeatureProcess;
import process.preprocessing.RemoveUnknownProcess;
import process.utils.Workout;
import process.utils.base.Files;

public class Main
{
    public static void main(String[] args) throws Exception
    {
//        CleanFeatureProcess cleanFeatureProcess = new CleanFeatureProcess();
//        cleanFeatureProcess.run("C:\\Users\\Sutik\\Downloads\\Dataset big data\\endomondoHR_proper.json", "output/clean_feature");

        NearestNeighborsProcess knn = new NearestNeighborsProcess();
        knn.run("C:\\Users\\Sutik\\Downloads\\Dataset big data\\endomondoHR_proper.json", "output/nearest_neighbor");

    }
}