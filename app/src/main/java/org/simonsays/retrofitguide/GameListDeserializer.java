//package org.simonsays.retrofitguide;
//
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParseException;
//
//import org.simonsays.retrofitguide.models.GameDetails;
//import org.simonsays.retrofitguide.models.GameOverview;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//
//public class GameListDeserializer implements JsonDeserializer {
//
//    @Override
//    public GameOverview deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
//            throws JsonParseException {
//        final JsonObject jsonObject = json.getAsJsonObject();
//
//        final GameOverview gameOverview = new GameOverview();
//
//        gameOverview.setViewers(jsonObject.get("viewers").getAsInt());
//        gameOverview.setChannels(jsonObject.get("channels").getAsInt());
//        gameOverview.setGameDetailsDetails(jsonObject.get("game"));
//        public ArrayList<GameDetails> gameDetailsDetails;
//
//        author.setId(jsonObject.get("id").getAsInt());
//        author.setName(jsonObject.get("name").getAsString());
//        return author;
//    }
//}
