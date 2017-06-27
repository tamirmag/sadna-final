package Users;

import Games.*;

import java.util.ArrayList;

public interface IUserManager {
    void logout() throws UserNotExists, AlreadyLoggedOut;

    void editProfile(String username, String password, String email)
            throws UsernameNotValid, EmailNotValid, PasswordNotValid, UserAlreadyExists, UserNotExists;

    void addPlayer(Player p);

    ArrayList<String> viewReplay(int gameNumber);

    void spectateGame(int gameNumber) throws SpectatingNotAllowed;

    int CreateGame(Preferences pref);

    ArrayList<IGame> findActiveGamesByLeague();

    ArrayList<IGame> findActiveGamesByPlayerName(String playerName);

    ArrayList<IGame> findActiveGamesByPotSize(int potSize);

    ArrayList<IGame> findActiveGamesBySpectatingPolicy(boolean spectatingAllowed);

    ArrayList<IGame> findActiveGamesByMinPlayersPolicy(int minimal);

    ArrayList<IGame> findActiveGamesByMaxPlayersPolicy(int maximal);

    ArrayList<IGame> findActiveGamesByMinimumBetPolicy(int minimumBet);

    ArrayList<IGame> findActiveGamesByChipPolicy(int numOfChips);

    ArrayList<IGame> findActiveGamesByBuyInPolicy(int costOfJoin);

    ArrayList<IGame> findActiveGamesByGameTypePolicy(String gameTypePolicy);

    User getUser();

    void JoinGame(int gameNumber) throws CantJoin, NoMuchMoney;

    //Game actions
    void check(int gameID) throws NoMuchMoney, NotYourTurn;

    void bet(int gameID, int amount) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void allIn(int gameID) throws NoMuchMoney, NotYourTurn, NotLegalAmount;

    void fold(int gameID) throws NotYourTurn;

    void raise(int gameID, int amount) throws NotAllowedNumHigh, NoMuchMoney, NotYourTurn, NotLegalAmount;

    void startGame(int gameID) throws NotYourTurn, NoMuchMoney, NotLegalAmount;

    //

    ArrayList<User> getTop20NumOfGames();

    ArrayList<User> getTop20highestCashGained();

    ArrayList<User> getTop20GrossProfit();

    double getUserAverageCashGain(String username) throws UserNotExists;

    double getUserAverageGrossProfit(String username) throws UserNotExists;

    void leaveGame(int gamenum) throws UserNotLoggedIn, UserNotExists, NoMuchMoney, CantJoin;
}
