package com.guhack.alpha.beaconchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * Displays basic information about beacon.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class ChatroomListAdapter extends BaseAdapter {

    private ArrayList<Chatroom> beacons;
    private LayoutInflater inflater;

    public ChatroomListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.beacons = new ArrayList<Chatroom>();
    }

    public void replaceWith(JSONObject newBeacons) {
        this.beacons.clear();

        Iterator iterator = newBeacons.keys();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            String name = "Notfound";
            try {

                name = ((JSONObject) newBeacons.get(key)).get("name").toString();
            } catch (JSONException e){
                //hello
            }
            beacons.add(new Chatroom(key, name));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beacons.size();
    }

    @Override
    public Chatroom getItem(int position) {
        return beacons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    private void bind(Chatroom beacon, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.chatroomDescription.setText(beacon.getName());//String.format("MAC: %s (%.2fm)", beacon.getMacAddress(), Utils.computeAccuracy(beacon)));
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.chatroom_row, parent, false);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView chatroomDescription;

        ViewHolder(View view) {
            chatroomDescription = (TextView) view.findViewById(R.id.chatroom_description);
        }
    }
}
