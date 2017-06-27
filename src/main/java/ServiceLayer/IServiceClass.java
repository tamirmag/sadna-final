package ServiceLayer;

import Games.*;
import Users.*;

import java.util.ArrayList;


public interface IServiceClass {

    ServiceUser register(String username, String password, String email, int wallet) throws EmailNotValid, NegativeValue, UsernameNotValid, UserAlreadyExists, PasswordNotValid;

    ServiceUser login(String username, String password) throws UsernameAndPasswordNotMatch, AlreadyLoggedIn, UsernameNotValid, PasswordNotValid, UserNotExists;

    void logout(String username) throws UserNotExists, AlreadyLoggedOut;

    void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists, UserNotExists, UserNotLoggedIn;

    ArrayList<Integer> findSpectatableGames(String username) throws UserNotLoggedIn, UserNotExists;

    int createGame(String username,String gameType, int BuyInPolicy, int ChipPolicy,
                   int minimumBet, int minimalAmountOfPlayers,
                   int maximalAmountOfPlayers, boolean spectatingMode) throws UserNotLoggedIn, UserNotExists;

    void joinGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin;

    void leaveGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin;

    void spectateGame(int gamenum, String username) throws UserNotLoggedIn, UserNotExists, SpectatingNotAllowed;

    ArrayList<String> viewReplay(int gamenum, String username) throws UserNotLoggedIn, UserNotExists;

  //  void saveFavoriteTurn(int gamenum, String username, String turn) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByPotSize(int potSize, String username) throws UserNotLoggedIn, UserNotExists;

    //void setDefaultLeague(String username, int defaultLeague) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking;

   // void setCriteria(String username, int criteria) throws UserNotLoggedIn, UserNotExists, NegativeValue, NotHighestRanking;

    //void moveToLeague(String username, String userToMove, int league) throws UserNotLoggedIn, UserNotExists, LeagueNotExists, NegativeValue, UserAlreadyInLeague, UserNotInLeague, NotHighestRanking;

    void check(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn;

    void bet(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn, NotLegalAmount;

    void raise(String username, int gameID, int amount) throws UserNotLoggedIn, UserNotExists, NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount;

    void allIn(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, NotYourTurn, NotLegalAmount;

    void fold(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn;

    void terminateGame( int gameID) ;

    void clearLoggedInUsers();

    void clearUsers();

    void clearAllFinishedGameLogs();

    void startGame(String username, int gameID) throws UserNotLoggedIn, UserNotExists, NotYourTurn, NoMuchMoney, NotLegalAmount;

    ArrayList<Integer> findActiveGamesByLeague(String username) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByGameTypePolicy(String username,String gameTypePolicy) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByMinimumBetPolicy(String username,int minimumBet) throws UserNotLoggedIn, UserNotExists ;

    ArrayList<Integer> findActiveGamesByMinPlayersPolicy(String username,int minPlayers) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByMaxPlayersPolicy(String username,int maxPlayers) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByChipPolicy(String username,int numOfChips) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByBuyInPolicy(String username,int costOfJoin) throws UserNotLoggedIn, UserNotExists;

    ArrayList<Integer> findActiveGamesByPlayerName(String username,String playerName) throws UserNotLoggedIn, UserNotExists;

    ArrayList<String> getTop20NumOfGames(String username) throws UserNotLoggedIn, UserNotExists;

    ArrayList<String> getTop20highestCashGained(String username) throws UserNotLoggedIn, UserNotExists;

    ArrayList<String> getTop20GrossProfit(String username) throws UserNotLoggedIn, UserNotExists;

    double getUserAverageCashGain(String username ,String toFind) throws UserNotExists, UserNotLoggedIn;

    double getUserAverageGrossProfit(String username ,String toFind) throws UserNotExists, UserNotLoggedIn;

    int getPlayersNum(int id);

}
