package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;


public class mip_frgmenuproductos extends Fragment {

    Button mip_mpbtnregistrarproducto, mip_mpbtnreporteproductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        SharedPreferences preferencias_prod = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias_prod.edit();
        editor.clear().apply();

        View view = inflater.inflate(R.layout.fragment_mip_frgmenuproductos, container, false);

        mip_mpbtnregistrarproducto = view.findViewById(R.id.mip_mpbtnregistrarproducto);
        mip_mpbtnreporteproductos = view.findViewById(R.id.mip_mpbtnreporteproductos);




        mip_mpbtnregistrarproducto.setOnClickListener(view1 -> {
            if (preferencias.getString("tieNombre", "").equals("")) Toast.makeText(getActivity(), "Elija una Tienda!!", Toast.LENGTH_SHORT).show();
            else Navigation.findNavController(view1).navigate(R.id.action_nav_mis_productos_to_mip_frgregistrarproducto);
        });

        mip_mpbtnreporteproductos.setOnClickListener(view12 ->{
            if (preferencias.getString("tieNombre", "").equals("")) Toast.makeText(getActivity(), "Elija una Tienda!!", Toast.LENGTH_SHORT).show();
            else Navigation.findNavController(view12).navigate(R.id.action_nav_mis_productos_to_mip_frgreporteproductos);
        });

        return view;
    }
}