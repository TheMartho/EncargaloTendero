package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.Curso;
import mx.com.encargalo.tendero.Util.Util;

import static android.content.ContentValues.TAG;

public class ap_adplistadofrgcategoria extends RecyclerView.Adapter<ap_adplistadofrgcategoria.ViewHolder> implements View.OnClickListener {

    LayoutInflater ap_inflater;
    ArrayList<Curso> ap_model;
    Context context;

    JsonObjectRequest request;
    RequestQueue requestQueue;

    private View.OnClickListener ap_listener;

    public ap_adplistadofrgcategoria(Context context, ArrayList<Curso>model){
        this.ap_inflater = LayoutInflater.from(context);
        this.ap_model = model;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = ap_inflater.inflate(R.layout.ap_ccitemcardcurso, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.ap_listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ap_adplistadofrgcategoria.ViewHolder holder, int position) {

        String estadolike;

        String ap_varlocnombrecurso = ap_model.get(position).getAp_varstrnombrecurso();
        holder.ap_varlocnombrecurso.setText(String.valueOf(ap_varlocnombrecurso));

        String ap_varlocid = ap_model.get(position).getAp_varstrid();//-------------------------------------------------- ID APRENDIZAJE

        SharedPreferences preferences= context.getSharedPreferences("Credencial_Global_usuario",0);
        String iduser = preferences.getString("CredId","");//------------------------------------------------ ID USUARIO

        estadolike =String.valueOf(ap_model.get(position).getAp_varstrlike());

        if (estadolike.equals("LIKE")){
            holder.lottieFav.setFrame(100);
        }else if (estadolike.equals("NOT LIKE")){
            holder.lottieFav.setFrame(0);
        }

        holder.lottieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (estadolike.equals("LIKE")){
                    holder.lottieFav.setMaxFrame(0);

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
//                    ap_model.get(position).setAp_varstrid("NOT LIKE");

                }else if (estadolike.equals("NOT LIKE")){
                    holder.lottieFav.setMinAndMaxProgress(0.0f,1.0f);
                    holder.lottieFav.playAnimation();
//                    Toast.makeText(ap_inflater.getContext(), ap_varlocid, Toast.LENGTH_SHORT).show();
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
//                    ap_model.get(position).setAp_varstrid("LIKE");
                }
                notifyDataSetChanged();
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
        TextView ap_varlocnombrecurso;
        LottieAnimationView lottieFav;
//        private Boolean btnFav = null;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ap_varlocnombrecurso = itemView.findViewById(R.id.ap_cctxtnombrecurso);
            lottieFav= itemView.findViewById(R.id.ap_ccbtnanimacion);
        }
    }
}
