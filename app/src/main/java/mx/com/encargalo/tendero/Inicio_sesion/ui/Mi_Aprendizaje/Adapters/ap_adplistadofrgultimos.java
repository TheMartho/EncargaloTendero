package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.LastFavoritos;
import mx.com.encargalo.tendero.Util.Util;

public class ap_adplistadofrgultimos extends RecyclerView.Adapter<ap_adplistadofrgultimos.ViewHolder> implements View.OnClickListener {

    LayoutInflater ap_inflater;
    ArrayList<LastFavoritos> ap_model;
    Context context;

    JsonObjectRequest request;
    RequestQueue requestQueue;

    private View.OnClickListener ap_listener;

    public ap_adplistadofrgultimos(Context context, ArrayList<LastFavoritos> model){
        this.ap_inflater =LayoutInflater.from(context);
        this.ap_model =model;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = ap_inflater.inflate(R.layout.ap_mfitemcardultimos, parent, false);
        view.setOnClickListener(this);
        return new ap_adplistadofrgultimos.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.ap_listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ap_adplistadofrgultimos.ViewHolder holder, int position) {
        String titulo = ap_model.get(position).getAp_strtitulo();
        holder.titulo.setText(String.valueOf(titulo));

        String ap_varlocid = ap_model.get(position).getAp_strid();//-------------------------------------------------- ID APRENDIZAJE

        SharedPreferences preferences= context.getSharedPreferences("Credencial_Global_usuario",0);
        String iduser = preferences.getString("CredId","");//------------------------------------------------ ID USUARIO

        if(holder.btnFav){
            holder.lottieFav.setFrame(100);
        }else {
            holder.lottieFav.setFrame(0);
        }//----------------------------------------solo para presentacion, borrar luego

        holder.lottieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btnFav){
                    holder.lottieFav.setMaxFrame(0);
                    holder.btnFav = false;
                    String URL = Util.RUTA+"a_favorito_apredizaje.php?id_Aprendizaje="+ap_varlocid+"&id_DocumentoPersona="+iduser;
                    request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>(){

                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(request);
                }else {
                    holder.lottieFav.setMinAndMaxProgress(0.0f,1.0f);
                    holder.lottieFav.playAnimation();
                    holder.btnFav = true;
                    String URL = Util.RUTA+"a_favorito_apredizaje.php?id_Aprendizaje="+ap_varlocid+"&id_DocumentoPersona="+iduser;
                    request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>(){

                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(request);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ap_model.size();
    }

    @Override
    public void onClick(View view) {
        if(ap_listener !=null){
            ap_listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        LottieAnimationView lottieFav;
        private Boolean btnFav = true;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.ap_catxtnombrecurso);
            lottieFav= itemView.findViewById(R.id.ap_cabtnanimacion);
        }
    }
}
