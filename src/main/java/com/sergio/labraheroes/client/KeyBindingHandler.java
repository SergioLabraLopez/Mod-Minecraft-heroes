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

    // Creamos nuestra tecla. Por defecto será la "V" (de Visión)
    public static final KeyMapping TOGGLE_SUIT_KEY = new KeyMapping(
            "key.labraheroes.toggle_suit", // El nombre interno
            KeyConflictContext.IN_GAME, // Solo funciona jugando, no en menús
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V, // Tecla V por defecto
            "key.categories.labraheroes" // Categoría en el menú de opciones
    );

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_SUIT_KEY);
    }
}