package online.starsmc.roleplayid.user;

import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.model.repository.CachedModelRepository;
import online.starsmc.roleplayid.util.ChatUtil;
import online.starsmc.roleplayid.util.UniqueIDGenerator;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class UserManager {

    private final Main plugin;
    private final CachedModelRepository<UserModel> cachedRepository;

    public UserManager(Main plugin, CachedModelRepository<UserModel> cachedRepository) {
        this.plugin = plugin;
        this.cachedRepository = cachedRepository;
    }

    public UserModel getModel(String id) {
        try {
            UserModel userModel = cachedRepository.getOrFind(id);
            if(userModel != null) {
                return userModel;
            }
        } catch (Exception ignored) {}
        return null;
    }

    public void create(Player player, UserModel userModel) {
        try {
            cachedRepository.saveInBoth(userModel);
            ChatUtil.sendMsgPlayerPrefix(player, "&aSaved correctly");
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cError, can't create the kit");
            plugin.getLogger().log(Level.WARNING, "Error, can't create the kit", e);
        }
    }

    public void remove(Player player) {
        try {
            UserModel userModel = this.getModel(player.getUniqueId().toString());

            if(userModel == null) {
                ChatUtil.sendMsgPlayerPrefix(player, "&cThat user not exist");
                return;
            }
            cachedRepository.removeInBoth(userModel);
            ChatUtil.sendMsgPlayerPrefix(player, "&cRemoved correctly");
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't remove the user");
            plugin.getLogger().log(Level.WARNING, "Error, can't remove the user", e);
        }
    }

    public String getRealName(Player player) {
        try {
            UserModel userModel = this.getModel(player.getUniqueId().toString());

            if(userModel == null) {
                userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            }

            return userModel.getRealName();
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't get the user real name");
            plugin.getLogger().log(Level.WARNING, "Error, can't get the user real name", e);
        }
        return null;
    }

    public void setRealName(Player player) {
        try {
            UserModel userModel = this.getModel(player.getUniqueId().toString());

            if(userModel == null) {
                userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            }

            userModel.setRealName(player.getName());
            cachedRepository.saveInBoth(userModel);
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't set the user real name");
            plugin.getLogger().log(Level.WARNING, "Error, can't set the user real name", e);
        }
    }

    public int getGameId(CommandSender player, OfflinePlayer targetPlayer) {
        try {
            UserModel userModel = this.getModel(targetPlayer.getUniqueId().toString());

            if(userModel == null) {
                userModel = new UserModel(targetPlayer.getUniqueId(), targetPlayer.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            }

            return userModel.getGameId();
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't get the user real name");
            plugin.getLogger().log(Level.WARNING, "Error, can't get the user real name", e);
        }

        return 0;
    }

    public void setGameId(Player player, OfflinePlayer targetPlayer, int gameId) {
        try {
            UserModel userModel = this.getModel(targetPlayer.getUniqueId().toString());

            if(userModel == null) {
                userModel = new UserModel(targetPlayer.getUniqueId(), targetPlayer.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            }

            userModel.setGameId(gameId);
            cachedRepository.saveInBoth(userModel);
            //ChatUtil.sendMsgPlayerPrefix(player, "&7New id &f" + gameId);
            plugin.getLogger().log(Level.INFO, "New id " + gameId + " for player " + targetPlayer.getName() + "(" + targetPlayer.getUniqueId() + ")");
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't set the user game id " + gameId);
            plugin.getLogger().log(Level.WARNING, "Error, can't set the user game id " + gameId, e);
        }
    }

    public void addLatestGameId(Player player, OfflinePlayer targetPlayer) {
        try {
            UserModel userModel = this.getModel(targetPlayer.getUniqueId().toString());

            if (userModel == null) {
                userModel = new UserModel(targetPlayer.getUniqueId(), targetPlayer.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            }

            int gameId = userModel.getGameId();
            List<Integer> latestGameIds = userModel.getLatestGameIds();

            if (latestGameIds.size() == 20) {
                latestGameIds.remove(0);
            }

            latestGameIds.add(gameId);
            cachedRepository.saveInBoth(userModel);
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't add game ID to the user's latest list");
            plugin.getLogger().log(Level.WARNING, "Error: Can't add game ID to the user's latest list", e);
        }
    }

    public void reloadCache(Player player) {
        try {
            UserModel userModel = this.getModel(player.getUniqueId().toString());

            if(userModel == null) {
                userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            }

            cachedRepository.removeInCache(userModel);

            this.addLatestGameId(player, player);
            this.setGameId(player, player, new UniqueIDGenerator().generatePositiveUniqueID());
            this.setRealName(player);
        } catch (Exception e) {
            ChatUtil.sendMsgPlayerPrefix(player, "&cCan't reload user " + player.getName());
            plugin.getLogger().log(Level.WARNING, "Error, can't reload user " + player.getName());
        }
    }
}
