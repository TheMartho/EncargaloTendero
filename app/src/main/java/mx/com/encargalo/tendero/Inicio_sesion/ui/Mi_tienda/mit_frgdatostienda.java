package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Hashtable;
import java.util.Map;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;

import static android.app.Activity.RESULT_OK;

public class mit_frgdatostienda extends Fragment {

    ImageView mit_dtimgtienda; //+@OHC20.11.2022
    TextInputEditText mit_dtedtnombretienda, mit_dtedtsitioweb, mit_dtedtdescripcion, mit_dtedtcorreo, mit_dtedttelefono, mit_dtedtubicacion; //+@OHC20.11.2022
    Spinner mit_dtspnrciudad; //+@OHC20.11.2022
    Button mit_dtbtnseleccionarimg, mit_dtbtnubicacion, mit_dtbtncontinuar; //+@OHC20.11.2022

    String mit_vargobidpass = "0"; //+@OHC20.11.2022
    int mit_vargobidspinner = 0; //+@OHC20.11.2022

    ProgressDialog mit_pdactualizartienda; //+@OHC08.11.2022
    RequestQueue mit_rqactualizartienda; //+@OHC08.11.2022
    StringRequest mit_sractualizartienda; //+@OHC08.11.2022

    Dialog dialog;
    private Uri imageUri;
    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        SharedPreferences preferencias_global = requireActivity().getSharedPreferences(Util.ARCHIVO_GLOBAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_global = preferencias_global.edit();

        View view = inflater.inflate(R.layout.fragment_mit_frgdatostienda, container, false);

        mit_dtbtnseleccionarimg=view.findViewById(R.id.mit_dtbtnseleccionarimg);
        mit_dtimgtienda=view.findViewById(R.id.mit_dtimgtienda);

        mit_dtedtnombretienda=view.findViewById(R.id.mit_dtedtnombretienda);
        mit_dtedtsitioweb=view.findViewById(R.id.mit_dtedtsitioweb);
        mit_dtedtdescripcion=view.findViewById(R.id.mit_dtedtdescripcion);
        mit_dtedtcorreo=view.findViewById(R.id.mit_dtedtcorreo);
        mit_dtedttelefono=view.findViewById(R.id.mit_dtedttelefono);
        mit_dtedtubicacion=view.findViewById(R.id.mit_dtedtubicacion);
        mit_dtedtubicacion.setEnabled(false);

        mit_dtspnrciudad=view.findViewById(R.id.mit_dtspnrciudad);
        mit_dtbtnubicacion=view.findViewById(R.id.mit_dtbtnubicacion);

        mit_dtbtncontinuar=view.findViewById(R.id.mit_dtbtncontinuar);

        int id= rt_fctautocompletado.IdSpinner(preferencias.getString("tieCiudadNom",""));

        /* LLENANDO DATOS DESDE PREFERENCES */
        if(preferencias.contains("Direccion")){
            mit_dtedtubicacion.setText(preferencias.getString("Direccion",""));
            mit_dtspnrciudad.setSelection(preferencias.getInt("tieCiudad", 0));
            mit_dtedtnombretienda.setText(preferencias.getString("tieNombre_tmp",""));
            if(preferencias.getString("tieURLWeb_tmp","").equals("#")) mit_dtedtsitioweb.setText("");
            else mit_dtedtsitioweb.setText(preferencias.getString("tieURLWeb_tmp",""));
            mit_dtedtdescripcion.setText(preferencias.getString("tieDescripcion_tmp",""));
            mit_dtedtcorreo.setText(preferencias.getString("tieCorreo_tmp",""));
            mit_dtedttelefono.setText(preferencias.getString("tieTelefono_tmp",""));
            //INSERTAR IMAGEN
            if (preferencias.getString("tieImagen_tmp", "").equals("")) ;
            else {
                mit_dtimgtienda.setBackgroundResource(R.color.white);
                rt_fctautocompletado.cargarimagen_base64_a_imageview(preferencias.getString("tieImagen_tmp",""), mit_dtimgtienda);
            }
        }else{
            mit_dtedtubicacion.setText(preferencias.getString("tieDireccion",""));
            mit_dtspnrciudad.setSelection(id);
            mit_dtedtnombretienda.setText(preferencias.getString("tieNombre",""));
            if(preferencias.getString("tieURLWeb","").equals("#")) mit_dtedtsitioweb.setText("");
            else mit_dtedtsitioweb.setText(preferencias.getString("tieURLWeb",""));
            mit_dtedtdescripcion.setText(preferencias.getString("tieDescripcion",""));
            mit_dtedtcorreo.setText(preferencias.getString("tieCorreo",""));
            mit_dtedttelefono.setText(preferencias.getString("tieTelefono",""));
            //INSERTAR IMAGEN
            if (preferencias.getString("tieImagen", "").equals("")) ;
            else{
                mit_dtimgtienda.setBackgroundResource(R.color.white);
                rt_fctautocompletado.cargarimagen_base64_a_imageview(preferencias.getString("tieImagen",""), mit_dtimgtienda);
            }
        }

        mit_dtbtnseleccionarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirAccion();
            }
        }); //+}@OHC20.11.2022

        mit_dtspnrciudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mit_vargobidspinner = i;
                rt_fctautocompletado.rt_fctseleccionspinner(i, requireContext());
                if(mit_vargobidpass.equals("1")) mit_dtedtubicacion.setText("");
                mit_vargobidpass = "1";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        }); //+}@OHC20.11.2022

        mit_dtbtnubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor_global.putString("origFragment", "2");
                editor_global.commit();

                /* GUARDANDO DATOS EN PREFERENCES PARA EL REFRESH */
                editor.putString("tieNombre_tmp", String.valueOf(mit_dtedtnombretienda.getText()));
                if(String.valueOf(mit_dtedtsitioweb.getText()).equals("")) editor.putString("tieURLWeb_tmp", "#");
                else editor.putString("tieURLWeb_tmp", String.valueOf(mit_dtedtsitioweb.getText()));
                editor.putString("tieDescripcion_tmp", String.valueOf(mit_dtedtdescripcion.getText()));
                editor.putString("tieCorreo_tmp", String.valueOf(mit_dtedtcorreo.getText()));
                editor.putString("tieTelefono_tmp", String.valueOf(mit_dtedttelefono.getText()));
                editor.putInt("tieCiudad", mit_vargobidspinner);
                editor.apply();


                Navigation.findNavController(view).navigate(R.id.action_mit_frgdatostienda_to_rt_frgubicacion);
            }
        }); //+}@OHC20.11.2022

        mit_dtbtncontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mit_dtedtnombretienda.getText().toString().equals("") ||
                        mit_dtedttelefono.getText().toString().equals("") ||
                        mit_dtedtubicacion.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Rellene los campos necesarios", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(preferencias.getString("tieImagen_tmp", "").equals(preferencias.getString("tieImagen",""))) EnviarDatos(preferencias, view, "0");
                    else EnviarDatos(preferencias, view, "1");
                }

            }
        });//+}@OHC20.11.2022

        return view;
    }

    public void elegirAccion(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.pub_dialog_camara_galeria);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        ImageView close = dialog.findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button camara = dialog.findViewById(R.id.btn_Camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int estadoDePermiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
                    estadoDePermiso = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
                        dialog.dismiss();
                        tomarFoto();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PackageManager.PERMISSION_GRANTED);
                    }
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            PackageManager.PERMISSION_GRANTED);
                }
            }
        });
        Button galeria = dialog.findViewById(R.id.btn_Galeria);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                seleccionarImagen();
            }
        });
        dialog.show();
    }

    public void tomarFoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, TOMAR_FOTO);
    }
    public void seleccionarImagen() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, SELEC_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK){
                if(requestCode == 200){
                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if(requestCode == TOMAR_FOTO){
                    CropImage.activity(imageUri)
                            .setAspectRatio(1, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                    //Croped image received
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK){
                        mit_dtimgtienda.setBackgroundResource(R.color.white);
                        Uri resultUri = result.getUri();
                        mit_dtimgtienda.setImageURI(resultUri);

                        //NUEVA IMAGEN EN CODE 64
                        BitmapDrawable drawable = (BitmapDrawable) mit_dtimgtienda.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        String imagen = rt_fctautocompletado.getStringImagen(bitmap);

                        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("tieImagen_tmp", imagen);
                        editor.commit();

                    }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                        Exception error = result.getError();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }//+}@OHC20.11.2022

    private void ActualizarDatos(View view){
        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        /* ACTUALIZANDO DATOS EN EL PREFERENCE */
        editor.putString("tieNombre", mit_dtedtnombretienda.getText().toString());
        editor.putString("tieURLWeb", mit_dtedtsitioweb.getText().toString());
        editor.putString("tieDescripcion", mit_dtedtdescripcion.getText().toString());
        editor.putString("tieCorreo", mit_dtedtcorreo.getText().toString());
        editor.putString("tieTelefono", mit_dtedttelefono.getText().toString());
        editor.putString("tieCiudadNom", mit_dtspnrciudad.getSelectedItem().toString());
        editor.putString("tieImagen", preferencias.getString("tieImagen_tmp", ""));

        editor.putString("tieLatitud", preferencias.getString("tieLatitud_tmp",""));
        editor.putString("tieLongitud", preferencias.getString("tieLongitud_tmp",""));
        editor.putString("tieDireccion", mit_dtedtubicacion.getText().toString());
        editor.putString("", preferencias.getString("tieImagen_tmp",""));

        /* ELIMINANDO DATOS TEMPORALES EN EL PREFERENCE */
        editor.remove("tieLatitud_tmp");
        editor.remove("tieLongitud_tmp");
        editor.remove("tieNombre_tmp");
        editor.remove("tieURLWeb_tmp");
        editor.remove("tieDescripcion_tmp");
        editor.remove("tieCorreo_tmp");
        editor.remove("tieTelefono_tmp");
        editor.remove("tieImagen_tmp");

        editor.apply();
    } //+}@OHC20.11.2022
    private void EnviarDatos(SharedPreferences preferencias, View view, String p_idValidacionImagen){
        mit_pdactualizartienda = new ProgressDialog(getContext());
        mit_pdactualizartienda.setMessage("Cargando datos");
        mit_pdactualizartienda.show();

        String url = Util.RUTA + "m_datos_tienda_registrotienda.php";
        mit_sractualizartienda = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Datos Actualizados Correctamente", Toast.LENGTH_SHORT).show();
                ActualizarDatos(view);
                mit_pdactualizartienda.hide();
                Navigation.findNavController(view).navigateUp();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mit_pdactualizartienda.hide();
                Toast.makeText(getContext(), "No se pudo actualizar." + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new Hashtable<String, String>();
                params.put("p_idTienda", preferencias.getString("idTienda",""));
                params.put("p_tieNombre", mit_dtedtnombretienda.getText().toString());
                params.put("p_tieImagen", preferencias.getString("tieImagen_tmp", ""));
                if(mit_dtedtsitioweb.getText().toString().equals("")) params.put("p_tieURLWeb", "#");
                else params.put("p_tieURLWeb", mit_dtedtsitioweb.getText().toString());
                params.put("p_tieDescripcion", mit_dtedtdescripcion.getText().toString());
                params.put("p_tieCorreo", mit_dtedtcorreo.getText().toString());
                params.put("p_tieTelefono", mit_dtedttelefono.getText().toString());
                params.put("p_tieDireccion", mit_dtedtubicacion.getText().toString());
                params.put("p_tieCiudad", mit_dtspnrciudad.getSelectedItem().toString());
                params.put("p_tieEstado", "Abierto");
                params.put("p_tieLatitud", preferencias.getString("tieLatitud_tmp", ""));
                params.put("p_tieLongitud", preferencias.getString("tieLongitud_tmp", ""));
                params.put("p_idRubroTienda", preferencias.getString("idRubroTienda", ""));
                params.put("p_idValidacionImagen", p_idValidacionImagen);
                return params;
            }
        };
        mit_rqactualizartienda= Volley.newRequestQueue(getContext());
        mit_rqactualizartienda.add(mit_sractualizartienda);
    } //+}@OHC20.11.2022

}