package mx.com.encargalo.tendero.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Entidad.rt_EntidadTiendasPersona;



public class rt_adaptiendaspersona extends RecyclerView.Adapter<rt_adaptiendaspersona.TiendasPersonaHolder> {

    Context context; //+@OHC11.11.2022
    List<rt_EntidadTiendasPersona> rt_listtiendaspersona; //+@OHC11.11.2022
    final rt_adaptiendaspersona.OnClickListener listener; //+@OHC11.11.2022

    public interface OnClickListener{
        void OnClick(rt_EntidadTiendasPersona item, View vista);

    } //+}@OHC08.11.2022

    public rt_adaptiendaspersona(Context context, List<rt_EntidadTiendasPersona> rt_listtiendaspersona, OnClickListener listener){
        this.context = context;
        this.rt_listtiendaspersona = rt_listtiendaspersona;
        this.listener = listener;
    } //+}@OHC11.11.2022


    @NonNull
    @NotNull
    @Override //+}@OHC08.11.2022
    public TiendasPersonaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rt_pltllistadotiendas, parent, false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        vista.setLayoutParams(layoutParams);
        return new TiendasPersonaHolder(vista);
    } //+}@OHC11.11.2022

    @Override
    public void onBindViewHolder(@NonNull @NotNull rt_adaptiendaspersona.TiendasPersonaHolder holder, int position) {
        holder.rt_ietxtnombretienda.setText( String.valueOf(rt_listtiendaspersona.get(position).getTieNombre()) );
        holder.binData(rt_listtiendaspersona.get(position));

    } //+}@OHC11.11.2022

    @Override
    public int getItemCount() {
        return rt_listtiendaspersona.size();
    } //+}@OHC11.11.2022

    public class TiendasPersonaHolder extends RecyclerView.ViewHolder {
        TextView rt_ietxtnombretienda; //+@OHC11.11.2022
        RadioButton rt_ptrbtntiendas; //+@OHC11.11.2022
        View view; //+@OHC11.11.2022

        public TiendasPersonaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            rt_ietxtnombretienda=itemView.findViewById(R.id.rt_pttxtnombretienda);
            view = itemView;
        } //+}@OHC11.11.2022

        void binData(final rt_EntidadTiendasPersona item){
            rt_ptrbtntiendas = view.findViewById(R.id.rt_ptrbtntiendas);
            rt_ptrbtntiendas.setOnClickListener(view -> {
                listener.OnClick(item,view);
            });
        } //+}@OHC11.11.2022

    } //+}@OHC11.11.2022
}

