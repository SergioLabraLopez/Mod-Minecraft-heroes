package com.sergio.labraheroes.network;

import com.sergio.labraheroes.labraheroes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    // Creamos nuestro canal de comunicación
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(labraheroes.MODID + "main"), // <--- Así ya no se quejará
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        // El paquete de encender/apagar traje (ID 0)
        INSTANCE.registerMessage(id(), ToggleSuitC2SPacket.class,
                ToggleSuitC2SPacket::encode,
                ToggleSuitC2SPacket::new,
                ToggleSuitC2SPacket::handle);

        // NUEVO: El paquete de disparar el láser (ID 1)
        INSTANCE.registerMessage(id(), FireLaserC2SPacket.class,
                FireLaserC2SPacket::encode,
                FireLaserC2SPacket::new,
                FireLaserC2SPacket::handle);
    }
}