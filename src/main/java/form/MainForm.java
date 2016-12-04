package form;

import uskov.mail.client.MessageInfo;
import uskov.mail.client.Settings;
import uskov.mail.client.SettingsReader;
import uskov.mail.client.SmtpClient;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by Dmitry on 04.12.2016.
 */
public class MainForm {
    private JPanel mainForm;
    private JTextArea messageBodyTextField;
    private JTextField themeTextField;
    private JPanel loginPanel;
    private JSpinner portSpinner;
    private JTextField hostTextField;
    private JLabel portLabel;
    private JTextField fromMailTextField;
    private JPasswordField passwordTextField;
    private JTextField mailTotextField;
    private JButton sendButton;
    private JLabel infoLabel;

    public MainForm() {
        messageBodyTextField.setFont(messageBodyTextField.getFont().deriveFont(12f));
        try {
            Settings settings = SettingsReader.readSettings();
            initSettings(settings);
        } catch (IOException e) {

        }

        sendButton.addActionListener(e->{
            Settings settings = readSettings();
            if(!settings.isValid()){
                info("incorrect settings!");
            }
            try {
                SettingsReader.saveSettings(settings);
            } catch (IOException e1) {

            }
            String message = messageBodyTextField.getText();
            String theme = themeTextField.getText();
            String mailTo = mailTotextField.getText();
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setBody(message);
            messageInfo.setTheme(theme);
            messageInfo.setMailTo(mailTo);
            messageInfo.setSettings(settings);
            new Thread(()->{
                try {
                    sendButton.setEnabled(false);
                    info("Busy");
                    SmtpClient.sendMessage(messageInfo);
                    messageInfo.setStatus("GOOD");
                    info("Ready");
                } catch (MessagingException e1) {
                    messageInfo.setStatus("ERROR");
                    info("Error of sending message");
                } finally {
                    sendButton.setEnabled(true);
                }
            }).start();
        });
    }

    private void info(String message) {
        infoLabel.setText(message);
    }

    private void initSettings(Settings settings) {
        if(settings == null) {
            return;
        }
        hostTextField.setText(settings.getHost());
        fromMailTextField.setText(settings.getMailFrom());
        passwordTextField.setText(settings.getPassword());
        portSpinner.setValue(Integer.valueOf(settings.getPort()));
    }

    private Settings readSettings(){
        Settings settings = new Settings();
        settings.setHost(hostTextField.getText());
        settings.setMailFrom(fromMailTextField.getText());
        settings.setPassword(passwordTextField.getText());
        settings.setPort(portSpinner.getValue().toString());
        return settings;
    }

    public static void main(String[] args) {
        setLookAndFeelClassName(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame("SMTP/SSL");
        frame.setContentPane(new MainForm().mainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void setLookAndFeelClassName(String systemLookAndFeelClassName) {
        try {
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


}
