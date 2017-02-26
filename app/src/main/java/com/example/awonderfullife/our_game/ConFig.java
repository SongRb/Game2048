package com.example.awonderfullife.our_game;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

/**
 * Created by 程涌潇 on 2016/5/23.
 */
public class ConFig {
    public static BluetoothServerSocket mserverSocket = null;
    public static BluetoothSocket socket = null;
    public static int MODE_SELECT = 0;
    public static MainBase mb;
    public static boolean isMaster = false;
}
