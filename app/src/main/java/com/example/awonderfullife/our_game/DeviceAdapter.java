package com.example.awonderfullife.our_game;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 程涌潇 on 2016/5/23.
 */
public class DeviceAdapter extends BaseAdapter {
    private List<BluetoothDevice> list;
    private LayoutInflater mInflater;

    public DeviceAdapter(Context context, List<BluetoothDevice> l) {
        list = l;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        BluetoothDevice item = list.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder((View) convertView.findViewById(R.id.list_child),
                    (TextView) convertView.findViewById(R.id.chat_msg));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.child.setBackgroundResource(R.drawable.msgbox_rec);    //找不到msgbox_rec
        viewHolder.msg.setText(item.getName() + "\n" + item.getAddress());
        return convertView;
    }

    class ViewHolder {
        protected View child;
        protected TextView msg;

        public ViewHolder(View child, TextView msg) {
            this.child = child;
            this.msg = msg;
        }
    }
}
