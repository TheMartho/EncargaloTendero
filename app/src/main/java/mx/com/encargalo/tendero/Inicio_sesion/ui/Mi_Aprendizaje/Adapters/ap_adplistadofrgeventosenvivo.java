package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades.EventoEnVivo;

public class ap_adplistadofrgeventosenvivo extends RecyclerView.Adapter<ap_adplistadofrgeventosenvivo.ViewHolder> implements View.OnClickListener {

    LayoutInflater ap_inflater;
    ArrayList<EventoEnVivo> ap_model;

    private View.OnClickListener ap_listener;

    public ap_adplistadofrgeventosenvivo(Context context, ArrayList<EventoEnVivo> model){
        this.ap_inflater =LayoutInflater.from(context);
        this.ap_model =model;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = ap_inflater.inflate(R.layout.ap_eevitemcardevento, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.ap_listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ap_adplistadofrgeventosenvivo.ViewHolder holder, int position) {
        String titulo = ap_model.get(position).getAp_strtitulo();
        String charla = ap_model.get(position).getAp_strcharla();
        String fecha = ap_model.get(position).getAp_strfecha();
        holder.titulo.setText(String.valueOf(titulo));
        holder.charla.setText(String.valueOf(charla));
        holder.fecha.setText(String.valueOf(fecha));

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
        TextView titulo, charla,fecha;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.ap_strtitulocurso);
            charla = itemView.findViewById(R.id.ap_eevtxtnombreevento);
            fecha = itemView.findViewById(R.id.ap_eevtxtfechahora);
        }
    }
}
