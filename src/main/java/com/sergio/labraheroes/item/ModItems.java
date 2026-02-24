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

    // --- TRAJE DE SUPERHÉROE (VIBRANIUM) ---
    public static final RegistryObject<Item> VIBRANIUM_HELMET = ITEMS.register("vibranium_helmet",
            () -> new net.minecraft.world.item.ArmorItem(ModArmorMaterials.VIBRANIUM, net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> VIBRANIUM_CHESTPLATE = ITEMS.register("vibranium_chestplate",
            () -> new net.minecraft.world.item.ArmorItem(ModArmorMaterials.VIBRANIUM, net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> VIBRANIUM_LEGGINGS = ITEMS.register("vibranium_leggings",
            () -> new net.minecraft.world.item.ArmorItem(ModArmorMaterials.VIBRANIUM, net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> VIBRANIUM_BOOTS = ITEMS.register("vibranium_boots",
            () -> new net.minecraft.world.item.ArmorItem(ModArmorMaterials.VIBRANIUM, net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));

    // 3. Método para conectar esta lista al motor principal del juego
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}