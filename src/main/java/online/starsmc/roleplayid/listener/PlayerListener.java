package online.starsmc.roleplayid.listener;

import dev.triumphteam.cmd.core.BaseCommand;
import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.handler.PlaceholderHandler;
import online.starsmc.roleplayid.user.UserManager;
import online.starsmc.roleplayid.user.UserModel;
import online.starsmc.roleplayid.util.UniqueIDGenerator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;

import javax.inject.Inject;
import java.util.Set;

public class PlayerListener implements Listener {

    @Inject private Set<BaseCommand> commands;
    @Inject private Main plugin;
    @Inject private UserManager userManager;
    @Inject private PlaceholderHandler placeholderHandler;

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = userManager.getModel(player.getUniqueId().toString());

        if(userModel == null) {
            userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID());
            userManager.create(player, userModel);
            return;
        }

        userManager.setGameId(player, player,new UniqueIDGenerator().generatePositiveUniqueID());
        userManager.setRealName(player);
    }

    @EventHandler
    public void onPluginDisableEvent(PluginDisableEvent event) {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                UserModel userModel = userManager.getModel(player.getUniqueId().toString());

                if (userModel == null) {
                    userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID());
                    userManager.create(player, userModel);
                    return;
                }

                userManager.reloadCache(player);
            });
        }
        placeholderHandler.unregister();
    }
}
