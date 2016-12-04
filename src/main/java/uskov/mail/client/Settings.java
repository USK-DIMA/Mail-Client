package uskov.mail.client;

/**
 * Created by Dmitry on 04.12.2016.
 */
public class Settings {
    private String host;
    private String port;
    private String mailFrom;
    private String password;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return host!=null &&  port!=null && mailFrom!=null && password!=null;
    }
}
