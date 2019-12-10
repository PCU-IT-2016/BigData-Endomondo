package process.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import process.utils.base.Metrics;
import process.utils.base.Resource;
import process.utils.base.Statistic;

public class Workout implements Resource
{
    private long id;
    private long userId;
    private String sport;
    private String gender;
    private String url;

    private float averageHeartRate;
    private float averageSpeed;
    private float averageLatitude;
    private float averageLongitude;
    private float averageAltitude;
    private float distance;

    public Workout()
    {
        this.id = 0;
        this.userId = 0;
        this.sport = "";
        this.gender = "";
        this.url = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public float getDistance()
    {
        return this.distance;
    }

    @Override
    public String toString() {
        return "process.utils.Workout{" +
                "id=" + id +
                ", userId=" + userId +
                ", sport='" + sport + '\'' +
                ", gender='" + gender + '\'' +
                ", url='" + url + '\'' +
                ", longitude=" + this.averageLongitude +
                ", latitude=" + this.averageLatitude +
                ", altitude=" + this.averageAltitude +
                ", heartRate=" + this.averageHeartRate +
//                ", timestamp=" + Arrays.toString(timestamp) +
                ", speed=" + this.averageSpeed +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(float averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    public void decode(JSONObject obj) {
        this.id = (long) obj.get("id");

        this.userId = (long)obj.get("userId");

        this.setGender((String) obj.get("gender"));

        this.setSport((String) obj.get("sport"));

        this.averageHeartRate = Statistic.mean((JSONArray) obj.get("heart_rate"));

        JSONArray speed = (JSONArray) obj.get("speed");
        if (speed != null) {
            this.averageSpeed = Statistic.mean(speed);
        }
        else {
            this.averageSpeed = 0;
        }

        this.averageLatitude = Statistic.mean((JSONArray) obj.get("latitude"));

        this.averageLongitude = Statistic.mean((JSONArray) obj.get("longitude"));

        this.averageAltitude = Statistic.mean((JSONArray) obj.get("altitude"));

        this.distance = Metrics.euclideanDistance((JSONArray) obj.get("longitude"), (JSONArray) obj.get("latitude"));
    }

    @Override
    public String encode() {
        String encoded = this.gender + "," +
                         String.valueOf(this.averageSpeed) + "," +
                         String.valueOf(this.averageHeartRate) + "," +
                         String.valueOf(this.averageSpeed) + "," +
                         String.valueOf(this.averageLatitude) + "," +
                         String.valueOf(this.averageLongitude) + "," +
                         String.valueOf(this.averageAltitude);
        return encoded;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("userId", this.userId);
        jsonObject.put("gender", this.gender);
        jsonObject.put("sport", this.sport);
        jsonObject.put("latitude", this.averageLatitude);
        jsonObject.put("longitude", this.averageLongitude);
        jsonObject.put("altitude", this.averageAltitude);
        jsonObject.put("heart_rate", this.averageHeartRate);
        jsonObject.put("speed", this.averageSpeed);
        return jsonObject;
    }
}
