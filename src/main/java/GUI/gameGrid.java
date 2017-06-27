package GUI;

import Games.*;
import ServerClient.Http_Client;
import ServerClient.Http_Server;
import Users.NoMuchMoney;
import Users.UserNotExists;
import Users.UserNotLoggedIn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nofar on 22/06/2017.
 */
public class gameGrid {
    public JPanel gameGridView;
    private JPanel afterStart;
    private JLabel chipsNum;
    private JLabel gameNumber;
    private JLabel potNum;
    private JTextPane gameMessagesTextPane;
    private JButton allInButton;
    private JButton betRaiseButton;
    private JButton checkButton;
    private JButton foldButton;
    private JTextField amountBetField;
    private JButton exitGameButton;
    private JButton startGameButton;
    private JLabel userName;
    private JTextPane allPlayeraInTheGameTextPane;
    private JTextPane tableCardsTextPane;
    private JPanel itsYourTurnJPannel;
    private JLabel userNumberJLabel;
    private JTextPane yourCardsJTextPannel;
    private JLabel waitingLable;
    private JFrame thisFrame;


    public gameGrid(String username, int gameNum,JFrame thisFame, boolean isTheCreator) {
        thisFrame= thisFame;
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTheCreator) {
                    System.out.println("gamenum2=" + gameNum);
                    gameNumber.setText("" + gameNum);
                    userName.setText(username);

                    chipsNum.setText("0~0");
                    thisFame.setSize(500, 530);
                    afterStart.setVisible(true);
                    startGameButton.setVisible(false);
                    waitingLable.setVisible(false);
                    try {

                        Http_Client.startGame(username, gameNum);
                        TimeUnit.SECONDS.sleep(15);
                        System.out.println("nom of yoooooni"+Http_Client.getPlayersNum(gameNum+""));
                        activate_get_game_state(gameNum,"");
                    } catch (UserNotLoggedIn userNotLoggedIn) {
                        userNotLoggedIn.printStackTrace();
                    } catch (UserNotExists userNotExists) {
                        userNotExists.printStackTrace();
                    } catch (NotYourTurn notYourTurn) {
                        notYourTurn.printStackTrace();
                    } catch (NoMuchMoney noMuchMoney) {
                        noMuchMoney.printStackTrace();
                    } catch (NotLegalAmount notLegalAmount) {
                        notLegalAmount.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    startGameButton.setVisible(false);
                    gameNumber.setText("" + gameNum);
                    userName.setText(username);
                }
            }
        });
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.fold(username,gameNum);
                    //activate_someone_folded(int game,String foldedPlayer) ??
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.leaveGame(gameNum,username);
                    thisFame.setVisible(false);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        allInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.allIn(username,gameNum);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        betRaiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //if(amountBetField.getText() != "")
                        Http_Client.raise(username,gameNum, Integer.parseInt(amountBetField.getText()));
                    //else
                    //    Http_Client.bet
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Http_Client.check(username, gameNum);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void activate_get_game_content(int game,String content){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the content is: " +content);
    }

    public void activate_get_cards(int game,ArrayList<Card> cards){
        String poop="";
        poop=poop+" my cards : \n";
        for(int i=0;i<cards.size()-1;i++)
            poop=poop+"number:"+cards.get(i).getnum()+"suit:"+cards.get(i).getsuit() +"color:"+cards.get(i).getcolor()+",\n";
        poop=poop+"number:"+cards.get(cards.size()-1).getnum()+"suit:"
                +cards.get(cards.size()-1).getsuit()+"color:"+cards.get(cards.size()-1).getcolor();

        yourCardsJTextPannel.setText(poop);
        JOptionPane.showMessageDialog(thisFrame,""+poop);
    }

    private ArrayList<Player> allPlayers;
    private ArrayList<Player> notFoldPlayers;
    private ArrayList<Card> tableCards;
    private ArrayList<Card> specificPlayerCards;
    private int currentMinimumBet;
    private int pot;
    // changes: card , gamestate , new class
    public void activate_get_game_state(int game,String s){// convert s = game state.
        Gson gson=new GsonBuilder().create();
        GameState gs= gson.fromJson(s,GameState.class);
        String poop = "";
        //allPlayers = gs.getap();
        //int numOp= allPlayers.size();
        //System.out.println("num of players: "+numOp);
        poop=poop+"all players are : \n";
        for(int i=0;i<gs.getap().size()-1;i++)
            poop=poop+gs.getap().get(i).getName()+",\n";
        poop=poop+gs.getap().get(gs.getap().size()-1).getName();
        allPlayeraInTheGameTextPane.setText(poop);

        poop="";
        poop=poop+"\n not fold players are : \n";
        for(int i=0;i<gs.getnfp().size()-1;i++)
            poop=poop+gs.getnfp().get(i).getName()+",";
        poop=poop+gs.getnfp().get(gs.getnfp().size()-1).getName(); //not displayed yet

        poop="";
        poop=poop+"The table cards are : \n";
        for(int i=0;i<gs.gettc().size()-1;i++)
            poop=poop+"number:"+gs.gettc().get(i).getnum()+"suit:"+gs.gettc().get(i).getsuit()
                    +"color:"+gs.gettc().get(i).getcolor()+",";
        poop=poop+"number:"+gs.gettc().get(gs.gettc().size()-1).getnum()+"suit:"
                +gs.gettc().get(gs.gettc().size()-1).getsuit()+"color:"+gs.gettc().get(gs.gettc().size()-1).getcolor();
        tableCardsTextPane.setText(poop);

        poop="";
        poop=poop+"\n specific player cards are : \n";
        for(int i=0;i<gs.getspc().size()-1;i++)
            poop=poop+"number:"+gs.getspc().get(i).getnum()+"suit:"+gs.getspc().get(i).getsuit()
                    +"color:"+gs.getspc().get(i).getcolor()+",";
        poop=poop+"number:"+gs.getspc().get(gs.getspc().size()-1).getnum()+"suit:"
                +gs.getspc().get(gs.getspc().size()-1).getsuit()+"color:"+gs.getspc().get(gs.getspc().size()-1).getcolor();

        poop=poop+"\n current member number is : \n";
        poop=poop+""+gs.getcmb();
        userNumberJLabel.setText(""+gs.getcmb());

        poop=poop+"\n pot size is : \n";
        poop=poop+""+gs.getpot();
        potNum.setText(""+gs.getpot());

        gameMessagesTextPane.setText(gameMessagesTextPane.getText() + "\n game is starting");

        afterStart.setVisible(true);
        JOptionPane.showMessageDialog(thisFrame,""+poop);

    }
    public void activate_someone_folded(int game,String foldedPlayer){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +foldedPlayer+" folded. ");
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+"\nthe player: " +foldedPlayer+" folded. ");
    }

    public void activate_someone_checked(int game,String checkPlayer){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +checkPlayer+" checked. ");
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+"\nthe player: " +checkPlayer+" checked. ");
    }

    public void activate_someone_allin(int game,String allinPlayer){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +allinPlayer+" all-in'd. ");
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+"\nthe player: " +allinPlayer+" all-in'd.");
        //        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+);
    }

    public void activate_winner(int game,String winnerPlayer){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +winnerPlayer+" fucking won!!! ");
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+"\nIn game "+ game +" the player: " +winnerPlayer+" fucking won!!! ");
    }

    public void activate_someone_raised(int game,String raisedPlayer,int amount){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +raisedPlayer+" raised by: "+amount);
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+"\nthe player: " +raisedPlayer+" raised by: "+amount);
    }

    public void activate_someone_bet(int game,String betPlayer,int amount){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +betPlayer+" bet by: "+amount);
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+"\nthe player: "+ betPlayer+" bet by: "+amount);
    }

    public void activate_someone_call(int game,String callPlayer,int amount){
        JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +callPlayer+" called by: "+amount);
        gameMessagesTextPane.setText(gameMessagesTextPane.getText()+" the player: " +callPlayer+" called by: "+amount);
    }
}
