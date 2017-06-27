package GUI;

import ServerClient.Http_Client;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Games.Card;
import Games.GameState;
import Games.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Nofar on 04/06/2017.
 */
public class game {
    private JButton betCheckButton;
    private JButton checkButton;
    private JButton foldButton;
    private JButton allInButton;
    private JButton exitGameButton;
    public JPanel gameView;
    private JFrame thisFrame; //the current frame

    public int a(){
    	return 0;
    }
    
    public game(/*int gamenum, int minPlayers, int maxPlayers,JFrame thisFrame,JFrame prevFrame*/) {
    	//this.thisFrame=thisFrame; //the current frame
        System.out.println(Http_Client.getFrameFromGame(""+/*gamenum*/0).toString());
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    thisFrame.setVisible(false);
                    //prevFrame.setVisible(true);
                    /*
                    int currentNumOfPlayers=Http_Client.getPlayersNum(gamenum+"");
                    if(currentNumOfPlayers >= minPlayers&& currentNumOfPlayers <= maxPlayers){

                    }*/
                    //Http_Client.terminateGame(gamenum);

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
		poop=poop+"number:"+cards.get(i).getnum()+"suit:"+cards.get(i).getsuit()
			+"color:"+cards.get(i).getcolor()+",";
		poop=poop+"number:"+cards.get(cards.size()-1).getnum()+"suit:"
			+cards.get(cards.size()-1).getsuit()+"color:"+cards.get(cards.size()-1).getcolor();


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
	poop=poop+"first of all, fuck you !!! now, \n all players are : \n";
	for(int i=0;i<gs.getap().size()-1;i++)
		poop=poop+gs.getap().get(i).getName()+",";
	poop=poop+gs.getap().get(gs.getap().size()-1).getName();
	
	poop=poop+"\n not fold players are : \n";
	for(int i=0;i<gs.getnfp().size()-1;i++)
		poop=poop+gs.getnfp().get(i).getName()+",";
	poop=poop+gs.getnfp().get(gs.getnfp().size()-1).getName();
	
	poop=poop+"\n current cards are : \n";
	for(int i=0;i<gs.gettc().size()-1;i++)
		poop=poop+"number:"+gs.gettc().get(i).getnum()+"suit:"+gs.gettc().get(i).getsuit()
		+"color:"+gs.gettc().get(i).getcolor()+",";
	poop=poop+"number:"+gs.gettc().get(gs.gettc().size()-1).getnum()+"suit:"
		+gs.gettc().get(gs.gettc().size()-1).getsuit()+"color:"+gs.gettc().get(gs.gettc().size()-1).getcolor();
	
	poop=poop+"\n specific player cards are : \n";
	for(int i=0;i<gs.getspc().size()-1;i++)
		poop=poop+"number:"+gs.getspc().get(i).getnum()+"suit:"+gs.getspc().get(i).getsuit()
		+"color:"+gs.getspc().get(i).getcolor()+",";
	poop=poop+"number:"+gs.getspc().get(gs.getspc().size()-1).getnum()+"suit:"
		+gs.getspc().get(gs.getspc().size()-1).getsuit()+"color:"+gs.getspc().get(gs.getspc().size()-1).getcolor();
	
	poop=poop+"\n current member number is : \n";
	poop=poop+""+gs.getcmb();
	
	poop=poop+"\n pot size is : \n";
	poop=poop+""+gs.getpot();
	
	JOptionPane.showMessageDialog(thisFrame,""+poop);
	
}
    public void activate_someone_folded(int game,String foldedPlayer){
    	JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +foldedPlayer+" folded. ");

    }

	public void activate_someone_checked(int game,String checkPlayer){
		JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +checkPlayer+" checked. ");
}

    public void activate_someone_allin(int game,String allinPlayer){
    	JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +allinPlayer+" all-in'd. ");
    }

    public void activate_winner(int game,String winnerPlayer){
    	JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +winnerPlayer+" fucking won!!! ");
    }

    public void activate_someone_raised(int game,String raisedPlayer,int amount){
    	JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +raisedPlayer+" raised by: "+amount);
    }

    public void activate_someone_bet(int game,String betPlayer,int amount){
    	JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +betPlayer+" bet by: "+amount);
    }

    public void activate_someone_call(int game,String callPlayer,int amount){
    	JOptionPane.showMessageDialog(thisFrame,"In game "+ game +" the player: " +callPlayer+" called by: "+amount);
    }

}
