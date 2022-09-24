package me.shedaniel.betterloadingscreen.quilt;

import net.fabricmc.api.EnvType;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;

import java.lang.reflect.InvocationTargetException;

public class BetterLoadingScreenQuiltPreInit implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch(ModContainer mod) {
        if (MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT) {
            try {
                Class.forName("me.shedaniel.betterloadingscreen.quilt.BetterLoadingScreenQuiltPreInitClient")
                        .getDeclaredMethod("onPreLaunch")
                        .invoke(null);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
