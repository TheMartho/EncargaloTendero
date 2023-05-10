//Creado por Morales Fabian Renzo Gabriel - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad;

import android.app.Application;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;
import static android.content.Context.MODE_PRIVATE;


public class pub_frgpagaranuncio extends Fragment {

        private static pub_frgpagaranuncio instance;

        public void onCreate() {
            instance = this;
        }

    public void toast() {
        Toast.makeText(getContext(), "Cancelar", Toast.LENGTH_SHORT).show();
    }
    Button pub_pabtnpagoefectivo;
    Button pub_pabtncancelarpago;
    Button pub_pabtnpagaranuncio;
    View auxview;
    TextView pub_patxtcargodia, pub_patxtdia, pub_patxttotalpago, pub_patxtfechadesde, pub_patxtfechahasta;
    float preciot = 0;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_pub_frgpagaranuncio, container, false);
        request= Volley.newRequestQueue(getContext());
        pub_pabtnpagoefectivo=vista.findViewById(R.id.pub_pabtnpagoefectivo);
        pub_pabtncancelarpago=vista.findViewById(R.id.pub_pabtncancelarpago);
        pub_pabtnpagaranuncio=vista.findViewById(R.id.pub_pabtnpagaranuncio);
        pub_patxtcargodia=vista.findViewById(R.id.pub_patxtcargodia);
        pub_patxtdia=vista.findViewById(R.id.pub_patxtdia);
        pub_patxttotalpago=vista.findViewById(R.id.pub_patxttotalpago);
        pub_patxtfechadesde=vista.findViewById(R.id.pub_patxtfechadesde);
        pub_patxtfechahasta=vista.findViewById(R.id.pub_patxtfechahasta);

        SharedPreferences sharedPreferences =
                getContext().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, MODE_PRIVATE);

        Date fecha_inicio = new Date(Date.parse(sharedPreferences.getString("fechaInicio",""))) ;
        Date fecha_fin = new Date(Date.parse(sharedPreferences.getString("fechaFin",""))) ;
        Date res = new Date(fecha_fin.getTime() - fecha_inicio.getTime());

        float preciodia = 1;
        int dias = (int) (res.getTime()/86400000);
        preciot = preciodia*dias;
        pub_patxtfechadesde.setText(sharedPreferences.getString("fechaInicio",""));
        pub_patxtfechahasta.setText(sharedPreferences.getString("fechaFin",""));
        pub_patxtcargodia.setText(preciodia+" $");
        pub_patxtdia.setText(dias+" dias");
        pub_patxttotalpago.setText(preciot+" $");
        pub_pabtncancelarpago.setVisibility(View.INVISIBLE);
        pub_pabtnpagaranuncio.setVisibility(View.INVISIBLE);
        pub_pabtnpagoefectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pub_pabtncancelarpago.setVisibility(View.VISIBLE);
                pub_pabtnpagaranuncio.setVisibility(View.VISIBLE);
            }
        });
        pub_pabtnpagaranuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auxview = view;
                showDialogoPagar();
            }
        });

        pub_pabtncancelarpago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auxview = view;
                //showDialogoCancelar();
                Navigation.findNavController(view).navigate(R.id.nav_crearanuncio);

            }
        });
        return vista;
    }

    private void  showDialogoPagar(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.pub_lytmodelpagaranuncio);
        dialog.show();
        Button btnpagar = dialog.findViewById(R.id.pub_mpabtnaceptar);
        Button btncancelar = dialog.findViewById(R.id.pub_mpabtncancelar);
        btnpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myURL = Util.IP_SERVER+ "a_nuevo_anuncio.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getContext(), "Publicación registrada", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(auxview).navigate(R.id.nav_codigopago);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SharedPreferences sharedPreferences =
                                getContext().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, MODE_PRIVATE);
                        Map<String, String> params = new Hashtable<String, String>();
                        params.put("pathImg", sharedPreferences.getString("pathImg",""));
                        params.put("titulo", sharedPreferences.getString("titulo",""));
                        params.put("descripcion", sharedPreferences.getString("descripcion",""));
                        params.put("linkRed", sharedPreferences.getString("linkRed",""));
                        params.put("fechaInicio", sharedPreferences.getString("fechaInicio",""));
                        params.put("fechaFin", sharedPreferences.getString("fechaFin",""));
                        params.put("idTienda", sharedPreferences.getString(Util.VARGOB_ID_TENDERO,""));
                        params.put("montoPubli", preciot+"" );
                        params.put("fechaPago", sharedPreferences.getString("fechaInicio",""));
                        return params;
                    }
                };
                request.add(stringRequest);
                dialog.dismiss();
            }
        });
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
