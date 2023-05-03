package com.example.technopolis_mobileapp;

import android.os.AsyncTask;
import android.util.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "EmailSender";
    private Exception exception;
    private String email;
    private String subject;
    private String Emailmessage;

    public EmailSender(String email, String subject, String Emailmessage) {
        this.email = email;
        this.subject = subject;
        this.Emailmessage = Emailmessage;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // create JavaMail session
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("technopolisprojectdut@gmail.com", "ddfkmymjfpfcfsam");
                        }
                    });

            // create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("technopolisprojectdut@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(Emailmessage);

            // send email message
            Transport.send(message);
            Log.i("EmailSender", "Email message sent successfully");
        } catch (MessagingException e) {
            Log.e("EmailSender", "Failed to send email message: " + e.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (exception == null) {
            Log.d("EmailSender", "Email sent successfully");
        } else {
            Log.e("EmailSender", "Error sending email", exception);
        }
    }
}
