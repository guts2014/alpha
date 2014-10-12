package com.guhack.alpha.beaconchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Displays basic information about beacon.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class ChatroomMessageAdapter extends BaseAdapter {

    private ArrayList<String> messages;
    private LayoutInflater inflater;

    public ChatroomMessageAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.messages = new ArrayList<String>();
    }

    public void addMessage(String message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public String getItem(int position) {
        return messages.get(position);
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

    private void bind(String message, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.chatMessages.setText(message);//String.format("MAC: %s (%.2fm)", beacon.getMacAddress(), Utils.computeAccuracy(beacon)));
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.chat_row, parent, false);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView chatMessages;

        ViewHolder(View view) {
            chatMessages = (TextView) view.findViewById(R.id.chat_message);
        }
    }
}
