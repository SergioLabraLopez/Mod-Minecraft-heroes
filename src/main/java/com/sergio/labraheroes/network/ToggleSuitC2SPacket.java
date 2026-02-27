package com.sergio.labraheroes.network;

import com.sergio.labraheroes.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ToggleSuitC2SPacket {

    public ToggleSuitC2SPacket() {}
    public ToggleSuitC2SPacket(FriendlyByteBuf buf) {}
    public void encode(FriendlyByteBuf buf) {}

    // ESTO ES LO QUE HACE EL SERVIDOR CUANDO RECIBE EL MENSAJE
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Sacamos al jugador que ha pulsado la tecla
            ServerPlayer player = context.getSender();
            if (player != null) {
                // 1. Comprobamos su ranura del pecho (índice 2 en la armadura)
                ItemStack chestplate = player.getInventory().getArmor(2);

                // 2. ¿Es la pechera de Visión?
                if (chestplate.getItem() == ModItems.VISION_CHESTPLATE.get()) {

                    // 3. Leemos su estado actual (por defecto es false)
                    boolean isCurrentlyActive = chestplate.getOrCreateTag().getBoolean("SuitActive");

                    // 4. Lo invertimos (si estaba apagado, lo enciende, y viceversa)
                    boolean newState = !isCurrentlyActive;
                    chestplate.getTag().putBoolean("SuitActive", newState);

                    // 5. Le avisamos al jugador por el chat de momento
                    if (newState) {
                        player.sendSystemMessage(Component.literal("§a[Visión] Sistemas Activados."));
                    } else {
                        player.sendSystemMessage(Component.literal("§c[Visión] Sistemas Desactivados."));
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}