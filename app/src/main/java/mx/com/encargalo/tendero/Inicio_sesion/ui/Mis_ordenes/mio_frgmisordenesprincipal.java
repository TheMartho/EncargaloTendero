/*
 * Autor: Jimmy Jiménez Cerrón
 * Institución: Universidad Continental
 * Año: 2022
 */

package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_ordenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Adapter.mio_adaprvOrdenesOrden;
import mx.com.encargalo.tendero.Entidad.mio_mdlOrdenesOrden;
import mx.com.encargalo.tendero.Util.Util;


public class mio_frgmisordenesprincipal extends Fragment  {

    RecyclerView mio_moprclvlistaproductos;
    RequestQueue requestQueue;
    ArrayList<mio_mdlOrdenesOrden> ArrayOrden;
    Spinner mio_mopspnordenarporestado, mio_mopspnordenarporfecha;
    mio_adaprvOrdenesOrden adapter;
    EditText mio_txtOrdenId;
    ImageButton mio_imgbtnOrdenId,mio_imgbtnOrdenEstado, mio_imgbtnOrdenFecha;

    String valorSpinnerEstado = "";
    String valorSpinnerFecha = "";
    String valorSearchOrdenId = "";
    String idtie = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mio_frgmisordenesprincipal, container, false);

        mio_moprclvlistaproductos = vista.findViewById(R.id.mio_moprclvlistaproductos);
        mio_mopspnordenarporestado = vista.findViewById(R.id.mio_mopspnordenarporestado);
        mio_mopspnordenarporfecha = vista.findViewById(R.id.mio_mopspnordenarporfecha);
        mio_txtOrdenId = vista.findViewById(R.id.mio_txtOrdenId);
        mio_imgbtnOrdenId = vista.findViewById(R.id.mio_imgbtnOrdenId);
        mio_imgbtnOrdenEstado = vista.findViewById(R.id.mio_imgbtnOrdenEstado);
        mio_imgbtnOrdenFecha = vista.findViewById(R.id.mio_imgbtnOrdenFecha);

        ArrayOrden = new ArrayList<>();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        idtie = sharedPreferences.getString("idTienda", "");
//        int idtie  = sharedPreferences.getInt("idTienda", 0);


        mio_moprclvlistaproductos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mio_moprclvlistaproductos.setHasFixedSize(true);

        requestQueue = Volley.newRequestQueue(getContext());

        mio_mopspnordenarporestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                valorSpinnerEstado = mio_mopspnordenarporestado.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mio_mopspnordenarporfecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valorSpinnerFecha = mio_mopspnordenarporfecha.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String urlPorDefecto = Util.RUTA+"c_lista_ordenes_mis_ordenes.php?sp_idTienda="+idtie+"&sp_idOrden=0&sp_odEstado=%&sp_odFechaPedido=DESC";
        webServicesOrdenes(urlPorDefecto);

        mio_imgbtnOrdenEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroPorEstado();
            }
        });

        mio_imgbtnOrdenId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorSearchOrdenId = mio_txtOrdenId.getText().toString();
                filtroPorId();
            }
        });

        mio_imgbtnOrdenFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtrarPorFecha();
            }
        });

        return vista;
    }

    private void filtrarPorFecha() {
        String url = "";
        String fecha = "";

        if(valorSpinnerFecha.equals("Más antiguos")){
            fecha = "ASC";

            url = Util.RUTA+"c_lista_ordenes_mis_ordenes.php?sp_idTienda="+idtie+"&sp_idOrden=0&sp_odEstado=%&sp_odFechaPedido=ASC";
        }
        else{
            url = Util.RUTA+"c_lista_ordenes_mis_ordenes.php?sp_idTienda="+idtie+"&sp_idOrden=0&sp_odEstado=%&sp_odFechaPedido=DESC";
        }

        webServicesOrdenes(url);
    }

    private void filtroPorId() {
        String url="";
        String id = "";
        id = valorSearchOrdenId;
        if(!valorSearchOrdenId.equals("")){
            url = Util.RUTA+"c_lista_ordenes_mis_ordenes.php?sp_idTienda="+idtie+"&sp_idOrden="+id+"&sp_odEstado=ninguno&sp_odFechaPedido=DESC";
            webServicesOrdenes(url);
        }


    }

    private void filtroPorEstado(){


        String url="";
        String estado = "";


        if(valorSpinnerEstado.equals("ESTADO")){
            estado = "%";
        }else{
            estado = valorSpinnerEstado;
        }

        url = Util.RUTA+"c_lista_ordenes_mis_ordenes.php?sp_idTienda="+idtie+"&sp_idOrden=0&sp_odEstado="+estado+"&sp_odFechaPedido=DESC";

        webServicesOrdenes(url);

    }



    private void webServicesOrdenes(String URL) {


        if(ArrayOrden == null || ArrayOrden.size() == 0 )
        {


        }
        else{
            ArrayOrden.clear();
            adapter.notifyDataSetChanged();

        }


        URL.replace(" ", "%20");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mio_mdlOrdenesOrden orden = null;
                JSONArray json = response.optJSONArray("lista_ordenes");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        orden = new mio_mdlOrdenesOrden();

                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        orden.setIdOrden(jsonObject.optInt("idOrden"));
                        orden.setOdFechaPedido(jsonObject.optString("odFechaPedido"));
                        orden.setOdHoraPedido(jsonObject.optString("odHoraPedido"));
                        orden.setPerNombreCompleto(jsonObject.optString("perNombreCompleto"));
                        orden.setOdEstado(jsonObject.optString("odEstado"));
                        orden.setIdRepartidor(jsonObject.optInt("idRepartidor"));
                        orden.setRepNombres(jsonObject.optString("repNombres"));
                        ArrayOrden.add(orden);
                    }
                    adapter = new mio_adaprvOrdenesOrden(getContext(), ArrayOrden);

                    adapter.notifyDataSetChanged();

                    mio_moprclvlistaproductos.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof ParseError) {
                    Toast.makeText(getContext(), "No existe registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);


    }


}
