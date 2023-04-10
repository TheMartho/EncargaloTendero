package mx.com.encargalo.tendero.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Entidad.rt_EntidadRubrosTienda;

public class rt_adaprubrostienda extends RecyclerView.Adapter<rt_adaprubrostienda.RubrosTiendaHolder> {

    Context context;//+@OHC11.11.2022
    List<rt_EntidadRubrosTienda> rt_listrubrostienda;//+@OHC11.11.2022
    final rt_adaprubrostienda.OnClickListener listener;//+@OHC11.11.2022

    public interface OnClickListener{
        void OnClick(rt_EntidadRubrosTienda item, View vista);

    } //+}@OHC11.11.2022

    public rt_adaprubrostienda(Context context, List<rt_EntidadRubrosTienda> rt_listrubrostienda, OnClickListener listener) {
        this.context = context;
        this.rt_listrubrostienda = rt_listrubrostienda;
        this.listener = listener;
    } //+}@OHC11.11.2022

    @NonNull
    @NotNull
    @Override //+}@OHC11.11.2022
    public RubrosTiendaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.rt_pltlcategoriatienda, parent, false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        vista.setLayoutParams(layoutParams);
        return new rt_adaprubrostienda.RubrosTiendaHolder(vista);
    } //+}@OHC11.11.2022

    @Override
    public void onBindViewHolder(@NonNull @NotNull rt_adaprubrostienda.RubrosTiendaHolder holder, int position) {
        holder.rt_pttxtnombrecategoria.setText( String.valueOf(rt_listrubrostienda.get(position).getRtDescripcion()) );
//        holder.rt_ctrbtncambiarrubro.setText( String.valueOf(rt_listrubrostienda.get(position).getRtDescripcion()) );

        holder.binData(rt_listrubrostienda.get(position));
    } //+}@OHC11.11.2022

    @Override
    public int getItemCount() {
        return rt_listrubrostienda.size();
    } //+}@OHC11.11.2022

    public class RubrosTiendaHolder extends RecyclerView.ViewHolder {

        TextView rt_pttxtnombrecategoria; //+@OHC11.11.2022
        RadioButton rt_ptrbtnrubro; //+@OHC11.11.2022
        View view; //+@OHC11.11.2022

        public RubrosTiendaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            rt_pttxtnombrecategoria=itemView.findViewById(R.id.rt_pttxtnombrecategoria);
            view = itemView;
        } //+}@OHC11.11.2022

        void binData(final rt_EntidadRubrosTienda item){
            rt_ptrbtnrubro = view.findViewById(R.id.rt_ptrbtnrubro);
            rt_ptrbtnrubro.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(R.id.action_rt_frgcategoriatienda_to_rt_frgcrearpuntoventa);
                listener.OnClick(item,view);
            }); //+}@OHC11.11.2022
        }
    } //+}@OHC11.11.2022
}
