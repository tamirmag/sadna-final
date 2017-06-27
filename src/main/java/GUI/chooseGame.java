package GUI;

import javax.swing.*;

import ServerClient.Http_Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nofar on 20/05/2017.
 */
public class chooseGame {
    public JPanel chooseGameView;
    private JButton exitButton;
    private JButton chooseButton;
    String[] j= {"a","b","c","d"};
    private JComboBox<String> comboListOfGames;//=new JComboBox(j);
    public JPanel spectateOrJoin;
    private JButton filterButton;
    private JComboBox findBy;
    private JRadioButton findActiveGamesByRadioButton;
    private JTextField textField1;
    private JRadioButton bbb;
    private JLabel n1;

    private JLabel n2;

    private ButtonGroup btnGroup = new ButtonGroup();



    public chooseGame(String username, String[] gamesNum, JFrame thisFrame, JFrame prevFrame, String action){
        //for (int i=0; i<gamesNum.length;i++)
        //    System.out.println(gamesNum[i]);
        //comboListOfGames.setModel(new javax.swing.DefaultComboBoxModel(gamesNum));

        comboListOfGames.setModel(new javax.swing.DefaultComboBoxModel(gamesNum));
        //comboListOfGames=new JComboBox<>(a);
        //comboListOfGames.setSelectedIndex(0);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
                prevFrame.setVisible(true);
            }
        });

        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer gameNum=Integer.parseInt((String)comboListOfGames.getSelectedItem());
                System.out.println("chosen num:"+gameNum.intValue());
                int gameNumID=gameNum.intValue();
                if(action.equals("joinExistingGame")) {
                    try {
                        Http_Client.joinGame(gameNum,username);
                        //JFrame fr=Http_Client.getFrameFromGame(""+gameNumID);
                        //fr.setVisible(true);
                        JFrame frame = new JFrame("game");
                        frame.setContentPane(new gameGrid(username, gameNum, frame, false).gameGridView);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                        thisFrame.setVisible(false);

                        //need to move the user to the game window
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    if(action.equals("spectateGame")){
                        try {
                            Http_Client.spectateGame(gameNum,username);
                            //need to move the user to spectating window
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        // showGameList.add(new JButton("Hello Button"));
//        n1= new JLabel("AAA");
//        showGameList.add(n1);
        //showGameList.add(new JLabel("BBB"));
//        gamesList.add("nof3", new JButton("Nof3 Button"));

//        setLayout(new java.awt.GridLayout(4, 4));
//        for (int i = 0; i < 4; ++i) {
//            JButton b = new JButton(String.valueOf(i));
//            b.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    //...
//                }
//            });
//            showGameList.add(b);
//        }

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterBy= (String) findBy.getSelectedItem();
                try {
                    switch (filterBy){
                        case "find active games by pot size":
                    }
                    Http_Client.findActiveGamesByPotSize(0,username);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }





}
