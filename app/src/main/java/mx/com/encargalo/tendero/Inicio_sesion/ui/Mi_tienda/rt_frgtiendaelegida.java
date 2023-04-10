package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;

public class rt_frgtiendaelegida extends Fragment {

    TextView rt_tietxtnombretienda; //+@OHC08.11.2022
    ImageView rt_imgtienda; //+@OHC08.11.2022

    ProgressDialog rt_pdunatienda; //+@OHC04.11.2022
    RequestQueue rt_rqunatienda; //+@OHC04.11.2022
    JsonObjectRequest rt_jorunatienda; //+@OHC04.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        View view = inflater.inflate(R.layout.fragment_rt_frgtiendaelegida, container, false);

        rt_tietxtnombretienda = view.findViewById(R.id.rt_tietxtnombretienda);
        rt_imgtienda = view.findViewById(R.id.rt_imgtienda);

        rt_rqunatienda = Volley.newRequestQueue(requireContext()); //+@OHC04.11.2022

        rt_pdunatienda = new ProgressDialog(getContext());
        rt_pdunatienda.setMessage("Obteniendo Datos");
        rt_pdunatienda.show();

        String url= Util.RUTA + "c_informacion_por_tienda_registrotienda.php?p_idTienda="+preferencias.getString("idTienda","");
        url=url.replace(" ","%20");
        rt_jorunatienda= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                rt_pdunatienda.hide();
                JSONArray jsonar_unatienda = response.optJSONArray("tiendas_info");
                String tieNombre = "";
                try{
                    for (int i = 0; i < Objects.requireNonNull(jsonar_unatienda).length(); i++) {
                        JSONObject jsonob_unatienda;
                        jsonob_unatienda = jsonar_unatienda.getJSONObject(i);
                        tieNombre = jsonob_unatienda.optString("tieNombre");
                        editor.putString("tieNombre", jsonob_unatienda.optString("tieNombre"));
                        editor.putString("tieImagen", jsonob_unatienda.optString("tieImagen"));
                        editor.putString("tieImagenurl", jsonob_unatienda.optString("tieImagenurl"));
                        editor.putString("tieURLWeb", jsonob_unatienda.optString("tieURLWeb"));
                        editor.putString("tieDescripcion", jsonob_unatienda.optString("tieDescripcion"));
                        editor.putString("tieCorreo", jsonob_unatienda.optString("tieCorreo"));
                        editor.putString("tieTelefono", jsonob_unatienda.optString("tieTelefono"));
                        editor.putString("tieDireccion", jsonob_unatienda.optString("tieDireccion"));
                        editor.putString("tieCiudadNom", jsonob_unatienda.optString("tieCiudad"));
                        editor.putString("tieEstado", jsonob_unatienda.optString("tieEstado"));
                        editor.putString("tieVentasMensuales", jsonob_unatienda.optString("tieVentasMensuales"));
                        editor.putString("tieInventarioEstimado", jsonob_unatienda.optString("tieInventarioEstimado"));
                        editor.putString("tieLatitud", jsonob_unatienda.optString("tieLatitud"));
                        editor.putString("tieLongitud", jsonob_unatienda.optString("tieLongitud"));
                        editor.putString("idRubroTienda", jsonob_unatienda.optString("idRubroTienda"));
                        editor.commit();
                    }
                    rt_tietxtnombretienda.setText(tieNombre);


                    //INSERTAR IMAGEN
                    if (preferencias.getString("tieImagenurl", "").equals("")){
                        editor.putString("tieImagen", "");
                        editor.commit();
                    }
                    else {
                        String urlimage = Util.RUTA + preferencias.getString("tieImagenurl", "");
                        Glide.with(getActivity()).asBitmap().load(urlimage).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                //Aquí obtengo el bitmap correctamente
                                String img = rt_fctautocompletado.getStringImagen(resource);
                                editor.putString("tieImagen", img);
                                editor.commit();
                            }
                        });
                        Glide.with(getActivity()).load(urlimage).into(rt_imgtienda);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No hay conexión con el servidor", Toast.LENGTH_SHORT).show();
                    rt_pdunatienda.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rt_pdunatienda.hide();
                Toast.makeText(getContext(), "Error al consultar " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        rt_rqunatienda.add(rt_jorunatienda);

//        Navigation.findNavController(view).getGraph().clear();

        return view;
    } //+}@OHC08.11.2022
}