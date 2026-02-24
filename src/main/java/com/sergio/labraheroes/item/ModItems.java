package com.sergio.labraheroes.item; // <-- Cambia "sergio" por el nombre de tu carpeta si es distinto

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    // 1. Creamos la "lista" (Registro) para los ítems de nuestro mod
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "labraheroes");

    // 2. ¡Creamos nuestro primer ítem! Lingote de Vibranium
    public static final RegistryObject<Item> VIBRANIUM = ITEMS.register("vibranium",
            () -> new Item(new Item.Properties()));

    // 3. Método para conectar esta lista al motor principal del juego
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}