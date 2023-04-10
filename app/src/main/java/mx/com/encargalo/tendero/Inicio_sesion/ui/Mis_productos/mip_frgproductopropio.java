package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

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
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda.rt_fctautocompletado;
import mx.com.encargalo.tendero.Util.Util;

import static android.app.Activity.RESULT_OK;

public class mip_frgproductopropio extends Fragment {

    ImageView mip_ppimgagregarimagen01, mip_ppimgagregarimagen02, mip_ppimgagregarimagen03;
    TextInputEditText mip_ppedtdescripcionproducto, mip_ppedtstock, mip_ppedtstockminimo, mip_ppedtcostoproducto, mip_ppedtprecioventa;
    Spinner mip_ppspnunidaddemedida;
    Button mip_ppbtncancelar, mip_ppbtnagregarproducto;

    ProgressDialog mip_pdregistrarproductopropio; //+@OHC20.11.2022
    RequestQueue mip_rqregistrarproductopropio; //+@OHC20.11.2022
    StringRequest mip_srregistrarproductopropio; //+@OHC20.11.2022

    Dialog dialog;
    private Uri imageUri;
    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mip_frgproductopropio, container, false);

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        mip_ppimgagregarimagen01 = view.findViewById(R.id.mip_ppimgagregarimagen01);
        mip_ppimgagregarimagen02 = view.findViewById(R.id.mip_ppimgagregarimagen02);
        mip_ppimgagregarimagen03 = view.findViewById(R.id.mip_ppimgagregarimagen03);

        mip_ppedtdescripcionproducto = view.findViewById(R.id.mip_ppedtdescripcionproducto);
        mip_ppedtstock = view.findViewById(R.id.mip_ppedtstock);
        mip_ppedtstockminimo = view.findViewById(R.id.mip_ppedtstockminimo);
        mip_ppspnunidaddemedida = view.findViewById(R.id.mip_ppspnunidaddemedida);
        mip_ppedtcostoproducto = view.findViewById(R.id.mip_ppedtcostoproducto);
        mip_ppedtprecioventa = view.findViewById(R.id.mip_ppedtprecioventa);

        mip_ppbtncancelar = view.findViewById(R.id.mip_ppbtncancelar);
        mip_ppbtnagregarproducto = view.findViewById(R.id.mip_ppbtnagregarproducto);

        mip_ppimgagregarimagen02.setVisibility(View.INVISIBLE);
        mip_ppimgagregarimagen03.setVisibility(View.INVISIBLE);

        mip_ppimgagregarimagen01.setEnabled(true);
        mip_ppimgagregarimagen02.setEnabled(true);
        mip_ppimgagregarimagen03.setEnabled(true);

        mip_ppedtstock.setText("0.0");
        mip_ppedtstockminimo.setText("0.0");

        mip_ppimgagregarimagen01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirAccion();
            }
        }); //+}@OHC20.11.2022

        mip_ppimgagregarimagen02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirAccion();
            }
        }); //+}@OHC20.11.2022

        mip_ppimgagregarimagen03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirAccion();
            }
        }); //+}@OHC20.11.2022


        mip_ppbtncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        mip_ppbtnagregarproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mip_ppedtdescripcionproducto.getText().toString().equals("")||mip_ppedtcostoproducto.getText().toString().equals("")
                        || mip_ppedtprecioventa.getText().toString().equals("") || preferencias.getString("lptImagen1", "").equals("")
                        || mip_ppedtstock.getText().toString().equals("") || mip_ppedtstockminimo.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Rellenar todos los campos!", Toast.LENGTH_SHORT).show();
                }else{

                    if( Float.valueOf(mip_ppedtstock.getText().toString())  >= Float.valueOf(mip_ppedtstockminimo.getText().toString())){
                        if(Float.valueOf(mip_ppedtprecioventa.getText().toString())  >= Float.valueOf(mip_ppedtcostoproducto.getText().toString()))
                            RegistrarProductoPropio(view);
                        else Toast.makeText(getActivity(), "Precio de Venta debe ser mayor o igual a Costo del Producto!", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getActivity(), "Stock debe ser mayor a Stock Mínimo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                if(requestCode == SELEC_IMAGEN){
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

                        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        Uri resultUri = result.getUri();
                        if(preferencias.getString("lptImagen1", "").equals("")){
                            String imgbase64 = Datosinsertadosimg(mip_ppimgagregarimagen01, editor, resultUri);
                            editor.putString("lptImagen1", imgbase64);
                            editor.putString("lptImagen2", "");
                            editor.putString("lptImagen3", "");
                            editor.commit();
                            mip_ppimgagregarimagen01.setEnabled(false);
                            mip_ppimgagregarimagen02.setVisibility(View.VISIBLE);
                        }else{
                            if (preferencias.getString("lptImagen2", "").equals("")){
                                String imgbase64 = Datosinsertadosimg(mip_ppimgagregarimagen02, editor, resultUri);
                                editor.putString("lptImagen2", imgbase64);
                                editor.putString("lptImagen3", "");
                                editor.commit();
                                mip_ppimgagregarimagen02.setEnabled(false);
                                mip_ppimgagregarimagen03.setVisibility(View.VISIBLE);
                            }else if(preferencias.getString("lptImagen3", "").equals("")){
                                String imgbase64 = Datosinsertadosimg(mip_ppimgagregarimagen03, editor, resultUri);
                                editor.putString("lptImagen3", imgbase64);
                                editor.commit();
                                mip_ppimgagregarimagen03.setEnabled(false);
                            }
                        }

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

    private String Datosinsertadosimg(ImageView imageView, SharedPreferences.Editor editor, Uri resultUri) {
        imageView.setBackgroundResource(R.color.white);
        imageView.setImageURI(resultUri);
        //NUEVA IMAGEN EN CODE 64
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String imagen = rt_fctautocompletado.getStringImagen(bitmap);
        return imagen;
    }//+}@OHC20.11.2022

    private void RegistrarProductoPropio(View view) {
        mip_pdregistrarproductopropio = new ProgressDialog(getContext());
        mip_pdregistrarproductopropio.setMessage("Enviando Datos...");
        mip_pdregistrarproductopropio.show();

        String url = Util.RUTA + "a_producto_propio_registroproducto.php?";
        mip_srregistrarproductopropio = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                mip_ppedtdescripcionproducto.setText("");
                mip_ppedtstock.setText("");
                mip_ppedtstockminimo.setText("");
                mip_ppspnunidaddemedida.setSelection(0);
                mip_ppedtcostoproducto.setText("");
                mip_ppedtprecioventa.setText("");
                mip_pdregistrarproductopropio.hide();
                Navigation.findNavController(view).navigate(R.id.action_mip_frgproductopropio_to_mip_frgproductoagregado);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mip_pdregistrarproductopropio.hide();
                Toast.makeText(getContext(), "No se pudo registrar." + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
                SharedPreferences preferencias_prod = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);

                Map<String, String> params = new Hashtable<String, String>();
                params.put("p_lptDescripcionProductoTienda", mip_ppedtdescripcionproducto.getText().toString());
                params.put("p_lptStock", mip_ppedtstock.getText().toString());
                params.put("p_lptUnidadMedida", mip_ppspnunidaddemedida.getSelectedItem().toString());
                params.put("p_lptStockMinimo", mip_ppedtstockminimo.getText().toString());
                params.put("p_lptImagen1", preferencias_prod.getString("lptImagen1", ""));
                params.put("p_lptImagen2", preferencias_prod.getString("lptImagen2", ""));
                params.put("p_lptImagen3", preferencias_prod.getString("lptImagen3", ""));
                params.put("p_lptPrecioCompra", mip_ppedtcostoproducto.getText().toString());
                params.put("p_lptPrecioVenta", mip_ppedtprecioventa.getText().toString());
                params.put("p_idTienda", preferencias.getString("idTienda", ""));
                return params;
            }

        };
        mip_rqregistrarproductopropio= Volley.newRequestQueue(getContext());
        mip_rqregistrarproductopropio.add(mip_srregistrarproductopropio);
    }

}