package online.starsmc.roleplayid.module.submodule;

import online.starsmc.roleplayid.listener.PlayerListener;
import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {
    @Override
    protected void configure() {
        multibind(Listener.class).asSet()
                .to(PlayerListener.class);
    }
}
