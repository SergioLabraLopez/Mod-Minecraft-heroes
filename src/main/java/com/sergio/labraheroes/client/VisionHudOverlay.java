package com.sergio.labraheroes.client;

import com.sergio.labraheroes.item.ModArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class VisionHudOverlay {

    public static final IGuiOverlay HUD_VISION = ((gui, guiGraphics, partialTick, width, height) -> {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) return;

        // Comprobamos si lleva la pechera de Visión
        ItemStack chestplate = player.getInventory().getArmor(2);
        if (chestplate.getItem() instanceof ArmorItem armor && armor.getMaterial() == ModArmorMaterials.VISION) {

            // Comprobamos si está encendida
            boolean isActive = chestplate.hasTag() && chestplate.getTag().getBoolean("SuitActive");

            if (isActive) {
                // Sacamos las medidas de la pantalla actual
                int screenWidth = minecraft.getWindow().getGuiScaledWidth();
                int screenHeight = minecraft.getWindow().getGuiScaledHeight();
                Font font = minecraft.font;

                // 1. DIBUJAR TEXTO CENTRAL SUPERIOR
                String text = "[ SISTEMAS ONLINE ]";
                int textWidth = font.width(text);
                // Dibujamos el texto (Fuente, texto, posición X, posición Y, color hex, sombra)
                guiGraphics.drawString(font, text, (screenWidth - textWidth) / 2, 10, 0x00FFFF, true);

                // 2. DIBUJAR RAYAS FUTURISTAS LATERALES (Color Cian semi-transparente)
                // Color formato ARGB: 0x8800FFFF (88 = Transparencia, 00 = Rojo, FF = Verde, FF = Azul)

                // Raya Izquierda
                guiGraphics.fill(10, 30, 15, screenHeight - 30, 0x8800FFFF);
                guiGraphics.fill(18, 50, 20, screenHeight - 50, 0x5500FFFF); // Detalle más fino

                // Raya Derecha
                guiGraphics.fill(screenWidth - 15, 30, screenWidth - 10, screenHeight - 30, 0x8800FFFF);
                guiGraphics.fill(screenWidth - 20, 50, screenWidth - 18, screenHeight - 50, 0x5500FFFF);
            }
        }
    });
}