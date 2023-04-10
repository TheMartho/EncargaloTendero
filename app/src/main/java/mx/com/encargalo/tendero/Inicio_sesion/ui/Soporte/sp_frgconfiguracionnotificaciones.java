//Creado por Briceño Malpartida Douglas Igancio - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion.ui.Soporte;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import mx.com.encargalo.R;

public class sp_frgconfiguracionnotificaciones extends Fragment {

    Button btn_guardarconnoti,btn_guardarconnot2;
    PendingIntent pendingIntent;

    Switch stch_mensajes;
    Switch stch_actividadcuenta;
    Switch stch_nuevaorden;
    Switch stch_seguimientoorden;
    Switch stch_ordencompletada;


    private final static String Channel_ID1="Notificaciones mensajes";
    private final static int Notificaiones_ID1=0;

    private final static String Channel_ID2="Notificaciones Actividad de la cuenta";
    private final static int Notificaiones_ID2=2;

    private final static String Channel_ID3="Notificaciones nueva orden";
    private final static int Notificaiones_ID3=3;

    private final static String Channel_ID4="Notificaciones Seguimineto ordenes";
    private final static int Notificaiones_ID4=4;

    private final static String Channel_ID5="Notificaciones Orden completada";
    private final static int Notificaiones_ID5=5;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sp_frgconfiguracionnotificaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sp_frgconfiguracionnotificaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static sp_frgconfiguracionnotificaciones newInstance(String param1, String param2) {
        sp_frgconfiguracionnotificaciones fragment = new sp_frgconfiguracionnotificaciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sp_frgconfiguracionnotificaciones, container, false);

        btn_guardarconnoti=view.findViewById(R.id.btn_guardarconfiguracionnotificaciones);

        stch_mensajes=view.findViewById(R.id.stch_confmensajes);
        stch_actividadcuenta=view.findViewById(R.id.stch_confavtividadcuenta);
        stch_nuevaorden=view.findViewById(R.id.stch_confnuevaorden);
        stch_seguimientoorden=view.findViewById(R.id.stch_confsegumientoorden);
        stch_ordencompletada=view.findViewById(R.id.stch_confordencompletada);

        SharedPreferences sp = getContext().getSharedPreferences("ConfiguracionNotificaciones",0);
        int activarnotuno = sp.getInt("Notuno",1);
        int activarnotdos = sp.getInt("Notdos",1);
        int activarnottres = sp.getInt("Notres",1);
        int activarnotcuatro = sp.getInt("Notcuatro",1);
        int activarnotcinco = sp.getInt("Notcinco",1);

        if (activarnotuno==1){ stch_mensajes.setChecked(false); }else{stch_mensajes.setChecked(true);}
        if (activarnotdos==1){ stch_actividadcuenta.setChecked(false); }else{stch_actividadcuenta.setChecked(true);}
        if (activarnottres==1){ stch_nuevaorden.setChecked(false); }else{stch_nuevaorden.setChecked(true);}
        if (activarnotcuatro==1){ stch_seguimientoorden.setChecked(false); }else{stch_seguimientoorden.setChecked(true);}
        if (activarnotcinco==1){ stch_ordencompletada.setChecked(false); }else{stch_ordencompletada.setChecked(true);}

        btn_guardarconnoti.setOnClickListener(new View.OnClickListener() {

            SharedPreferences switnotificaiones= getContext().getSharedPreferences("ConfiguracionNotificaciones",0);
            SharedPreferences.Editor editorswi=switnotificaiones.edit();
            @Override
            public void onClick(View v) {
                if (stch_mensajes.isChecked()){
                    Crearnotificaionmensaje();
                    CrearCanalNotificacionesmensajes();
                    editorswi.putInt("Notuno",0);
                    editorswi.putString("Tipouno","Mensajes");
                }else{
                    editorswi.putInt("Notuno",1);
                }

                if (stch_actividadcuenta.isChecked()){
                    CrearnotificaionActividadcuentas();
                    CrearCanalNotificacionesActividadcuenta();
                    editorswi.putInt("Notdos",0);
                    editorswi.putString("Tipodos","Actividad Cuenta");
                }else{editorswi.putInt("Notdos",1);}

                if (stch_nuevaorden.isChecked()){
                    CrearnotificacionesNuevaorden();
                    CrearCanalNotificacionesNuevaorden();
                    editorswi.putInt("Notres",0);
                    editorswi.putString("TipoTres","Nueva orden");
                }else{
                    editorswi.putInt("Notres",1);
                }

                if (stch_seguimientoorden.isChecked()){
                    CrearnotificaionSeguiminetoorden();
                    CrearCanalNotificacionesSeguimientoorden();
                    editorswi.putInt("Notcuatro",0);
                    editorswi.putString("Tipocuatro","Seguimiento Orden");
                }else{editorswi.putInt("Notcuatro",1);}
                if (stch_ordencompletada.isChecked()){
                    Crearnotificaionordencompletada();
                    CrearCanalNotificacionesordencompletada();
                    editorswi.putInt("Notcinco",0);
                    editorswi.putString("tipocinco","Orden Completada");
                }else{editorswi.putInt("Notcinco",1);}
                editorswi.commit();

            }
        });

        return view;
    }

    private void CrearCanalNotificacionesmensajes() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Notificaicones mensaje";
            NotificationChannel notificationChannel=new NotificationChannel(Channel_ID1,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager= (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void Crearnotificaionmensaje(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),Channel_ID1);
        builder.setSmallIcon(R.drawable.ic_prueba);
        builder.setContentTitle("Nuevo mensaje");
        builder.setContentText("Prueba de mensaje");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(Notificaiones_ID1,builder.build());

    }

    private void CrearCanalNotificacionesActividadcuenta() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Notificaicones Activivad";
            NotificationChannel notificationChannel2=new NotificationChannel(Channel_ID2,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager2= (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager2.createNotificationChannel(notificationChannel2);

        }
    }

    private void CrearnotificaionActividadcuentas(){
        NotificationCompat.Builder builder2=new NotificationCompat.Builder(getContext(),Channel_ID2);
        builder2.setSmallIcon(R.drawable.ic_prueba);
        builder2.setContentTitle("Nuevo Actividad");
        builder2.setContentText("Prueba de Actividad de cuenta");
        builder2.setColor(Color.RED);
        builder2.setPriority(2);
        builder2.setLights(Color.MAGENTA,1000,1000);
        builder2.setVibrate(new long[]{1000,1000,1000,1000});
        builder2.setDefaults(Notification.DEFAULT_SOUND);
        builder2.setCategory(NotificationCompat.CATEGORY_MESSAGE);

        NotificationManagerCompat notificationManagerCompat2=NotificationManagerCompat.from(getContext());
        notificationManagerCompat2.notify(Notificaiones_ID2,builder2.build());

    }

    private void CrearCanalNotificacionesNuevaorden() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Notificaicones Nueva orden";
            NotificationChannel notificationChannel=new NotificationChannel(Channel_ID3,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager= (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void CrearnotificacionesNuevaorden(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),Channel_ID3);
        builder.setSmallIcon(R.drawable.ic_prueba);
        builder.setContentTitle("Nuevo Orden");
        builder.setContentText("Prueba de nueva orden");
        builder.setColor(Color.GREEN);
        builder.setPriority(3);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(Notificaiones_ID3,builder.build());

    }

    private void CrearCanalNotificacionesSeguimientoorden() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Notificaicones Seguimiento orden";
            NotificationChannel notificationChannel=new NotificationChannel(Channel_ID4,name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager= (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void CrearnotificaionSeguiminetoorden(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),Channel_ID4);
        builder.setSmallIcon(R.drawable.ic_prueba);
        builder.setContentTitle("Nuevo Seguimiento orden");
        builder.setContentText("Prueba de Seguimiento orden");
        builder.setColor(Color.YELLOW);
        builder.setPriority(4);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(Notificaiones_ID4,builder.build());

    }

    private void CrearCanalNotificacionesordencompletada() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="Notificaicones Orden Completada";
            NotificationChannel notificationChannel=new NotificationChannel(Channel_ID5,name, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager= (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    private void Crearnotificaionordencompletada(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),Channel_ID5);
        builder.setSmallIcon(R.drawable.ic_prueba);
        builder.setContentTitle("Nuevo Orden completada");
        builder.setContentText("Prueba de Orden completada");
        builder.setColor(Color.YELLOW);
        builder.setPriority(5);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(Notificaiones_ID5,builder.build());

    }
}