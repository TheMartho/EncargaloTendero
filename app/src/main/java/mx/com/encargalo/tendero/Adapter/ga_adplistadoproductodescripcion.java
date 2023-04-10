package mx.com.encargalo.tendero.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Entidad.ga_EntidadListadoProductoTienda;
import mx.com.encargalo.tendero.Entidad.ga_EntidadProductos;

public class ga_adplistadoproductodescripcion extends RecyclerView.Adapter<ga_adplistadoproductodescripcion.ProductoHoulder> {
    ArrayList<ga_EntidadProductos> productos;
    ArrayList<ga_EntidadListadoProductoTienda> productoTiendas;
    Context context;
    Dialog dialog;
    TextInputEditText ga_isedtdescripcion;
    TextView ga_istxtstockactual,idListProductoTienda;

    String sDescripcion,sStockActual;
    int sIdListProductoTienda;

    public ga_adplistadoproductodescripcion(TextView idListProductoTienda,TextView ga_istxtstockactual,TextInputEditText ga_isedtdescripcion,Dialog dialog,ArrayList<ga_EntidadProductos> productos, ArrayList<ga_EntidadListadoProductoTienda> productoTiendas, Context context) {
        this.productos = productos;
        this.productoTiendas = productoTiendas;
        this.context = context;
        this.dialog=dialog;
        this.ga_isedtdescripcion=ga_isedtdescripcion;
        this.ga_istxtstockactual=ga_istxtstockactual;
        this.idListProductoTienda=idListProductoTienda;
    }


    @NonNull
    @NotNull
    @Override
    public ga_adplistadoproductodescripcion.ProductoHoulder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.ga_item_producto,parent,false);
        ProductoHoulder productoHoulder=new ProductoHoulder(view);
        return productoHoulder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ga_adplistadoproductodescripcion.ProductoHoulder holder, int position) {

        ga_EntidadProductos producto=productos.get(position);
        ga_EntidadListadoProductoTienda productotienda=productoTiendas.get(position);
        holder.txtDescripcion.setText(producto.getProdDescripcion());
        sDescripcion=producto.getProdDescripcion();
        sStockActual=String.valueOf(productotienda.getLptStock());
        sIdListProductoTienda=productotienda.getIdListadoProductoTienda();

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ProductoHoulder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtDescripcion;
        public ProductoHoulder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtDescripcion=itemView.findViewById(R.id.ga_txtNombreProductoitem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ga_isedtdescripcion.setText(sDescripcion);
            ga_istxtstockactual.setText(sStockActual);
            idListProductoTienda.setText(String.valueOf(sIdListProductoTienda));

            dialog.dismiss();
        }
    }
}
