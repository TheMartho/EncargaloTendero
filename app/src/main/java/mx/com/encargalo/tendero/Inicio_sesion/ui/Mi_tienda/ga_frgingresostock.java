package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Adapter.ga_adplistadoproductodescripcion;
import mx.com.encargalo.tendero.Entidad.ga_EntidadListadoProductoTienda;
import mx.com.encargalo.tendero.Entidad.ga_EntidadProductos;
import mx.com.encargalo.tendero.Util.Util;




public class ga_frgingresostock extends Fragment {
    ImageButton ga_isbtnbuscarprod;
    TextInputEditText ga_isedtcodsku,ga_isedtnombreprod,ga_isedtdescripcion,ga_isedtcantproding;
    TextView ga_istxtstockactual,idListProductoTienda,ga_txtNombreTienda;

    ProgressDialog progressDialogActualizar;
    RequestQueue requestQueueBuscar,requestQueueActualizar,requestQueueListar;
    JsonObjectRequest jsonObjectRequestBuscar,jsonObjectRequestListar;
    StringRequest stringRequestActualizar;
    String idTie="",tieNombre;
    Button ga_isbtnregistrarstok;

    int xp_modbusc=0;

    ArrayList<ga_EntidadProductos> entidadProductosArrayList;
    ArrayList<ga_EntidadListadoProductoTienda> entidadListadoProductoTiendaArrayList;

    RecyclerView recyclerView;
    ga_adplistadoproductodescripcion recyclerproducto;

    Dialog dialog;

    ListView ga_lstDescripcionProducto;
    ArrayAdapter<String> adapterList;
    ArrayList<String> productosDescripcionLis=new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ga_frgingresostock, container, false);

        ga_isbtnregistrarstok=view.findViewById(R.id.ga_isbtnregistrarstok);
        ga_isbtnbuscarprod=view.findViewById(R.id.ga_isbtnbuscarprod);
        ga_isedtnombreprod=view.findViewById(R.id.ga_isedtnombreprod);
        ga_isedtcodsku=view.findViewById(R.id.ga_isedtcodsku);
        ga_isedtdescripcion=view.findViewById(R.id.ga_isedtdescripcion);
        ga_isedtcantproding=view.findViewById(R.id.ga_isedtcantproding);
        ga_istxtstockactual=view.findViewById(R.id.ga_istxtstockactual);
        idListProductoTienda=view.findViewById(R.id.idListProductoTienda);
        ga_txtNombreTienda=view.findViewById(R.id.ga_txtNombreTienda);
        ga_lstDescripcionProducto=view.findViewById(R.id.ga_lstDescripcionProducto);

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        idTie=preferencias.getString("idTienda","");
        tieNombre=preferencias.getString("tieNombre","");
        listarProducto();

        ga_txtNombreTienda.setText(tieNombre);
        idListProductoTienda.setVisibility(View.GONE);

        entidadProductosArrayList=new ArrayList<ga_EntidadProductos>();
        entidadListadoProductoTiendaArrayList=new ArrayList<ga_EntidadListadoProductoTienda>();

        ga_isedtdescripcion.setEnabled(false);
        ga_isedtcantproding.setEnabled(false);

        ga_isedtcodsku.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ga_isedtnombreprod.setText("");
                ga_isedtdescripcion.setText("");
                ga_istxtstockactual.setText("");
                xp_modbusc=1;
                return false;
            }
        });

        ga_isedtnombreprod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ga_isedtcodsku.setText("");
                ga_isedtdescripcion.setText("");
                ga_istxtstockactual.setText("");
                xp_modbusc=0;
                return false;
            }
        });

        requestQueueBuscar= Volley.newRequestQueue(getContext());
        requestQueueActualizar= Volley.newRequestQueue(getContext());

        ga_isbtnregistrarstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                ga_actualizarStock(view);

            }
        });

        ga_isbtnbuscarprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URLbuscar="http://129.151.103.228/Encargalo/APIS/TenderoApp/c_consultar_stock_producto_x_nombre_codigo_almacen.php?idTienda="+idTie+"&idProducto="+ga_isedtcodsku.getText().toString()+"&proDescripcion="+ga_isedtnombreprod.getText().toString()+"&xp_modbusc="+xp_modbusc;
                    ga_buscarProducto(URLbuscar);
            }
        });

        return view;
    }

    private void listarProducto() {
        String URLlistar="http://129.151.103.228/Encargalo/APIS/TenderoApp/c_consultar_lista_producto.php?idTienda="+idTie;
        URLlistar.replace(" ", "%20");
        jsonObjectRequestListar = new JsonObjectRequest(Request.Method.GET, URLlistar, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                ga_EntidadProductos productoList = null;
                ga_EntidadListadoProductoTienda listaStockproductoList = null;
                JSONArray json = response.optJSONArray("consulta");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        productoList = new ga_EntidadProductos();
                        listaStockproductoList = new ga_EntidadListadoProductoTienda();

                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        productoList.setProdDescripcion(jsonObject.optString("proDescripcion"));
                        listaStockproductoList.setLptStock(jsonObject.optInt("lptStock"));
                        productosDescripcionLis.add(productoList.getProdDescripcion()+"  -  Stock actual: "+listaStockproductoList.getLptStock()+" Unid.");
                    }
                    adapterList = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, productosDescripcionLis);
                    ga_lstDescripcionProducto.setAdapter(adapterList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Código o nombre del producto no existe ", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueueListar= Volley.newRequestQueue(getContext());
        requestQueueListar.add(jsonObjectRequestListar);

    }

    private void ga_actualizarStock(View view) {
        if (ga_isedtcantproding.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Ingresa una cantidad stock del producto !!!", Toast.LENGTH_SHORT).show();
        }else {
            progressDialogActualizar = new ProgressDialog(getContext());
            progressDialogActualizar.setMessage("Actualizando Stock .........");
            progressDialogActualizar.show();
            String urlactualizarstock = "http://129.151.103.228/Encargalo/APIS/TenderoApp/m_mod_stock_producto_almacen.php";

            stringRequestActualizar = new StringRequest(Request.Method.POST, urlactualizarstock, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialogActualizar.hide();
                    Navigation.findNavController(view).navigate(R.id.action_nav_ingreso_stock_to_nav_stock_actualizado);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialogActualizar.hide();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String idTienda = idTie;
                    String idListProducto =idListProductoTienda.getText().toString();
                    String stockNuevo = ga_isedtcantproding.getText().toString();
                    Map<String, String> params = new HashMap<>();
                    params.put("idTienda", idTienda);
                    params.put("idListadoProductoTienda", idListProducto);
                    params.put("ptStock", stockNuevo);

                    return params;
                }
            };
            requestQueueActualizar.add(stringRequestActualizar);
        }
    }

    private void ga_buscarProducto(String URLbuscar) {
        if (ga_isedtnombreprod.getText().toString().isEmpty() && ga_isedtcodsku.getText().toString().isEmpty()) {
            ga_isedtcantproding.setEnabled(false);
            Toast.makeText(getContext(), "Escribir Nombre o código del producto !!!", Toast.LENGTH_SHORT).show();
        } else {
            ga_isedtcantproding.setEnabled(true);

                entidadProductosArrayList.clear();
                entidadListadoProductoTiendaArrayList.clear();

            URLbuscar.replace(" ", "%20");
            jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET, URLbuscar, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject response) {
                    ga_EntidadProductos producto = null;
                    ga_EntidadListadoProductoTienda listaStockproducto = null;
                    JSONArray json = response.optJSONArray("consulta");

                    try {
                        for (int i = 0; i < json.length(); i++) {
                            producto = new ga_EntidadProductos();
                            listaStockproducto = new ga_EntidadListadoProductoTienda();

                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            producto.setIdProducto(jsonObject.optInt("idProducto"));
                            producto.setProdDescripcion(jsonObject.optString("proDescripcion"));
                            listaStockproducto.setLptStock(jsonObject.optInt("lptStock"));
                            listaStockproducto.setIdListadoProductoTienda(jsonObject.optInt("idListadoProductoTienda"));
                            if (xp_modbusc == 0) {
                                entidadProductosArrayList.add(producto);
                                entidadListadoProductoTiendaArrayList.add(listaStockproducto);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (xp_modbusc == 1) {
                        ga_isedtdescripcion.setText(producto.getProdDescripcion());
                        ga_istxtstockactual.setText(Float.toString(listaStockproducto.getLptStock()));
                        idListProductoTienda.setText(String.valueOf(listaStockproducto.getIdListadoProductoTienda()));
                    } else {

                        dialog = new Dialog(getContext());
                        dialog.setTitle("Seleccione un producto");
                        dialog.setContentView(R.layout.ga_dialog_buscar_por_nombre);
                        recyclerView = dialog.findViewById(R.id.ga_dialog_recycler_view_list_productos);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setHasFixedSize(true);
                        recyclerproducto = new ga_adplistadoproductodescripcion(idListProductoTienda, ga_istxtstockactual, ga_isedtdescripcion, dialog, entidadProductosArrayList, entidadListadoProductoTiendaArrayList, getContext());
                        recyclerView.setAdapter(recyclerproducto);
                        dialog.show();


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Código o nombre del producto no existe ", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueueBuscar.add(jsonObjectRequestBuscar);
        }
    }
}