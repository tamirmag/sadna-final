package ServerClient;
//package ServiceLayer;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ServiceLayer.ServiceUser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import GUI.gameGrid;
//import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import Games.Card;

public class Http_Middle_Server_Of_Client extends AbstractVerticle {

	int port;
	
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Http_Middle_Server_Of_Client(8089));
    }
    
    public Http_Middle_Server_Of_Client(int port){
    	this.port=port;
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

        router.route("/getGameContent/:game/:content").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String content = routingContext.request().getParam("content");
            try {
            	
            	//call a func that prints in the GUI the content.
            	//activate_get_game_content(game,content);
            
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        });

        router.route("/getCards/:game/:s").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String s = routingContext.request().getParam("s");
			try {
				Gson gson=new GsonBuilder().create();
				ArrayList<Card> cards = gson.fromJson(s,new TypeToken<ArrayList<Card>>(){}.getType());
				
				// call to func in GUI that will display the "cards" arrayList.
				//activate_get_cards(game,cards);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        
        router.route("/getGameState/:game/:s").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String s = routingContext.request().getParam("s");
            try {

            	// convert gamestate to something readable and call a 
            	// func that prints it in the GUI .
            	
            	//activate_get_game_state(game,s); the convertion in GUI
            	
            	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        router.route("/SomeoneFolded/:game/:foldedPlayer").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String foldedPlayer = routingContext.request().getParam("foldedPlayer");
            try {
            	// call func that prints in GUI that "foldedPlayer" has folded in game no. "game".
            	//activate_someone_folded(game,foldedPlayer);
            	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        router.route("/SomeoneChecked/:game/:checkPlayer").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String checkPlayer = routingContext.request().getParam("checkPlayer");
			try {
				// call func that prints in GUI that "checkPlayer" has checked in game no. "game".
				//activate_someone_checked(game,checkPlayer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
      
        router.route("/SomeoneAllIn/:game/:allinPlayer").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String allinPlayer = routingContext.request().getParam("allinPlayer");
        	try {

				// call func that prints in GUI that "allinPlayer" has alledIN in game no. "game".
				//activate_someone_allin(game,allinPlayer);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

        router.route("/winner/:game/:winnerPlayer").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String winnerPlayer = routingContext.request().getParam("winnerPlayer");
			try {

				// call func that prints in GUI that "winnerPlayer" has won in game no. "game".
				//activate_winner(game,winnerPlayer);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
       
        router.route("/SomeoneRaised/:game/:raisedPlayer/:amount").handler(routingContext -> {
            int game = Integer.parseInt(routingContext.request().getParam("game"));
            String raisedPlayer = routingContext.request().getParam("raisedPlayer");
            int amount = Integer.parseInt(routingContext.request().getParam("amount"));
            try {
            	
				// call func that prints in GUI that "raisedPlayer" has raised in game no. "game" in amount....
				//activate_someone_raised(game,raisedPlayer,amount);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

        	router.route("/SomeoneBet/:game/:betPlayer/:amount").handler(routingContext -> {
                int game = Integer.parseInt(routingContext.request().getParam("game"));
                String betPlayer = routingContext.request().getParam("betPlayer");
                int amount = Integer.parseInt(routingContext.request().getParam("amount"));
        	try {
        		
				//activate_someone_bet(game,betPlayer,amount);
			
        	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        	
        	router.route("/SomeoneCall/:game/:callPlayer/:amount").handler(routingContext -> {
                int game = Integer.parseInt(routingContext.request().getParam("game"));
                String callPlayer = routingContext.request().getParam("callPlayer");
                int amount = Integer.parseInt(routingContext.request().getParam("amount"));
        	try {

        		//activate_someone_call(game,callPlayer,amount);
        		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        

         vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8089),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
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


