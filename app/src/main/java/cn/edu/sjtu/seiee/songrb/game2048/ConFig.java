package cn.edu.sjtu.seiee.songrb.game2048;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

// Bluetooth support

class ConFig {
    static BluetoothServerSocket mserverSocket = null;
    static BluetoothSocket socket = null;
    static int MODE_SELECT = 0;
    static MainBase mb;
    static boolean isMaster = false;
}
