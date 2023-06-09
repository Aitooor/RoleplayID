package online.starsmc.roleplayid.user;

import online.starsmc.roleplayid.model.Model;

import java.util.List;
import java.util.UUID;


public class UserModel implements Model {

    private final UUID uuid;
    private String realName;
    private int gameId;
    private final List<Integer> latestGameIds;

    public UserModel(UUID uuid, String realName, int gameId, List<Integer> latestGameIds) {
        this.uuid = uuid;
        this.realName = realName;
        this.gameId = gameId;
        this.latestGameIds = latestGameIds;
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

    public List<Integer> getLatestGameIds() {
        return latestGameIds;
    }
}
