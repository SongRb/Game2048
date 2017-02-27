package cn.edu.sjtu.seiee.songrb.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

// Bluetooth support

public class GameView extends GridLayout implements RWService.Move {
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initGameView();
    }

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                if (ConFig.MODE_SELECT == 1) {
                                    ConFig.mb.rwService.sendMessageHandle("12");
                                }
                                swipeLeft();
                            } else if (offsetX > 5) {
                                if (ConFig.MODE_SELECT == 1) {
                                    ConFig.mb.rwService.sendMessageHandle("13");
                                }
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                if (ConFig.MODE_SELECT == 1) {
                                    ConFig.mb.rwService.sendMessageHandle("10");
                                }
                                swipeUp();
                            } else if (offsetY > 5) {
                                if (ConFig.MODE_SELECT == 1) {
                                    ConFig.mb.rwService.sendMessageHandle("11");
                                }
                                swipeDown();
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) - 10) / 4;

        addCards(cardWidth, cardWidth);

        startGame();
    }

    private void addCards(int cardWidth, int cardHeight) {

        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }

    MainActivity_2 mainActivity_2 = new MainActivity_2();

    private void startGame() {

        mainActivity_2.getMainActivity().clearScore();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        if (ConFig.MODE_SELECT != 1) {
            addRandomNum();
            addRandomNum();
        } else {
            if (ConFig.isMaster) {
                addStartRandomNum();
            }
        }
    }

    private void addStartRandomNum() {
        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        int position = (int) (Math.random() * emptyPoints.size());
        Point p = emptyPoints.remove(position);
        int number = Math.random() > 0.1 ? 2 : 4;
        cardsMap[p.x][p.y].setNum(number);
        String str;
        if (position < 10) {
            str = "0" + position;
        } else {
            str = "" + position;
        }
        String strNumber = "" + number;
        String value = "1" + str + strNumber;
        if (ConFig.MODE_SELECT == 1 && ConFig.mb.rwService != null) {
            ConFig.mb.rwService.sendMessageHandle(value);
        }
    }

    private void addRandomNum() {

        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        int position = (int) (Math.random() * emptyPoints.size());
        Point p = emptyPoints.remove(position);
        int number = Math.random() > 0.1 ? 2 : 4;
        cardsMap[p.x][p.y].setNum(number);
    }

    private void swipeLeft() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            mainActivity_2.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (ConFig.MODE_SELECT != 1) {
            if (merge) {
                addRandomNum();
                checkComplete();
            }
        } else {
            if (ConFig.isMaster && merge == true) {
                addStartRandomNum();
                checkComplete();
            }
        }
    }

    private void swipeRight() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {

                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            mainActivity_2.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (ConFig.MODE_SELECT != 1) {
            if (merge) {
                addRandomNum();
                checkComplete();
            }
        } else {
            if (ConFig.isMaster) {
                addStartRandomNum();
                checkComplete();
            }
        }
    }

    private void swipeUp() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            mainActivity_2.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;

                    }
                }
            }
        }

        if (ConFig.MODE_SELECT != 1) {
            if (merge) {
                addRandomNum();
                checkComplete();
            }
        } else {
            if (ConFig.isMaster) {
                addStartRandomNum();
                checkComplete();
            }
        }
    }

    private void swipeDown() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            mainActivity_2.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if (ConFig.MODE_SELECT != 1) {
            if (merge) {
                addRandomNum();
                checkComplete();
            }
        } else {
            if (ConFig.isMaster) {
                addStartRandomNum();
                checkComplete();
            }
        }
    }

    private void checkComplete() {

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
                        || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
                        || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
                        || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束")
                    .setPositiveButton("重来", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
        }

    }

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();

    @Override
    public void moveLeft() {
        // TODO Auto-generated method stub
        Message msg = Message.obtain();
        msg.what = 1;
        handler.sendMessage(msg);

    }

    @Override
    public void moveRight() {
        // TODO Auto-generated method stub
        Message msg = Message.obtain();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    @Override
    public void moveUp() {
        // TODO Auto-generated method stub
        Message msg = Message.obtain();
        msg.what = 3;
        handler.sendMessage(msg);
    }

    @Override
    public void moveDown() {
        // TODO Auto-generated method stub
        Message msg = Message.obtain();
        msg.what = 4;
        handler.sendMessage(msg);
    }

    @Override
    public void initNumber(int number) {
        // TODO Auto-generated method stub
        Message msg = Message.obtain();
        msg.arg1 = number;
        msg.what = 5;
        handler.sendMessage(msg);
    }

    private void addSelf(int number) {

        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        int value = number % 1000;
        int position = value / 10;
        int random = value % 10;
        Point p = emptyPoints.remove(position);
        cardsMap[p.x][p.y].setNum(random);
        checkComplete();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    swipeLeft();
                    break;
                case 2:
                    swipeRight();
                    break;
                case 3:
                    swipeUp();
                    break;
                case 4:
                    swipeDown();
                    break;
                case 5:
                    addSelf(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    };

}
