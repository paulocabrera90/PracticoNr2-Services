package com.example.practiconr2services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class MensajeService extends Service {
    Thread tarea;
    public MensajeService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri dbMensaje = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        tarea = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Cursor cursor = cr.query(dbMensaje,null,null,null,null);
                    String id = null;
                    String msn = null;
                    if (cursor != null && cursor.getCount() > 0) {
                        int contador = 0;
                        while (cursor.moveToNext() && contador < 5) {
                            id = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                            msn = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                            Log.d("Mensaje", id + " " + msn);
                            contador++;
                        }
                        try {
                            Thread.sleep(9000);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                    cursor.close();
                }
            }
        });

        tarea.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}