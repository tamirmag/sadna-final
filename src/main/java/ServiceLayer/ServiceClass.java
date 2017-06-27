package ServiceLayer;


import Games.*;
import Loggers.IFinishedGamesManager;
import Users.*;

import java.util.ArrayList;

public class ServiceClass implements IServiceClass {
    @Override
    public ServiceUser register(String username, String password, String email, int wallet) throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        IAccountManager.getInstance().register(username, password, email, wallet);
        int league = IAccountManager.getInstance().getUnknownLeague();
        return new ServiceUser(username, password, email, new ServiceWallet(wallet), league, 0, 0, 0, 0);
    }

    @Override
    public ServiceUser login(String username, String password) throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserNotExists {
        IUserManager u = IAccountManager.getInstance().login(username, password);
        int league = u.getUser().getLeague();
        ServiceWallet wallet = new ServiceWallet(u.getUser().getWallet().getAmountOfMoney());
        String email = u.getUser().getEmail();
        int numOfGames = u.getUser().getNumOfGames();
        int grossProfit = u.getUser().getGrossProfit();
        int highestCashGained = u.getUser().getHighestCashGained();
        int totalCashGain = u.getUser().getTotalCashGain();
        return new ServiceUser(username, password, email, wallet, league, numOfGames, grossProfit, highestCashGained, totalCashGain);
    }

    @Override
    public void logout(String username) throws UserNotExists, AlreadyLoggedOut {
        IAccountManager.getInstance().logout(username);
    }

    @Override
    public void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists, UserNotExists, UserNotLoggedIn {
        IUserManager u=new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.editProfile(username,password,email);
    }

    @Override
    public int createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy,
                          int minimumBet, int minimalAmountOfPlayers,
                          int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists {

        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        Preferences p = new Preferences();
        p.setBuyInPolicy(BuyInPolicy);
        p.setChipPolicy(ChipPolicy);
        p.setMinBetPolicy(minimumBet);
        p.setMinAmountPolicy(minimalAmountOfPlayers);
        p.setMaxAmountPolicy(maximalAmountOfPlayers);
        p.setSpectatePolicy(spectatingMode);
        if (gameType.equals("NoLimitHoldem")) p.setNoLimitHoldem(true);
        if (gameType.equals("LimitHoldem")) p.setLimitHoldem(true);
        if (gameType.equals("PotLimitHoldem")) p.setPotLimitHoldem(true);
        int i = u.CreateGame(p);
        return i;
    }


    @Override
    public void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.JoinGame(gamenum);
    }

    @Override
    public void leaveGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.leaveGame(gamenum);
    }

    @Override
    public void spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, SpectatingNotAllowed {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.spectateGame(gamenum);
    }

    @Override
    public ArrayList<String> viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        return u.viewReplay(gamenum);
    }

    /********filter games******************/

    @Override
    public ArrayList<Integer> findActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByPotSize(potSize);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByPlayerName(String username, String playerName) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByPlayerName(playerName);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByBuyInPolicy(String username, int costOfJoin) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByBuyInPolicy(costOfJoin);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByChipPolicy(String username, int numOfChips) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByChipPolicy(numOfChips);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByMaxPlayersPolicy(String username, int maxPlayers) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByMaxPlayersPolicy(maxPlayers);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByMinPlayersPolicy(String username, int minPlayers) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByMinPlayersPolicy(minPlayers);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByMinimumBetPolicy(String username, int minimumBet) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByMinimumBetPolicy(minimumBet);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByGameTypePolicy(String username, String gameTypePolicy) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByGameTypePolicy(gameTypePolicy);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesBySpectatingPolicy(true);
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }

    @Override
    public ArrayList<Integer> findActiveGamesByLeague(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<IGame> games = u.findActiveGamesByLeague();
        ArrayList<Integer> ret = new ArrayList<>();
        for (IGame i : games) ret.add(i.getId());
        return ret;
    }


    @Override
    public void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.check(gameID);
    }

    @Override
    public void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn, NotLegalAmount {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.bet(gameID, amount);
    }

    @Override
    public void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.raise(gameID, amount);
    }

    @Override
    public void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn, NotLegalAmount {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.allIn(gameID);
    }

    @Override
    public void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.fold(gameID);
    }

    @Override
    public void terminateGame(int gameID) {
        IActiveGamesManager.getInstance().terminateGame(gameID);
    }

    @Override
    public void clearLoggedInUsers() {
        IAccountManager.getInstance().clearLoggedInUsers();
    }

    @Override
    public void clearUsers() {
        IAccountManager.getInstance().clearUsers();
    }

    @Override
    public void clearAllFinishedGameLogs() {
        IFinishedGamesManager.getInstance().clearAllFinishedGames();
    }

    @Override
    public void startGame(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, NotLegalAmount {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        System.out.println("service class start game ");
        u.startGame(gameID);
    }

    @Override
    public ArrayList<String> getTop20NumOfGames(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<User> top20NumOfGames = u.getTop20NumOfGames();
        ArrayList<String> ret = new ArrayList<>();
        for (User user : top20NumOfGames) {
            ret.add(user.getUsername());
        }
        return ret;
    }

    @Override
    public ArrayList<String> getTop20highestCashGained(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<User> top20NumOfGames = u.getTop20highestCashGained();
        ArrayList<String> ret = new ArrayList<>();
        for (User user : top20NumOfGames) {
            ret.add(user.getUsername());
        }
        return ret;
    }

    @Override
    public ArrayList<String> getTop20GrossProfit(String username) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        ArrayList<User> top20NumOfGames = u.getTop20GrossProfit();
        ArrayList<String> ret = new ArrayList<>();
        for (User user : top20NumOfGames) {
            ret.add(user.getUsername());
        }
        return ret;
    }

    @Override
    public double getUserAverageCashGain(String username ,String toFind) throws UserNotExists, UserNotLoggedIn {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        return u.getUserAverageCashGain(toFind) ;
    }

    @Override
    public double getUserAverageGrossProfit(String username ,String toFind) throws UserNotExists, UserNotLoggedIn {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        return u.getUserAverageGrossProfit(toFind) ;
    }

    public int getPlayersNum(int id){
        return IActiveGamesManager.getInstance().getPlayersNum(id);
    }

}








     /* @Override
    public void saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.addFavoriteTurn(turn);
    }*/


 /* @Override
    public void setDefaultLeague(String username, int defaultLeague) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.setDefaultLeague(defaultLeague);
    }

    @Override
    public void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.setCriteria();
    }

    @Override
    public void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists, LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking {
        IUserManager u = new UserManager(IAccountManager.getInstance().getLoggedInUser(username));
        u.moveUserToLeague(userToMove, league);
    }*/

