package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters.ap_adplistadofrgmisfavoritos;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters.ap_adplistadofrgultimos;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.Favorito;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.LastFavoritos;
import mx.com.encargalo.tendero.Util.Util;

public class ap_frgcategoriaaprendizaje extends Fragment {
    Button ap_cabtnvermas;
    Button ap_cabtncursosvender;
    Button ap_cabtncursosautoaprendizaje;
    Button ap_cabtncursosoperiones;

    ap_adplistadofrgultimos ap_adpultimos;
    RecyclerView ap_rclvultimos;
    ArrayList<LastFavoritos> ap_listaultimos;

    LastFavoritos lastFavoritos;
    RequestQueue requestQueue;
    JsonObjectRequest request;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ap_frgcategoriaaprendizaje, container, false);

        //Recycler

        ap_rclvultimos =vista.findViewById(R.id.ap_carclvcursosfavrecientes);
        ap_rclvultimos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ap_rclvultimos.setHasFixedSize(true);
        ap_listaultimos = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

        parseJSON();


        ap_cabtnvermas=vista.findViewById(R.id.ap_cabtnvermas);
        ap_cabtnvermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_misfavoritos);
            }
        });
        ap_cabtncursosvender=vista.findViewById(R.id.ap_cabtncursosvender);
        ap_cabtncursosvender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = "vender";
                SharedPreferences preferencias=getContext().getSharedPreferences(Util.SHAREDPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("ap_categoriacurso", categoria);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.nav_cursoscategoria);
            }
        });
        ap_cabtncursosautoaprendizaje=vista.findViewById(R.id.ap_cabtncursosautoaprendizaje);
        ap_cabtncursosautoaprendizaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = "autoaprendizaje";
                SharedPreferences preferencias=getContext().getSharedPreferences(Util.SHAREDPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("ap_categoriacurso", categoria);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.nav_cursoscategoria);
            }
        });
        ap_cabtncursosoperiones=vista.findViewById(R.id.ap_cabtncursosoperiones);
        ap_cabtncursosoperiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = "las operaciones de negocio";
                SharedPreferences preferencias=getContext().getSharedPreferences(Util.SHAREDPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("ap_categoriacurso", categoria);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.nav_cursoscategoria);
            }
        });
        return vista;
    }

    private void parseJSON() {

        SharedPreferences preferences= getContext().getSharedPreferences("Credencial_Global_usuario",0);
        String iduser = preferences.getString("CredId","");

        String URL = Util.RUTA+"c_listar_ultimos_favoritos_aprendizaje.php?id_DocumentoPersona="+iduser;

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        lastFavoritos = null;
                        JSONArray json=response.optJSONArray("consulta"); ///--------------------///--------------------///--------------------
                        try {
                            ap_listaultimos.clear();
                            for (int i = 0; i<json.length();i++){
                                lastFavoritos=new LastFavoritos();
                                JSONObject jsonObject=null;
                                jsonObject=json.getJSONObject(i);
                                lastFavoritos.setAp_strtitulo(jsonObject.optString("apreTituloRecurso"));
                                lastFavoritos.setAp_strlink(jsonObject.optString("apreContenido"));
                                lastFavoritos.setAp_strid(jsonObject.optString("idAprendizaje"));
                                ap_listaultimos.add(lastFavoritos);

                            }
                            ap_adpultimos = new ap_adplistadofrgultimos(getContext(), ap_listaultimos);
                            ap_rclvultimos.setAdapter(ap_adpultimos);
                            ap_adpultimos.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String titulo = ap_listaultimos.get(ap_rclvultimos.getChildAdapterPosition(view)).getAp_strtitulo();
                                    String link = ap_listaultimos.get(ap_rclvultimos.getChildAdapterPosition(view)).getAp_strlink();
                                    Uri url = Uri.parse(link);
                                    Intent i = new Intent(Intent.ACTION_VIEW, url);
                                    startActivity(i);
                                    Toast.makeText(getContext(), "Seleccionó: " + titulo, Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No tienes favoritos", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);
    }
}