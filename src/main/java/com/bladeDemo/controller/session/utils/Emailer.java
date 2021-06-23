package com.bladeDemo.controller.session.utils;

import com.bladeDemo.utils.SecretKeyGenerator;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Emailer {
    private final static Logger logger = LoggerFactory.getLogger(Emailer.class);

    private static class Conf{
        private String sender = "nemalovetest@gmail.com";
        private String host = "smtp.gmail.com";
        private Integer port = 587;
        private String password = "Nemalove63";
        private String username = "nemalovetest@gmail.com";

    }

    private static Email buildMail(String subject, String recipient, String htmlBody){
        Conf conf = new Conf();
        Email email = EmailBuilder.startingBlank()
                .from(conf.sender)
                .to(recipient)
                .withSubject(subject).withHTMLText(htmlBody)
                .buildEmail();
        return email;
    }

    private static void sendMail(Email email){
        Conf conf = new Conf();
        MailerBuilder.withSMTPServer(conf.host, conf.port, conf.username, conf.password)
                .withTransportStrategy(TransportStrategy.SMTP_TLS).buildMailer().sendMail(email);
    }

    private static void welcomeEmail(String userEmail){
        String htmlbody = "<h1>Welcome To this blade Demo App</h1>";
        logger.info("Sending mail to " + userEmail);
        Email email = buildMail("SignUp Complete", userEmail, htmlbody);
        logger.info(email.toString());
        sendMail(email);
        logger.info("Mail sent successfully");
    }

    public static void emailVerification(String userName, String userEmail, String hash){

        logger.info("Sending Email to: "+userEmail);

        String url = "http://localhost:9000/session/verify/"+hash;
        String htmlbody = String.format("<p>Hello verify your email by clicking on this link <a " +
                "href='%s'> " +
                "%s" +
                " </a> </p>", url, url);
        String subject = "Verify Email";

        Email email = buildMail(subject , userEmail, htmlbody);

        sendMail(email);

    }

    private static void resetPasswordMail(String userEmail, String hash){
        String url = "http://localhost:9000/session/verify-password/"+hash;

        String htmlbody = String.format("<p>Hello To reset your password follow this link <a " +
                "href='%s'> " +
                "%s" +
                " </a> </p>", url, url);

        String subject = "Reset Password";

        Email email = buildMail(subject, userEmail,htmlbody);

        sendMail(email);
    }

    public static String verifyEmailMailer(String email){
        String token = SecretKeyGenerator.getSecretKey(email);
        emailVerification(null, email, token);

        return token;
    }
    public static String forgotPasswordMailer(String email){
        logger.info("Sending Email to: "+email);
        String token = SecretKeyGenerator.getSecretKey(email);
        resetPasswordMail(email, token);

        return token;
    }
}
