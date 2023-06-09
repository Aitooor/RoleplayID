package online.starsmc.roleplayid.module.submodule;

import online.starsmc.roleplayid.service.CommandService;
import online.starsmc.roleplayid.service.ListenerService;
import online.starsmc.roleplayid.service.Service;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
  @Override
  protected void configure() {
    multibind(Service.class)
            .asSet()
            .to(CommandService.class)
            .to(ListenerService.class);
  }
}
