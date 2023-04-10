// Creado por Yaranga Avila Marlon - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Hashtable;
import java.util.Map;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;


import static android.content.ContentValues.TAG;
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        SharedPreferences sh_soportenotf=getSharedPreferences("ConfiguracionNotificaciones",0);
        int activarnotuno = sh_soportenotf.getInt("Notuno",1);
        int activarnotdos = sh_soportenotf.getInt("Notdos",1);
        int activarnottres = sh_soportenotf.getInt("Notres",1);
        int activarnotcuatro = sh_soportenotf.getInt("Notcuatro",1);
        int activarnotcinco = sh_soportenotf.getInt("Notcinco",1);

        switch (activarnotuno){
            case 0:
                if(remoteMessage.getData() != null) {
                    enviarNotificacion(remoteMessage);
                }
                if(remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Body notification: "+remoteMessage.getNotification().getBody());
                    enviarNotificacion(remoteMessage);
                }
                break;

            case 1:
                break;
        }
        switch (activarnotdos){
            case 0:
                if(remoteMessage.getData() != null) {
                    enviarNotificacion(remoteMessage);
                }
                if(remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Body notification: "+remoteMessage.getNotification().getBody());
                    enviarNotificacion(remoteMessage);
                }
                break;

            case 1:
                break;
        }switch (activarnottres){
            case 0:
                if(remoteMessage.getData() != null) {
                    enviarNotificacion(remoteMessage);
                }
                if(remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Body notification: "+remoteMessage.getNotification().getBody());
                    enviarNotificacion(remoteMessage);
                }
                break;

            case 1:
                break;
        }switch (activarnotcuatro){
            case 0:
                if(remoteMessage.getData() != null) {
                    enviarNotificacion(remoteMessage);
                }
                if(remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Body notification: "+remoteMessage.getNotification().getBody());
                    enviarNotificacion(remoteMessage);
                }
                break;

            case 1:
                break;
        }switch (activarnotcinco){
            case 0:
                if(remoteMessage.getData() != null) {
                    enviarNotificacion(remoteMessage);
                }
                if(remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Body notification: "+remoteMessage.getNotification().getBody());
                    enviarNotificacion(remoteMessage);
                }
                break;

            case 1:
                break;
        }


    }

    private void enviarNotificacion(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="Notificaciones mensajes";
        int Notificaiones_ID1=0;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Solo para android Oreo o superior
            @SuppressLint("WrongConstant")
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Mi notificacion",
                    NotificationManager.IMPORTANCE_MAX
            );

            // Configuracion del canal de notificacion
            channel.setDescription("primer canal de prueba");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableVibration(true);

            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setTicker("Hearty365")
                .setContentTitle(title)
                .setContentText(body)
                .setColor(Color.BLUE)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setContentIntent(pendingIntent)
                .setContentInfo("info");

        manager.notify(Notificaiones_ID1, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed Token: "+token);

        FirebaseMessaging.getInstance().subscribeToTopic("dispositivos");
        enviarTokenToServer(token);
    }

    private void enviarTokenToServer(final String token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Util.RUTA+"m_registrar_token_soporte.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se registro exitosamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                SharedPreferences creduser= getSharedPreferences("Credencial_Global_usuario",0);
                String iduser=creduser.getString("CredId","");
                parametros.put("sp_Doc_persona", iduser);
                parametros.put("sp_Token_personal", token);
                return parametros;
            }
        };
        
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
