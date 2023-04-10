package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters.ap_adplistadofrgeventosenvivo;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.EventoEnVivo;
import mx.com.encargalo.tendero.Util.Util;

public class ap_frgeventosenvivo extends Fragment {

    ap_adplistadofrgeventosenvivo ap_adpeventosenvivo;
    RecyclerView ap_rclvevento;
    ArrayList<EventoEnVivo> ap_listaeventoenvivo;

    EventoEnVivo eventoEnVivo;
    RequestQueue requestQueue;
    JsonObjectRequest request;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_ap_frgeventosenvivo, container, false);
        ap_rclvevento =vista.findViewById(R.id.ap_eevrclveventos);
        ap_rclvevento.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ap_rclvevento.setHasFixedSize(true);
        ap_listaeventoenvivo = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getContext());

        parseJSON();

        return vista;
    }

    private void parseJSON() {

        String URL = Util.RUTA+"c_consultar_eventos_aprendizaje.php";

        request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        eventoEnVivo = null;
                        JSONArray json=response.optJSONArray("eventos"); ///--------------------///--------------------///--------------------
                        try {
                            ap_listaeventoenvivo.clear();
                            for (int i = 0; i<json.length();i++){
                                eventoEnVivo=new EventoEnVivo();
                                JSONObject jsonObject=null;
                                jsonObject=json.getJSONObject(i);
                                eventoEnVivo.setAp_strtitulo(jsonObject.optString("evTitulo"));
                                eventoEnVivo.setAp_strcharla(jsonObject.optString("evDescripcion"));
                                eventoEnVivo.setAp_strfecha(jsonObject.optString("evFechaPublicacion"));
                                eventoEnVivo.setAp_strlink(jsonObject.optString("evLink"));
                                ap_listaeventoenvivo.add(eventoEnVivo);

                            }
                            ap_adpeventosenvivo = new ap_adplistadofrgeventosenvivo(getContext(),ap_listaeventoenvivo);
                            ap_rclvevento.setAdapter(ap_adpeventosenvivo);
                            ap_adpeventosenvivo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String charla = ap_listaeventoenvivo.get(ap_rclvevento.getChildAdapterPosition(view)).getAp_strcharla();
                                    String url = ap_listaeventoenvivo.get(ap_rclvevento.getChildAdapterPosition(view)).getAp_strlink();

                                    Uri link = Uri.parse(url);
                                    Intent i = new Intent(Intent.ACTION_VIEW, link);
                                    startActivity(i);
                                    Toast.makeText(getContext(), "Seleccionó: " + charla, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al consultar"+ error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }
}
