package com.sergio.labraheroes.item;

import com.sergio.labraheroes.labraheroes; // Importa tu clase principal
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    VIBRANIUM("vibranium", 37, new int[]{3, 6, 8, 3}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(ModItems.VIBRANIUM.get())), // <--- Ojo a la coma aquí

    VISION("vision", 30, new int[]{3, 5, 7, 3}, 25,
            SoundEvents.ARMOR_EQUIP_GOLD, 2.0F, 0.0F, () -> Ingredient.of(net.minecraft.world.item.Items.AMETHYST_SHARD)), // <--- Coma aquí

    IRON_MAN("iron_man", 15, new int[]{2, 5, 6, 2}, 9,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(net.minecraft.world.item.Items.IRON_INGOT)); // <--- Punto y coma

    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
                      SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override public int getDurabilityForType(ArmorItem.Type pType) { return BASE_DURABILITY[pType.getSlot().getIndex()] * this.durabilityMultiplier; }
    @Override public int getDefenseForType(ArmorItem.Type pType) { return this.slotProtections[pType.getSlot().getIndex()]; }
    @Override public int getEnchantmentValue() { return this.enchantmentValue; }
    @Override public SoundEvent getEquipSound() { return this.equipSound; }
    @Override public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
    @Override public String getName() { return labraheroes.MODID + ":" + this.name; } // ESTO ES CLAVE PARA LA TEXTURA 3D
    @Override public float getToughness() { return this.toughness; }
    @Override public float getKnockbackResistance() { return this.knockbackResistance; }
}