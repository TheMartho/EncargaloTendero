//Creado por Morales Fabian Renzo Gabriel - Universidad Continental - 2022
package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Inicio_sesion.MainActivity;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Soporte.sp_frgTerminoscondicionesdetalles;
import mx.com.encargalo.tendero.Util.Util;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class pub_frgcrearanuncio extends Fragment implements View.OnClickListener {
    Button pub_cabtncontinuar, pub_cabtnseleccionarimg, pub_varstrterminos, pub_varstrpolitica;
    ImageView pub_caimgvwpubcargada;
    EditText pub_caedttituloanuncio, pub_caedttextoanuncio, pub_caedtlinkanuncio;
    TextView pub_caedtfechainitanuncio, pub_caedtfechafinanuncio;
    CheckBox pub_cachkbaceptarterminos;
    DatePickerDialog.OnDateSetListener setListener1, setListener2;
    Calendar calendar;
    Dialog dialog;

    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;
    boolean cimg=false;
    private Uri imageUri;
    Bitmap bitmapimgselecionada;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pub_frgcrearanuncio, container, false);

        pub_cabtncontinuar=vista.findViewById(R.id.pub_cabtncontinuar);
        pub_cabtnseleccionarimg=vista.findViewById(R.id.pub_cabtnseleccionarimg);
        pub_caimgvwpubcargada=vista.findViewById(R.id.pub_caimgvwpubcargada);
        pub_caedttituloanuncio=vista.findViewById(R.id.pub_caedttituloanuncio);
        pub_caedttextoanuncio=vista.findViewById(R.id.pub_caedttextoanuncio);
        pub_caedtlinkanuncio=vista.findViewById(R.id.pub_caedtlinkanuncio);
        pub_caedtfechainitanuncio=vista.findViewById(R.id.pub_caedtfechainitanuncio);
        pub_caedtfechafinanuncio=vista.findViewById(R.id.pub_caedtfechafinanuncio);
        pub_cachkbaceptarterminos=vista.findViewById(R.id.pub_cachkbaceptarterminos);
        pub_varstrterminos=vista.findViewById(R.id.pub_varstrterminos);
        pub_varstrpolitica=vista.findViewById(R.id.pub_varstrpolitica);
        pub_varstrterminos.setOnClickListener(this);
        pub_varstrpolitica.setOnClickListener(this);
        calendar = Calendar.getInstance();

        final int año = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

        pub_caedtfechainitanuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth, setListener1, año, mes, dia
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pub_caedtfechainitanuncio.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        };
        pub_caedtfechafinanuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Dialog_MinWidth, setListener2, año, mes, dia
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pub_caedtfechafinanuncio.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        };
        pub_cabtnseleccionarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirAccion();
            }
        });
        pub_cabtncontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(
                    bitmapimgselecionada != null &&
                    pub_caedttituloanuncio.length() != 0 &&
                    pub_caedttextoanuncio.length() != 0 &&
                    pub_caedtlinkanuncio.length() != 0 &&
                    pub_caedtfechainitanuncio.length() != 0 &&
                    pub_caedtfechafinanuncio.length() != 0
                ){
                    Date fec_inicio = new Date(Date.parse(pub_caedtfechainitanuncio.getText().toString()));
                    Date fec_fin    = new Date(Date.parse(pub_caedtfechafinanuncio.getText().toString()));
                    if(
                            fec_inicio.getTime() < fec_fin.getTime()
                    ){
                        Date currentTime = Calendar.getInstance().getTime();

                        if(currentTime.getTime()-86400000 <= fec_inicio.getTime() &&
                           currentTime.getTime()-86400000 <= fec_fin.getTime()){
                            if(pub_cachkbaceptarterminos.isChecked()){
                                SharedPreferences sharedPreferences =
                                        getContext().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("pathImg", getStringImagen(bitmapimgselecionada));
                                editor.putString("titulo", pub_caedttituloanuncio.getText().toString());
                                editor.putString("descripcion", pub_caedttextoanuncio.getText().toString());
                                editor.putString("linkRed", pub_caedtlinkanuncio.getText().toString());
                                editor.putString("fechaInicio", pub_caedtfechainitanuncio.getText().toString());
                                editor.putString("fechaFin", pub_caedtfechafinanuncio.getText().toString());
                                editor.apply();
                                Navigation.findNavController(view).navigate(R.id.nav_pagaranuncios);
                            }else{
                                Toast.makeText(getContext(), "Acepte los terminos y condiciones", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "Fechas ingresadas estan atrasadas", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Fechas ingresadas incorrectamente", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return vista;
    }
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.pub_varstrterminos:{
                bundle.putString("tipo", "terminos_condiciones");
                sp_frgTerminoscondicionesdetalles dialogoDetalle = new sp_frgTerminoscondicionesdetalles();
                dialogoDetalle.setArguments(bundle);
                dialogoDetalle.show(getActivity().getSupportFragmentManager(), "terminos_condiciones");
                break;
            }
            case R.id.pub_varstrpolitica:{
                bundle.putString("tipo", "politica_privacidad");
                sp_frgTerminoscondicionesdetalles dialogoDetalle = new sp_frgTerminoscondicionesdetalles();
                dialogoDetalle.show(getActivity().getSupportFragmentManager(), "politica_privacidad");
                dialogoDetalle.setArguments(bundle);
                break;
            }
        }
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
                            .setAspectRatio(2, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if(requestCode == TOMAR_FOTO){
                    CropImage.activity(imageUri)
                            .setAspectRatio(2, 1)
                            .setBorderCornerColor(Color.BLACK)
                            .start(getContext(), this);
                }
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                    //Croped image received
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK){
                        Uri resultUri = result.getUri();
                        imageUri = resultUri;
                        pub_caimgvwpubcargada.setImageURI(resultUri);
                        Bitmap bm = ((BitmapDrawable)pub_caimgvwpubcargada.getDrawable()).getBitmap();
                        bitmapimgselecionada=Bitmap.createScaledBitmap(bm, 100, 100, true);
                        cimg=true;
                    }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                        Exception error = result.getError();
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
