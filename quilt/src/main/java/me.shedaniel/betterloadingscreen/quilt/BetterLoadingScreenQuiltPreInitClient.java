package me.shedaniel.betterloadingscreen.quilt;

import me.shedaniel.betterloadingscreen.BetterLoadingScreen;
import me.shedaniel.betterloadingscreen.BetterLoadingScreenClient;
import me.shedaniel.betterloadingscreen.EarlyGraphics;
import me.shedaniel.betterloadingscreen.Tasks;
import me.shedaniel.betterloadingscreen.launch.EarlyWindow;
import me.shedaniel.betterloadingscreen.launch.early.BackgroundRenderer;
//import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.QuiltLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class BetterLoadingScreenQuiltPreInitClient {
    public static final Logger LOGGER = LogManager.getLogger(BetterLoadingScreenQuiltPreInitClient.class);
    
    public static void onPreLaunch() {
        if (QuiltLoader.isModLoaded("dashloader")) {
            LOGGER.info("Found DashLoader");
            Tasks.MAIN.reset(Tasks.LAUNCH_COUNT(false) + 1);
        } else {
            Tasks.MAIN.reset(Tasks.LAUNCH_COUNT(false));
        }
        EarlyGraphics.resolver = url -> {
            Path path = QuiltLoader.getModContainer("minecraft").get().getPath(url);
            if (path != null && Files.exists(path)) {
                try {
                    return Files.newInputStream(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            InputStream stream = BetterLoadingScreenQuiltPreInit.class.getClassLoader().getResourceAsStream(url);
            if (stream != null) {
                return stream;
            }
            throw new RuntimeException("Resource not found: " + url);
        };
        BetterLoadingScreenClient.inDev = QuiltLoader.isDevelopmentEnvironment();
        
        BackgroundRenderer renderer = BackgroundRenderer.DEFAULT;
        
        if (BetterLoadingScreen.CONFIG.detectKubeJS && QuiltLoader.isModLoaded("kubejs")) {
            LOGGER.info("[BetterLoadingScreen] Detected KubeJS, inheriting KubeJS options!");
            renderer = Objects.requireNonNullElse(BackgroundRenderer.useKubeJs(QuiltLoader.getGameDir()), renderer);
        }
        
        if (Files.exists(BetterLoadingScreen.BACKGROUND_PATH)) {
            LOGGER.info("[BetterLoadingScreen] Detected background image, using it!");
            renderer = BackgroundRenderer.wrapWithBackground(renderer, BetterLoadingScreen.BACKGROUND_PATH);
        }
        
        BetterLoadingScreenClient.renderer = renderer;
        
        if (BetterLoadingScreen.isEarlyLoadingEnabled()) {
            EarlyWindow.start(QuiltLoader.getLaunchArguments(true), QuiltLoader.getGameDir(), String.valueOf(QuiltLoader.getModContainer("minecraft").get()
                    .metadata().version()), renderer, true);
        }
    }
}
