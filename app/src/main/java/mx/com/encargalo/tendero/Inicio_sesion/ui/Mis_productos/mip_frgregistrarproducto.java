package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Entidad.mip_EntidadProductosTienda;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda.rt_fctautocompletado;
import mx.com.encargalo.tendero.Util.Util;

public class mip_frgregistrarproducto extends Fragment {

    TextInputEditText mip_rpedtbusquedanombre, mip_rpedtdescpricion, mip_rpedtstock, mip_rpedtstockminimo, mip_rpedtcategoria, mip_rpedtcostoproducto, mip_rpedtprecioventa;
    Button mit_rpbtnagregarproducto, mit_rpbtnproductopropio;
    Spinner mip_rpspnunidaddemedida;
    ImageButton mip_rpbtnbuscarproducto;
    ImageView mip_rpimgproducto;

    public static ArrayList<mip_EntidadProductosTienda> mit_arlregistrarproducto; //+@OHC20.11.2022
    ProgressDialog mip_pdregistrarproducto; //+@OHC20.11.2022
    RequestQueue mip_rqregistrarproducto, mip_rqregistroproducto; //+@OHC20.11.2022
    JsonObjectRequest mip_jorregistrarproducto; //+@OHC20.11.2022
    StringRequest mip_srregistrarproducto; //+@OHC20.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mip_frgregistrarproducto, container, false);

        mip_rpimgproducto = view.findViewById(R.id.mip_rpimgproducto);

        mip_rpedtbusquedanombre = view.findViewById(R.id.mip_rpedtbusquedanombre);
        mip_rpbtnbuscarproducto = view.findViewById(R.id.mip_rpbtnbuscarproducto);

        mit_rpbtnagregarproducto = view.findViewById(R.id.mip_rpbtnagregarproducto);
        mit_rpbtnproductopropio = view.findViewById(R.id.mip_rpbtnproductopropio);
        mip_rpedtdescpricion = view.findViewById(R.id.mip_rpedtdescpricion);
        mip_rpedtstock = view.findViewById(R.id.mip_rpedtstock);
        mip_rpedtstockminimo = view.findViewById(R.id.mip_rpedtstockminimo);
        mip_rpedtcategoria = view.findViewById(R.id.mip_rpedtcategoria);

        mip_rpspnunidaddemedida = view.findViewById(R.id.mip_rpspnunidaddemedida);

        mip_rpedtcostoproducto = view.findViewById(R.id.mip_rpedtcostoproducto);
        mip_rpedtprecioventa = view.findViewById(R.id.mip_rpedtprecioventa);

        mit_arlregistrarproducto = new ArrayList<>(); //+@OHC20.11.2022
        mit_arlregistrarproducto.clear();

        mip_rpedtdescpricion.setEnabled(false);
        mip_rpedtstock.setEnabled(false);
        mip_rpedtstockminimo.setEnabled(false);
        mip_rpspnunidaddemedida.setEnabled(false);
        mip_rpedtcategoria.setEnabled(false);
        mip_rpedtcostoproducto.setEnabled(false);
        mip_rpedtprecioventa.setEnabled(false);

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        if (preferencias.contains("idProducto")){
            String urlimage = Util.RUTA + preferencias.getString("proImagen", "");
            Glide.with(getActivity()).load(urlimage).into(mip_rpimgproducto);

            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("proDescripcion", mip_rpedtdescpricion, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("cpNombre", mip_rpedtcategoria, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("proPrecioCostoPromedio", mip_rpedtcostoproducto, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("proPrecioVentaRecomendado", mip_rpedtprecioventa, requireContext());
            mip_rpspnunidaddemedida.setSelection(rt_fctautocompletado.IdSpinnerUnidadMedida(preferencias.getString("proUnidadMedida","")));

            mip_rpedtstock.setText("0.0");
            mip_rpedtstockminimo.setText("0.0");

            mip_rpedtdescpricion.setEnabled(true);
            mip_rpedtstock.setEnabled(true);
            mip_rpedtstockminimo.setEnabled(true);
            mip_rpedtcostoproducto.setEnabled(true);
            mip_rpedtprecioventa.setEnabled(true);

        }

        mip_rqregistrarproducto = Volley.newRequestQueue(requireContext()); //+@OHC04.11.2022


        mip_rpbtnbuscarproducto.setOnClickListener(view1 -> {
            TraerListadoProductos(view, mip_rpedtbusquedanombre.getText().toString());
        });
        mit_rpbtnagregarproducto.setOnClickListener(view2 -> {
            if(mip_rpedtdescpricion.getText().toString().equals("")||mip_rpedtcostoproducto.getText().toString().equals("")
                    || mip_rpedtprecioventa.getText().toString().equals("")|| mip_rpedtstock.getText().toString().equals("")
                    || mip_rpedtstockminimo.getText().toString().equals(""))
                Toast.makeText(getActivity(), "Rellenar todos los campos!", Toast.LENGTH_SHORT).show();
            else{
                if( Float.valueOf(mip_rpedtstock.getText().toString())  >= Float.valueOf(mip_rpedtstockminimo.getText().toString())){
                    if(Float.valueOf(mip_rpedtprecioventa.getText().toString())  >= Float.valueOf(mip_rpedtcostoproducto.getText().toString()))
                        RegistrarProducto(view2);
                    else Toast.makeText(getActivity(), "Precio de Venta debe ser mayor o igual a Costo del Producto!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), "Stock debe ser mayor o igual a Stock Mínimo!", Toast.LENGTH_SHORT).show();
            }
        });
        mit_rpbtnproductopropio.setOnClickListener(view3 ->{
            editor.clear().apply();
            Navigation.findNavController(view3).navigate(R.id.action_mip_frgregistrarproducto_to_mip_frgproductopropio);
        });

        return view;
    }

    private void TraerListadoProductos(View view, String p_proDescripcion) {
        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);

        mip_pdregistrarproducto = new ProgressDialog(getContext());
        mip_pdregistrarproducto.setMessage("Consultando Productos");
        mip_pdregistrarproducto.show();

        String url = Util.RUTA + "c_productos_globales_mitienda.php?p_proDescripcion=" + p_proDescripcion+ "&p_idTienda="+ preferencias.getString("idTienda", "");
        url = url.replace(" ", "%20");
        mip_jorregistrarproducto = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mit_arlregistrarproducto.clear();
                mip_pdregistrarproducto.hide();
                mip_EntidadProductosTienda mit_etlistadoproductos;
                JSONArray json_listadoproductos = response.optJSONArray("lista_productos");
                try {
                    for (int i = 0; i < json_listadoproductos.length(); i++) {

                        mit_etlistadoproductos = new mip_EntidadProductosTienda();
                        JSONObject rt_jojsonObject_misproductos;
                        rt_jojsonObject_misproductos = json_listadoproductos.getJSONObject(i);

                        mit_etlistadoproductos.setLptImagen1(rt_jojsonObject_misproductos.optString(""));
                        mit_etlistadoproductos.setIdProducto(rt_jojsonObject_misproductos.optInt("idProducto"));
                        mit_etlistadoproductos.setProImagen(rt_jojsonObject_misproductos.optString("proImagen"));
                        mit_etlistadoproductos.setProDescripcion(rt_jojsonObject_misproductos.optString("proDescripcion"));
                        mit_etlistadoproductos.setProPrecioCostoPromedio(rt_jojsonObject_misproductos.optDouble("proPrecioCostoPromedio"));
                        mit_etlistadoproductos.setProPrecioVentaRecomendado(rt_jojsonObject_misproductos.optDouble("proPrecioVentaRecomendado"));
                        mit_etlistadoproductos.setProUnidadMedida(rt_jojsonObject_misproductos.optString("proUnidadMedida"));
                        mit_etlistadoproductos.setCpNombre(rt_jojsonObject_misproductos.optString("cpNombre"));

                        mit_arlregistrarproducto.add(mit_etlistadoproductos);
                    }
                    mip_pdregistrarproducto.hide();

                    Navigation.findNavController(view).navigate(R.id.action_mip_frgregistrarproducto_to_mip_frglistaproductos);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    mip_pdregistrarproducto.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mip_pdregistrarproducto.hide();

                if (error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value [] of type org.json.JSONArray cannot be converted to JSONObject")) {

                    mit_arlregistrarproducto.clear();

                    Toast.makeText(getContext(), "No existe el producto buscado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al consultar " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mip_rqregistrarproducto.add(mip_jorregistrarproducto);
    }

    private void RegistrarProducto(View view) {
        mip_pdregistrarproducto = new ProgressDialog(getContext());
        mip_pdregistrarproducto.setMessage("Enviando Datos...");
        mip_pdregistrarproducto.show();

        String url = Util.RUTA + "a_producto_existente_registroproducto.php?";
        mip_srregistrarproducto = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                mip_rpedtdescpricion.setText("");
                mip_rpedtstock.setText("");
                mip_rpedtstockminimo.setText("");
                mip_rpspnunidaddemedida.setSelection(0);
                mip_rpedtcategoria.setText("");
                mip_rpedtcostoproducto.setText("");
                mip_rpedtprecioventa.setText("");
                mip_pdregistrarproducto.hide();
                Navigation.findNavController(view).navigate(R.id.action_mip_frgregistrarproducto_to_mip_frgproductoagregado);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mip_pdregistrarproducto.hide();
                Toast.makeText(getContext(), "No se pudo registrar." + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
                SharedPreferences preferencias_prod = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);

                Map<String, String> params = new Hashtable<String, String>();
                params.put("p_lptDescripcionProductoTienda", mip_rpedtdescpricion.getText().toString());
                params.put("p_lptStock", mip_rpedtstock.getText().toString());
                params.put("p_lptUnidadMedida", mip_rpspnunidaddemedida.getSelectedItem().toString());
                params.put("p_lptStockMinimo", mip_rpedtstockminimo.getText().toString());
                params.put("p_lptImagen1", preferencias_prod.getString("proImagen", ""));
                params.put("p_lptPrecioCompra", mip_rpedtcostoproducto.getText().toString());
                params.put("p_lptPrecioVenta", mip_rpedtprecioventa.getText().toString());
                params.put("p_idProducto", preferencias_prod.getString("idProducto", ""));
                params.put("p_idTienda", preferencias.getString("idTienda", ""));
                return params;
            }

        };
        mip_rqregistroproducto= Volley.newRequestQueue(getContext());
        mip_rqregistroproducto.add(mip_srregistrarproducto);

    }

}