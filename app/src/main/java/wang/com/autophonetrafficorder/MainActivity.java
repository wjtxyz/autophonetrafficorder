package wang.com.autophonetrafficorder;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    static final String TAG = "MainActivity";
    static final String[] REQUEST_PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.RECEIVE_SMS,
    };

    private static final String ACTION_SMS_SENT = "action.com.wang.ACTION_SMS_SENT";
    private static final String ACTION_SMS_DELIVERED = "action.com.wang.ACTION_SMS_DELIVERED";

    private final BroadcastReceiver mSmsSendReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive:: intent=" + intent);
            if(ACTION_SMS_DELIVERED.equals(intent.getAction())){
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, 0);


        final PendingIntent piSent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_SMS_SENT), 0);
        final PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_SMS_DELIVERED), 0);

        findViewById(R.id.btnExecuteNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager mgr = SmsManager.getDefault();
                mgr.sendTextMessage("10001", null, "108", piSent, piDelivered);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SMS_DELIVERED);
        filter.addAction(ACTION_SMS_SENT);

        registerReceiver(mSmsSendReceiver, filter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSmsSendReceiver);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "onRequestPermissionsResult:: permissions=" + Arrays.toString(permissions) + " grantResults=" + Arrays.toString(grantResults));

    }
}
