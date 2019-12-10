package process.utils.base;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class Statistic {

    /**
     * returns the sum value of array
     * @param numbers | array of float
     * @return float
     */
    public static float sum(float[] numbers) {
        float total = 0;
        for (float number : numbers) {
            total += number;
        }
        return total;
    }

    /**
     *
     * @param numbers | JSONArray
     * @return
     */
    public static float sum(JSONArray numbers) {
        float total = 0;
        for (Object number : numbers) {
            total += ((Number) number).floatValue();
        }
        return total;
    }

    /**
     * returns the average value of array
     * @param numbers | array of float
     * @return float
     */
    public static float mean(float[] numbers) {
        return sum(numbers) / numbers.length;
    }

    /**
     *
     * @param numbers | JSONArray
     * @return
     */
    public static float mean(JSONArray numbers) {
        return sum(numbers) / numbers.size();
    }

}
