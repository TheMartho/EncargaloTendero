package mx.com.encargalo.tendero;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Util.Util;
import mx.com.encargalo.tendero.Util.is_cls_ActivitysInicioSesion;
import mx.com.encargalo.tendero.Util.is_cls_Constants_InicioSesion;
import mx.com.encargalo.tendero.Util.is_cls_Consulta_Disponibilidad_Red;
import mx.com.encargalo.tendero.Util.is_cls_Session_Inicio_Sesion;
import mx.com.encargalo.tendero.Inicio_sesion.MainActivity;

public class activity_is_actiniciarsesionprincipal extends AppCompatActivity {
    JsonObjectRequest jsonObjectRequest;

    RequestQueue request;
    Button btniniciarfb;
    Button btniniciargmail;
    Button btncrearcuenta;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 9001;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    String token;
    CallbackManager mCallbackManager;
    String id;
    String device_token, device_UDID;
    String TAG = "FragmentLogin";
    String keyHash = "";
    String emailFB;
    String emailGmail;
    String Fullname;
    String docPerson;
    String idusu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_actiniciarsesionprincipal);
        request = Volley.newRequestQueue(this);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

//        Init Google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();

//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    getPackageName(),                  //Insert your own package name.
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//
//                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }


        btniniciarfb=(Button)findViewById(R.id.is_ispbtnfacebook);
        btniniciarfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(activity_is_actiniciarsesionprincipal.this, Arrays.asList("public_profile", "email"));

                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        handleFacebookAccessToken2(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(activity_is_actiniciarsesionprincipal.this,"Error, su cuenta es de gmail", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(activity_is_actiniciarsesionprincipal.this,"Error, su cuenta es de gmail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btncrearcuenta=(Button)findViewById(R.id.btn_crear_cuenta);
        btncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redireccionar a activity_is_actcrearcuenta.xml
                Intent intent = new Intent(view.getContext(), activity_is_actcrearunacuenta.class);
                startActivity(intent);
            }
        });

        btniniciargmail=(Button)findViewById(R.id.btn_google_singin);
        btniniciargmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });


        //TODO FACEBOOK
        SharedPreferences prfs = getSharedPreferences("Credencial_Global_usuario", Context.MODE_PRIVATE);
        boolean is_login = prfs.getBoolean("is_login", false);

        if (is_login){
            is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad();
        }


        //Ejemplo para eliminar las preferencia que se puede usar cuando cerramos session
        //SharedPreferences preferences = getSharedPreferences("login", 0);
        //preferences.edit().remove("is_login").commit();


    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        showProgressDialog();
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Cargando");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            hideProgressDialog();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with FireBase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(activity_is_actiniciarsesionprincipal.this,"Error, su cuenta es de gmail", Toast.LENGTH_SHORT).show();
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                        FirebaseUser user = mAuth.getCurrentUser();

                        assert user != null;
                        String personName = user.getDisplayName();

                        if (personName.contains(" ")) {
                            personName = personName.substring(0, personName.indexOf(" "));
                        }
                        String email = user.getEmail();
                        String[] userName = user.getEmail().split("@");

                        if (isNew) {
                            hideProgressDialog();
                            //ShowReferDialog(user.getUid(), userName[0] + id, personName, email, user.getPhotoUrl().toString(), "gmail");
                        } else
                            UserSignUpWithSocialMedia(user.getUid(), "", userName[0] + id, user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString(), "1", "gmail", "idDocumentoPersona");
                    } else {
                        hideProgressDialog();

                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException | FirebaseAuthUserCollisionException invalidEmail) {

                            //setSnackBar(invalidEmail.getMessage(), getString(R.string.ok));
                        } catch (Exception e) {
                            e.printStackTrace();
                            //setSnackBar(e.getMessage(), getString(R.string.ok));
                        }
                    }

                });
    }

    public void UserSignUpWithSocialMedia(final String authId, final String fCode, final String referCode, final String name, final String email, final String profile, final String rolusuario, final String type, final String idDocumentopersona) {
        HashMap<String, String> params = new HashMap<>();
        //TODO FACEBOOK
        emailGmail= email; // CAMBIAR por la variable email
        params.put(is_cls_Constants_InicioSesion.email, email);
        params.put(is_cls_Constants_InicioSesion.name, name);
        params.put(is_cls_Constants_InicioSesion.PROFILE, profile);
        params.put(is_cls_Constants_InicioSesion.rolusuario, rolusuario);

        String url= Util.RUTA+"c_existencia_usuario_inicio_sesion.php?sp_usuCorreo="+emailGmail;
        url=url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response ) {
                {

                    try {
                        JSONArray jsonArray = response.getJSONArray("usuario");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject usuario = jsonArray.getJSONObject(i);
                            params.put(is_cls_Constants_InicioSesion.idDocumentoPersona, usuario.getString("idDocumentoPersona"));
                            docPerson = usuario.getString("idDocumentoPersona");
                            idusu   = usuario.getString("idUsuario");

                            //String valor=usuario.getString("usuToken");

                            String url= Util.RUTA+"c_estado_usuario_inicio_sesion.php?sp_codvCorreo="+emailGmail;
                            url=url.replace(" ","%20");

                            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response ) {

                                    {
                                        SharedPreferences creduser=getSharedPreferences("Credencial_Global_usuario",0);
                                        SharedPreferences.Editor editocred=creduser.edit();
                                        editocred.putString("CredId",docPerson);
                                        editocred.putString("CredIdUsu",idusu);
                                        editocred.commit();

//                                        SharedPreferences preferences = getSharedPreferences("", 0);
//                                        preferences.edit().remove("Credencial_Global_usuario").commit();
//

                                        SharedPreferences sessionpreferences = getSharedPreferences("Credencial_Global_usuario", 0);
                                        sessionpreferences.edit().remove("is_login").commit();
                                        SharedPreferences.Editor editor = sessionpreferences.edit();
                                        editor.putBoolean("is_login", true);
                                        editor.apply();

                                        //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);

                                        is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad(params);

                                    }



                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                    is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);
                                    //startActivity(intent);

                                }
                            });
                            request.add(jsonObjectRequest);
                            //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);

                            // is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad(params);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
                //startActivity(intent);

            }
        });
        request.add(jsonObjectRequest);





        //  Activitys.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
    }

    public void UserSignUpWithSocialMediaFB(final String authId, final String fCode, final String referCode, final String name, final String email, final String profile, final String rolusuario, final String type, final String idDocumentopersona) {
        HashMap<String, String> params = new HashMap<>();
        //TODO FACEBOOK
        emailGmail= email; // CAMBIAR por la variable email
        params.put(is_cls_Constants_InicioSesion.email, email);
        params.put(is_cls_Constants_InicioSesion.name, Fullname);
        params.put(is_cls_Constants_InicioSesion.PROFILE, profile);
        params.put(is_cls_Constants_InicioSesion.rolusuario, rolusuario);

        String url= Util.RUTA+"c_existencia_usuario_inicio_sesion.php?sp_usuCorreo="+emailGmail;
        url=url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response ) {
                {

                    try {
                        JSONArray jsonArray = response.getJSONArray("usuario");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject usuario = jsonArray.getJSONObject(i);
                            params.put(is_cls_Constants_InicioSesion.idDocumentoPersona, usuario.getString("idDocumentoPersona"));
                            docPerson = usuario.getString("idDocumentoPersona");
                            idusu   = usuario.getString("idUsuario");
                            //String valor=usuario.getString("usuToken");

                            String url= Util.RUTA+"c_estado_usuario_inicio_sesion.php?sp_codvCorreo="+emailGmail;
                            url=url.replace(" ","%20");

                            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response ) {

                                    {
                                        SharedPreferences creduser=getSharedPreferences("Credencial_Global_usuario",0);
                                        SharedPreferences.Editor editocred=creduser.edit();
                                        editocred.putString("CredId",docPerson);
                                        editocred.putString("CredIdUsu",idusu);
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

                                        //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);

                                        is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad(params);

                                    }



                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {


                                    is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);
                                    //startActivity(intent);

                                }
                            });
                            request.add(jsonObjectRequest);
                            //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);

                            // is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad(params);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
                //startActivity(intent);

            }
        });
        request.add(jsonObjectRequest);





        //  Activitys.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
    }


    //TODO FACEBOOK
    private void handleFacebookAccessToken2(AccessToken token) {
        showProgressDialog();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    try {
                        if (task.isSuccessful()) {
                            //Sign in success, update UI with the signed-in user's information
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String personName = user.getDisplayName();
                            if (personName.contains(" ")) {
                                String  fname = personName.substring(0, personName.lastIndexOf(" ") + 1);
                                String     lname = personName.substring(personName.lastIndexOf(" ") + 1);
                                Fullname = fname+lname;

                                //personName = personName.substring(0, personName.indexOf(" "));
                            }
                            String referCode = "";

                            if (user.getEmail() != null) {
                                String[] userName = user.getEmail().split("@");
                                referCode = userName[0];
                            } else {
                                referCode = user.getPhoneNumber();
                            }
                            if (isNew) {
                                hideProgressDialog();
                                //ShowReferDialog(user.getUid(), referCode + id, personName, "" + user.getEmail(), user.getPhotoUrl().toString(), "fb");
                            } else
                                UserSignUpWithSocialMediaFB(user.getUid(), "", referCode + id, Fullname, "" + user.getEmail(), user.getPhotoUrl().toString(), "1","fb", "idDocumentoPersona");
                        } else {
                            // If sign in fails, display a message to the user.

                            LoginManager.getInstance().logOut();
                            hideProgressDialog();
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException | FirebaseAuthUserCollisionException invalidEmail)

                            {
                                // setSnackBar(invalidEmail.getMessage(), getString(R.string.ok));
                            } catch (Exception e) {
                                e.printStackTrace();

                                //setSnackBar(e.getMessage(), getString(R.string.ok));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
    }



    public void setSnackBarStatus() {
        final Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), getString(R.string.account_deactivate), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.ok), view -> {

            is_cls_Session_Inicio_Sesion.clearUserSession(this);
            mAuth.signOut();
            LoginManager.getInstance().logOut();

        });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
        hideProgressDialog();
    }

    public void setSnackBar(String message, String action) {
        final Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(action, view -> {
            if (is_cls_Consulta_Disponibilidad_Red.isNetworkAvailable(this)) {
                snackbar.dismiss();
            } else {
                snackbar.show();
            }
        });
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }



    //    private void wsInicioSesionFB(){
//        String url= "http://192.168.101.85/API/c_existencia_usuario.php?sp_usuCorreo="+emailFB;
//        url=url.replace(" ","%20");
//        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response ) {
//                Intent verif = new Intent(activity_is_actiniciarsesionprincipal.this, MainActivity.class);
//                startActivity(verif);
//
//
//
//                // Navigation.findNavController(Activity getContext()).navigate(R.id.nav_mi_tienda);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//
//                Intent intent = new Intent(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class);
//
//                startActivity(intent);
//                //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
//                // Activitys.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
//            }
//        });
//        request.add(jsonObjectRequest);
//
//    }
    public void consultarestadocuentagmail(){
        String url= Util.RUTA+"c_estado_usuario_inicio_sesion.php?sp_codvCorreo="+emailGmail;
        url=url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response ) {

                {



                    //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actverificacioncodigo.class).muestraActividad(params);

//                         is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, MainActivity.class).muestraActividad(params);

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //is_cls_ActivitysInicioSesion.getSingleton(activity_is_actiniciarsesionprincipal.this, activity_is_actcrearunacuenta.class).muestraActividad(params);
                //startActivity(intent);

            }
        });
        request.add(jsonObjectRequest);
    }


}