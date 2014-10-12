package com.guhack.alpha.beaconchat;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ChatroomActivity extends Activity {
    private String beacon, device;
    int lastMessage = 0;
    private ChatroomMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        beacon = getIntent().getStringExtra("ID");
        device = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        // Configure device list.
        adapter = new ChatroomMessageAdapter(this);
        ListView list = (ListView) findViewById(R.id.chatmsg_list);
        list.setAdapter(adapter);
        findViewById(R.id.leave_chat_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ((TextView) findViewById(R.id.chatroom_name)).setText(getIntent().getStringExtra("NAME"));
        try {
            connect();
        } catch (JSONException e) {

        }
        findViewById(R.id.send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendMessage(((EditText) findViewById(R.id.message_field)).getText().toString());
                } catch (JSONException e){

                }
            }
        });
    }

    public void connect() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beaconchat-env.elasticbeanstalk.com/connect";

        JSONObject postdata = new JSONObject();
        postdata.put("beacon", beacon);
        JSONObject user = new JSONObject();
        user.put("deviceID", device);
        user.put("name", UserManager.getInstance().getUsername());
        postdata.put("user", user);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, postdata, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
 // {"message":{"id":2,"text":"What did he mean by \"operator precedence\"?!","timestamp":1412985600000},"user":{"deviceID":"12345","name":"Martin Podlubny","email":null},"beacon":{"id":"c0:82:62:85:8e:ae","name":null}}
                            JSONArray messages = response.getJSONArray("messages");
                            for (int index = 0; index < messages.length(); index++){
                                adapter.addMessage(messages.getJSONObject(index).getJSONObject("user").getString("name")
                                + " : " + messages.getJSONObject(index).getJSONObject("message").getString("text"));
                            }

                        } catch (JSONException e){
                            //catch me
                        }
                        //mTxtDisplay.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        String s;
                        s = "";
                    }
                });
// Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    public void getMessages() throws JSONException {
      //  {"beacon": {"id":"c0:82:62:85:8e:ae"}, "user":{"deviceID": "12347", "name": "Reinis"}, "filter": {"from":1}}
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beaconchat-env.elasticbeanstalk.com/messages";

        JSONObject postdata = new JSONObject();
        JSONObject beaconObject = new JSONObject(), filter = new JSONObject(), user = new JSONObject();
        beaconObject.put("id", beacon);
        user.put("deviceID", device);
        user.put("name", UserManager.getInstance().getUsername());
        filter.put("from", 1);//change to actual msg
        postdata.put("beacon", beaconObject);
        postdata.put("user", user);
        postdata.put("filter", filter);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, postdata, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String s;
                        s = "";
                        //mTxtDisplay.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        String s;
                        s = "";
                    }
                });
// Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    public void sendMessage(String message) throws JSONException {
//         {
//            "message": {
//                "text": "yolo",
//                        "dt": 1412985600000
//            },
//            "user": {
//                "deviceID": "12345",
//                        "name": "Martin Podlubny",
//                        "email": null
//            },
//            "beacon": {
//                "id": "c0:82:62:85:8e:ae",
//                        "name": null
//            }
//        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beaconchat-env.elasticbeanstalk.com/send";

        JSONObject postdata = new JSONObject();
        JSONObject beaconObject = new JSONObject(), messageObject = new JSONObject(), user = new JSONObject();
        beaconObject.put("id", beacon);
        user.put("deviceID", device);
        user.put("name", UserManager.getInstance().getUsername());
        user.put("email", "");
        messageObject.put("text", message);//change to actual msg
        postdata.put("beacon", beaconObject);
        postdata.put("user", user);
        postdata.put("message", messageObject);

        adapter.addMessage(UserManager.getInstance().getUsername() + " : "  + message);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, postdata, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ChatroomActivity.this, "message sent", Toast.LENGTH_SHORT).show();
                        ((EditText) findViewById(R.id.message_field)).setText("");
                        //mTxtDisplay.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        String s;
                        s = "";
                    }
                });
// Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }
}
