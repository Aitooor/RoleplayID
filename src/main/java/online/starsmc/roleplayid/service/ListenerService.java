package online.starsmc.roleplayid.service;

import online.starsmc.roleplayid.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import javax.inject.Inject;
import java.util.Set;

public class ListenerService implements Service {

    @Inject private Set<Listener> listeners;
    @Inject private Main plugin;

    @Override
    public void start() {
        this.listeners.forEach(it -> Bukkit.getPluginManager().registerEvents(it, this.plugin));
    }
}
