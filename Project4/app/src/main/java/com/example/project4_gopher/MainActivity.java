package com.example.project4_gopher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private Handler runnableHandler;
    private Handler messageHandler;
    private Handler messageHandler2;
    private Switch switchView;
    private Button startButton;
    private TextView commentary;
    private TextView player1Status;
    private TextView player2Status;
    private Button startAgain;
    private TextView result;
    final int gopher = 3;
    final int player1 = 1;
    final int player2 = 2;
    int low = 1;
    int high = 101;
    private Random r;
    Resources res;
    final int cellId[] = {R.id.cell1, R.id.cell1, R.id.cell2, R.id.cell3, R.id.cell4, R.id.cell5, R.id.cell6, R.id.cell7, R.id.cell8, R.id.cell9, R.id.cell10, R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell14, R.id.cell15, R.id.cell16, R.id.cell17, R.id.cell18, R.id.cell19, R.id.cell20, R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell24, R.id.cell25, R.id.cell26, R.id.cell27, R.id.cell28, R.id.cell29, R.id.cell30, R.id.cell31, R.id.cell32, R.id.cell33, R.id.cell34, R.id.cell35, R.id.cell36, R.id.cell37, R.id.cell38, R.id.cell39, R.id.cell40, R.id.cell41, R.id.cell42, R.id.cell43, R.id.cell44, R.id.cell45, R.id.cell46, R.id.cell47, R.id.cell48, R.id.cell49, R.id.cell50, R.id.cell51, R.id.cell52, R.id.cell53, R.id.cell54, R.id.cell55, R.id.cell56, R.id.cell57, R.id.cell58, R.id.cell59, R.id.cell60, R.id.cell61, R.id.cell62, R.id.cell63, R.id.cell64, R.id.cell65, R.id.cell66, R.id.cell67, R.id.cell68, R.id.cell69, R.id.cell70, R.id.cell71, R.id.cell72, R.id.cell73, R.id.cell74, R.id.cell75, R.id.cell76, R.id.cell77, R.id.cell78, R.id.cell79, R.id.cell80, R.id.cell81, R.id.cell82, R.id.cell83, R.id.cell84, R.id.cell85, R.id.cell86, R.id.cell87, R.id.cell88, R.id.cell89, R.id.cell90, R.id.cell91, R.id.cell92, R.id.cell93, R.id.cell94, R.id.cell95, R.id.cell96, R.id.cell97, R.id.cell98, R.id.cell99, R.id.cell100};
    final int[] holes = new int[101];
    final int SUCCESS = 1;
    final int NEARMISS = 2;
    final int CLOSEGUESS = 3;
    final int COMPLETEMISS = 4;
    final int DISASTER = 5;
    private boolean gopherFound = false;
    private int gopherPosition;
    final String[] outcomesList = {"", "Success", "Near Miss", "Close Guess", "Complete Miss", "Disaster"};
    private String winner;
    int threadHigh = 800;
    int threadLow = 600;
    private final AtomicBoolean running = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runnableHandler = new Handler();
        switchView = findViewById(R.id.switch1);
        startButton = findViewById(R.id.start);
        commentary = findViewById(R.id.commentary);
        player1Status = findViewById(R.id.player1_status);
        player2Status = findViewById(R.id.player2_status);
        startAgain = findViewById(R.id.startagain);
        result = findViewById(R.id.result);
        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running.set(false);
                Intent intent = new Intent(MainActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        begin();
    }

    private void begin() {

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setEnabled(false);
                boolean mode = switchView.isChecked();
                if (mode) {
                    //manual
                    commentary.setText("Game is started in manual mode");
                    switchView.setClickable(false);
                    switchView.setChecked(true);
                    beginInManualMode();
                } else {
                    //continuous
                    commentary.setText("Game is started in continuous mode");
                    switchView.setClickable(false);
                    switchView.setChecked(false);
                    beginInContinuousMode();
                }
            }
        });
    }

    private void beginInManualMode() {
        r = new Random();
        gopherPosition = r.nextInt(high - low) + low;
        //setGopherPosition
        setPosition(gopher, gopherPosition);
        ResourceLock lock = new ResourceLock();
        messageHandler = new Handler() {
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    case player1:
                        setPosition(player1, msg.arg1);
                        player1Status.setText("Guessed " + msg.arg1 + ": " + outcomesList[msg.arg2]);
                        break;

                    case player2:
                        setPosition(player2, msg.arg1);
                        player2Status.setText("Guessed " + msg.arg1 + ": " + outcomesList[msg.arg2]);
                        break;
                }

            }
        };

        Thread player1Thread = new Thread(new Player1RunnableManual(lock));
        Thread player2Thread = new Thread(new Player2RunnableManual(lock));
        player1Thread.start();
        player2Thread.start();
    }

    @SuppressLint("HandlerLeak")
    private void beginInContinuousMode() {
        r = new Random();
        gopherPosition = r.nextInt(high - low) + low;
        //setGopherposition
        setPosition(gopher, gopherPosition);
        running.set(true);

        messageHandler = new Handler() {
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    case player1:
                        setPosition(player1, msg.arg1);
                        player1Status.setText("Guessed " + msg.arg1 + ": " + outcomesList[msg.arg2]);
                        break;

                    case player2:
                        setPosition(player2, msg.arg1);
                        player2Status.setText("Guessed " + msg.arg1 + ": " + outcomesList[msg.arg2]);
                        break;
                }

            }
        };
        Thread player1Thread = new Thread(new Player1RunnableCont());
        player1Thread.start();
        Thread player2Thread = new Thread(new Player2RunnableCont());
        player2Thread.start();
    }

    private void setPosition(int actor, int position) {
        Log.i("Demo", "Actor " + actor + "Position " + position);
        if (actor == gopher) {
            int gopher_color = ContextCompat.getColor(this, R.color.gopher);
            TextView currCell = (TextView) findViewById(cellId[position]);
            currCell.setBackgroundColor(gopher_color);
            holes[position] = gopher;
        } else if (actor == player1) {
            int player1_color = ContextCompat.getColor(this, R.color.player1);
            TextView currCell = (TextView) findViewById(cellId[position]);
            currCell.setBackgroundColor(player1_color);
            holes[position] = player1;
        } else if (actor == player2) {
            int player2_color = ContextCompat.getColor(this, R.color.player2);
            TextView currCell = (TextView) findViewById(cellId[position]);
            currCell.setBackgroundColor(player2_color);
            holes[position] = player2;
        }
        //generating random number for gopher position

    }

    private int calculateProximity(int actor, int playerPosition) {

        int playerRow = playerPosition / 10, playerCol = playerPosition % 10, gopherRow = gopherPosition / 10, gopherCol = gopherPosition % 10;

        if (holes[playerPosition] != 0 && holes[playerPosition] != 3) {
            Log.i("Demo", "Actor " + actor + " player position " + playerPosition + " gopher position " + gopherPosition + " returning disaster");
            return DISASTER;
        } else if (gopherPosition == playerPosition) {
            Log.i("Demo", "Actor " + actor + " player position " + playerPosition + " gopher position " + gopherPosition + " returning success");
            return SUCCESS;
        } else if (playerPosition >= gopherPosition - 8 && playerPosition <= gopherPosition + 8) {
            Log.i("Demo", "Actor " + actor + " player position " + playerPosition + " gopher position " + gopherPosition + " returning nearmiss");
            return NEARMISS;
        } else if ((playerRow == gopherRow && Math.abs(playerCol - gopherCol) == 2) || (playerCol == gopherCol && Math.abs(playerRow - gopherRow) == 2) || (Math.abs(playerCol - gopherCol) == 2 && Math.abs(playerRow - gopherRow) == 2)) {
            Log.i("Demo", "Actor " + actor + " player position " + playerPosition + " gopher position " + gopherPosition + " returning closeguess");
            return CLOSEGUESS;
        } else {
            Log.i("Demo", "Actor " + actor + " player position " + playerPosition + " gopher position " + gopherPosition + " returning closemiss");
            return COMPLETEMISS;
        }
    }


    class Player1RunnableCont implements Runnable {

        @Override
        public void run() {
            while (!gopherFound) {
                if (!running.get())
                    break;
                r = new Random();
                final int player1Position = r.nextInt(high - low) + low;
                final int outcome = calculateProximity(player1, player1Position);
                if (outcome != DISASTER) {
                    runnableHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (holes[player1Position] == 0) {
                                setPosition(player1, player1Position);
                                player1Status.setText("Guessed " + player1Position + ": " + outcomesList[outcome]);
                            }
                        }
                    });
                    if (outcome == SUCCESS) {
                        gopherFound = true;
                        winner = "Player 1";
                        result.setText("Player 1 wins");
                        int player1_color = ContextCompat.getColor(getApplicationContext(), R.color.player1);
                        TextView currCell = (TextView) findViewById(cellId[player1Position]);
                        currCell.setBackgroundColor(player1_color);
                        player1Status.setText("Guessed " + player1Position + ": Success");
                        running.set(false);
                        break;
                    }
                } else {
                    player1Status.setText("Guessed " + player1Position + ": " + outcomesList[5]);
                }
                try {
                    int sleep = r.nextInt(threadHigh - threadLow) + threadLow;
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Player1RunnableManual implements Runnable {

        ResourceLock lock;

        Player1RunnableManual(ResourceLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            running.set(true);
            Log.i("Demo", "Player2 " + running.get());
            try {
                synchronized (lock) {
                    while (!gopherFound) {
                        if (!running.get())
                            break;
                        while (lock.flag != 1)
                            lock.wait();
                        r = new Random();
                        final int player1Position = r.nextInt(high - low) + low;
                        final int outcome = calculateProximity(player1, player1Position);
                        if (outcome != DISASTER) {
                            runnableHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (holes[player1Position] == 0) {
                                        setPosition(player1, player1Position);
                                        player1Status.setText("Guessed " + player1Position + ": " + outcomesList[outcome]);
                                    }
                                }
                            });
                            if (outcome == SUCCESS) {
                                gopherFound = true;
                                winner = "Player 1";
                                result.setText("Player 1 wins");
                                int player1_color = ContextCompat.getColor(getApplicationContext(), R.color.player1);
                                TextView currCell = (TextView) findViewById(cellId[player1Position]);
                                currCell.setBackgroundColor(player1_color);
                                player1Status.setText("Guessed " + player1Position + ": Success");
                                running.set(false);
                                break;
                            }
                        } else {
                            player1Status.setText("Guessed " + player1Position + ": " + outcomesList[5]);
                        }
                        try {
                            int sleep = r.nextInt(threadHigh - threadLow) + threadLow;
                            Thread.sleep(sleep);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock.flag = 2;
                            lock.notifyAll();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class Player2RunnableCont implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 100 && !gopherFound; i++) {
                if (!running.get())
                    break;

                final int player2Position = i;
                final int outcome = calculateProximity(player2, player2Position);
                if (outcome != DISASTER) {
                    Message msg = messageHandler.obtainMessage(player2);
                    msg.arg1 = player2Position;
                    msg.arg2 = outcome;
                    messageHandler.sendMessage(msg);
                    if (outcome == SUCCESS) {
                        gopherFound = true;
                        winner = "Player 2";
                        result.setText("Player 2 wins");
                        int player2_color = ContextCompat.getColor(getApplicationContext(), R.color.player2);
                        TextView currCell = (TextView) findViewById(cellId[player2Position]);
                        currCell.setBackgroundColor(player2_color);
                        player2Status.setText("Guessed " + player2Position + ": Success");
                        running.set(false);
                        break;
                    }
                } else {
                    player2Status.setText("Guessed " + player2Position + ": " + outcomesList[5]);
                }
                try {
                    int sleep = r.nextInt(threadHigh - threadLow) + threadLow;
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Player2RunnableManual implements Runnable {

        ResourceLock lock;

        Player2RunnableManual(ResourceLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            Log.i("Demo", "Player2 " + running.get());
            try {
                synchronized (lock) {
                    for (int i = 1; i <= 100 && !gopherFound; i++) {
                        if (!running.get())
                            break;
                        while (lock.flag != 2)
                            lock.wait();
                        final int player2Position = i;
                        final int outcome = calculateProximity(player2, player2Position);
                        if (outcome != DISASTER) {
                            Message msg = messageHandler.obtainMessage(player2);
                            msg.arg1 = player2Position;
                            msg.arg2 = outcome;
                            messageHandler.sendMessage(msg);
                            if (outcome == SUCCESS) {
                                gopherFound = true;
                                winner = "Player 2";
                                result.setText("Player 2 wins");
                                int player2_color = ContextCompat.getColor(getApplicationContext(), R.color.player2);
                                TextView currCell = (TextView) findViewById(cellId[player2Position]);
                                currCell.setBackgroundColor(player2_color);
                                player2Status.setText("Guessed " + player2Position + ": Success");
                                running.set(false);
                                break;
                            }
                        } else {
                            player2Status.setText("Guessed " + player2Position + ": " + outcomesList[5]);
                        }
                        try {
                            int sleep = r.nextInt(threadHigh - threadLow) + threadLow;
                            Thread.sleep(sleep);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock.flag = 1;
                            lock.notifyAll();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        running.set(false);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        running.set(false);
        super.onPause();
    }
}




