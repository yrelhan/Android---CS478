package com.example.project5client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.clipserver.AudioAIDL;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView status;
    Button startService;
    Button stopService;
    Button playSong1;
    Button playSong2;
    Button playSong3;
    Button playSong4;
    Button playSong5;
    Button stopSong;
    Button resumeSong;
    Button pauseSong;
    private int NOT_STARTED = 1;
    private int WAITING = 2;
    private int PLAYING = 3;
    private int PAUSED = 5;

    int currentState;

    private ServiceConnection serviceConnection;
    private AudioAIDL binder;

    private Intent intent = startIntent();

    private Intent startIntent(){
        Intent intent = new Intent("musicservice");
        intent.setPackage("com.example.clipserver");
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.status);
        playSong1 = (Button)findViewById(R.id.song1_button);
        playSong2 = (Button)findViewById(R.id.song2_button);
        playSong3 = (Button)findViewById(R.id.song3_button);
        playSong4 = (Button)findViewById(R.id.song4_button);
        playSong5 = (Button)findViewById(R.id.song5_button);
        stopSong = (Button)findViewById(R.id.stop_button);
        resumeSong = (Button)findViewById(R.id.resume_button);
        stopService = (Button)findViewById(R.id.stopservice);
        startService = (Button)findViewById(R.id.startservice);
        pauseSong = (Button)findViewById(R.id.pause_button);

        currentState=1;
        redrawViews();
        registerBroadcast();
        serviceConnection = initServiceConnection();

        playSong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    onClickPlay("1");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        playSong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    onClickPlay("2");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        playSong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    onClickPlay("3");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        playSong4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    onClickPlay("4");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        playSong5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    onClickPlay("5");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        resumeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    binder.resume();
                    currentState = PLAYING;
                    redrawViews();
                    status.setText("Song resumed");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        stopSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    binder.stop();
                    if(binder!=null){
                        unbindService(serviceConnection);
                    }
                    status.setText("Song stopped");
                    currentState = WAITING;
                    redrawViews();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        pauseSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    status.setText("Song Paused");
                    binder.pause();
                    currentState = PAUSED;
                    redrawViews();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startForegroundService(intent);
                    status.setText("Initiated service");
                    bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
                    status.setText("Binding now");
                    currentState = WAITING;
                    redrawViews();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    status.setText("Initiated binding");
                    binder.stop();
                    if(binder!=null)
                        unbindService(serviceConnection);
                    status.setText("Stopped and unbinded service");
                    currentState = NOT_STARTED;
                    redrawViews();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            status.setText("Initiated binding");
            binder.stop();
            if(binder!=null)
                unbindService(serviceConnection);
            status.setText("Stopped and unbinded service");
            currentState = NOT_STARTED;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private ServiceConnection initServiceConnection(){
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                binder = AudioAIDL.Stub.asInterface(service);
                status.setText("Binded to service");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                status.setText("Service unbind");
                binder=null;
                currentState=NOT_STARTED;
            }
        };
    }

    private void registerBroadcast(){
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(binder!=null)
                    unbindService(serviceConnection);
                    currentState=2;
                    redrawViews();
            }
        };

        IntentFilter intentFilter = new IntentFilter("unbind");
        intentFilter.setPriority(1);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private void redrawViews(){

        ArrayList<Integer> visible = new ArrayList<>();
        ArrayList<Integer> notEnabled = new ArrayList<>();
        if(currentState==NOT_STARTED){
            visible.add(R.id.startservice);
            notEnabled.add(R.id.song1_button);
            notEnabled.add(R.id.song2_button);
            notEnabled.add(R.id.song3_button);
            notEnabled.add(R.id.song4_button);
            notEnabled.add(R.id.song5_button);
            notEnabled.add(R.id.stop_button);
            notEnabled.add(R.id.stopservice);
            notEnabled.add(R.id.pause_button);
            notEnabled.add(R.id.resume_button);
            status = findViewById(R.id.status);
            status.setText("Service not started or stopped");
        }else if(currentState==WAITING){
            visible.add(R.id.song1_button);
            visible.add(R.id.song2_button);
            visible.add(R.id.song3_button);
            visible.add(R.id.song4_button);
            visible.add(R.id.song5_button);
            visible.add(R.id.stopservice);
            notEnabled.add(R.id.startservice);
            notEnabled.add(R.id.pause_button);
            notEnabled.add(R.id.resume_button);
            notEnabled.add(R.id.stop_button);
        }else if(currentState==PLAYING){
            visible.add(R.id.song1_button);
            visible.add(R.id.song2_button);
            visible.add(R.id.song3_button);
            visible.add(R.id.song4_button);
            visible.add(R.id.song5_button);
            visible.add(R.id.stopservice);
            visible.add(R.id.pause_button);
            visible.add(R.id.stop_button);
            notEnabled.add(R.id.startservice);
            notEnabled.add(R.id.resume_button);
        }else if(currentState==PAUSED){
            visible.add(R.id.song1_button);
            visible.add(R.id.song2_button);
            visible.add(R.id.song3_button);
            visible.add(R.id.song4_button);
            visible.add(R.id.song5_button);
            visible.add(R.id.stopservice);
            visible.add(R.id.pause_button);
            visible.add(R.id.stop_button);
            visible.add(R.id.resume_button);
            notEnabled.add(R.id.startservice);
            notEnabled.add(R.id.pause_button);
        }

        for(int id : visible){
            Button button = findViewById(id);
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        for(int id:notEnabled){
            Button button = findViewById(id);
            button.setVisibility(View.VISIBLE);
            button.setEnabled(false);
        }
    }

    public void onClickPlay(String name) throws RemoteException{
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        status.setText("Initiated binding");
        binder.play(name);
        currentState=PLAYING;
        redrawViews();
    }

}
