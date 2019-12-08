package process.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import process.utils.base.Resource;
import process.utils.base.Statistic;

import java.util.Arrays;

public class Workout implements Resource
{
    private long id;
    private long userId;
    private String sport;
    private String gender;
    private String url;

//    private JSONArray longitude;
//    private JSONArray latitude;
//    private double[] altitude;
//    private JSONArray heartRate;
//    private long[] timestamp;
//    private JSONArray speed;

    private float averageHeartRate;
    private float averageSpeed;
    private float averageLatitude;
    private float averageLongitude;

    public Workout()
    {
        this.id = 0;
        this.userId = 0;
        this.sport = "";
        this.gender = "";
        this.url = "";

//        this.longitude = new double[1];
//        this.latitude = new double[1];
//        this.altitude = new double[1];
//        this.heartRate = new int[1];
//        this.timestamp = new long[1];
//        this.speed = new double[1];
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

    @Override
    public String toString() {
        return "process.utils.Workout{" +
                "id=" + id +
                ", userId=" + userId +
                ", sport='" + sport + '\'' +
                ", gender='" + gender + '\'' +
                ", url='" + url + '\'' +
//                ", longitude=" + longitude.toString() +
//                ", latitude=" + latitude.toString() +
//                ", altitude=" + Arrays.toString(altitude) +
//                ", heartRate=" + heartRate.toString() +
//                ", timestamp=" + Arrays.toString(timestamp) +
//                ", speed=" + speed.toString() +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public double[] getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double[] longitude) {
//        this.longitude = longitude;
//    }
//
//    public double[] getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double[] latitude) {
//        this.latitude = latitude;
//    }

//    public double[] getAltitude() {
//        return altitude;
//    }
//
//    public void setAltitude(double[] altitude) {
//        this.altitude = altitude;
//    }
//
//    public long[] getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(long[] timestamp) {
//        this.timestamp = timestamp;
//    }

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

        this.averageHeartRate = Statistic.mean((JSONArray) obj.get("heart_rate"));

        JSONArray speed = (JSONArray) obj.get("speed");
        if (speed != null) {
            this.averageSpeed = Statistic.mean(speed);
        }
        else {
            this.averageSpeed = 0;
        }

        this.setSport((String) obj.get("sport"));

//        this.latitude = (JSONArray) obj.get("latitude");
        this.averageLatitude = Statistic.mean((JSONArray) obj.get("latitude"));

//        this.longitude = (JSONArray) obj.get("longitude");
        this.averageLongitude = Statistic.mean((JSONArray) obj.get("longitude"));
    }

    @Override
    public String encode() {
        String encoded = this.gender + "," +
                         String.valueOf(this.averageSpeed) + "," +
                         String.valueOf(this.averageHeartRate) + "," +
                         String.valueOf(this.averageSpeed) + "," +
                         String.valueOf(this.averageLatitude) + "," +
                         String.valueOf(this.averageLongitude);
        return encoded;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("userId", this.userId);
        jsonObject.put("gender", this.gender);
        jsonObject.put("sport", this.sport);
        jsonObject.put("lat", this.averageLatitude);
        jsonObject.put("lon", this.averageLongitude);
        jsonObject.put("heart_rate", this.averageHeartRate);
        jsonObject.put("speed", this.averageSpeed);
        return jsonObject;
    }
}
