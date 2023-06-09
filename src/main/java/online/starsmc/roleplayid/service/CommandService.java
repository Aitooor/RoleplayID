package online.starsmc.roleplayid.service;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.BaseCommand;
import online.starsmc.roleplayid.Main;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.Set;

public class CommandService implements Service{
    @Inject private Set<BaseCommand> commands;

    @Inject private Main plugin;

    @Override
    public void start() {
        BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(plugin);

        commands.forEach(manager::registerCommand);
    }

    @Override
    public void stop() {
        BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(plugin);

        commands.forEach(manager::unregisterCommand);
    }
}
