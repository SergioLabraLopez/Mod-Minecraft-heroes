package com.sergio.labraheroes.client;

import com.sergio.labraheroes.labraheroes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// Bus.MOD significa que se carga cuando el juego se está abriendo
@Mod.EventBusSubscriber(modid = labraheroes.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        // Registramos nuestro HUD por encima de todo ("Above All")
        event.registerAboveAll("vision_hud", VisionHudOverlay.HUD_VISION);
    }
}