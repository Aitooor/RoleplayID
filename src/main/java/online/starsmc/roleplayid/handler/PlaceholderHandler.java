package online.starsmc.roleplayid.handler;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.user.UserModel;
import online.starsmc.roleplayid.user.UserManager;
import online.starsmc.roleplayid.util.UniqueIDGenerator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlaceholderHandler extends PlaceholderExpansion {

    private final Main plugin;
    private final UserManager userManager;

    public PlaceholderHandler(Main plugin, UserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        UserModel userModel = userManager.getModel(player.getUniqueId().toString());

        // %roleplayid_gameId%
        if (params.equalsIgnoreCase("gameId")) {
            if(userModel == null) {
                userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
                userManager.create(player, userModel);
            }

            return String.valueOf(userModel.getGameId());
        }

        // %roleplayid_realname%
        if (params.equalsIgnoreCase("realname")) {
            if(userModel == null) {
                userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
                userManager.create(player, userModel);
            }

            return userModel.getRealName();
        }

        return "Placeholder not found";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "roleplayid";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.valueOf(plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
