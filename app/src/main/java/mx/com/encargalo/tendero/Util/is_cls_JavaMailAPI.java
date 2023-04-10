package mx.com.encargalo.tendero.Util;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class is_cls_JavaMailAPI extends AsyncTask<Void, Void, Void> {

    private Context context;

    private Session session;
    private String email, subject, message;

    public is_cls_JavaMailAPI(Response.Listener<JSONObject> context, String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(is_cls_Constants_InicioSesion.EMAILNOTIFICACIONES, is_cls_Constants_InicioSesion.PASSWORD);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(is_cls_Constants_InicioSesion.EMAILNOTIFICACIONES));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;

    }
}
