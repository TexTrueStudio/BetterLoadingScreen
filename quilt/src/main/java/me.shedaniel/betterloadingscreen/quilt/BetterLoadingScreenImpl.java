package me.shedaniel.betterloadingscreen.quilt;

import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class BetterLoadingScreenImpl {
    public static Path getConfigDir() {
        return QuiltLoader.getConfigDir();
    }
}
