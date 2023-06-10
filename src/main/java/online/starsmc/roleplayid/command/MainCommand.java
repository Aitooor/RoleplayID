package online.starsmc.roleplayid.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.*;
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
import java.util.List;

@Command(value = "roleplayid")
@Permission("roleplayid.admin")
public class MainCommand extends BaseCommand {

    @Inject private Main plugin;
    @Inject private UserManager userManager;
    @Inject private PlaceholderHandler placeholderHandler;

    @SubCommand(value = "help")
    @Permission(value = "roleplayid.help")
    public void help(CommandSender sender) {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("&eHelp of RoleplayID command");
        list.add("  &7All commands have autocomplete with tab key");
        list.add("");
        list.add(" &8- &a/roleplayid help &8- &7Show this help message");
        list.add(" &8- &a/roleplayid reloadall &8- &7Reload the game id for all players online");
        list.add(" &8- &a/roleplayid reload_player &8- &7Reload the game id for target player");
        list.add(" &8- &a/roleplayid check &8- &7Check the game id of the target player");
        list.add(" &8- &a/latestids (targetPlayer) 1/4 &8- &7Check a list of last ids of the target player");
        list.add("");
        list.forEach(s -> ChatUtil.sendMsgPlayer(sender, s));
    }

    @SubCommand(value = "reloadall")
    @Permission(value = "roleplayid.reloadall")
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
    @Permission(value = "roleplayid.reload_player")
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
    @Permission(value = "roleplayid.check")
    public void check(CommandSender sender, @Optional @Suggestion("players") String target) {
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
