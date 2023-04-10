package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Map;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;


public class ga_frgregistrarproveedor extends Fragment {

    Button ga_RPbtnAgregarProv;
    ImageButton ga_RPbtnNuevoProv;
    EditText rpRfcprov,rpNombreprov,rpDireccionprov,rpContactoprov,rpTelefonoprov,ga_rpedtfechaactual;
    Spinner rpCiudadprov,rpDistritoprov;
    ProgressDialog progreso;
    RequestQueue request;

   String sCiudad,sDistrito;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ga_frgregistrarproveedor, container, false);


        ga_RPbtnAgregarProv=view.findViewById(R.id.ga_rpbtnagregarprov);
        ga_RPbtnNuevoProv=view.findViewById(R.id.ga_rpbtnnuevoprov);
        rpRfcprov = view.findViewById(R.id.ga_rpedtrfcprov);
        rpNombreprov = view.findViewById(R.id.ga_rpedtnombreprov);
        rpDireccionprov = view.findViewById(R.id.ga_rpedtdireccionprov);
        rpCiudadprov = view.findViewById(R.id.ga_rpspnciudadprov);
        rpDistritoprov = view.findViewById(R.id.ga_rpspndistritoprov);
        rpContactoprov = view.findViewById(R.id.ga_rpedtcontactoprov);
        rpTelefonoprov = view.findViewById(R.id.ga_rpedttelefonoprov);
        ga_rpedtfechaactual=view.findViewById(R.id.ga_rpedtfechaactual);
        
        optenerFechaActualProveedor();

        rpCiudadprov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sCiudad = (String) rpCiudadprov.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rpDistritoprov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sDistrito=(String) rpDistritoprov.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        rpNombreprov.setEnabled(false);
        rpDireccionprov.setEnabled(false);
        rpCiudadprov.setEnabled(false);
        rpDistritoprov.setEnabled(false);
        rpContactoprov.setEnabled(false);
        rpTelefonoprov.setEnabled(false);
        ga_RPbtnAgregarProv.setEnabled(false);

        request = Volley.newRequestQueue(getContext());

        ga_RPbtnAgregarProv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                validar();
                if (validar()==true){
                    insertproveedor(view);
                }
                rpRfcprov.setText("");
                rpNombreprov.setText("");
                rpDireccionprov.setText("");
                rpContactoprov.setText("");
                rpTelefonoprov.setText("");

            }


        });

        ga_RPbtnNuevoProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rpNombreprov.setEnabled(true);
                rpDireccionprov.setEnabled(true);
                rpCiudadprov.setEnabled(true);
                rpDistritoprov.setEnabled(true);
                rpContactoprov.setEnabled(true);
                rpTelefonoprov.setEnabled(true);
                ga_RPbtnAgregarProv.setEnabled(true);
            }
        });
        return view;
    }

    private void optenerFechaActualProveedor() {

        ga_rpedtfechaactual.setEnabled(false);

        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String formatteDate = df.format(date);

        ga_rpedtfechaactual.setText(formatteDate+" ");
    }

    public boolean validar (){
        boolean retorno = true;

        String sCiud=sCiudad;
        String sDist=sDistrito;
        String Rfc = rpRfcprov.getText().toString();
        String NomPro = rpNombreprov.getText().toString();
        String DirecPro = rpDireccionprov.getText().toString();
        String ContaPro = rpContactoprov.getText().toString();
        String TelePro = rpTelefonoprov.getText().toString();


        if (Rfc.isEmpty()){
            rpRfcprov.setError("Este campo no puede quedar vacio");
            retorno=false;
        }
        if (NomPro.isEmpty()){
            rpNombreprov.setError("Este campo no puede quedar vacio");
            retorno=false;

        }
        if (DirecPro.isEmpty()){
            rpDireccionprov.setError("Este campo no puede quedar vacio");
            retorno=false;
        }
        if (ContaPro.isEmpty()){
            rpContactoprov.setError("Este campo no puede quedar vacio");
            retorno=false;
        }
        if (TelePro.isEmpty()){
            rpTelefonoprov.setError("Este campo no puede quedar vacio");
            retorno=false;
        }

        if (sCiud.equals("Seleccione")){
            Toast.makeText(getContext(), "Seleccione una Ciudad", Toast.LENGTH_SHORT).show();
            retorno=false;
        }
        if (sDist.equals("Seleccione")){
            Toast.makeText(getContext(), "Seleccione un Distrito", Toast.LENGTH_SHORT).show();
            //rpTelefonoprov.setError("Seleccione una Distrito");
            retorno=false;
        }
        ga_RPbtnNuevoProv.setEnabled(false);
        return retorno;

    }

    private void insertproveedor(View view) {


        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Registrando........");
        progreso.show();

        String url = Util.RUTA+"a_reg_proveedor_almacen.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progreso.hide();

                        Navigation.findNavController(view).navigate(R.id.action_nav_registrar_proveedor_to_nav_proveedor_registrado);
                        Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progreso.hide();

                        Toast.makeText(getContext(), "No hay conexion a internet" +volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{


                String provNombre = rpNombreprov.getText().toString().trim();
                String provDireccion = rpDireccionprov.getText().toString().trim();
                String provCiudad = sCiudad;
                String provDistrito =sDistrito;
                String provCorreo = rpContactoprov.getText().toString().trim();
                String provTelefono = rpTelefonoprov.getText().toString().trim();
                String provRFC = rpRfcprov.getText().toString().trim();

                Map<String,String> params = new Hashtable<String, String>();

                params.put("provNombre",provNombre);
                params.put("provDireccion",provDireccion);
                params.put("provCiudad",provCiudad);
                params.put("provDistrito",provDistrito);
                params.put("provCorreo",provCorreo);
                params.put("provTelefono",provTelefono);
                params.put("provRFC",provRFC);

                return params;
            }
        };

        request.add(stringRequest);

    }
}