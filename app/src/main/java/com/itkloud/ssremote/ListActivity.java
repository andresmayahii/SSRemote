package com.itkloud.ssremote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.itkloud.ssremote.apis.Pusher;
import com.itkloud.ssremote.apis.SlideShare;

import org.json.JSONObject;


public class ListActivity extends Activity {

    public static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    public void testOpen(View view) {
        final Pusher pusher = new Pusher(ApisAccounts.getPusherAccount());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("command","load");
                    jsonObject.put("doc","diseograficoenmxico-090309200720-phpapp02");
                    pusher.trigger("334020435057532","commands",jsonObject);
                } catch(Exception e) {
                    Log.e("ListActivity","No se mando el comando ", e);
                }
            }
        }).start();
    }

    public void testNext(View view) {
        final Pusher pusher = new Pusher(ApisAccounts.getPusherAccount());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("command","next");
                    pusher.trigger("334020435057532","commands",jsonObject);
                } catch(Exception e) {
                    Log.e("ListActivity","No se mando el comando ", e);
                }
            }
        }).start();
    }

    public void testPrevious(View view) {
        final Pusher pusher = new Pusher(ApisAccounts.getPusherAccount());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("command","previous");
                    pusher.trigger("334020435057532","commands",jsonObject);
                } catch(Exception e) {
                    Log.e("ListActivity","No se mando el comando ", e);
                }
            }
        }).start();
    }

    public void testClose(View view) {
        final Pusher pusher = new Pusher(ApisAccounts.getPusherAccount());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("command","close");
                    pusher.trigger("334020435057532","commands",jsonObject);
                } catch(Exception e) {
                    Log.e(TAG,"No se mando el comando ", e);
                }
            }
        }).start();
    }

    public void testSlider(View view) {
        Log.d(TAG,"inicia el testSlider");
        final SlideShare sh = new SlideShare(ApisAccounts.getSlideShareAccount());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // sh.getResultByCommand("modelos de negocio");
                    sh.getResultByCommand("@basie_new",10,0,null,null);
                    //sh.getResultByCommand("#jquery");
                } catch(Exception e) {
                    Log.e("ListActivity","No funciono el comando de slideshare ", e);
                }
            }
        }).start();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
