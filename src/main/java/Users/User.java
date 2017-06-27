package Users;

import Games.Player;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;

@Entity
public class User {
    public int getId() {
        return id;
    }

    @Id
    private int id;
    private String username;
    private String password;
    private String email;
    private int league;
    private Wallet wallet;
    private int numOfGames;
    private int grossProfit; //The total gross profit
    private int highestCashGained; //The highest cash gain in a game.
    private boolean loggedIn;
    private int totalCashGain;
    @Reference
    private ArrayList<Player> existingPlayers = new ArrayList<>();


    public User() {
    }

    public User(int id, String username, String password, int league, String email, Wallet wallet) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.league = league;
        this.email = email;
        this.wallet = wallet;
        this.numOfGames = 0;
        grossProfit = 0;
        highestCashGained = 0;
        totalCashGain = 0;
    }

    @Override
    public String toString() {
        return "username: " + username + " ,password:" + password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public void getCharToPrint(char c) {
        System.out.print(c);
    }

    public ArrayList<Player> getExistingPlayers() {
        return existingPlayers;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public boolean equals(Object other) {
        return username.equals(((User) other).username) && password.equals(((User) other).password);
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    public int getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(int grossProfit) {
        this.grossProfit = grossProfit;
    }

    public int getHighestCashGained() {
        return highestCashGained;
    }

    public void setHighestCashGained(int highestCashGained) {
        this.highestCashGained = highestCashGained;
    }

    public int getTotalCashGain() {
        return totalCashGain;
    }

    public void setTotalCashGain(int totalCashGain) {
        this.totalCashGain = totalCashGain;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


}

//public boolean isHighestRanking() {return isHighestRanking;}

// public void setHighestRanking(boolean highestRanking) {isHighestRanking = highestRanking;}

       /* public ArrayList<String> getFavoriteTurns() {
        return favoriteTurns;
    }

    public void setFavoriteTurns(ArrayList<String> favoriteTurns) {
        this.favoriteTurns = favoriteTurns;
    }
    */