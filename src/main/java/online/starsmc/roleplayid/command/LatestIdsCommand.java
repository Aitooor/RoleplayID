package online.starsmc.roleplayid.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Optional;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.handler.PlaceholderHandler;
import online.starsmc.roleplayid.user.UserManager;
import online.starsmc.roleplayid.user.UserModel;
import online.starsmc.roleplayid.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Command(value = "latestids")
@Permission("basictp.admin")
public class LatestIdsCommand extends BaseCommand {

    @Inject private Main plugin;
    @Inject private UserManager userManager;
    @Inject private PlaceholderHandler placeholderHandler;

    @Default()
    public void noArg(CommandSender sender, @Suggestion("players") String target, @Suggestion("pages") String page) {
        if(target.isEmpty()) {
            ChatUtil.sendMsgPlayerPrefix(sender, "&cUse: /latestids (player) 1/4");
            return;
        }

        if(page.isEmpty()) {
            ChatUtil.sendMsgPlayerPrefix(sender, "&cUse: /latestids (player) 1/4");
            return;
        }

        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target);
        UserModel userModel = userManager.getModel(targetPlayer.getUniqueId().toString());
        int pageInt = Integer.parseInt(page);

        if (userManager.getModel(targetPlayer.getUniqueId().toString()) == null) {
            ChatUtil.sendMsgPlayerPrefix(sender, "&b" + targetPlayer.getName() + " &cnever join");
            return;
        }

        List<Integer> latestGameIds = userModel.getLatestGameIds();
        int totalElements = latestGameIds.size();
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        if (pageInt < 1 || pageInt > totalPages) {
            ChatUtil.sendMsgPlayerPrefix(sender, "&cInvalid page number. Type from 1 to 4");
            return;
        }

        ChatUtil.sendMsgPlayer(Objects.requireNonNull(targetPlayer.getPlayer()), "");
        ChatUtil.sendMsgPlayer(Objects.requireNonNull(targetPlayer.getPlayer()), "&7Latest IDs of " + targetPlayer.getName());
        ChatUtil.sendMsgPlayer(Objects.requireNonNull(targetPlayer.getPlayer()), "");

        int startIndex = (pageInt - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalElements);
        List<Integer> group = latestGameIds.subList(startIndex, endIndex);

        group.forEach(latestGameId ->
            ChatUtil.sendMsgPlayer(Objects.requireNonNull(targetPlayer.getPlayer()), "&8- &b" + latestGameId.toString())
        );

        ChatUtil.sendMsgPlayer(Objects.requireNonNull(targetPlayer.getPlayer()), "&7Page &8- &f" + pageInt + "&8/&f" + totalPages);
        ChatUtil.sendMsgPlayer(Objects.requireNonNull(targetPlayer.getPlayer()), "");
    }

}
