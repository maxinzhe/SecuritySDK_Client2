package com.ccit.security.sdk.clientDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mApplication;
import com.messagehandler.MessagePacketerOfClient;
import com.messagehandler.MessagePacketerOfPeer;
import com.servcie.VoipService;

import java.util.ArrayList;
import java.util.Map;

import udpReliable.JsonModel;

public class CallWindow extends Activity {
    IBinder localBinder;
    Button callButton;
    public static Handler handler;
    Button watchDataButton;
    Button hangupButton;
    Button acceptButton;
    VoipService mService;
    boolean mBound=false;
    ServiceConnection mConnection;
    TextView targetNameView;

    TextView watchDatatextView;
    ListView contactsListView;

    public static boolean isOnTop=false;

    String targetId;
    String targetName;
    @Override
    protected void onResume() {
        super.onResume();

        isOnTop=true;
        initListView();

        mApplication.voice_dialog_state=mApplication.DIALOG_STAE_READY;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnTop=false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_window);
        initBindService();
        initView();

        handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==0){
                    initListView();//refresh the contacts list
                }else if(msg.what==2){//2 for negative answer window
                    Log.i("test CallWindow","handler received msg==2,try to start A Call Window");
                    Intent intent=new Intent(CallWindow.this,AnswerWindow.class) ;
                    intent.putExtra("caller_name",mApplication.targetName);
                    startActivity(intent);
                    Log.i("test CallWindow","handler received msg==2,try to start A Call Window");
                }else if(msg.what==10){//set the positivew view
                    initView();
                }
            }

        };

        mApplication.handler=handler;

            watchDatatextView = (TextView) findViewById(R.id.encrypted_editText);


    }
    private void testInitList(){
        if(mApplication.contactsList==null){
            mApplication.contactsList=new ArrayList<Map<String, String>>();
        }
        //Map<String,String> map1=new HashMap<String, String>();
        //map1.put("name","Tom");
        //map1.put("id","00001");
        //Map<String,String> map2=new HashMap<String,String >();
        //map2.put("name","Marc");
        //map2.put("id","00002");
        //mApplication.contactsList.add(map1);
        //mApplication.contactsList.add(map2);
    }
    private  void initListView(){
        Log.i("test","initListView");
        testInitList();
        String [] from ={"name","id"};
        int [] itemId={R.id.name_list_view,R.id.id_list_view};
        if(mApplication.contactsList!=null){

            SimpleAdapter adapter=new SimpleAdapter(CallWindow.this,mApplication.contactsList,R.layout.list_view_layout,from,itemId);
            contactsListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound=false;
        }
    }
    private void initBindService(){

        mConnection=  new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                VoipService.LocalBinder binder= (VoipService.LocalBinder)service;
                mService=binder.getService();
                if(mService==null){
                    Log.e("test","failed to get mService");
                }else{
                    Log.i("test","get mService!: "+mService.toString());
                }
                mBound=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound=false;
            }
        };

        Intent intent=new Intent(CallWindow.this, VoipService.class);
        boolean isBound=CallWindow.this.bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        if(isBound){
            Log.i("test","service is bound");
        }else {
            Log.e("test","service fail to bind");
        }
    }
    private void  initView(){
        callButton=(Button)findViewById(R.id.imageButton2);
        callButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mApplication.player.play();///////////////
                if(targetId==null){
                    //new AlertDialog to remind
                    AlertDialog.Builder builder=new NoContactAlertDialog(CallWindow.this);
                    builder.show();
                    return;
                }
                mApplication.isAcive=true;
                new MessagePacketerOfClient(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.t = JsonModel.MESSAGE_TYPE.TYPE_P2P_REQUEST;
                        modeltosend.s1 =mApplication.deviceId;
                        modeltosend.s2 =targetId;

                    }
                }.send();

               // mService.startThreadtoPear();
                setContentView(R.layout.activity_on_talk_acitivty);

                mApplication.voice_dialog_state=mApplication.DIALOG_STAE_POSITIVE;
                initView2();
                /**
                 *  set active for the DH
                 */
            }
        });
        contactsListView =(ListView)findViewById(R.id.listView);
        contactsListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("test","clicked in the listView");
                Map<String,Object> map= (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(CallWindow.this,(String)map.get("name"),Toast.LENGTH_SHORT).show();

                targetId=(String)map.get("id");
                mApplication.targetId=targetId;
                targetName=(String)map.get("name");
                mApplication.targetName=targetName;
            }
        });

        //initListView() is in onResume;
    }

    private void initView2(){
      hangupButton=(Button)  findViewById(R.id.hangup_button);
        hangupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mApplication.setHangupFlag();
                new MessagePacketerOfPeer(){
                    @Override
                    protected void setModel() {
                        super.setModel();
                        modeltosend.t= JsonModel.MESSAGE_TYPE.TYPE_HANG_UP;

                    }
                }.send();
                if(mApplication.recordThread!=null&&mApplication.recordThread.isAlive()){
                    mApplication.recordThread.interrupt();
                }
                mService.stopThreadtoPeer();

                setContentView(R.layout.activity_call_window);
                initView();

                initListView();
            }
        });
       targetNameView=(TextView)findViewById(R.id.textview_receiverName);
        targetNameView.setText("正在呼叫"+targetName);
        watchDataButton=(Button)findViewById(R.id.watch_data_button_from_caller);
        watchDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new DisplayDialogBuilder(CallWindow.this).create();
                alertDialog.show();
                WindowManager.LayoutParams params =
                        alertDialog.getWindow().getAttributes();
                params.width = 1000;
                params.height = 1500 ;
                alertDialog.getWindow().setAttributes(params);

            }
        });
    }

}
