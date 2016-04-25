package udpReliable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzhe on 2016/4/16.
 */
public class UdpReliableModle {
    public static int
            TYPE_CER_DATA=1,
    TYPE_CER_ACK=2,
    TYPE_VOICE=3,
    TYPE_DH_A=4,
    TYPE_DH_B=5;
    int type;
    byte[] data;
    JSONObject jsonObject=new JSONObject();
    public UdpReliableModle(int type,byte[]data) {
        this.type=type;
        this.data=data;

    }

    public UdpReliableModle(JSONObject object) throws JSONException {
        this.jsonObject=object;
        this.type=object.getInt("type");
        this.data= (byte[]) object.get("data");
    }
    public int getType(){
       return type;
    }
    public byte[] getData(){
        return data;
    }
    JSONObject getJsonObj() throws JSONException {
        jsonObject.put("type",type);
        jsonObject.put("data",data);

        return jsonObject;
    }
    String getJsonString() throws JSONException {
        return getJsonObj().toString();
    }
}
