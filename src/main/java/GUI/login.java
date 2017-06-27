package GUI;

import ServerClient.Http_Client;
import ServiceLayer.ServiceUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

/**
 * Created by Nofar on 20/05/2017.
 */
public class login {
    public JPanel loginView;
    private JTextField password;
    private JTextField user;
    private JButton logInButton;
    private JButton newUserRegisterNowButton;
    private JLabel userName;

    static public JFrame loginFrame;

    public login() {
    logInButton.addActionListener(new loginClicked());
    newUserRegisterNowButton.addActionListener(new registerClicked());
    }

    private class registerClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            register.registerFrame = new JFrame("register");
            register.registerFrame.setContentPane(new register().registerView);
            register.registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            register.registerFrame.pack();
            register.registerFrame.setVisible(true);

            loginFrame.setVisible(false);
            password.setText("");
            user.setText("");
        }

    }

    private class loginClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ServiceUser user1 = Http_Client.login(user.getText(), password.getText());
                if (user1 !=null){
                    JFrame homePageFrame = new JFrame("homePage");
                    homePageFrame.setContentPane(new homePage(user.getText(),homePageFrame).homePageView);
                    homePageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    homePageFrame.pack();
                    homePageFrame.setVisible(true);
                    loginFrame.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(loginFrame,"Incorrect user or password");
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        loginFrame = new JFrame("login");
        loginFrame.setContentPane(new login().loginView);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);

    }
}

