package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

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

public class mit_frgmenutienda extends Fragment {

    Button mit_mtbtneditardatos, mit_mtbtncambiartienda, mit_mtbtnagregartienda,mit_mtbtnproveedor,mit_mtbtningresostock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        View view = inflater.inflate(R.layout.fragment_mit_frgmenutienda, container, false);

        mit_mtbtneditardatos = view.findViewById(R.id.mit_mtbtneditardatos);
        mit_mtbtncambiartienda = view.findViewById(R.id.mit_mtbtncambiartienda);
        mit_mtbtnagregartienda = view.findViewById(R.id.mit_mtbtnagregartienda);
        mit_mtbtnproveedor = view.findViewById(R.id.mit_mtbtnproveedor);
        mit_mtbtningresostock = view.findViewById(R.id.mit_mtbtningresostock);
        int idspn = rt_fctautocompletado.IdSpinner(preferencias.getString("tieCiudadNom",""));
        editor.putInt("tieCiudad", idspn);
        editor.remove("Direccion");
        editor.remove("tieNombre_tmp");
        editor.remove("tieURLWeb_tmp");
        editor.remove("tieDescripcion_tmp");
        editor.remove("tieCorreo_tmp");
        editor.remove("tieTelefono_tmp");
        editor.remove("tieImagen_tmp");
        editor.remove("tieLatitud_tmp");
        editor.remove("tieLongitud_tmp");
        editor.remove("tieImagen_tmp");
        editor.remove("");
        editor.apply();

        mit_mtbtneditardatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferencias.getString("tieNombre", "").equals("")) Toast.makeText(getActivity(), "Elija una Tienda!!", Toast.LENGTH_SHORT).show();
                else {
                    editor.putString("tieLatitud_tmp", preferencias.getString("tieLatitud",""));
                    editor.putString("tieLongitud_tmp", preferencias.getString("tieLongitud",""));
                    editor.putString("tieImagen_tmp", preferencias.getString("tieImagen",""));
                    editor.apply();
                    Navigation.findNavController(view).navigate(R.id.action_mit_frgmenutienda_to_mit_frgdatostienda);
                }
            }
        });

        mit_mtbtncambiartienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mit_frgmenutienda_to_mit_frgcambiartienda);
            }
        });

        mit_mtbtnagregartienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mit_frgmenutienda_to_rt_frgcategoriatienda);
            }
        });

        mit_mtbtnproveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferencias.getString("tieNombre", "").equals("")) Toast.makeText(getActivity(), "Elija una Tienda!!", Toast.LENGTH_SHORT).show();
                else Navigation.findNavController(view).navigate(R.id.action_nav_mi_tienda_to_nav_menu_proveedor);

            }
        });

        mit_mtbtningresostock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferencias.getString("tieNombre", "").equals("")) Toast.makeText(getActivity(), "Elija una Tienda!!", Toast.LENGTH_SHORT).show();
                else Navigation.findNavController(view).navigate(R.id.action_nav_menutienda_to_nav_ingreso_stock);
            }
        });

        return view;
    }
}