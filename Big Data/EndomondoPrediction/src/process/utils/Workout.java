package process.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import process.utils.base.Resource;

import java.util.Arrays;

public class Workout implements Resource
{
    private long id;
    private long userId;
    private String sport;
    private String gender;
    private String url;

    private double[] longitude;
    private double[] latitude;
    private double[] altitude;
    private JSONArray heartRate;
    private long[] timestamp;
    private JSONArray speed;

    private float averageHeartRate;
    private float averageSpeed;

    public Workout()
    {
        this.id = 0;
        this.userId = 0;
        this.sport = "";
        this.gender = "";
        this.url = "";

        this.longitude = new double[1];
        this.latitude = new double[1];
        this.altitude = new double[1];
//        this.heartRate = new int[1];
        this.timestamp = new long[1];
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
                ", longitude=" + Arrays.toString(longitude) +
                ", latitude=" + Arrays.toString(latitude) +
                ", altitude=" + Arrays.toString(altitude) +
                ", heartRate=" + heartRate.toString() +
                ", timestamp=" + Arrays.toString(timestamp) +
                ", speed=" + speed.toString() +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double[] getLongitude() {
        return longitude;
    }

    public void setLongitude(double[] longitude) {
        this.longitude = longitude;
    }

    public double[] getLatitude() {
        return latitude;
    }

    public void setLatitude(double[] latitude) {
        this.latitude = latitude;
    }

    public double[] getAltitude() {
        return altitude;
    }

    public void setAltitude(double[] altitude) {
        this.altitude = altitude;
    }

    public long[] getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long[] timestamp) {
        this.timestamp = timestamp;
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
        this.setId((long) obj.get("id"));
        this.setGender((String) obj.get("gender"));

        this.heartRate = ((JSONArray) obj.get("heart_rate"));
        int totalHeartRate = 0;
        for (Object heartRate : this.heartRate) {
            totalHeartRate += ((Long) heartRate).intValue();
        }
        this.averageHeartRate = totalHeartRate / this.heartRate.size();

        this.speed = (JSONArray) obj.get("speed");
        if (this.speed != null) {
            float totalSpeed = 0;
            for (Object speed : this.speed) {
                totalSpeed += ((Double) speed).floatValue();
            }
            this.averageSpeed = totalSpeed / this.speed.size();
        }
        else {
            this.averageSpeed = 0;
        }
        this.setSport((String) obj.get("sport"));
    }

    @Override
    public String encode() {
        String encoded = this.gender + "," +
                         String.valueOf(this.averageSpeed) + "," +
                         String.valueOf(this.averageHeartRate) + "," +
                         String.valueOf(this.speed);
        return encoded;
    }
}
