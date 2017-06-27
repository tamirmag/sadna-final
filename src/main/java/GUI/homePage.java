package GUI;

import ServerClient.Http_Client;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Nofar on 20/05/2017.
 */
public class homePage {
    private JButton exitButton;
    private JButton createNewGameButton;
    private JButton editProfileButton;
    private JButton joinExistingGameButton;
    private JButton spectateGameButton;
    public JPanel homePageView;


    private void createGameList(JFrame frm)
    {
        frm.add(new JButton("AAA"));
        frm.add(new JButton("BBB"));
    }

    public homePage(String username,JFrame thisFrame) {

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.logout(username);
                    System.exit(0);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        spectateGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createGameList(frame);

                /*String[] gamesL = {"one", "two"};
                JFrame frame= new JFrame("spectateGame");
                frame.setContentPane(new chooseGame(username, gamesL,frame,thisFrame,"spectateGame").chooseGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);*/
                String[] gamesL = {"one", "two"};
                try {
                    ArrayList<Integer> gamesNum= Http_Client.findSpectatableGames(username);
                    /*for (int i = 0; i < gamesNum.size(); i++) {
                        System.out.println(""+gamesNum);
                    }
                    System.out.println("1");
                    int numOfGames=gamesNum.size(); //falls HERE!!!!
                    System.out.println("numOfGames:  "+numOfGames);*/
                    if(gamesNum.isEmpty()) {
                        String[] games = new String[gamesNum.size()];
                        String temp = "";
                        for (int i = 0; i < gamesNum.size(); i++) {
                            games[i] = "" + gamesNum.get(i);
                        }
                        gamesL=games;

                    }
                    else{
                        System.out.println("no games to spectate!");
                        JOptionPane.showMessageDialog(thisFrame,"no games to spectate!");
                    }

                } catch (Exception e1) {
                    System.out.println("didnt sucess (even with 0)");
                    e1.printStackTrace();
                }
                //when it works- move the lines below to the end of if in the the "try"!!
                JFrame frame= new JFrame("spectateGame");
                frame.setContentPane(new chooseGame(username, gamesL,frame,thisFrame,"spectateGame").chooseGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        joinExistingGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] gamesL = {"one", "two"};
                try {
                    ArrayList<Integer> gamesNum= Http_Client.findActiveGamesByLeague(username);
                    int numOfGames=gamesNum.size();
                    System.out.println("numOfGames:  "+numOfGames);
                    if(numOfGames>0) {
                        String[] games = new String[numOfGames];
                        String temp = "";
                        for (int i = 0; i < gamesNum.size(); i++) {
                            games[i] = "" + gamesNum.get(i);
                        }
                        gamesL=games;

                    }
                    else{
                        System.out.println("no games to spectate!");
                        JOptionPane.showMessageDialog(thisFrame,"no games to spectate!");
                    }

                } catch (Exception e1) {
                    System.out.println("didnt sucess (even with 0)");
                    e1.printStackTrace();
                }
                //when it works- move the lines below to the end of the if in the "try"!!
                JFrame frame= new JFrame("joinGame");
                frame.setContentPane(new chooseGame(username, gamesL,frame,thisFrame,"joinExistingGame").chooseGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);


            }
        });
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("editProfile");
                frame.setContentPane(new editProfile(username,frame).editProfileView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
        createNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("createNewGame");
                frame.setContentPane(new createNewGame(username,frame,thisFrame).createNewGameView);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
