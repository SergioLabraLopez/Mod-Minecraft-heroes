package com.sergio.labraheroes.client;

import com.sergio.labraheroes.labraheroes;
import com.sergio.labraheroes.network.FireLaserC2SPacket;
import com.sergio.labraheroes.network.PacketHandler;
import com.sergio.labraheroes.network.ToggleSuitC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = labraheroes.MODID, value = Dist.CLIENT)
public class ClientHooks {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {

            // Si pulsas la "V" (Encender traje)
            while (KeyBindingHandler.TOGGLE_SUIT_KEY.consumeClick()) {
                if (Minecraft.getInstance().player != null) {
                    PacketHandler.INSTANCE.sendToServer(new ToggleSuitC2SPacket());
                }
            }

            // Si pulsas la "R" (Ataque principal)
            while (KeyBindingHandler.MAIN_ATTACK_KEY.consumeClick()) {
                if (Minecraft.getInstance().player != null) {
                    // ¡Enviamos la orden de disparar el rayo al servidor!
                    PacketHandler.INSTANCE.sendToServer(new FireLaserC2SPacket());
                }
            }
        }
    }
}