package online.starsmc.roleplayid.user;

import online.starsmc.roleplayid.model.Model;

import java.util.UUID;


public class UserModel implements Model {

    private final UUID uuid;
    private String realName;
    private int gameId;

    public UserModel(UUID uuid, String realName, int gameId) {
        this.uuid = uuid;
        this.realName = realName;
        this.gameId = gameId;
    }

    @Override
    public String getId() {
        return getUuid().toString();
    }

    public UUID getUuid() { return uuid; }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
