package me.shedaniel.betterloadingscreen.quilt;

import me.shedaniel.betterloadingscreen.BetterLoadingScreen;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class BetterLoadingScreenQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        BetterLoadingScreen.init();
    }
}
