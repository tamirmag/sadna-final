package ServerClient;
//package ServiceLayer;

import ObservableLayer.IObservableHandler;
import ServiceLayer.ServiceUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

//import java.util.HashMap;
//import java.util.Map;
//import io.vertx.core.json.JsonObject;


public class Http_Server extends AbstractVerticle {

	public static void main(String[] args) {
		VertxOptions options = new VertxOptions();
		options.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle(new Http_Server());
	}

	@Override
	public void start(Future<Void> fut) {

		Router router = Router.router(vertx);
		Handler hand=new Handler();
		// Bind "/" to our hello message - so we are still compatible.
		router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response
					.putHeader("content-type", "text/html")
					.end("<h1>Hello from my first Vert.x 3 application</h1>");
		});

		router.route("/register/:username/:password/:email/:wallet").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			String password = routingContext.request().getParam("password");
			String email = routingContext.request().getParam("email");
			int wallet = Integer.parseInt(routingContext.request().getParam("wallet"));
			System.out.println("received connection from "+ username);
			try {
				ServiceUser su=hand.handleRegister(username,password,email,wallet);
				//Map<String,Object> map=new HashMap<String,Object>();
				//map.put("key", su);
				//JsonObject jsonObject=new JsonObject(map);
				//String toSend=jsonObject.encode();

				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(su,ServiceUser.class);

				HttpServerResponse response = routingContext.response();
				response.end(toSend);

			} catch (Exception e) {
				ServiceUser su = null;
				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(su,ServiceUser.class);

				HttpServerResponse response = routingContext.response();
				response.end(toSend);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		router.route("/startGame/:username/:gameID").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			try {
				String ipAddress = routingContext.request().remoteAddress().host();
				int port = routingContext.request().remoteAddress().port();
				System.out.println(username+" was joined as ip " +ipAddress +" and port "+port);

				hand.handleStartGame(username,gameID);
				HttpServerResponse response = routingContext.response();
				response.end("game started successfully");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		router.route("/getPlayersNum/:gameID").handler(routingContext -> {
			Integer id = Integer.parseInt(routingContext.request().getParam("gameID"));
			try {
				int ans=hand.handleGetPlayersNum(id.intValue());
				//Map<String,Object> map=new HashMap<String,Object>();
				//map.put("key", su);
				//JsonObject jsonObject=new JsonObject(map);
				//String toSend=jsonObject.encode();

				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(new Integer(ans),Integer.class);

				HttpServerResponse response = routingContext.response();
				response.end(toSend);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/login/:id/:password").handler(routingContext -> {
			String id = routingContext.request().getParam("id");
			String password = routingContext.request().getParam("password");
			try {
				ServiceUser su=hand.handleLogin(id,password);
				//Map<String,Object> map=new HashMap<String,Object>();
				//map.put("key", su);
				//JsonObject jsonObject=new JsonObject(map);
				//String toSend=jsonObject.encode();

				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(su,ServiceUser.class);

				HttpServerResponse response = routingContext.response();
				response.end(toSend);
			} catch (Exception e) {
				ServiceUser su = null;
				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(su,ServiceUser.class);

				HttpServerResponse response = routingContext.response();
				response.end(toSend);
				e.printStackTrace();
			}
		});

		router.route("/logout/:username").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			try {
				hand.handleLogout(username);
				HttpServerResponse response = routingContext.response();
				response.end("logout successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/editProfile/:username/:password/:email").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			String password = routingContext.request().getParam("password");
			String email = routingContext.request().getParam("email");
			try {
				hand.handleEditProfile(username,password,email);
				HttpServerResponse response = routingContext.response();
				response.end("editing profile successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/findSpectatableGames/:username").handler(routingContext -> {

			String username = routingContext.request().getParam("username");
			try {
				ArrayList<Integer> ans=hand.handleFindSpectatableGames(username);
				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(ans,new TypeToken<ArrayList<Integer>>(){}.getType());

				HttpServerResponse response = routingContext.response();
				response.end(toSend);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/findActiveGamesByLeague/:username").handler(routingContext -> {

			String username = routingContext.request().getParam("username");
			try {
				ArrayList<Integer> ans=hand.handleFindActiveGamesByLeague(username);
				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(ans,new TypeToken<ArrayList<Integer>>(){}.getType());
				System.out.println("sending response");
				HttpServerResponse response = routingContext.response();
				response.end(toSend);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/createGame/:username/:gameType/:BuyInPolicy/:ChipPolicy/:minimumBet/:minimalAmountOfPlayers/:maximalAmountOfPlayers/:spectatingMode").handler(routingContext -> {
			System.out.println("create game in server");
			String username = routingContext.request().getParam("username");
			String gameType = routingContext.request().getParam("gameType");
			int BuyInPolicy = Integer.parseInt(routingContext.request().getParam("BuyInPolicy"));
			int ChipPolicy = Integer.parseInt(routingContext.request().getParam("ChipPolicy"));
			int minimumBet = Integer.parseInt(routingContext.request().getParam("minimumBet"));
			int minimalAmountOfPlayers = Integer.parseInt(routingContext.request().getParam("minimalAmountOfPlayers"));
			int maximalAmountOfPlayers = Integer.parseInt(routingContext.request().getParam("maximalAmountOfPlayers"));
			String spectatingModeTemp = routingContext.request().getParam("spectatingMode");
			boolean spectatingMode = spectatingModeTemp.equals("true");
			try {
				int ans=hand.handleCreateGame(username, gameType, BuyInPolicy, ChipPolicy,
						minimumBet, minimalAmountOfPlayers, maximalAmountOfPlayers, spectatingMode);
				//I addeed this
				String ipAddress = routingContext.request().remoteAddress().host();
				int port = routingContext.request().remoteAddress().port();
				System.out.println(username+" was joined as ip " +ipAddress +" and port "+port);
				IObservableHandler.getInstance().attachPlayer(username ,ipAddress,8080, ans);
				//endof I addeed this
				HttpServerResponse response = routingContext.response();
				response.end(ans+"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		router.route("/leaveGame/:username/:gameNum").handler(routingContext -> {
			System.out.println("player leave active game");
			String username = routingContext.request().getParam("username");
			int gameNum = Integer.parseInt(routingContext.request().getParam("gameNum"));
			try {
				hand.handleLeaveGame(username , gameNum);
				//I addeed this
				IObservableHandler.getInstance().detachPlayer(username , gameNum);
				//endof I addeed this
				HttpServerResponse response = routingContext.response();
				response.end("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/joinGame/:gamenum/:username").handler(routingContext -> {
			int gamenum = Integer.parseInt(routingContext.request().getParam("gamenum"));
			String username = routingContext.request().getParam("username");
			try {
				hand.handleJoinGame(gamenum, username);
				//I addeed this
				String ipAddress = routingContext.request().remoteAddress().host();
				int port = routingContext.request().remoteAddress().port();
				IObservableHandler.getInstance().attachPlayer(username ,ipAddress,8080, gamenum);
				//endof I addeed this
				HttpServerResponse response = routingContext.response();
				response.end("joined successfully");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/spectateGame/:gamenum/:username").handler(routingContext -> {
			int gamenum = Integer.parseInt(routingContext.request().getParam("gamenum"));
			String username = routingContext.request().getParam("username");
			try {
				hand.handleSpectateGame(gamenum, username);
				//I addeed this
				String ipAddress = routingContext.request().remoteAddress().host();
				int port = routingContext.request().remoteAddress().port();
				IObservableHandler.getInstance().attachSpectator(username ,ipAddress,8080, gamenum);
				//endof I addeed this
				HttpServerResponse response = routingContext.response();
				response.end("spectating successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/viewReplay/:gamenum/:username").handler(routingContext -> {
			int gamenum = Integer.parseInt(routingContext.request().getParam("gamenum"));
			String username = routingContext.request().getParam("username");
			try {
				ArrayList<String> ans=hand.handleViewReplay(gamenum, username);
				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(ans,new TypeToken<ArrayList<String>>(){}.getType());
				HttpServerResponse response = routingContext.response();
				response.end(toSend);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/findActiveGamesByPotSize/:potSize/:username").handler(routingContext -> {
			int potSize = Integer.parseInt(routingContext.request().getParam("potSize"));
			String username = routingContext.request().getParam("username");
			try {
				ArrayList<Integer> ans=hand.handleFindActiveGamesByPotSize(potSize, username);

				Gson gson=new GsonBuilder().create();
				String toSend=gson.toJson(ans,new TypeToken<ArrayList<Integer>>(){}.getType());
				HttpServerResponse response = routingContext.response();
				response.end(toSend);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/check/:username/:gameID").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			try {
				hand.handleCheck(username, gameID);
				HttpServerResponse response = routingContext.response();
				response.end("check was successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/bet/:username/:gameID/:amount").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			int amount = Integer.parseInt(routingContext.request().getParam("amount"));
			try {
				hand.handleBet(username, gameID, amount);
				HttpServerResponse response = routingContext.response();
				response.end("bet was successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/raise/:username/:gameID/:amount").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			int amount = Integer.parseInt(routingContext.request().getParam("amount"));
			try {
				hand.handleRaise(username, gameID, amount);
				HttpServerResponse response = routingContext.response();
				response.end("raise was successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/allIn/:username/:gameID").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			try {
				hand.handleAllIn(username, gameID);
				HttpServerResponse response = routingContext.response();
				response.end("allIn was successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/fold/:username/:gameID").handler(routingContext -> {
			String username = routingContext.request().getParam("username");
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			try {
				hand.handleFold(username, gameID);
				HttpServerResponse response = routingContext.response();
				response.end("fold was successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		router.route("/terminateGame/:gameID").handler(routingContext -> {
			int gameID = Integer.parseInt(routingContext.request().getParam("gameID"));
			hand.handleTerminateGame(gameID);
			HttpServerResponse response = routingContext.response();
			response.end("terminate game was successful");
		});

		router.route("/clearLoggedInUsers").handler(routingContext -> {
			hand.handleClearLoggedInUsers();
			HttpServerResponse response = routingContext.response();
			response.end("clear logged in users was successful");
		});

		router.route("/clearUsers").handler(routingContext -> {
			hand.handleClearUsers();
			HttpServerResponse response = routingContext.response();
			response.end("clear users was successful");
		});

		router.route("/clearAllFinishedGameLogs").handler(routingContext -> {
			hand.handleClearAllFinishedGameLogs();
			HttpServerResponse response = routingContext.response();
			response.end("clear all finished game logs was successful");
		});

		vertx.createHttpServer()
				.requestHandler(router::accept)
				.listen(
						// Retrieve the port from the configuration,
						// default to 8080.
						config().getInteger("http.port", 8081),
						result -> {
							if (result.succeeded()) {
								Enumeration e = null;
								try {
									e = NetworkInterface.getNetworkInterfaces();
								} catch (SocketException e1) {
									e1.printStackTrace();
								}
								while(e.hasMoreElements())
								{
									NetworkInterface n = (NetworkInterface) e.nextElement();
									Enumeration ee = n.getInetAddresses();
									while (ee.hasMoreElements())
									{
										InetAddress i = (InetAddress) ee.nextElement();
										System.out.println(i.getHostAddress());
									}
								}
								System.out.println("server is up!");
								fut.complete();
							} else {
								System.out.println("server is down!");
								fut.fail(result.cause());
							}
						}
				);

        /* create a very simple http-server that accepts anything */
      /*  vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        }).listen(8080);*/



	}
}


