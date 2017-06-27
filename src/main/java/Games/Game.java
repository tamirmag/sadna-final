package Games;

import Loggers.ActiveGamesLogManager;
import Loggers.IActiveGamesLogManager;
import Loggers.IFinishedGamesManager;
import ObservableLayer.IObservableHandler;
import ObservableLayer.ObservableHandler;
import Users.IAccountManager;
import Users.NoMuchMoney;
import Users.Wallet;
import Loggers.GameLogger;
import Users.User;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tamir on 16/04/2017.
 */
@Entity
public class Game implements IGame {


    private Chat chat;
    @Id
    private int id;
    private int league;
    private DeckManager deck = new DeckManager();
    private ArrayList<Player> desk = new ArrayList<Player>(); // players that not yet fold
    private ArrayList<Card> flop = new ArrayList<Card>(); // cards on desk
    private ArrayList<Player> players; //all players in the room
    private ArrayList<String> spectators; //all spectators in the room
    private int dealerId = 0;
    private int turnId = 0; //number of current playing player
    private int turn = 0; // number of round
    private boolean up = true; // if someone's raised in this round
    private int currentMinimumBet;
    private int pot;
    private ArrayList<Integer> playerDesk; //the amount of many that every player put
    private boolean locked = false;
    private String type = "nurmal";
    private boolean inStart = true;
    IObservableHandler handler;
    @Transient
    private GameLogger logger;

    public Game(ArrayList<Player> players, int id, int league) {
        this.players = players;
        currentMinimumBet = 0;
        pot = 0;
        this.id = id;
        logger = new GameLogger(id);
        ActiveGamesLogManager.getInstance().AddGameLogger(logger);
        this.league = league;
        this.spectators = new ArrayList<>();
        chat = new Chat();
        handler = ObservableHandler.getInstance();
    }

    @Override
    public int getLeague(){ return  league;}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void publishMessage(String msg, Player player) {
        // TODO implement
        //log.addToLog(msg);
    }
    @Override
    public int getPlayersNum() {
        return players.size();
    }

    @Override
    public boolean inMax(){
        return true;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void join(Player player) throws CantJoin {
        if(locked)
            throw new CantJoin(getId(), player.getName());
        players.add(player);

    }

    @Override
    public int getMaxPlayers()
    {
        return 0;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean isPlayerInGame(String name){
        for (Player player: players) {
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }


    @Override
    public int getTurn(){
        return turnId;
    }

    @Override
    public void setMinimumBet(int bet) {
        currentMinimumBet = bet;
        //      minimumBet = bet;
    }


    @Override
    public void setMinimumPlayers(int num) {
        //minPlayers = num;
    }

    @Override
    public void setMaximumPlayers(int num) {
        //maxPlayers = num;
    }

    @Override
    public void setChipNum(int num) {

        for (Player player:players) {
            player.wallet = new Wallet(num);
        }
    }


    @Override
    public int getMinimumBet(){
        return currentMinimumBet;
    }

    @Override
    public int getPot(){
        return pot;
    }

    @Override
    public boolean spectaAble() {
        return false;
    }

    @Override
    public void raise(int amount, Player player) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount {
        if (desk.get(turnId).equals(player)) {
            if(amount >= currentMinimumBet){
            this.currentMinimumBet += amount;
            player.wallet.sub(currentMinimumBet);

            playerDesk.set(turnId, this.currentMinimumBet);
            handler.sendSomeoneRaised(player.getName(), getId(), amount);
            endTurn(player);
            }else{
                throw new NotLegalAmount(amount, currentMinimumBet);
            }
        }else
            throw new NotYourTurn();
    }

    @Override
    public void fold(Player player) throws NotYourTurn {

        if (desk.get(turnId).equals(player)) {
            handler.sendSomeoneFold(player.getName(),getId());
            desk.remove(turnId);
            if(desk.size() == 1){
                win(desk.get(0));
            }
            if (this.turnId == desk.size())
                endRound();

        }else
            throw new NotYourTurn();
    }

    @Override
    public void win(Player player) {

        if (player != null) {
            player.getWallet().add(pot);
        } else {
            for (Player p : desk) {
                p.hand.addAll(flop);
            }


            Player winner = player;
            if ((winner = hasRoyalFlash()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasStraightFlush()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasFourOfAKind()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasFullHouse()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasFlush()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasStraight()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasThreeOfAKind()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasTwoPair()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasOnePair()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            } else if ((winner = hasHighCard()) != null) {
                winner.getWallet().add(pot);
                //          pot = 0;
            }
            handler.sendSomeoneWinner(player.getName(),getId());
            pot = 0;
        }
    }

    private Player hasHighCard() {
        int max =0;
        Player winner = null;
        for (Player player: desk) {
            if(CardActions.getMaxValue(player.getHand())>=max){
                max = CardActions.getMaxValue(player.getHand());
                winner = player;
            }
        }
        return winner;
    }

    private Player hasOnePair() {
        for (Player player: desk) {
            if(CardActions.isPair(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasTwoPair() {
        for (Player player: desk) {
            if(CardActions.isTwoPair(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasThreeOfAKind() {
        for (Player player: desk) {
            if(CardActions.isThreeOfAKind(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasStraight() {
        for (Player player: desk) {
            if(CardActions.isStraight(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasFlush() {
        for (Player player: desk) {
            if(CardActions.isFlush(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasFullHouse() {
        for (Player player: desk) {
            if(CardActions.isFullHouse(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasFourOfAKind() {
        for (Player player: desk) {
            if(CardActions.isFourOfAKind(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasStraightFlush() {
        for (Player player: desk) {
            if(CardActions.isStraightFlush(player.hand)){
                return player;
            }
        }
        return null;
    }

    private Player hasRoyalFlash() {
        for (Player player: desk) {
            if(CardActions.isRoyalFlush(player.hand)){
                return player;
            }
        }
        return null;
    }

    @Override
    public void dealCard(Player player) {
        Card card = deck.dealCard();
        player.hand.add(card);
    }

    @Override
    public void bet(int amount, Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        if (desk.get(turnId).equals(player)) {
        call(amount,player);
        }else
            throw new NotYourTurn();
    }

    @Override
    public void call(int amount, Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        if (desk.get(turnId).equals(player)) {
            if(inStart || amount >= currentMinimumBet) {
                playerDesk.set(turnId, amount);
                player.wallet.sub(amount);
                currentMinimumBet = amount;
                handler.sendSomeoneCall(player.getName(), getId(), amount);
                endTurn(player);
            }else{
                throw new NotLegalAmount(amount, currentMinimumBet);
            }
        }else
            throw new NotYourTurn();
    }

    @Override
    public void check(Player player) throws NoMuchMoney, NotYourTurn {
        if (desk.get(turnId).equals(player)) {
            playerDesk.set(turnId, currentMinimumBet);
            player.wallet.sub(currentMinimumBet);
            handler.sendSomeoneCheck(player.getName(), getId());
            endTurn(player);
        }else
            throw new NotYourTurn();
    }

    @Override
    public void leaveGame(Player player, int userID) {
        desk.remove(player);
        if(desk.size() == 1){
            win(desk.get(0));
        }
        if (this.turnId == desk.size())
            endRound();
        players.remove(player);
    }

    @Override
    public void allIn(Player player) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        if (desk.get(turnId).equals(player)) {
        call(player.wallet.getAmountOfMoney(),player);
        handler.sendSomeoneAllIn(player.getName(), getId());
        }else
            throw new NotYourTurn();
    }

    @Override
    public void terminateGame() {
        IAccountManager.getInstance().updateNumOfGames(players);
        logger.writeToFile("game ended");
        IFinishedGamesManager.getInstance().addFinishedGameLog(logger);
        IActiveGamesLogManager.getInstance().RemoveGameLogger(logger);
    }

    @Override
    public void startGame() throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        locked = true;
        playerDesk = new ArrayList<Integer>();
        for (Player p:players) {
            desk.add(p);
            playerDesk.add(0);
            dealCard(p);
            dealCard(p);
            sandState(p);
        }

        blinedBet();
        inStart = false;
    }

    private void sandState(Player p) {
        GameState state = new GameState(desk, desk, flop, p.getHand(), getMinimumBet(), getPot());
        handler.sendGameStateToPlayer(p.getName(), getId(), state);
    }

    private void blinedBet() throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        Player smallBlind = desk.get(dealerId);
        Player bigBlind = desk.get(dealerId+1);
        call(getMinimumBet()/2, smallBlind);
        call(getMinimumBet(), bigBlind);
    }

    @Override
    public void endTurn(Player player) {

        if (desk.get(turnId).equals(player)) {
            this.turnId = this.turnId + 1;

            if (this.turnId == desk.size()) {
                endRound();
            }
        }
    }

    @Override
    public void endRound() {
        if (up) {
            this.up = false;
            this.turnId = 0;
            for (int i = 0; i < playerDesk.size(); i++) {
                pot += playerDesk.get(i);
                playerDesk.set(i, 0);
            }
        } else {
            turnId = 0;
            switch(turn)
            {
                case 0:
                    turn++;
                    break;
                case 1:
                    flop.add(deck.dealCard());
                    flop.add(deck.dealCard());
                    flop.add(deck.dealCard());
                    turn++;
                    for (Player p:players) {
                        sandState(p);
                    }
                    break;
                case 2:
                    flop.add(deck.dealCard());
                    turn++;
                    for (Player p:players) {
                        sandState(p);
                    }
                    break;
                case 3:
                    flop.add(deck.dealCard());
                    turn++;
                    for (Player p:players) {
                        sandState(p);
                    }
                    win(null);
                    break;
            }
        }
    }

    @Override
    public ArrayList<String> getAllTurnsOfPlayer(Player p, ArrayList<String> allTurns)
    {
        for (String s: allTurns) {
            if(!s.contains(p.getName())) allTurns.remove(s);
        }
        return allTurns;
    }

    @Override
    public void spectateGame(User user) {
        spectators.add(user.getUsername());
    }

    @Override
    public Hashtable<String ,ArrayList<String>> getAllTurnsByAllPlayers()
    {
        Hashtable<String ,ArrayList<String>> ans = new Hashtable<>();
        ArrayList<String> allTurns = logger.getContentOfFile();

        for(Player p:players)
        {
            ArrayList<String> temp = new ArrayList<>();
            for(String a : allTurns) temp.add(a);
            ans.put(p.getName(), getAllTurnsOfPlayer(p, temp));
        }
        return ans;

    }


    @Override
    public int getMinPlayers(){
        return 0;
    }

    @Override
    public int getChips() {
        return 0;
    }

    @Override
    public int getBuyIn() {
        return 0;
    }

    @Override
    public Player findPlayer(User usr)
    {
        for (Player p:players) {
            if(p.getName().equals(usr.getUsername()))
                return p;
        }
        return null;
    }


    @Override
    public boolean canJoin(User user) throws NotYourLeague {
        if((user.getLeague() != 0)){
            if(league != user.getLeague()){
                throw new NotYourLeague(league, user.getLeague());
            }
        }else{
            if(user.getNumOfGames() > 10){
                throw new NotYourLeague(league, user.getLeague());
            }
        }
        return true;
    }

    @Override
    public void sendMessage(String from, String to, String data) {
        chat.sendMessage(from, to, data);
    }

}
