package Acceptance;

import java.util.ArrayList;

import Games.*;
import ServiceLayer.IServiceClass;
import ServiceLayer.ServiceUser;
import Users.*;

public class ProxyBridge implements Bridge {

    private IServiceClass real;

    public ProxyBridge() {
        real = null;
    }

    public void setRealBridge(IServiceClass implementation) {
        if (real == null)
            real = implementation;
    }

    @Override
    public ServiceUser register(String username, String password, String email, int wallet)
            throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid {
        return real.register(username, password, email, wallet);
    }

    @Override
    public ServiceUser login(String username, String password)
            throws UsernameAndPasswordNotMatch, AlreadyLoggedIn,
            UsernameNotValid, PasswordNotValid, UserNotExists {
        return real.login(username, password);
    }

    @Override
    public void logout(String username) throws UserNotExists, AlreadyLoggedOut {
        real.logout(username);
    }

    @Override
    public void editProfile(String username,String password,String email) throws EmailNotValid, UsernameNotValid, UserAlreadyExists, UserNotExists, UserNotLoggedIn, PasswordNotValid {
        real.editProfile(username,password,email);
    }

    @Override
    public ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists {
        return real.findSpectatableGames(username);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByLeague(String username) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByLeague(username);
    }

    @Override
    public int createGame(String username, String gameType, int BuyInPolicy, int ChipPolicy, int minimumBet,
                          int minimalAmountOfPlayers, int maximalAmountOfPlayers, boolean spectatingMode)
            throws UserNotLoggedIn, UserNotExists {
        return real.createGame(username, gameType, BuyInPolicy, ChipPolicy,
                minimumBet, minimalAmountOfPlayers, maximalAmountOfPlayers, spectatingMode);
    }

    @Override
    public void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, CantJoin, NoMuchMoney {
        real.joinGame(gamenum, username);

    }

    @Override
    public void leaveGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin {
        real.leaveGame(gamenum,username);
    }

    @Override
    public void spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, SpectatingNotAllowed {
        real.spectateGame(gamenum, username);
    }

    @Override
    public ArrayList<String> viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists {
        return real.viewReplay(gamenum, username);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByPotSize(int potSize, String username)
            throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByPotSize(potSize, username);
    }


    @Override
    public void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn {
        real.check(username, gameID);
    }

    @Override
    public void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn, NotLegalAmount {
        real.bet(username, gameID, amount);
    }

    @Override
    public void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount {
        real.raise(username, gameID, amount);
    }

    @Override
    public void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn, NotLegalAmount {
        real.allIn(username, gameID);
    }

    @Override
    public void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn {
        real.fold(username, gameID);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByGameTypePolicy(String username, String gameTypePolicy) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByGameTypePolicy(username, gameTypePolicy);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByMinimumBetPolicy(String username, int minimumBet) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByMinimumBetPolicy(username, minimumBet);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByMinPlayersPolicy(String username, int minPlayers) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByMinPlayersPolicy(username, minPlayers);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByMaxPlayersPolicy(String username, int maxPlayers) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByMaxPlayersPolicy(username, maxPlayers);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByChipPolicy(String username, int numOfChips) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByChipPolicy(username, numOfChips);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByBuyInPolicy(String username, int costOfJoin) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByBuyInPolicy(username, costOfJoin);
    }

    @Override
    public ArrayList<Integer> findActiveGamesByPlayerName(String username, String playerName) throws UserNotLoggedIn, UserNotExists {
        return real.findActiveGamesByPlayerName(username, playerName);
    }

    @Override
    public ArrayList<String> getTop20NumOfGames(String username) throws UserNotLoggedIn, UserNotExists {
        return real.getTop20NumOfGames(username);
    }

    @Override
    public ArrayList<String> getTop20highestCashGained(String username) throws UserNotLoggedIn, UserNotExists {
        return real.getTop20highestCashGained(username);
    }

    @Override
    public ArrayList<String> getTop20GrossProfit(String username) throws UserNotLoggedIn, UserNotExists {
        return real.getTop20GrossProfit(username);
    }

    @Override
    public double getUserAverageCashGain(String username, String toFind) throws UserNotExists, UserNotLoggedIn {
        return real.getUserAverageCashGain(username ,toFind);
    }

    @Override
    public double getUserAverageGrossProfit(String username, String toFind) throws UserNotExists, UserNotLoggedIn {
        return real.getUserAverageGrossProfit(username,toFind);
    }


    @Override
    public void terminateGame(int gameID) {
        real.terminateGame(gameID);
    }

    @Override
    public void clearLoggedInUsers() {
        real.clearLoggedInUsers();
    }

    @Override
    public void clearUsers() {
        real.clearUsers();
    }

    @Override
    public void clearAllFinishedGameLogs() {
        real.clearAllFinishedGameLogs();
    }

    @Override
    public void startGame(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, NotLegalAmount {
        real.startGame(username, gameID);
    }

    public int getPlayersNum(int gameID){
        return real.getPlayersNum(gameID);
    }

}

    /*
        @Override
        public void saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists {
            real.saveFavoriteTurn(gamenum, username, turn);
        }

        @Override
        public void setDefaultLeague(String username, int defaultLeague)
                throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
            real.setDefaultLeague(username, defaultLeague);

        }

        @Override
        public void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking {
            real.setCriteria(username, criteria);
        }

        @Override
        public void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists,
                LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking {
            real.moveToLeague(username, userToMove, league);

        }*/
