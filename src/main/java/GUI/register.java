package GUI;

import ServerClient.Http_Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nofar on 20/05/2017.
 */
public class register {
    private JButton backButton;
    private JTextField user;
    private JTextField email;
    private JTextField password;
    private JButton registerButton;
    public JPanel registerView;
    private JTextField wallet;

    static public JFrame registerFrame;

    public register() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("new user registered:   userName: " + user.getText()+ ",  password: "+ password.getText() + "  e-mail:" + email.getText());
                try {
                    Http_Client.register(user.getText(), password.getText(), email.getText(), Integer.parseInt(wallet.getText()));
                    System.out.println("new user registered:   userName: " + user.getText()+ ",  password: "+ password.getText() + "  e-mail:" + email.getText());
                }
                catch(Exception es) {

                }
                JFrame homePageFrame = new JFrame("homePage");
                homePageFrame.setContentPane(new homePage(user.getText(),homePageFrame).homePageView);
                homePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                homePageFrame.pack();
                homePageFrame.setVisible(true);
                registerFrame.setVisible(false);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.loginFrame.setVisible(true);
                registerFrame.dispose();
            }
        });
    }
}


