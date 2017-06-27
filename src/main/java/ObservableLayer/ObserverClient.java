package ObservableLayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;

public class ObserverClient extends AbstractVerticle implements IObserverClient {
    private static final ObserverClient instance = new ObserverClient();

    private ObserverClient() {
    }

    public static IObserverClient getInstance() {
        return instance;
    }

    public void sendToSpectator(ObservableSpectator spectator, int game, String content) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = spectator.getIpAddress();
        int port = spectator.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/getGameContent/" + game + "/" + content, httpClientResponse -> {
        });
    }

    public void sendCardsToPlayer(ObservablePlayer player, int game, Object cards) {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(cards);

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/getCards/" + game + "/" + s, httpClientResponse -> {
        });
    }

    public void sendGameStateToPlayer(ObservablePlayer player, int game, Object gameState) {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(gameState);

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/getGameState/" + game + "/" + s, httpClientResponse -> {
        });
        System.out.println("in observerclient , sending game state to player " + player.getName());
    }

    public void sendSomeoneFold(ObservablePlayer player, int game, String foldedPlayer) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/SomeoneFolded/" + game + "/" + foldedPlayer, httpClientResponse -> {
        });
    }

    public void sendSomeoneCheck(ObservablePlayer player, int game, String checkPlayer) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/SomeoneChecked/" + game + "/" + checkPlayer, httpClientResponse -> {
        });
    }

    public void sendSomeoneAllIn(ObservablePlayer player, int game, String allinPlayer) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/SomeoneAllIn/" + game + "/" + allinPlayer, httpClientResponse -> {
        });
    }

    public void sendSomeoneWinner(ObservablePlayer player, int game, String winnerPlayer) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/winner/" + game + "/" + winnerPlayer, httpClientResponse -> {
        });
    }

    public void sendSomeoneRaised(ObservablePlayer player, int game, String raisedPlayer, int amount) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/SomeoneRaised/" + game + "/" + raisedPlayer + "/" + amount, httpClientResponse -> {
        });
    }

    public void sendSomeoneBet(ObservablePlayer player,int game,String betPlayer ,int amount){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/SomeoneBet/" + game + "/" + betPlayer + "/" + amount, httpClientResponse -> {
        });
    }

    public void sendSomeoneCall(ObservablePlayer player,int game,String callPlayer ,int amount){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ObserverClient());
        String ipAddress = player.getIpAddress();
        int port = player.getPort();
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.getNow(port, ipAddress, "/SomeoneCall/" + game + "/" + callPlayer + "/" + amount, httpClientResponse -> {
        });
    }

}
