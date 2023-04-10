package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Adapter.mip_adapproductostienda;
import mx.com.encargalo.tendero.Entidad.mip_EntidadProductosTienda;
import mx.com.encargalo.tendero.Util.Util;

public class mip_frglistaproductos extends Fragment {

    ArrayList<mip_EntidadProductosTienda> mit_arllistaproductos; //+@OHC20.11.2022
    RecyclerView mip_lprclvproductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mip_frglistaproductos, container, false);

        mip_lprclvproductos = view.findViewById(R.id.mip_lprclvproductos);

        mit_arllistaproductos = new ArrayList<>(); //+@OHC20.11.2022
        mit_arllistaproductos.clear();
        mit_arllistaproductos = mip_frgregistrarproducto.mit_arlregistrarproducto;
        mip_lprclvproductos.setLayoutManager(new LinearLayoutManager(this.getContext())); //+@OHC04.11.2022
        mip_lprclvproductos.setHasFixedSize(true); //+@OHC04.11.2022


        mip_adapproductostienda adapter = new mip_adapproductostienda(getContext(), mit_arllistaproductos,
                (item, vista) -> {
                    //CONTENIDO
                    SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("idProducto", String.valueOf(item.getIdProducto()));
                    editor.putString("proImagen", String.valueOf(item.getProImagen()));
                    editor.putString("proDescripcion", String.valueOf(item.getProDescripcion()));
                    editor.putString("proPrecioCostoPromedio", String.valueOf(item.getProPrecioCostoPromedio()));
                    editor.putString("proPrecioVentaRecomendado", String.valueOf(item.getProPrecioVentaRecomendado()));
                    editor.putString("proUnidadMedida", String.valueOf(item.getProUnidadMedida()));
                    editor.putString("cpNombre", String.valueOf(item.getCpNombre()));
                    editor.apply();
                    Navigation.findNavController(vista).navigateUp();

                    //Refrescar Pantalla Registrar Producto
                    Fragment fragment = new mip_frgregistrarproducto();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                    fragmentTransaction.commit();
                });

        mip_lprclvproductos.setAdapter(adapter);



        return  view;
    }
}