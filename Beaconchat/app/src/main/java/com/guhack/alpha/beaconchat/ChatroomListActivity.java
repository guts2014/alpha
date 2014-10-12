package com.guhack.alpha.beaconchat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ChatroomListActivity extends Activity {
    ;
    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private BeaconManager beaconManager;
    private ChatroomListAdapter adapter;
    private View progress;

    private boolean send = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        progress = findViewById(R.id.chat_list_progress);
        ((TextView) findViewById(R.id.chatroom_list_username)).setText(UserManager.getInstance().getUsername());
        findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Configure device list.
        adapter = new ChatroomListAdapter(this);
        ListView list = (ListView) findViewById(R.id.device_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ChatroomListActivity.this, ChatroomActivity.class);
                Chatroom room = adapter.getItem(i);
                intent.putExtra("NAME", room.getName());
                intent.putExtra("ID", room.getId());
                startActivity(intent);
                //startActivity(new Intent(ChatroomListActivity.this, ChatroomActivity.class));
            }
        });

        // Configure BeaconManager.
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                // Note that results are not delivered on UI thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Note that beacons reported here are already sorted by estimated
                        // distance between device and beacon.
//                            getActionBar().setSubtitle("Found beacons: " + beacons.size());
                        if (send) {
                            send = false;
                            findChatrooms(beacons);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();

        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if device supports Bluetooth Low Energy.
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
            return;
        }

        // If Bluetooth is not enabled, let user enable it.
        if (!beaconManager.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            connectToService();
        }
    }

    @Override
    protected void onStop() {
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {
//                Log.d(TAG, "Error while stopping ranging", e);
        }

        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            } else {
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void connectToService() {

//        adapter.replaceWith(Collections.<Beacon>emptyList());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Toast.makeText(ChatroomListActivity.this, "Cannot start ranging, something terrible happened",
                            Toast.LENGTH_LONG).show();
//                        Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    public void findChatrooms(List<Beacon> beacons) {
        JSONArray array = new JSONArray();
        try {
            for (Beacon beacon : beacons) {

                array.put((new JSONObject()).put("id", beacon.getMacAddress()));
            }
            JSONObject postData = new JSONObject(
                    /*"{\"beacons\":[{\"id\":\"c0:82:62:85:8e:ae\"}, {\"id\":\"ec:8f:96:10:f1:30\"}]}"
                    */);

            postData.put("beacons", array);
            Log.i("array", postData.toString());

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://beaconchat-env.elasticbeanstalk.com/names";

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            adapter.replaceWith(response);
                            progress.setVisibility(View.INVISIBLE);
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
        } catch (JSONException e) {
            //do something
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        progress.setVisibility(View.VISIBLE);
    }
}
