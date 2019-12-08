import process.analysis.UniqueSportCountProcess;
import process.preprocessing.RemoveUnknownProcess;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        UniqueSportCountProcess countSportProcess = new UniqueSportCountProcess();
        countSportProcess.run("input/real", "output/sport_count");
    }
}
