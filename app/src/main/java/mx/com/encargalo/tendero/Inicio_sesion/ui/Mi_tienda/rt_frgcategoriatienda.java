package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Adapter.rt_adaprubrostienda;
import mx.com.encargalo.tendero.Entidad.rt_EntidadRubrosTienda;
import mx.com.encargalo.tendero.Util.Util;

import static android.content.Context.MODE_PRIVATE;

public class rt_frgcategoriatienda extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    RecyclerView rt_ctrclvrubros; //+@OHC04.11.2022

    ArrayList<rt_EntidadRubrosTienda> rt_arlrubrostienda; //+@OHC04.11.2022
    ProgressDialog rt_pdrubrostienda; //+@OHC04.11.2022
    RequestQueue rt_rqrubrostienda; //+@OHC04.11.2022
    JsonObjectRequest rt_jorrubrostienda; //+@OHC04.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_rt_frgcategoriatienda, container, false);

        rt_arlrubrostienda = new ArrayList<>();
        rt_ctrclvrubros = vista.findViewById(R.id.rt_ctrclvrubros);
        rt_ctrclvrubros.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rt_ctrclvrubros.setHasFixedSize(true);

        rt_rqrubrostienda = Volley.newRequestQueue(requireContext());
        rt_fctWebServiceRubrosTienda();

        //Borrando Shared Preferences
        SharedPreferences.Editor borrandosharedpreferences = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, MODE_PRIVATE).edit();
        borrandosharedpreferences.clear().apply();

        return vista;
    }//+}@OHC04.11.2022

    private void rt_fctWebServiceRubrosTienda() {
        rt_pdrubrostienda = new ProgressDialog(getContext());
        rt_pdrubrostienda.setMessage("Consultando Rubros");
        rt_pdrubrostienda.show();

        String url= Util.RUTA + "c_rubros_tiendas_registrotienda.php";
        url=url.replace(" ","%20");
        rt_jorrubrostienda= new JsonObjectRequest(Request.Method.GET, url,null,this, this);
        rt_rqrubrostienda.add(rt_jorrubrostienda);

    } //+}@OHC04.11.2022


    @Override
    public void onErrorResponse(VolleyError error) {
        rt_pdrubrostienda.hide();
        Toast.makeText(getContext(), "Error al consultar " + error.toString(), Toast.LENGTH_SHORT).show();
    } //+}@OHC04.11.2022

    @Override
    public void onResponse(JSONObject response) {
        rt_pdrubrostienda.hide();
        rt_EntidadRubrosTienda rt_etrubros;
        JSONArray json=response.optJSONArray("rubros");
        try {
            for (int i = 0; i < Objects.requireNonNull(json).length(); i++) {
                rt_etrubros = new rt_EntidadRubrosTienda();

                JSONObject rt_jojsonObject;
                rt_jojsonObject = json.getJSONObject(i);
                rt_etrubros.setIdRubroTienda(rt_jojsonObject.optInt("idRubroTienda"));
                rt_etrubros.setRtDescripcion(rt_jojsonObject.optString("rtDescripcion"));

                rt_arlrubrostienda.add(rt_etrubros);
            }
            rt_pdrubrostienda.hide();

            rt_adaprubrostienda adapter = new rt_adaprubrostienda(getContext(), rt_arlrubrostienda,
                    (item, vista) -> {
                        //CONTENIDO
                        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("idRubroTienda", String.valueOf(item.getIdRubroTienda()));
                        editor.apply();

                    });
            rt_ctrclvrubros.setAdapter(adapter);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "No hay conexión con el servidor", Toast.LENGTH_SHORT).show();
            rt_pdrubrostienda.hide();
        }
    } //+}@OHC04.11.2022
}