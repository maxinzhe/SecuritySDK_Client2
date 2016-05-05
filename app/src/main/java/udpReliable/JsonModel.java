package udpReliable;

import com.google.gson.Gson;
import com.messagemodel.BasicModel;
import com.messagemodel.client2server.LOGIN_Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzhe on 2016/4/16.
 */
public class JsonModel {
    public  enum MESSAGE_TYPE{
        /*
        From client to server
         */

        /**
         * TYPE_LOGIN string1:id number(phone number);string2:name
         *
         */
        TYPE_LOGIN,

        /**
         * TYPE_LOGOFF string1:id
         */
        TYPE_LOGOFF,

        /**
         * TYPE_P2PpREQUESET string1:target id in string form
         */
        TYPE_P2P_REQUEST,

        /**
         * string1: wrong info.server should manage and return the TYPE_P2P_CANCEL
         */
        TYPE_CER_WRONG,

        /**
         * just tell the server it's ok,for future reliable hole making
         */
        TYPE_P2P_OK,

        /**
         * when dh finished ,return this to server to just report.
         */
        TYPE_DH_OK,

        /*
        From server to client
         */

        /**
         *   list:the HashMap<String,String>: id for key,name for value
         */
        TYPE_CONTACTS_LIST,

        /**
         * cancel the p2p config which is running
         */
        TYPE_P2P_CANCEL,

        /**
         *list:the offline people id and name
         */
        TYPE_CONTACTS_OFFLINE,


        /**
         *list:the online people id and name
         */
        TYPE_CONTACTS_ONlINE,

        /**
         *    string1:the peer ip,port: peer port
         *
         */
        TYPE_HOLE_INFO,



        /*
        for  P2P
         */

        TYPE_HOLE_P2P,

        /**
         * string1:the  certification for exchange
         */
        TYPE_CER_DATA,

        TYPE_CER_ACK,
        TYPE_VOICE,
        TYPE_DH_A,
        TYPE_DH_B
    } ;

    MESSAGE_TYPE type;
    byte[] data;
    Gson gson=new Gson();

    JSONObject jsonObject;
    BasicModel basicModel=new BasicModel();

    public JsonModel(byte[] json){
        data=json;
        basicModel=gson.fromJson(new String(json),BasicModel.class);
        type=basicModel.type;
    }

    public JsonModel(BasicModel basicModel){
        data=gson.toJson(basicModel).getBytes();
        this.basicModel=basicModel;
        this.type=basicModel.type;
    }

    public MESSAGE_TYPE getType(){
        return type;
    }
    public BasicModel getBasicModel(){
        return basicModel;
    }
    public byte[] getBytes(){
        return data;
    }



}
