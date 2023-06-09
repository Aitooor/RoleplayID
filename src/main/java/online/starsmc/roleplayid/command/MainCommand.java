package online.starsmc.roleplayid.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Optional;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.handler.PlaceholderHandler;
import online.starsmc.roleplayid.user.UserManager;
import online.starsmc.roleplayid.user.UserModel;
import online.starsmc.roleplayid.util.ChatUtil;
import online.starsmc.roleplayid.util.UniqueIDGenerator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.ArrayList;

@Command(value = "roleplayid")
@Permission("basictp.admin")
public class MainCommand extends BaseCommand {

    @Inject private Main plugin;
    @Inject private UserManager userManager;
    @Inject private PlaceholderHandler placeholderHandler;

    @Default()
    public void noArg(CommandSender sender){
        ChatUtil.sendMsgPlayerPrefix(sender, "&cUse: /roleplayid check (targetPlayer)");
    }

    @SubCommand(value = "reload")
    public void reload(CommandSender sender) {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            new UniqueIDGenerator().clearGeneratedIds();
            Bukkit.getOnlinePlayers().forEach(player -> {
                UserModel userModel = userManager.getModel(player.getUniqueId().toString());

                if (userModel == null) {
                    userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
                    userManager.create(player, userModel);
                    return;
                }

                userManager.reloadCache(player);
            });
        }
        ChatUtil.sendMsgPlayerPrefix(sender, "&aPlugin reloaded correctly");
    }

    @SubCommand(value = "reload_player")
    public void reloadPlayer(CommandSender sender, Player player) {
        UserModel userModel = userManager.getModel(player.getUniqueId().toString());

        if (userModel == null) {
            userModel = new UserModel(player.getUniqueId(), player.getName(), new UniqueIDGenerator().generatePositiveUniqueID(), new ArrayList<>());
            userManager.create(player, userModel);
            return;
        }

        userManager.reloadCache(player);
        ChatUtil.sendMsgPlayerPrefix(sender, "&fChanged id to &b" + player.getName());
    }

    @SubCommand(value = "check")
    public void check(CommandSender sender, @Optional String target) {
        if(target == null) {
            ChatUtil.sendMsgPlayerPrefix(sender, "&cUse: /roleplayid check (targetPlayer)");
            return;
        }

        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target);

        if (userManager.getModel(targetPlayer.getUniqueId().toString()) == null) {
            ChatUtil.sendMsgPlayerPrefix(sender, "&b" + targetPlayer.getName() + " &cnever join");
            return;
        }
        ChatUtil.sendMsgPlayerPrefix(sender, "&b" + targetPlayer.getName() + " &fplayer game id is &e" + userManager.getGameId(sender, targetPlayer));
    }
}
