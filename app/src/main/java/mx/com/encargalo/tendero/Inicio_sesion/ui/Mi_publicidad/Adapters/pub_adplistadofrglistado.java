package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Entidades.Publicidad;

import static mx.com.encargalo.tendero.Util.Util.IP_SERVER;

public class pub_adplistadofrglistado extends RecyclerView.Adapter<pub_adplistadofrglistado.ViewHolder> implements View.OnClickListener{

    LayoutInflater pub_inflater;
    ArrayList<Publicidad> pub_model;
    Context context;

    //listener
    private View.OnClickListener pub_listener;

    public pub_adplistadofrglistado(Context context, ArrayList<Publicidad> model){
        this.pub_inflater =LayoutInflater.from(context);
        this.pub_model =model;
        this.context= context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = pub_inflater.inflate(R.layout.pub_lmaitemcardanuncio, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.pub_listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull pub_adplistadofrglistado.ViewHolder holder, int position) {
        String pub_strtitulo = pub_model.get(position).getPub_strtitulo();
        String pub_strfecha = pub_model.get(position).getPub_strfechainicio();
        String pu_strduracion = pub_model.get(position).getPub_strduracion();
        String pub_strvistas = pub_model.get(position).getPub_strvistas();
        String pub_strconversion = pub_model.get(position).getPub_strconversion();
        String imagen = pub_model.get(position).getPub_strimagenurl();
        holder.pub_txttitulo.setText(String.valueOf(pub_strtitulo));
        holder.pub_txtfecha.setText(String.valueOf(pub_strfecha));
        holder.pub_txtduracion.setText(String.valueOf(pu_strduracion));
        holder.pub_txtvistas.setText(String.valueOf(pub_strvistas));
        holder.pub_txtconversion.setText(String.valueOf(pub_strconversion)+"%");

        String url = IP_SERVER;
        Glide.with(context)
                .load(url+imagen)
                .into(holder.pub_imgvwimagen);
    }

    @Override
    public int getItemCount() {
        return pub_model.size();
    }

    @Override
    public void onClick(View view) {
        if(pub_listener !=null){
            pub_listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pub_txttitulo, pub_txtfecha, pub_txtduracion, pub_txtvistas, pub_txtconversion;
        ImageView pub_imgvwimagen;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            pub_txttitulo = itemView.findViewById(R.id.pub_lmatxttitulo);
            pub_txtfecha = itemView.findViewById(R.id.pub_lmatxtfechapub);
            pub_txtduracion = itemView.findViewById(R.id.pub_lmatxtduracion);
            pub_txtvistas = itemView.findViewById(R.id.pub_lmatxtnrovistas);
            pub_txtconversion = itemView.findViewById(R.id.pub_lmatxttasaconv);
            pub_imgvwimagen = itemView.findViewById(R.id.pub_lmaimgvwimgprevia);
        }
    }
}
