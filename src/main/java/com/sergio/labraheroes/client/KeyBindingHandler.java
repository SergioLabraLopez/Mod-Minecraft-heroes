package com.sergio.labraheroes.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.sergio.labraheroes.labraheroes;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

// Esta clase solo se carga en el Cliente (tu pantalla), no en el servidor
@Mod.EventBusSubscriber(modid = labraheroes.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindingHandler {

    // El botón que ya tenías para encender la armadura
    public static final KeyMapping TOGGLE_SUIT_KEY = new KeyMapping(
            "key.labraheroes.toggle_suit",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.labraheroes"
    );

    // NUEVO: Botón para el ataque (Por defecto la "R")
    public static final KeyMapping MAIN_ATTACK_KEY = new KeyMapping(
            "key.labraheroes.main_attack",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.labraheroes"
    );

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_SUIT_KEY);
        event.register(MAIN_ATTACK_KEY); // Registramos el nuevo
    }
}