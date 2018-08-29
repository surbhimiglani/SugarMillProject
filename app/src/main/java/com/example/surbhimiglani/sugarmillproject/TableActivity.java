package com.example.surbhimiglani.sugarmillproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;


public class TableActivity extends AppCompatActivity {

    EditText editText;
    static String emailExist="";
    List<KYStypes> list;
    TextView status, status2, status3;
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://www.mill.somee.com/WebService1.asmx";
    Entry categories;
    Gson gson = new Gson();
    KSadapter kysAdapter;
    BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        status=(TextView)findViewById(R.id.status);
        status2=(TextView)findViewById(R.id.status2);
        status3=(TextView)findViewById(R.id.status3);
        kysAdapter=new KSadapter(TableActivity.this);
        kysAdapter.delete();
        list=kysAdapter.getAllQuestionsList();
        //This is the handler that will manager to process the broadcast intent
       mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String value= intent.getStringExtra("message");
                if(value.equals("hi")) {
                    kysAdapter.delete();
                    list = kysAdapter.getAllQuestionsList();
                    AsyncEmailVerify asyncEmailVerify = new AsyncEmailVerify();
                    asyncEmailVerify.execute();
                }
            }
        };
        AsyncEmailVerify asyncEmailVerify=new AsyncEmailVerify();
        asyncEmailVerify.execute();
    }


    private class AsyncEmailVerify extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            invokeEmailVerfy("Entries","http://tempuri.org/Entries");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(isNetworkAvailable()) {
               toast(emailExist);
                list=kysAdapter.getAllQuestionsList();
                categories = gson.fromJson(emailExist, Entry.class);
                if (categories != null) {
                        for (int i = 0; i < categories.getMessages().getMessages().size(); i++) {
                            int c = 0;
                            for (int j = 0; j < list.size(); j++) {
                                if (list.get(j).getEntryNo().equals(categories.getMessages().getMessages().get(i).getEntryNumber())) {
                                    c = 1;
                                }
                            }
                            if (c == 0) {
                                kysAdapter.add(new KYStypes(categories.getMessages().getMessages().get(i).getEntryNumber()));
                            }
                        }
                }
            }
            else
            {
                toast("Please check your internet connection");
            }
            list=kysAdapter.getAllQuestionsList();
            if(list!=null && list.size()!=0) {
                status.setText(list.get(0).getEntryNo());
                status2.setText(list.get(1).getEntryNo());
                status3.setText(list.get(2).getEntryNo());
            }
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void toast(String text){

        Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT ).show();
    }


    public static String invokeEmailVerfy(String name, String webMethName) {

        SoapObject request = new SoapObject(NAMESPACE, name);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            emailExist=response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            emailExist=""+e;
        }
        return emailExist;
    }

    private boolean isNetworkAvailable() {                           // check if the network is available
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter(FcmMessagingService.key));
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter(FcmMessagingService.key));
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mMessageReceiver);
    }




}
