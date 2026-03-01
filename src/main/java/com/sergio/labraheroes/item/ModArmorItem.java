package com.sergio.labraheroes.item; // Cambia "sergio" por tu nombre si es distinto

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ModArmorItem extends ArmorItem {

    private static final Map<ArmorMaterial, MobEffectInstance[]> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance[]>())
                    .put(ModArmorMaterials.VIBRANIUM, new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false),
                            new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2, false, false),
                            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1, false, false)
                    })
                    // --- PODERES DE VISIÓN ---
                    .put(ModArmorMaterials.VISION, new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.REGENERATION, 200, 1, false, false), // Regeneración 2
                            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1, false, false) // Resistencia 2
                    })
                    .build();

    public ModArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    // Este método se ejecuta constantemente (cada "tick" del juego) mientras el ítem esté en el inventario
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            boolean isVision = this.getMaterial() == ModArmorMaterials.VISION;
            boolean hasFullSuit = hasFullSuitOfArmorOn(player);

            // 1. Efectos de pociones (Esto SOLO va en el Servidor)
            if (!level.isClientSide() && hasFullSuit) {
                evaluateArmorEffects(player);
            }

            // 2. Vuelo y atravesar paredes (Esto va en AMBOS lados)
            if (isVision && hasFullSuit) {
                ItemStack chestplate = player.getInventory().getArmor(2);
                boolean isActive = chestplate.hasTag() && chestplate.getTag().getBoolean("SuitActive");

                if (isActive) {
                    // Dar vuelo
                    if (!player.getAbilities().mayfly) {
                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();
                    }

                    // ATRAVESAR PAREDES (¡Ahora funciona porque el Cliente también lo lee!)
                    if (player.getAbilities().flying) {
                        player.noPhysics = true;
                    } else {
                        player.noPhysics = false;
                    }
                } else {
                    // Si apagamos el traje, volvemos a ser sólidos y caemos
                    player.noPhysics = false;
                    if (player.getAbilities().mayfly && !player.isCreative() && !player.isSpectator()) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.onUpdateAbilities();
                    }
                }
            } else if (isVision && !hasFullSuit) {
                // Si nos quitamos el traje por completo
                player.noPhysics = false;
                if (!player.isCreative() && !player.isSpectator() && player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }
            // --- LÓGICA ESPECIAL PARA IRON MAN ---
            else if (this.getMaterial() == ModArmorMaterials.IRON_MAN && this.getType() == Type.CHESTPLATE) {
                ItemStack chestplate = player.getInventory().getArmor(2);
                boolean isActive = chestplate.hasTag() && chestplate.getTag().getBoolean("SuitActive");

                if (isActive) {
                    // MODO ACTIVO: Forzamos las piezas fantasma en tu cuerpo
                    equipPhantomPiece(player, 3, ModItems.IRON_MAN_HELMET.get());
                    equipPhantomPiece(player, 1, ModItems.IRON_MAN_LEGGINGS.get());
                    equipPhantomPiece(player, 0, ModItems.IRON_MAN_BOOTS.get());

                    // (Tus efectos de poción de Iron Man siguen aquí...)
                    if (!player.hasEffect(MobEffects.REGENERATION)) player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0, false, false));
                    if (!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1, false, false));
                    if (!player.hasEffect(MobEffects.DAMAGE_BOOST)) player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0, false, false));
                    if (!player.hasEffect(MobEffects.NIGHT_VISION)) player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0, false, false));
                } else {
                    // MODO APAGADO: Borramos las piezas fantasma para que solo quede el reactor
                    removePhantomPiece(player, 3, ModItems.IRON_MAN_HELMET.get());
                    removePhantomPiece(player, 1, ModItems.IRON_MAN_LEGGINGS.get());
                    removePhantomPiece(player, 0, ModItems.IRON_MAN_BOOTS.get());
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, MobEffectInstance[]> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance[] mapStatusEffects = entry.getValue();

            if (hasCorrectArmorOn(mapArmorMaterial, player)) {
                for (MobEffectInstance effect : mapStatusEffects) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, effect);
                }
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if (hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            // Le damos el poder al jugador. Creamos una nueva instancia para que no se agote
            player.addEffect(new MobEffectInstance(mapStatusEffect.getEffect(),
                    mapStatusEffect.getDuration(), mapStatusEffect.getAmplifier(),
                    mapStatusEffect.isAmbient(), mapStatusEffect.isVisible()));
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material
                && leggings.getMaterial() == material && boots.getMaterial() == material;
    }

    private void forceDropArmorSlot(Player player, int slotIndex) {
        ItemStack stack = player.getInventory().getArmor(slotIndex);
        if (!stack.isEmpty()) {
            // Intenta meterlo en el inventario normal. Si está lleno, lo tira al suelo.
            if (!player.getInventory().add(stack)) {
                player.drop(stack, false);
            }
            player.getInventory().armor.set(slotIndex, ItemStack.EMPTY);
        }
    }

    private void equipPhantomPiece(Player player, int slot, Item phantomItem) {
        ItemStack current = player.getInventory().getArmor(slot);
        if (current.getItem() != phantomItem) {
            forceDropArmorSlot(player, slot); // Te quita lo que lleves puesto (ej. casco de diamante)
            player.getInventory().armor.set(slot, new ItemStack(phantomItem)); // Genera la pieza de Iron Man
        }
    }

    private void removePhantomPiece(Player player, int slot, Item phantomItem) {
        ItemStack current = player.getInventory().getArmor(slot);
        if (current.getItem() == phantomItem) {
            player.getInventory().armor.set(slot, ItemStack.EMPTY); // Borra la pieza fantasma
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, net.minecraft.world.entity.EquipmentSlot slot, String type) {

        // Si el material es Iron Man...
        if (this.getMaterial() == ModArmorMaterials.IRON_MAN) {
            boolean isActive = stack.hasTag() && stack.getTag().getBoolean("SuitActive");

            // Si está ACTIVO (Armadura Completa)
            if (isActive) {
                // Si la ranura es PANTALONES (slotId 1), usamos la LAYER 2
                if (slot == net.minecraft.world.entity.EquipmentSlot.LEGS) {
                    return "labraheroes:textures/models/armor/iron_man_active_layer_2.png";
                }
                // Para el resto (Casco, Pecho, Botas), usamos la LAYER 1
                return "labraheroes:textures/models/armor/iron_man_active_layer_1.png";
            } else {
                // Si está APAGADO (Solo Reactor), usamos la LAYER 1
                return "labraheroes:textures/models/armor/iron_man_reactor_layer_1.png";
            }
        }

        // Si es Visión o Vibranium, dejamos que use el sistema normal
        return super.getArmorTexture(stack, entity, slot, type);
    }
}