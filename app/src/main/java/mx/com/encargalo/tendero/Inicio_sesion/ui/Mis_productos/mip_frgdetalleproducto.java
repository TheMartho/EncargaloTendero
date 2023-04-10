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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda.rt_fctautocompletado;
import mx.com.encargalo.tendero.Util.Util;

import static android.app.Activity.RESULT_OK;

public class mip_frgdetalleproducto extends Fragment {

    ImageView mip_dpimgagregarimagen01, mip_dpimgagregarimagen02, mip_dpimgagregarimagen03;
    TextInputEditText mip_dpedtcodigo, mip_dpedtdescripcion, mip_dpedtstock, mip_dpedtstockminimo, mip_dpedtcostoproducto, mip_dpedtprecioventa;
    Spinner mip_dpspnunidaddemedida;
    Button mip_dpbtneliminar, mip_dpbtnmodificar;

    ProgressDialog mip_pdactualizarproducto; //+@OHC20.11.2022
    RequestQueue mip_rqactualizarproducto, mip_rqeliminarproducto; //+@OHC20.11.2022
    StringRequest mip_sractualizarproducto; //+@OHC20.11.2022
    JsonObjectRequest mip_joreliminarproducto; //+@OHC04.11.2022

    int imgsel= 0;
    String img01ant_64 = "";
    String img02ant_64 = "";
    String img03ant_64 = "";

    Dialog dialog;
    private Uri imageUri;
    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mip_frgdetalleproducto, container, false);

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        mip_dpimgagregarimagen01 = view.findViewById(R.id.mip_dpimgagregarimagen01);
        mip_dpimgagregarimagen02 = view.findViewById(R.id.mip_dpimgagregarimagen02);
        mip_dpimgagregarimagen03 = view.findViewById(R.id.mip_dpimgagregarimagen03);

        mip_dpedtcodigo = view.findViewById(R.id.mip_dpedtcodigo);
        mip_dpedtstock = view.findViewById(R.id.mip_dpedtstock);
        mip_dpedtstockminimo = view.findViewById(R.id.mip_dpedtstockminimo);
        mip_dpedtdescripcion = view.findViewById(R.id.mip_dpedtdescripcion);
        mip_dpedtcostoproducto = view.findViewById(R.id.mip_dpedtcostoproducto);
        mip_dpedtprecioventa = view.findViewById(R.id.mip_dpedtprecioventa);

        mip_dpspnunidaddemedida = view.findViewById(R.id.mip_dpspnunidaddemedida);

        mip_dpbtneliminar = view.findViewById(R.id.mip_dpbtneliminar);
        mip_dpbtnmodificar = view.findViewById(R.id.mip_dpbtnmodificar);

        mip_dpimgagregarimagen03.setVisibility(View.INVISIBLE);
        if(preferencias.contains("idListadoProductoTienda")){
            String urlimage1 = Util.RUTA + preferencias.getString("lptImagen1_tmp", "");
            Glide.with(getActivity()).load(urlimage1).into(mip_dpimgagregarimagen01);

            Glide.with(getActivity()).asBitmap().load(urlimage1).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    //Aquí obtengo el bitmap correctamente
                    img01ant_64 = rt_fctautocompletado.getStringImagen(resource);
                    editor.putString("lptImagen1_tmp_64", img01ant_64);
                    editor.commit();
                }
            });

            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("idProducto", mip_dpedtcodigo, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("lptDescripcionProductoTienda", mip_dpedtdescripcion, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("lptStock", mip_dpedtstock, requireContext());
            mip_dpspnunidaddemedida.setSelection(rt_fctautocompletado.IdSpinnerUnidadMedida(preferencias.getString("lptUnidadMedida","")));
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("lptStockMinimo", mip_dpedtstockminimo, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("lptPrecioCompra", mip_dpedtcostoproducto, requireContext());
            rt_fctautocompletado.rt_fctautorrellenadoporcampoprod("lptPrecioVenta", mip_dpedtprecioventa, requireContext());

            if(preferencias.getString("lptImagen2_tmp", "").equals(""));
            else{
                String urlimage2 = Util.RUTA + preferencias.getString("lptImagen2_tmp", "");
                Glide.with(getActivity()).load(urlimage2).into(mip_dpimgagregarimagen02);

                Glide.with(getActivity()).asBitmap().load(urlimage2).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        //Aquí obtengo el bitmap correctamente
                        img02ant_64 = rt_fctautocompletado.getStringImagen(resource);
                        editor.putString("lptImagen2_tmp_64", img02ant_64);
                        editor.commit();
                    }
                });

                mip_dpimgagregarimagen03.setVisibility(View.VISIBLE);

            }
            if(preferencias.getString("lptImagen3_tmp", "").equals(""));
            else{
                String urlimage3 = Util.RUTA + preferencias.getString("lptImagen3_tmp", "");
                Glide.with(getActivity()).load(urlimage3).into(mip_dpimgagregarimagen03);

                Glide.with(getActivity()).asBitmap().load(urlimage3).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        //Aquí obtengo el bitmap correctamente
                        img03ant_64 = rt_fctautocompletado.getStringImagen(resource);
                        editor.putString("lptImagen3_tmp_64", img03ant_64);
                        editor.commit();
                    }
                });
            }
        }

        mip_rqeliminarproducto = Volley.newRequestQueue(requireContext()); //+@OHC20.11.2022

        mip_dpimgagregarimagen01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsel= 1;
                elegirAccion();
            }
        }); //+}@OHC20.11.2022

        mip_dpimgagregarimagen02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsel= 2;
                elegirAccion();
            }
        }); //+}@OHC20.11.2022

        mip_dpimgagregarimagen03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgsel= 3;
                elegirAccion();
            }
        }); //+}@OHC20.11.2022

        mip_dpbtneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarDatos(view, preferencias);

            }
        });

        mip_dpbtnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferencias.getString("lptImagen1_tmp", "").equals("") || mip_dpedtstock.getText().toString().equals("")
                        || mip_dpedtdescripcion.getText().toString().equals("")|| mip_dpedtcostoproducto.getText().toString().equals("")
                        || mip_dpedtprecioventa.getText().toString().equals("") || mip_dpedtstockminimo.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Rellenar todos los campos!", Toast.LENGTH_SHORT).show();
                }
                else{

                    if( Float.valueOf(mip_dpedtstock.getText().toString())  >= Float.valueOf(mip_dpedtstockminimo.getText().toString())){
                        if(Float.valueOf(mip_dpedtprecioventa.getText().toString())  >= Float.valueOf(mip_dpedtcostoproducto.getText().toString())){
                            if(img01ant_64.equals(preferencias.getString("lptImagen1_tmp_64", ""))
                                    &&img02ant_64.equals(preferencias.getString("lptImagen2_tmp_64", ""))
                                    &&img03ant_64.equals(preferencias.getString("lptImagen3_tmp_64", "")))
                                ActualizarProducto(view, "0");
                            else ActualizarProducto(view, "1");
                        }
                        else Toast.makeText(getActivity(), "Precio de Venta debe ser mayor o igual a Costo del Producto!", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getActivity(), "Stock debe ser mayor a Stock Mínimo!", Toast.LENGTH_SHORT).show();


                }
            }
        });

        if(Integer.valueOf(preferencias.getString("idCategoriaProducto", "")) > 1) mip_dpspnunidaddemedida.setEnabled(false);
        else mip_dpspnunidaddemedida.setEnabled(true);

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

    private void EliminarDatos(View view, SharedPreferences preferencias) {
        mip_pdactualizarproducto = new ProgressDialog(getContext());
        mip_pdactualizarproducto.setMessage("Eliminando Producto");
        mip_pdactualizarproducto.show();

        String url= Util.RUTA + "m_producto_inactivo_registroproducto.php?p_idListadoProductoTienda="+preferencias.getString("idListadoProductoTienda","");
        url=url.replace(" ","%20");
        mip_joreliminarproducto= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                mip_pdactualizarproducto.hide();
                Navigation.findNavController(view).navigateUp();
                //Refrescar Pantalla Reporte Productos
                Fragment fragment = new mip_frgreporteproductos();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mip_pdactualizarproducto.hide();
                if(error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value Producto of type java.lang.String cannot be converted to JSONObject")){
                    Toast.makeText(getContext(), "Actualización exitosa", Toast.LENGTH_SHORT).show();
                    mip_dpedtcodigo.setText("");
                    mip_dpedtstock.setText("");
                    mip_dpedtdescripcion.setText("");
                    mip_dpedtcostoproducto.setText("");
                    mip_dpedtprecioventa.setText("");
                    Navigation.findNavController(view).navigateUp();

                    //Refrescar Pantalla Reporte Productos
                    Fragment fragment = new mip_frgreporteproductos();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                    fragmentTransaction.commit();
                }
                else Toast.makeText(getContext(), "Error al consultar " + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        mip_rqeliminarproducto.add(mip_joreliminarproducto);
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

                        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        Uri resultUri = result.getUri();

                        if(imgsel== 1){
                            String imgbase64 = Datosinsertadosimg(mip_dpimgagregarimagen01, editor, resultUri);
                            editor.putString("lptImagen1_tmp_64", imgbase64);
                            editor.commit();
                        }else{
                            if (imgsel== 2){
                                String imgbase64 = Datosinsertadosimg(mip_dpimgagregarimagen02, editor, resultUri);
                                editor.putString("lptImagen2_tmp_64", imgbase64);
                                editor.commit();
                                mip_dpimgagregarimagen03.setVisibility(View.VISIBLE);
                            }else if(imgsel== 3){
                                String imgbase64 = Datosinsertadosimg(mip_dpimgagregarimagen03, editor, resultUri);
                                editor.putString("lptImagen3_tmp_64", imgbase64);
                                editor.commit();
                            }
                        }

                        imgsel= 0;
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

    private void ActualizarProducto(View view, String p_idValidacionImagen) {
        mip_pdactualizarproducto = new ProgressDialog(getContext());
        mip_pdactualizarproducto.setMessage("Actualizando Datos...");
        mip_pdactualizarproducto.show();

        String url = Util.RUTA + "m_datos_producto_registroproducto.php?";
        mip_sractualizarproducto = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Actualización exitosa", Toast.LENGTH_SHORT).show();
                mip_dpedtcodigo.setText("");
                mip_dpedtstock.setText("");
                mip_dpedtdescripcion.setText("");
                mip_dpedtstockminimo.setText("");
                mip_dpspnunidaddemedida.setSelection(0);
                mip_dpedtcostoproducto.setText("");
                mip_dpedtprecioventa.setText("");
                Navigation.findNavController(view).navigateUp();
                mip_pdactualizarproducto.hide();

                //Refrescar Pantalla Reporte Productos
                Fragment fragment = new mip_frgreporteproductos();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mip_pdactualizarproducto.hide();
                Toast.makeText(getContext(), "No se pudo registrar." + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences preferencias_prod = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);

                Map<String, String> params = new Hashtable<String, String>();
                params.put("p_lptDescripcionProductoTienda", mip_dpedtdescripcion.getText().toString());
                params.put("p_lptStock", mip_dpedtstock.getText().toString());
                params.put("p_lptUnidadMedida", mip_dpspnunidaddemedida.getSelectedItem().toString());
                params.put("p_lptStockMinimo", mip_dpedtstockminimo.getText().toString());
                params.put("p_lptImagen1", preferencias_prod.getString("lptImagen1_tmp_64", ""));
                params.put("p_lptImagen2", preferencias_prod.getString("lptImagen2_tmp_64", ""));
                params.put("p_lptImagen3", preferencias_prod.getString("lptImagen3_tmp_64", ""));
                params.put("p_lptPrecioCompra", mip_dpedtcostoproducto.getText().toString());
                params.put("p_lptPrecioVenta", mip_dpedtprecioventa.getText().toString());
                params.put("p_idListadoProductoTienda", preferencias_prod.getString("idListadoProductoTienda", ""));
                params.put("p_idValidacionImagen", p_idValidacionImagen);
                return params;
            }

        };
        mip_rqactualizarproducto= Volley.newRequestQueue(getContext());
        mip_rqactualizarproducto.add(mip_sractualizarproducto);
    }
}