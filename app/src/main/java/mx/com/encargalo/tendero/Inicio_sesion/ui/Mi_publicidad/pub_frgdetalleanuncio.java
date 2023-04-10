//Creado por Bullon Arango Fernando Alcides - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;
import static mx.com.encargalo.tendero.Util.Util.IP_SERVER;

public class pub_frgdetalleanuncio extends Fragment {
    Button pub_pabtnvolverlista;
    ImageView pub_daimgvwimgprevia;
    TextView pub_datxttitulo, pub_datxtdescripcion, pub_datxtfechapub, pub_datxtduracion, pub_datxtmonto, pub_datxtnrovistas, pub_datxttasa;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pub_frgdetalleanuncio, container, false);

        SharedPreferences preferencias=getContext().getSharedPreferences(Util.SHAREDPREFERENCES, Context.MODE_PRIVATE);
        String dpimagenurl = preferencias.getString("pub_dpimagenurl", "");
        String dptitulo = preferencias.getString("pub_dptitulo","");
        String dpdescripcion = preferencias.getString("pub_dpdescripcion","");
        String dpfechainicio = preferencias.getString("pub_dpfechainicio","");
        String dpduracion = preferencias.getString("pub_dpduracion","");
        String dpmonto = preferencias.getString("pub_dpmonto","");
        String dpvistas = preferencias.getString("pub_dpvistas","");
        String dpconversion = preferencias.getString("pub_dpconversion", "")+"%";

        pub_daimgvwimgprevia=vista.findViewById(R.id.pub_daimgvwimgprevia);

        String url = IP_SERVER;
        Glide.with(getContext())
                .load(url+dpimagenurl)
                .into(pub_daimgvwimgprevia);

        pub_datxttitulo=vista.findViewById(R.id.pub_datxttitulo);
        pub_datxttitulo.setText(dptitulo);
        pub_datxtdescripcion=vista.findViewById(R.id.pub_datxtdescripcion);
        pub_datxtdescripcion.setText(dpdescripcion);
        pub_datxtfechapub=vista.findViewById(R.id.pub_datxtfechapub);
        pub_datxtfechapub.setText(dpfechainicio);
        pub_datxtduracion=vista.findViewById(R.id.pub_datxtduracion);
        pub_datxtduracion.setText(dpduracion);
        pub_datxtmonto=vista.findViewById(R.id.pub_datxtmonto);
        pub_datxtmonto.setText(dpmonto);
        pub_datxtnrovistas=vista.findViewById(R.id.pub_datxtnrovistas);
        pub_datxtnrovistas.setText(dpvistas);
        pub_datxttasa=vista.findViewById(R.id.pub_datxttasa);
        pub_datxttasa.setText(dpconversion);

        pub_pabtnvolverlista=vista.findViewById(R.id.pub_pabtnvolverlista);
        pub_pabtnvolverlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_listadomisanuncios);
            }
        });
        return vista;
    }
}
