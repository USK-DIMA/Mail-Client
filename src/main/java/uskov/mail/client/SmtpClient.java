package uskov.mail.client;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Dmitry on 04.12.2016.
 */
public class SmtpClient {

    public static void sendMessage(final MessageInfo messageInfo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", messageInfo.getSettings().getHost());
        props.put("mail.smtp.socketFactory.port", messageInfo.getSettings().getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", messageInfo.getSettings().getPort());
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(messageInfo.getSettings().getMailFrom(), messageInfo.getSettings().getPassword());
                    }
                });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(messageInfo.getSettings().getMailFrom()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(messageInfo.getMailTo()));
        message.setSubject(messageInfo.getTheme());
        message.setText(messageInfo.getBody());

        Transport.send(message);
    }

}
