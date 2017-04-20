package com.kankanla.m0417b;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Uri u;
    private Context context;
    private Intent intent;
    private PlayerService2 playerService2;
    private boolean flag;
    private PlayerService2.L_Binder l_binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        intent = new Intent(this, PlayerService2.class);
        bindService(intent, new con(), Context.BIND_AUTO_CREATE);
    }

    public void t1(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");
//        intent.setType(DocumentsContract.Document.MIME_TYPE_DIR);
//        intent.setType("image/*");
        startActivityForResult(intent, 99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            u = data.getData();
            if (flag) {
                playerService2.addUri(u);
                playerService2.player();
            }
        }
    }

    public class con implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            l_binder = (PlayerService2.L_Binder) service;
            playerService2 = l_binder.getServer();
            flag = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
