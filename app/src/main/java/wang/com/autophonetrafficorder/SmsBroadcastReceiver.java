package wang.com.autophonetrafficorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    static final String TAG = "SmsBroadcastReceiver";
    public static final String SMS_BUNDLE = "pdus";

    public static final Pattern REGEXP_10001 = Pattern.compile("本地流量.*共计(\\d+\\.\\d+)MB,已使用(\\d+\\.\\d+)MB,剩余(\\d+\\.\\d+)MB.*全国流量.*共计(\\d+\\.\\d+)MB,已使用(\\d+\\.\\d+)MB,剩余(\\d+\\.\\d+)MB", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d(TAG, "onReceive:: intent=" + intent + " extra(pdus)=" + intent.getExtras().get("pdus"));
        Object[] pdu = (Object[])intent.getExtras().get("pdus");
        for(Object o : pdu){
            SmsMessage message = SmsMessage.createFromPdu((byte[])o);
            final String phoneNumber = message.getDisplayOriginatingAddress();
            final String messageBody = message.getDisplayMessageBody();
            if(phoneNumber.equals("10001")){
                Matcher matcher = REGEXP_10001.matcher(messageBody);
                Log.d(TAG, "onReceive:: " + matcher);
            }
        }
    }
}
