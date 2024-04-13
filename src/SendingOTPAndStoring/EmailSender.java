package SendingOTPAndStoring;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static void sendOTP(String generatedOTP, String enteredEmail) {
        // Gmail account credentials
        String senderEmail = "prajwal0123456acharya@gmail.com";
        String senderPassword = "syoe sjid aelz apes"; // Use your Gmail password
        //PRAJWAL1234567890

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with the Gmail SMTP server
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        String htmlContent = "<html><body>"
                + "<h1>Your OTP is : </h1>"
                + "<table border='1' cellspacing='0' cellpadding='10' style='border-collapse: collapse; border: 1px solid black; background-color: white; display: inline-block;'>"
                + "<tr><td style='background-color: white; border: 1px solid black; padding: 10px;'><strong>" + generatedOTP + "</strong></td></tr>"
                + "</table>"
                + "</body></html>";

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);


            // Set the sender and recipient email addresses
            message.setFrom(new InternetAddress(senderEmail,"OTP Sending Final Project"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(enteredEmail));

            // Set the subject and text of the email
            message.setSubject("OTP Generation Project");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(enteredEmail));

            message.setContent(htmlContent, "text/html");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully to " + enteredEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
