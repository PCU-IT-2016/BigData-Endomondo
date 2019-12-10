package process.utils.base;

import org.json.simple.JSONObject;

public interface Resource {
    public void decode(JSONObject jsonObject);
    public String encode();
    public JSONObject toJson();
}
