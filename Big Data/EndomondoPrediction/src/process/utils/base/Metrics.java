package process.utils.base;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class Metrics {

    /**
     * Calculates distance between 2 points with minkowski p=2 aka euclidean
     * @param point1
     * @param point2
     * @return the distance between 2 points
     */
    public static float euclideanDistance(JSONArray point1, JSONArray point2) {

        ArrayList<Float> deltas = new ArrayList<>();

        for (int dimension = 0; dimension < point1.size(); dimension++)
        {
            deltas.add((float)
                    Math.pow(((Double) point1.get(dimension)).floatValue() - ((Double) point2.get(dimension)).floatValue(), 2));
        }

        float sum = 0;
        for (float number : deltas) {
            sum += number;
        }

        return (float) Math.sqrt(sum);
    }

}
