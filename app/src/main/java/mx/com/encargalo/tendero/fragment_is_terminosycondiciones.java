package mx.com.encargalo.tendero;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Entidad.is_entidad_tyc;
import mx.com.encargalo.tendero.Util.Util;


public class fragment_is_terminosycondiciones extends DialogFragment {



    TextView  is_tyctxtcontenido;
    TextView  is_politicastxtcontenido;
    Button btnregresar;
    Activity activity;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return creardialogoterminosycondiciones();
    }

    private AlertDialog creardialogoterminosycondiciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.fragment_is_terminosycondiciones, null);
        builder.setView(view);

        is_tyctxtcontenido = view.findViewById(R.id.is_txtcontenidotyc);
        is_politicastxtcontenido = view.findViewById(R.id.is_txtcontenidopoliticas);
        btnregresar = view.findViewById(R.id.is_tycbtncerrarterminos);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        mostrarTerminosCondiciones();
        mostrarPoliticasprivacidad();

        return builder.create();
    }

    private void mostrarTerminosCondiciones() {

        String url = null;
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Terminos_Condiciones");
//        progressDialog.show();

        url = Util.RUTA + "c_consultar_terms_condiciones_uso.php";


        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                is_entidad_tyc entidadTerms = null;
                JSONArray jsonArray = response.optJSONArray("termscondicionesuso");

                try {
                    entidadTerms = new is_entidad_tyc();
                    JSONObject jsonObject = null;
                    jsonObject = jsonArray.getJSONObject(0);


                    entidadTerms.setTyccontenido(jsonObject.optString("tecoContenido"));
//                    progressDialog.hide();


                    is_tyctxtcontenido.setText(entidadTerms.getTyccontenido());
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Error al Consultar", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void mostrarPoliticasprivacidad() {

        String url = null;
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Politicas de privacidad");
//        progressDialog.show();

        url = Util.RUTA + "c_consultar_politicas_privacidad.php";


        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                is_entidad_tyc entidadTerms = null;
                JSONArray jsonArray = response.optJSONArray("politicasprivacidad");

                try {
                    entidadTerms = new is_entidad_tyc();
                    JSONObject jsonObject = null;
                    jsonObject = jsonArray.getJSONObject(0);


                    entidadTerms.setTycpoliticas(jsonObject.optString("tecoContenido"));
//                    progressDialog.hide();


                    is_politicastxtcontenido.setText(entidadTerms.getTycpoliticas());
                } catch (JSONException e) {
                    e.printStackTrace();
//                    progressDialog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Error al Consultar", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.activity = (Activity) context;
        }else{
            throw new RuntimeException(context.toString() + "Debe implementar OnFragmentInteractionListener");
        }
    }
}