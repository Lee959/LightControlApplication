package com.example.lightcontrolapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Adapter for displaying device list
 */
public class DeviceAdapter extends ArrayAdapter<DeviceInfoBean> {

    private Context context;
    private List<DeviceInfoBean> deviceList;

    public DeviceAdapter(Context context, List<DeviceInfoBean> deviceList) {
        super(context, R.layout.item_device, deviceList);
        this.context = context;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_device, parent, false);

            holder = new ViewHolder();
            holder.deviceName = convertView.findViewById(R.id.device_name);
            holder.deviceStatus = convertView.findViewById(R.id.device_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DeviceInfoBean device = deviceList.get(position);

        holder.deviceName.setText(device.getName());
        holder.deviceStatus.setText(device.isOnline() ? "在线" : "离线");

        return convertView;
    }

    private static class ViewHolder {
        TextView deviceName;
        TextView deviceStatus;
    }
}
