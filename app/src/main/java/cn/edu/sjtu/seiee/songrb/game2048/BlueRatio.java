package cn.edu.sjtu.seiee.songrb.game2048;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 程涌潇 on 2016/5/23.
 */
public class BlueRatio extends Activity implements View.OnClickListener, OnItemClickListener {
    private Button btn_local, btn_net, btn_create;
    private LinearLayout ll_layout1, ll_layout2;
    private ListView lv;
    private ServerThread serverThread = null;
    private ClientThread clientThread = null;
    private List<BluetoothDevice> _bdList;
    private BluetoothAdapter mBtAdapter;
    private DeviceAdapter adAdapter;
    private boolean create_wait = true;// create room, then enter wait

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            handlerSelfMessage();
        }

        ;
    };

    private void handlerSelfMessage() {
        if (create_wait) {
            create_wait = false;
            btn_create.setText(getResources().getString(cn.edu.sjtu.seiee.songrb.game2048.R.string.wait_somebody));
        } else {
            create_wait = true;
            btn_create.setText(getResources().getString(cn.edu.sjtu.seiee.songrb.game2048.R.string.create_room));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.sjtu.seiee.songrb.game2048.R.layout.select);

        ll_layout1 = (LinearLayout) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.ll_layout1);
        ll_layout2 = (LinearLayout) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.ll_layout2);
        lv = (ListView) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.lv_container);
        btn_local = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.btn_local);
        btn_net = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.btn_net);
        btn_create = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.btn_create);
        lv.setOnItemClickListener(this);
        btn_local.setOnClickListener(this);
        btn_net.setOnClickListener(this);
        btn_create.setOnClickListener(this);
        init();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void init() {
        // TODO Auto-generated method stub
        _bdList = new ArrayList<>();
        IntentFilter discoveryFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, discoveryFilter);
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, foundFilter);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bdPairs = mBtAdapter.getBondedDevices();
        for (BluetoothDevice bd : bdPairs) {
            _bdList.add(bd);
        }
        adAdapter = new DeviceAdapter(BlueRatio.this, _bdList);
        lv.setAdapter(adAdapter);
        lv.setFastScrollEnabled(true);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    _bdList.add(device);
                    adAdapter.notifyDataSetChanged();
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                adAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case cn.edu.sjtu.seiee.songrb.game2048.R.id.btn_local:
                ConFig.MODE_SELECT = 0;
                Intent intent = new Intent(BlueRatio.this, MainActivity.class);
                startActivity(intent);
                break;
            case cn.edu.sjtu.seiee.songrb.game2048.R.id.btn_net:
                ConFig.MODE_SELECT = 1;
                ll_layout1.setVisibility(View.GONE);
                ll_layout2.setVisibility(View.VISIBLE);
                break;
            case cn.edu.sjtu.seiee.songrb.game2048.R.id.btn_create:
                // create a server
                if (!mBtAdapter.isEnabled()) {
                    Toast.makeText(this, cn.edu.sjtu.seiee.songrb.game2048.R.string.bluetooth, Toast.LENGTH_SHORT).show();
                } else {
                    if (create_wait) {
                        serverThread = new ServerThread();
                        serverThread.start();
                    } else {
                        serverThread.interrupt();
                        if (ConFig.socket != null) {
                            try {
                                ConFig.socket.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    handlerSelfMessage();
                }
                break;
            default:
                break;
        }
    }

    public class ServerThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                ConFig.mserverSocket = mBtAdapter.listenUsingRfcommWithServiceRecord("btspp",
                        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                ConFig.socket = ConFig.mserverSocket.accept();
                ConFig.isMaster = true;
                Intent intent = new Intent(BlueRatio.this, ClassLoadView.class);
                startActivity(intent);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(BlueRatio.this, cn.edu.sjtu.seiee.songrb.game2048.R.string.create_error, Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(1);
                e.printStackTrace();
            }
        }
    }

    public class ClientThread extends Thread {
        private BluetoothDevice bd_local;

        public ClientThread(BluetoothDevice bd) {
            // TODO Auto-generated constructor stub
            bd_local = bd;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                ConFig.socket = bd_local
                        .createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                ConFig.socket.connect();
                ConFig.isMaster = false;
                Intent intent = new Intent(BlueRatio.this, ClassLoadView.class);
                startActivity(intent);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(BlueRatio.this, cn.edu.sjtu.seiee.songrb.game2048.R.string.enter_error, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            super.run();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // connect to a server
        if (create_wait) {
            if (!mBtAdapter.isEnabled()) {
                Toast.makeText(this, cn.edu.sjtu.seiee.songrb.game2048.R.string.bluetooth, Toast.LENGTH_SHORT).show();
            } else {
                clientThread = new ClientThread(_bdList.get(position));
                clientThread.start();
            }
        }
    }

}
