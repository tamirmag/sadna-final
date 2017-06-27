package ObservableLayer;


public interface IObservableHandler {

    static IObservableHandler getInstance(){return ObservableHandler.getInstance();}

    void attachPlayer(String playerName, String ipAddress, int port, int game);

    void attachSpectator(String playerName, String ipAddress, int port, int game);

    void notifySpectators(int game, String content);

    void sendCardsToPlayer(String playerName, int game, Object cards);

    void sendGameStateToPlayer(String playerName, int game, Object gameState);

    void sendSomeoneFold(String foldedPlayer, int game);

    void sendSomeoneCheck(String checkPlayer, int game);

    void sendSomeoneAllIn(String allinPlayer, int game);

    void sendSomeoneWinner(String winnerPlayer, int game);

    void sendSomeoneRaised(String raisedPlayer, int game, int amount);

    void sendSomeoneBet(String betPlayer, int game, int amount);

    void sendSomeoneCall(String callPlayer, int game, int amount);

    void detachPlayer(String playerName , int game);
}
