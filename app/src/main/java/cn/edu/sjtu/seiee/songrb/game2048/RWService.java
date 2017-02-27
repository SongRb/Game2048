package cn.edu.sjtu.seiee.songrb.game2048;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Bluetooth support

public class RWService extends Service {
    private final GetMyBinder mBinder = new GetMyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return (IBinder) mBinder;
    }

    public class GetMyBinder extends Binder {
        public RWService getRWService() {
            return RWService.this;
        }
    }

    public interface Move {
        public void moveLeft();

        public void moveRight();

        public void moveUp();

        public void moveDown();

        public void initNumber(int number);
    }

    private Move move;

    public void setMove(Move mMove) {
        move = mMove;
    }

    public void sendMessageHandle(String msg) {
        if (ConFig.socket == null) {
            Toast.makeText(this, "没有连接", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            OutputStream os = ConFig.socket.getOutputStream();
            os.write(msg.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        new Thread() {
            public void run() {
                byte[] buffer = new byte[1024];
                int bytes;
                InputStream mmInStream = null;
                try {
                    mmInStream = ConFig.socket.getInputStream();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                while (true) {
                    try {
                        // Read from the InputStream
                        if ((bytes = mmInStream.read(buffer)) > 0) {
                            byte[] buf_data = new byte[bytes];
                            for (int i = 0; i < bytes; i++) {
                                buf_data[i] = buffer[i];
                            }
                            String s = new String(buf_data);
                            if (move != null) {
                                switch (Integer.parseInt(s)) {
                                    case 10:
                                        // up
                                        move.moveUp();
                                        break;
                                    case 11:
                                        // down
                                        move.moveDown();
                                        break;
                                    case 12:
                                        // left
                                        move.moveLeft();
                                        break;
                                    case 13:
                                        // right
                                        move.moveRight();
                                        break;
                                    default:
                                        move.initNumber(Integer.parseInt(s));
                                        break;
                                }
                            } else {
                                // in this time, maybe move is not initial, so initial is not done
                                while (move == null) {
                                }
                                move.initNumber(Integer.parseInt(s));
                            }
                        }
                    } catch (IOException e) {
                        try {
                            mmInStream.close();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        break;
                    }
                }
            }

            ;
        }.start();
    }
}
