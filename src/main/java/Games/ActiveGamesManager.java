package Games;

import DB.GamesDB;
import DB.IGamesDB;
import Loggers.ActiveGamesLogManager;
import Loggers.IActiveGamesLogManager;
import Users.NoMuchMoney;
import Users.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ActiveGamesManager implements IActiveGamesManager {
    private static ActiveGamesManager instance = null;
    private ArrayList<IGame> games;
    private AtomicInteger index;

    private ActiveGamesManager() {
        games = new ArrayList();
        index = new AtomicInteger(0);
    }

    public static ActiveGamesManager getInstance() {
        if (instance == null) {
            instance = new ActiveGamesManager();
        }
        return instance;
    }

    @Override
    public void publishMessage(String msg, int gameNumber, Player player) {
        for (IGame game : games) {
            if (game.getId() == gameNumber) {
                game.publishMessage(msg, player);
            }
        }
    }

    @Override
    public int createGame(User user, Preferences pref) {
        ArrayList<Player> players = new ArrayList<Player>();
        Player p = new Player(user.getUsername(), user.getWallet());
        p.wallet = user.getWallet();
        p.name = user.getUsername();
        players.add(p);
        IGame game = null;
        int i = this.index.incrementAndGet();
        game = new Game(players, i,user.getLeague());
        game = buildByPref(pref, game);
        games.add(game);
       // System.out.println("player "+ p.getName() +"was added to game " +i);
       // System.out.println(games.size());
        return i;
    }

    private IGame buildByPref(Preferences pref, IGame game) {
        if (pref.isNoLimitHoldem()) {
            game = new NoLimitHoldem(game);
   //         System.out.println("no limit");
        }

        if (pref.isLimitHoldem()) {
            game = new LimitHoldem(game);
    //        System.out.println("limit");
        }

        if (pref.isPotLimitHoldem()) {
            game = new PotLimitHoldem(game);
     //       System.out.println("pot limit");
        }

        if (pref.getBuyInPolicy() > 0) {
            game = new BuyInPolicy(game, pref.getBuyInPolicy());
     //       System.out.println("buy in");
        }

        if (pref.getChipPolicy() > 0) {
            game = new ChipPolicy(game, pref.getChipPolicy());
      //      System.out.println("chip");
        }

        if (pref.getMaxAmountPolicy() > 0) {
            game = new MaxAmountPolicy(game, pref.getMaxAmountPolicy());
     //       System.out.println("max amount");
        }

        if (pref.getMinAmountPolicy() > 0) {
            game = new MinAmountPolicy(game, pref.getMinAmountPolicy());
    //        System.out.println("minimum amount");
        }

        if (pref.getMinBetPolicy() > 0) {
            game = new MinBetPolicy(game, pref.getMinBetPolicy());
      //      System.out.println("minimum bet");
        }

        if (pref.isSpectatePolicy()) {
            game = new SpectatePolicy(game, pref.isSpectatePolicy());
      //      System.out.println("spectate");
        }

    //    System.out.println("game");
        return game;
    }

    private IGame find(int id) {
        IGame myGame = null;
        for (IGame game : games) {
            if (game.getId() == id)
                myGame = game;
        }
        if(myGame == null)
        {
            IGamesDB db = GamesDB.getInstance();
            myGame = db.getGame(id);
        }
        return myGame;
    }

    @Override
    public void startGame(int id) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        IGame myGame = find(id);
    //    System.out.println("active games manager start game ");
        myGame.startGame();
    }

    @Override
    public void raise(int id, int amount, User usr) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.raise(amount, p);
    }

    public int getMinimumBet(int id) {
        IGame myGame = find(id);
        return myGame.getMinimumBet();
    }

    public boolean isLocked(int id) {
        IGame myGame = find(id);
        return myGame.isLocked();
    }

    @Override
    public void fold(int id, User usr) throws NotYourTurn {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.fold(p);
    }


    @Override
    public void allIn(int id, User usr) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.allIn(p);
    }


    @Override
    public void check(int id, User usr) throws NoMuchMoney, NotYourTurn {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.check(p);
    }

    @Override
    public void bet(int id, int amount, User usr) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        User u = usr;
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.bet(amount, p);
    }


    @Override
    public void JoinGame(int id, User user) throws NoMuchMoney, CantJoin {
        IGame myGame = null;
        for (IGame game : games) {
            if (game.getId() == id)
                myGame = game;
        }

        Player p = new Player(user.getUsername(), user.getWallet());
        myGame.join(p);
    }


    @Override
    public List<IGame> findAllActiveGames(User user) throws NotYourLeague {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games){
            if (game.canJoin(user))
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByLeague(User user) {
        ArrayList<IGame> ourGames = new ArrayList<>();
        for (IGame game : games){
            if (user.getLeague()==game.getLeague())
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public List<IGame> findActiveGamesByPlayer(String name) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.isPlayerInGame(name))
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public void logout(String name){
        List<IGame> arr = findActiveGamesByPlayer(name);
        IGamesDB db = GamesDB.getInstance();
        for (IGame g:arr) {
            db.save(g);
        }
    }

    @Override
    public ArrayList<IGame> findActiveGamesByPotSize(int potSize) {

        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getPot() == potSize)
                ourGames.add(game);
        }
        return ourGames;
    }


    @Override
    public List<IGame> findSpectatableGames(User user) {

        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.spectaAble())
                ourGames.add(game);
        }
        return ourGames;
    }


    @Override
    public ArrayList<IGame> findActiveGamesByPlayersMinimumPolicy(int minimal) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getMinPlayers() == minimal)
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByPlayersMaximumPolicy(int maximal) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getMaxPlayers() == maximal)
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            int i = game.getMinimumBet();
            if (game.getMinimumBet() == minimumBet)
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getChips() == numOfChips)
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getBuyIn() == costOfJoin)
                ourGames.add(game);
        }
        return ourGames;
    }

    @Override
    public ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy) {
        ArrayList<IGame> ourGames = new ArrayList();
        for (IGame game : games) {
            if (game.getType() .equals(gameTypePolicy))
                ourGames.add(game);
        }
        return ourGames;
    }


    @Override
    public void leaveGame(int id, User usr, int userID) {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.leaveGame(p, userID);
    }


    @Override
    public void publishMessage(int id, String msg, Player player) {
        IGame myGame = find(id);
        myGame.publishMessage(msg, player);
    }


    @Override
    public void terminateGame(int id) {
        IGame myGame = find(id);
        myGame.terminateGame();
        Iterator<IGame> iter = games.iterator();
        while (iter.hasNext()) {
            IGame game = iter.next();
            if(game.getId() == id) iter.remove();
        }
    }


    public void spectateGame(int id, User user) throws SpectatingNotAllowed {
        /*ROY changed the implementation of this function*/
        /*IGame myGame = find(id);
        myGame.spectateGame(user);*/
        IGame myGame = find(id);
        if(!myGame.spectaAble()) throw new SpectatingNotAllowed(id);
        myGame.spectateGame(user);
        ActiveGamesLogManager.getInstance().spectateGame(id,user);
    }


    @Override
    public void call(int id, int amount, User usr) throws NoMuchMoney, NotYourTurn, NotLegalAmount {
        IGame myGame = find(id);
        Player p = myGame.findPlayer(usr);
        myGame.call(amount, p);
    }

    @Override
    public void sendMessage(int id, String from, String to, String data) {
        IGame myGame = find(id);
        myGame.sendMessage(from, to, data);
    }

    public void deleteAllActiveGames() {
        if (games != null) {
            for (IGame g : games) {
                int i = g.getId();
                IActiveGamesLogManager.getInstance().RemoveGameLogger(i);
            }
            games.clear();
        }
    }

    public int getPlayersNum(int id){
        IGame myGame = find(id);
        return  myGame.getPlayersNum();
    }

}

