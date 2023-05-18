//Creado por Briceño Malpartida Douglas Igancio - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion.ui.Soporte;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.activity_is_actiniciarsesionprincipal;

public class sp_frgmenusoporte extends Fragment {

    Button btnconfNotificaciones,btncentroayuda,btnTerminosyCondiciones,btncompartir, btnacercade,btn_codigotienda,btn_cerrar_sesion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sp_frgmenusoporte, container, false);
        btnconfNotificaciones=view.findViewById(R.id.btn_Notificaciones);
        btncentroayuda=view.findViewById(R.id.btn_centroayuda);
        btnTerminosyCondiciones=view.findViewById(R.id.btn_terminois_condiciones);
        btncompartir=view.findViewById(R.id.btn_compartir);
        btnacercade=view.findViewById(R.id.btn_acerca_de);
        btn_codigotienda=view.findViewById(R.id.btn_spcodigo_tienda);
        btn_cerrar_sesion=view.findViewById(R.id.btn_cerrar_sesion);


        btnconfNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_configuracionnotificaiones);

            }
        });

        btncentroayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_centroayuda);
            }
        });
        btnTerminosyCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_terminosYcondiciones);
            }
        });

        btncompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Descarga la aplicacion URL");
                startActivity(Intent.createChooser(intent,"Compartir App con"));

            }
        });

        btnacercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_acercade);
            }
        });


        btn_codigotienda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_codigotienda);
            }
        });
        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.sp_litmodelcerrarsesion);
                dialog.show();
                Button btnaceptar = dialog.findViewById(R.id.sp_mpabtnaceptar);
                Button btncancelar = dialog.findViewById(R.id.sp_mpabtncancelar);

                btnaceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //implementacion de cierre de sesion
                        //SE CIERRA LA SESION Y ENVIA A LA PANTALLA PRINCIPAL
                        SharedPreferences sharedPreferencesLogin = getActivity().getSharedPreferences("Credencial_Global_usuario", 0);
                        SharedPreferences.Editor editor = sharedPreferencesLogin.edit();

                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(getActivity(), activity_is_actiniciarsesionprincipal.class);
                        startActivity(intent);
                    }
                });

                btncancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //SE DESAPARECE EL DIALOGO
                        dialog.dismiss();
                    }
                });



            }
        });


        return view;
    }
}