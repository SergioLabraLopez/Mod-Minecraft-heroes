package com.sergio.labraheroes.item; // <-- Cambia "sergio" por el nombre de tu carpeta si es distinto

import net.minecraft.world.item.ArmorItem;
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
            () -> new ModArmorItem(ModArmorMaterials.VIBRANIUM, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> VIBRANIUM_CHESTPLATE = ITEMS.register("vibranium_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.VIBRANIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> VIBRANIUM_LEGGINGS = ITEMS.register("vibranium_leggings",
            () -> new ModArmorItem(ModArmorMaterials.VIBRANIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> VIBRANIUM_BOOTS = ITEMS.register("vibranium_boots",
            () -> new ModArmorItem(ModArmorMaterials.VIBRANIUM, ArmorItem.Type.BOOTS, new Item.Properties()));


    // --- TRAJE DE VISIÓN ---
    public static final RegistryObject<Item> VISION_HELMET = ITEMS.register("vision_helmet",
            () -> new ModArmorItem(ModArmorMaterials.VISION, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> VISION_CHESTPLATE = ITEMS.register("vision_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.VISION, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> VISION_LEGGINGS = ITEMS.register("vision_leggings",
            () -> new ModArmorItem(ModArmorMaterials.VISION, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> VISION_BOOTS = ITEMS.register("vision_boots",
            () -> new ModArmorItem(ModArmorMaterials.VISION, ArmorItem.Type.BOOTS, new Item.Properties()));

    // --- TRAJE DE IRON MAN (Solo Pechera) ---
    public static final RegistryObject<Item> IRON_MAN_CHESTPLATE = ITEMS.register("iron_man_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.IRON_MAN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> IRON_MAN_HELMET = ITEMS.register("iron_man_helmet",
            () -> new ModArmorItem(ModArmorMaterials.IRON_MAN, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> IRON_MAN_LEGGINGS = ITEMS.register("iron_man_leggings",
            () -> new ModArmorItem(ModArmorMaterials.IRON_MAN, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> IRON_MAN_BOOTS = ITEMS.register("iron_man_boots",
            () -> new ModArmorItem(ModArmorMaterials.IRON_MAN, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}