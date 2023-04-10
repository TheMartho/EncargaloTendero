package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.encargalo.R;

public class mip_frgproductoagregado extends Fragment {

    Button mit_pabtnvolver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mip_frgproductoagregado, container, false);

        mit_pabtnvolver = view.findViewById(R.id.mip_pabtnvolver);

        mit_pabtnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mip_frgproductoagregado_to_nav_mis_productos);
            }
        });


        return view;
    }
}