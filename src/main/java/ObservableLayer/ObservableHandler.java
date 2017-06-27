package ObservableLayer;


import java.util.ArrayList;
import java.util.HashMap;

public class ObservableHandler implements IObservableHandler {

    private HashMap<Integer, ArrayList<ObservableSpectator>> spectators;
    private HashMap<Integer, ArrayList<ObservablePlayer>> players;
    private static final IObservableHandler instance = new ObservableHandler();
    private ObservableHandler() {
        players = new HashMap<>();
        spectators = new HashMap<>();
    }

    public static IObservableHandler getInstance()
    {return instance;}

    public void attachPlayer(String playerName, String ipAddress, int port, int game) {
        ObservablePlayer player = new ObservablePlayer(ipAddress, port, playerName);
        if (!players.containsKey(game)) {
            players.put(game, new ArrayList<ObservablePlayer>());
        }
        players.get(game).add(player);

    }

    public void detachPlayer(String playerName , int game)
    {
        for(ObservablePlayer p : players.get(game))
        {
            if(p.getName().equals(playerName)) players.get(game).remove(p);
        }
    }


    public void attachSpectator(String playerName, String ipAddress, int port, int game) {
        ObservableSpectator spectator = new ObservableSpectator(ipAddress, port, playerName);
        if (!spectators.containsKey(game)) {
            spectators.put(game, new ArrayList<ObservableSpectator>());
        }
        spectators.get(game).add(spectator);

    }

    public void notifySpectators(int game, String content) {
        if (!spectators.containsKey(game)) return;
        for (ObservableSpectator spectator : spectators.get(game)) {
            IObserverClient.getInstance().sendToSpectator(spectator, game, content);
        }
    }

    public void sendCardsToPlayer(String playerName, int game, Object cards) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            if (player.getName().equals(playerName)) {
                IObserverClient.getInstance().sendCardsToPlayer(player, game, cards);
                break;
            }
        }
    }

    public void sendGameStateToPlayer(String playerName, int game, Object gameState) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            if (player.getName().equals(playerName)) {
                IObserverClient.getInstance().sendGameStateToPlayer(player, game, gameState);
                break;
            }
        }
    }

    public void sendSomeoneFold(String foldedPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneFold(player, game, foldedPlayer);
        }
    }

    public void sendSomeoneCheck(String checkPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneCheck(player, game, checkPlayer);
        }
    }

    public void sendSomeoneAllIn(String allinPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneAllIn(player, game, allinPlayer);
        }
    }

    public void sendSomeoneWinner(String winnerPlayer, int game) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneWinner(player, game, winnerPlayer);
        }
    }

    public void sendSomeoneRaised(String raisedPlayer, int game, int amount) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneRaised(player, game, raisedPlayer, amount);
        }
    }

    public void sendSomeoneBet(String betPlayer, int game, int amount) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneBet(player, game, betPlayer, amount);
        }
    }

    public void sendSomeoneCall(String callPlayer, int game, int amount) {
        if (!players.containsKey(game)) return;
        for (ObservablePlayer player : players.get(game)) {
            IObserverClient.getInstance().sendSomeoneCall(player, game, callPlayer, amount);
        }
    }

}



