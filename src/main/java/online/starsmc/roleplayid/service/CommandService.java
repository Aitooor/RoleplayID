package online.starsmc.roleplayid.service;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandService implements Service{
    @Inject private Set<BaseCommand> commands;

    @Inject private Main plugin;

    @Override
    public void start() {
        BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(plugin);

        this.registerSuggestions(manager);
        this.registerKeys(manager);

        commands.forEach(manager::registerCommand);
    }

    @Override
    public void stop() {
        BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(plugin);

        commands.forEach(manager::unregisterCommand);
    }

    public void registerSuggestions(BukkitCommandManager<CommandSender> manager) {
        manager.registerSuggestion(SuggestionKey.of("players"), (sender, context) -> {
            List<String> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));
            return players;
        });
        manager.registerSuggestion(SuggestionKey.of("pages"), (sender, context) -> {
            List<String> pages = new ArrayList<>();
            pages.add("1");
            pages.add("2");
            pages.add("3");
            pages.add("4");
            return pages;
        });
    }

    public void registerKeys(BukkitCommandManager<CommandSender> manager) {
        manager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, context) -> {
            ChatUtil.sendMsgPlayer(sender, "");
            ChatUtil.sendMsgPlayer(sender, "&7Use space and tab key for auto complete.");
            if(context.getSubCommand().isEmpty()) {
                ChatUtil.sendMsgPlayer(sender, "&cLike: /" + context.getCommand() + " (args)");
            } else {
                ChatUtil.sendMsgPlayer(sender, "&cLike: /" + context.getCommand() + " " + context.getSubCommand() + " (args)");
            }
            ChatUtil.sendMsgPlayer(sender, "");
        });
        manager.registerMessage(MessageKey.UNKNOWN_COMMAND, (sender, context) -> {
            ChatUtil.sendMsgPlayer(sender, "");
            ChatUtil.sendMsgPlayer(sender, "&7Use space and tab key for auto complete.");
            ChatUtil.sendMsgPlayer(sender, "&cLike: /" + context.getCommand() + " (args)");
            ChatUtil.sendMsgPlayer(sender, "");
        });
    }
}
