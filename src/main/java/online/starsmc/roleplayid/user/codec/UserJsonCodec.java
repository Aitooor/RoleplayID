package online.starsmc.roleplayid.user.codec;

import com.google.gson.*;
import online.starsmc.roleplayid.user.UserModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserJsonCodec implements JsonSerializer<UserModel>, JsonDeserializer<UserModel> {

    @Override
    public JsonElement serialize(UserModel model, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject serializedModel = new JsonObject();

        serializedModel.addProperty("id", model.getId());
        serializedModel.addProperty("realName", model.getRealName());
        serializedModel.addProperty("gameId", model.getGameId());

        JsonArray serializedItems = new JsonArray();
        for(Integer item : model.getLatestGameIds()) {
            serializedItems.add(item);
        }
        serializedModel.add("latestGameIds", serializedItems);

        return serializedModel;
    }

    @Override
    public UserModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject serializedModel = json.getAsJsonObject();

        String id = serializedModel.get("id").getAsString();
        UUID uuid = UUID.fromString(id);
        String realName = serializedModel.get("realName").getAsString();
        int gameId = serializedModel.get("gameId").getAsInt();

        JsonArray itemsArray = serializedModel.get("latestGameIds").getAsJsonArray();
        List<Integer> latestGameIds = new ArrayList<>();
        for(JsonElement item : itemsArray) {
            latestGameIds.add(item.getAsInt());
        }

        return new UserModel(uuid, realName, gameId, latestGameIds);
    }
}
