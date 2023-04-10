//Creado por Briceño Malpartida Douglas Igancio - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion.ui.Soporte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;


public class sp_frgCodigoTienda extends Fragment {

    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextInputEditText txt_codTienda;
    Button btn_Compartircodtienda;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sp_frgCodigoTienda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sp_frgCodigoTienda.
     */
    // TODO: Rename and change types and number of parameters
    public static sp_frgCodigoTienda newInstance(String param1, String param2) {
        sp_frgCodigoTienda fragment = new sp_frgCodigoTienda();
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
        View view= inflater.inflate(R.layout.fragment_sp_frg_codigo_tienda, container, false);
        request = Volley.newRequestQueue(getContext());
        txt_codTienda=view.findViewById(R.id.txt_Codigotienda);
        btn_Compartircodtienda=view.findViewById(R.id.btn_compartircodtendero);


        CargarCodigoTienda();


        btn_Compartircodtienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Su Còdigo de tienda es:   "+txt_codTienda.getText().toString());
                startActivity(Intent.createChooser(intent,"Comparte tu còdigo con"));
            }
        });
        return view;
    }

    private void CargarCodigoTienda() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("consultando ...");
        progreso.show();
        //Obtencion de Credenciales
        SharedPreferences creduser= getContext().getSharedPreferences("Credencial_Global_usuario",0);
        String iduser=creduser.getString("CredId","");
        String url =  Util.RUTA +  "c_consultar_codigo_tienda_soporte.php?idpersona="+iduser;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                JSONArray json=response.optJSONArray("CodTienda");
                JSONObject jsonObject= null;
                try {
                    jsonObject=json.getJSONObject(0);  //

                    txt_codTienda.setText(jsonObject.optString("idTienda"));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(),"No se pudo consultar"+ error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Error ", error.toString());
            }
        });
        request.add(jsonObjectRequest);


    }
}