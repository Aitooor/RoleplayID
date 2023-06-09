package online.starsmc.roleplayid.module.submodule;

import dev.triumphteam.cmd.core.BaseCommand;
import online.starsmc.roleplayid.command.LatestIdsCommand;
import online.starsmc.roleplayid.command.MainCommand;
import team.unnamed.inject.AbstractModule;

public class CommandModule extends AbstractModule {

        @Override
        protected void configure() {
            multibind(BaseCommand.class)
                    .asSet()
                    .to(MainCommand.class)
                    .to(LatestIdsCommand.class);
        }
}
