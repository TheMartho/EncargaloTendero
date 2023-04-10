package mx.com.encargalo.tendero.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Entidad.mip_EntidadProductosTienda;

public class mip_adapproductostienda extends RecyclerView.Adapter<mip_adapproductostienda.ProductosTiendaHolder>{

    Context context; //+@OHC20.11.2022
    List<mip_EntidadProductosTienda> mip_listproductosttienda; //+@OHC20.11.2022
    final mip_adapproductostienda.OnClickListener listener; //+@OHC20.11.2022

    public interface OnClickListener{
        void OnClick(mip_EntidadProductosTienda item, View vista);

    } //+}@OHC20.11.2022

    public mip_adapproductostienda(Context context, List<mip_EntidadProductosTienda> mip_listproductosttienda, OnClickListener listener) {
        this.context = context;
        this.mip_listproductosttienda = mip_listproductosttienda;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ProductosTiendaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.mip_pltlproductotienda, parent, false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ProductosTiendaHolder(vista);
    } //+}@OHC20.11.2022

    @Override
    public void onBindViewHolder(@NonNull @NotNull mip_adapproductostienda.ProductosTiendaHolder holder, int position) {
        holder.mip_pttxtcodigoproducto.setText( String.valueOf(mip_listproductosttienda.get(position).getIdProducto()) );

        if(String.valueOf(mip_listproductosttienda.get(position).getLptImagen1()).equals("")){
            holder.mip_pttxtdescripcionproducto.setText( String.valueOf(mip_listproductosttienda.get(position).getProDescripcion()) );
            holder.mip_pttxtstockproductostr.setText("Categoría: ");
            holder.mip_pttxtstockproducto.setText( String.valueOf(mip_listproductosttienda.get(position).getCpNombre()) );
        }else{
            holder.mip_pttxtstockproducto.setText( String.valueOf(mip_listproductosttienda.get(position).getLptStock()) );
            holder.mip_pttxtdescripcionproducto.setText( String.valueOf(mip_listproductosttienda.get(position).getLptDescripcionProductoTienda()) );
        }
        holder.binData(mip_listproductosttienda.get(position));
    }

    @Override
    public int getItemCount() {
        return mip_listproductosttienda.size();
    }

    public class ProductosTiendaHolder extends RecyclerView.ViewHolder {
        TextView mip_pttxtstockproductostr; //+@OHC20.11.2022
        TextView mip_pttxtcodigoproducto, mip_pttxtdescripcionproducto, mip_pttxtstockproducto; //+@OHC20.11.2022
        Button mip_ptbtnProducto; //+@OHC20.11.2022
        View view; //+@OHC20.11.2022

        public ProductosTiendaHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mip_pttxtstockproductostr=itemView.findViewById(R.id.mip_pttxtstockproductostr);

            mip_pttxtcodigoproducto=itemView.findViewById(R.id.mip_pttxtcodigoproducto);
            mip_pttxtdescripcionproducto=itemView.findViewById(R.id.mip_pttxtdescripcionproducto);
            mip_pttxtstockproducto=itemView.findViewById(R.id.mip_pttxtstockproducto);
            view = itemView;
        } //+}@OHC20.11.2022

        void binData(final mip_EntidadProductosTienda item){
            mip_ptbtnProducto = view.findViewById(R.id.mip_ptbtnProducto);
            mip_ptbtnProducto.setOnClickListener(view -> {
                listener.OnClick(item,view);
            });
        } //+}@OHC20.11.2022
    }
}
