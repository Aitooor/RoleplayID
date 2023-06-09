package online.starsmc.roleplayid.user.codec;

import com.google.gson.*;
import online.starsmc.roleplayid.user.UserModel;

import java.lang.reflect.Type;
import java.util.UUID;

public class UserJsonCodec implements JsonSerializer<UserModel>, JsonDeserializer<UserModel> {

    @Override
    public JsonElement serialize(UserModel model, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject serializedModel = new JsonObject();

        serializedModel.addProperty("id", model.getId());
        serializedModel.addProperty("realName", model.getRealName());
        serializedModel.addProperty("gameId", model.getGameId());

        return serializedModel;
    }

    @Override
    public UserModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject serializedModel = json.getAsJsonObject();

        String id = serializedModel.get("id").getAsString();
        UUID uuid = UUID.fromString(id);
        String realName = serializedModel.get("realName").getAsString();
        int gameId = serializedModel.get("gameId").getAsInt();

        return new UserModel(uuid, realName, gameId);
    }
}
