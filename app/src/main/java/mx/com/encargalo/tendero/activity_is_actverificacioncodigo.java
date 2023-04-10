package mx.com.encargalo.tendero;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;
import mx.com.encargalo.tendero.Util.is_cls_ActivitysInicioSesion;
import mx.com.encargalo.tendero.Util.is_cls_JavaMailAPI;
import mx.com.encargalo.tendero.Inicio_sesion.MainActivity;

public class activity_is_actverificacioncodigo extends AppCompatActivity {
    Button btnvalidar, btnreenviar;
    String email, rolusuario;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    EditText edtcodigoverificacion;
    String codigo;
    String correo;
    String idDocumentoPersona;
    EditText textprueba;
    String docPerson;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_actverificacioncodigo);
        request= Volley.newRequestQueue(this);
        Intent intent = getIntent();
        this.email = intent.getStringExtra("usuCorreo");
        this.rolusuario = intent.getStringExtra("idRolUsuario");
        btnvalidar=(Button)findViewById(R.id.is_vcbtncontinuar);
        btnreenviar=(Button)findViewById(R.id.is_vcbtnreenviarcodigo);
        edtcodigoverificacion = findViewById(R.id.is_vcedtcodigoverificacion);
        correo=email;
//        cargarWebService();
//        enviar_correo();



        btnreenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                enviar_correo();
            }
        });

//VALIDAR Y ENVIAR A MAIN ACTIVITY
        btnvalidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verificarcuenta();
                // camposobligatorios();
            }
        });
        SharedPreferences prfs = getSharedPreferences("Credencial_Global_usuario", Context.MODE_PRIVATE);
        boolean is_login = prfs.getBoolean("is_login", false);

        if (is_login){
            is_cls_ActivitysInicioSesion.getSingleton(activity_is_actverificacioncodigo.this, MainActivity.class).muestraActividad();
        }
    }




//    public void cargarWebService() {
//        //progreso = new ProgressDialog(this);
//        //progreso.setMessage("CARGANDO DATOS");
//        //progreso.show();
//        String url= Util.RUTA+"a_registro_cod_verificacion_usuario_inicio_sesion.php?sp_codvCorreo="+email;
//        url=url.replace(" ","%20");
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(activity_is_actverificacioncodigo.this,"Se ha enviado su correo con éxito", Toast.LENGTH_SHORT).show();
//               // Intent intent=new Intent(activity_is_actverificacioncodigo.this,activity_is_actverificacioncodigo.class);
//                //startActivity(intent);
//                //progreso.hide();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //progreso.hide();
//                Toast.makeText(activity_is_actverificacioncodigo.this,"Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        request.add(jsonObjectRequest);
//    }

    public void enviar_correo() {
//        progreso = new ProgressDialog(this);
//        progreso.setMessage("ENVIANDO CÓDIGO AL CORREO ELECTRÓNICO");
//        progreso.show();
        String url= Util.RUTA+"c_codigo_verificacion_inicio_sesion.php?sp_codvCorreo="+email;
        url=url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                {
                    Toast.makeText(activity_is_actverificacioncodigo.this,"Se ha enviado su correo con éxito", Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray jsonArray = response.getJSONArray("usuario");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject usuario = jsonArray.getJSONObject(i);
                            String correousuario=usuario.getString("codvCorreo");
                            String codigov=usuario.getString("codvCodigo");
                            String mEmail = correousuario;
                            String mSubject = "Código de confirmación de registro";
                            String mMessage = "Estimado usuario, su código de registro es: "+codigov;


                            is_cls_JavaMailAPI is_cls_JavaMailAPI= new  is_cls_JavaMailAPI(this, mEmail, mSubject, mMessage);
                            is_cls_JavaMailAPI.execute();
                            //  Intent intent=new Intent(activity_is_actcrearunacuenta.this,activity_is_actverificacioncodigo.class);
                            // startActivity(intent);
                            //String valor=usuario.getString("usuToken");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(activity_is_actcrearunacuenta.this,"Se ha enviado su correo con éxito", Toast.LENGTH_SHORT).show();
                    // Intent intent=new Intent(activity_is_actverificacioncodigo.this,activity_is_actverificacioncodigo.class);
                    //startActivity(intent);
                    // progreso.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progreso.hide();
                //Toast.makeText(activity_is_actcrearunacuenta.this,"Error", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }



    public void verificarcuenta(){
        String url= Util.RUTA+"c_validar_codigo_verificacion_inicio_sesion.php?sp_codvCorreo="+email+"&sp_codvCodigo="+ edtcodigoverificacion.getText().toString();
        url=url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(activity_is_actverificacioncodigo.this,"Código válido", Toast.LENGTH_SHORT).show();
                insertartoken();
                enviardatos();

                // Intent intent=new Intent(activity_is_actverificacioncodigo.this,activity_is_actverificacioncodigo.class);
                //intent.putExtra("UsuCorreo", is_edtcorreo.getText().toString());
                //startActivity(intent);
                //   progreso.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progreso.hide();
                Toast.makeText(activity_is_actverificacioncodigo.this,"Error, código incorrecto", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    public void insertartoken(){
        String url= Util.RUTA+"m_codigo_verificacion_valido_inicio_sesion.php?sp_codvCorreo="+email+"&sp_codvCodigo="+edtcodigoverificacion.getText().toString();
        url=url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(activity_is_actverificacioncodigo.this,"", Toast.LENGTH_SHORT).show();


                // Intent intent=new Intent(activity_is_actverificacioncodigo.this,activity_is_actverificacioncodigo.class);
                //intent.putExtra("UsuCorreo", is_edtcorreo.getText().toString());
                //startActivity(intent);
//            progreso.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progreso.hide();
                //   Toast.makeText(activity_is_actverificacioncodigo.this,"Error, consulte con soporte", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);


    }

    public void enviardatos(){
        String url= Util.RUTA+"c_existencia_usuario_inicio_sesion.php?sp_usuCorreo="+email;
        url=url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response ) {
                {
                    try {
                        JSONArray jsonArray = response.getJSONArray("usuario");
                        for (int i=0; i<jsonArray.length();i++){
                            JSONObject usuario = jsonArray.getJSONObject(i);
                            idDocumentoPersona = usuario.getString("idDocumentoPersona");
                            docPerson = usuario.getString("idDocumentoPersona");
                            SharedPreferences creduser=getSharedPreferences("Credencial_Global_usuario",0);
                            SharedPreferences.Editor editocred=creduser.edit();
                            editocred.putString("CredId",docPerson);
                            editocred.commit();
//
//                                        SharedPreferences preferences = getSharedPreferences("", 0);
//                                        preferences.edit().remove("Credencial_Global_usuario").commit();
//

                            SharedPreferences sessionpreferences = getSharedPreferences("Credencial_Global_usuario", 0);
                            sessionpreferences.edit().remove("is_login").commit();
                            SharedPreferences.Editor editor = sessionpreferences.edit();
                            editor.putBoolean("is_login", true);
                            editor.apply();
                            Intent intent=new Intent(activity_is_actverificacioncodigo.this,MainActivity.class);
                            intent.putExtra("idDocumentoPersona", idDocumentoPersona);

                            startActivity(intent);
                            // params.put(is_cls_Constants_InicioSesion.idDocumentoPersona, usuario.getString("idDocumentoPersona"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//codigo.setText(idDocumentoPersona);

                //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad(params);

                // Navigation.findNavController(Activity getContext()).navigate(R.id.nav_mi_tienda);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //   progreso.hide();
                // Toast.makeText(activity_is_actverificacioncodigo.this,"Error, consulte con soporte", Toast.LENGTH_SHORT).show();

            }
        });
        request.add(jsonObjectRequest);
    }
//    public void camposobligatorios() {
//        if (edtcodigoverificacion.length() !=0) {
//            verificarcuenta();
//            insertartoken();
//        }
//        else{
//            Toast.makeText(getApplicationContext(), "TODOS LOS CAMPOS SON OBLIGATORIOS, POR FAVOR INGRESE UN CÓDIGO", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    @Override
    public void onBackPressed() {

    }

}

