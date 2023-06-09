package online.starsmc.roleplayid.module;

import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import online.starsmc.roleplayid.Main;
import online.starsmc.roleplayid.handler.PlaceholderHandler;
import online.starsmc.roleplayid.model.repository.CachedModelRepository;
import online.starsmc.roleplayid.model.repository.GuavaModelRepository;
import online.starsmc.roleplayid.model.repository.JsonModelRepository;
import online.starsmc.roleplayid.module.submodule.CommandModule;
import online.starsmc.roleplayid.module.submodule.ListenerModule;
import online.starsmc.roleplayid.module.submodule.ServiceModule;
import online.starsmc.roleplayid.user.UserModel;
import online.starsmc.roleplayid.user.UserManager;
import online.starsmc.roleplayid.user.codec.UserJsonCodec;
import team.unnamed.inject.AbstractModule;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class PluginModule extends AbstractModule {
    private final Main plugin;
    private final UserManager userManager;
    private final PlaceholderHandler placeholderHandler;

    public PluginModule(Main plugin) {
        this.plugin = plugin;

        File pluginFolder = plugin.getDataFolder();
        Path pluginPath = pluginFolder.toPath();
        Gson createGson = new GsonBuilder()
                .registerTypeAdapter(UserModel.class, new UserJsonCodec())
                .setPrettyPrinting()
                .create();

        CachedModelRepository<UserModel> userCachedModelRepository = new CachedModelRepository<>(
                new JsonModelRepository<>(pluginPath.resolve("users"), UserModel.class, createGson),
                new GuavaModelRepository<>(
                        CacheBuilder.newBuilder()
                                .expireAfterAccess(10, TimeUnit.MINUTES)
                                .expireAfterWrite(10, TimeUnit.MINUTES)
                                .build()
                )
        );

        this.userManager = new UserManager(plugin, userCachedModelRepository);
        this.placeholderHandler = new PlaceholderHandler(plugin, userManager);
    }

    @Override
    protected void configure() {
        bind(Main.class).toInstance(plugin);
        bind(UserManager.class).toInstance(userManager);
        bind(PlaceholderHandler.class).toInstance(placeholderHandler);

        install(new ServiceModule());
        install(new CommandModule());
        install(new ListenerModule());

        placeholderHandler.register();
    }
}
