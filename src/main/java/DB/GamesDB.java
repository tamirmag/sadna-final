package DB;

import Games.DeckManager;
import Games.Game;
import Games.IGame;
import Games.Policy;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by tamir on 05/06/2017.
 */
public class GamesDB implements IGamesDB{
    private static final GamesDB instance = new GamesDB();

    Morphia morphia;
    MongoClient m;
    Datastore datastore;

    private GamesDB() {
        morphia = new Morphia();
        morphia.mapPackage("Games");
        m = new MongoClient("localhost", 27017);
        datastore = morphia.createDatastore(m, "systemDatabase");
    }

    public static GamesDB getInstance() {
        return instance;
    }

    @Override
    public IGame getGame(int id) {
        final Query<Policy> query = datastore.createQuery(Policy.class).filter("id =", id);
        List<Policy> games = query.asList();
        if(games.size() == 0)
            return null;
        return games.get(0);
    }

    @Override
    public void save(IGame game) {
        datastore.save(game);
    }
}
