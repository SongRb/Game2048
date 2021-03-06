package cn.edu.sjtu.seiee.songrb.game2048;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// Bluetooth Support

class DeviceAdapter extends BaseAdapter {
    private List<BluetoothDevice> list;
    private LayoutInflater mInflater;

    DeviceAdapter(Context context, List<BluetoothDevice> l) {
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
            convertView = mInflater.inflate(cn.edu.sjtu.seiee.songrb.game2048.R.layout.list_item, null);
            viewHolder = new ViewHolder((View) convertView.findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.list_child),
                    (TextView) convertView.findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.chat_msg));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.child.setBackgroundResource(cn.edu.sjtu.seiee.songrb.game2048.R.drawable.msgbox_rec);    //找不到msgbox_rec
        viewHolder.msg.setText(item.getName() + "\n" + item.getAddress());
        return convertView;
    }

    private class ViewHolder {
        View child;
        TextView msg;

        ViewHolder(View child, TextView msg) {
            this.child = child;
            this.msg = msg;
        }
    }
}
