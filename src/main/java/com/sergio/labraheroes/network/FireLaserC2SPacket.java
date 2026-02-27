package com.sergio.labraheroes.network;

import com.sergio.labraheroes.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class FireLaserC2SPacket {

    public FireLaserC2SPacket() {}
    public FireLaserC2SPacket(FriendlyByteBuf buf) {}
    public void encode(FriendlyByteBuf buf) {}

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                // Comprobamos si lleva la pechera y está activa
                ItemStack chestplate = player.getInventory().getArmor(2);
                boolean isVision = chestplate.getItem() == ModItems.VISION_CHESTPLATE.get();
                boolean isActive = chestplate.hasTag() && chestplate.getTag().getBoolean("SuitActive");

                if (isVision && isActive) {

                    // --- MATEMÁTICAS DEL LÁSER ---
                    double maxDistance = 20.0; // El rayo llega a 20 bloques de distancia
                    Vec3 eyePos = player.getEyePosition(); // Desde los ojos
                    Vec3 lookVec = player.getLookAngle(); // Hacia donde mira
                    // El punto final teórico del rayo
                    Vec3 endPos = eyePos.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);

                    // Buscamos entidades en una caja grande alrededor del rayo
                    AABB searchBox = player.getBoundingBox().expandTowards(lookVec.scale(maxDistance)).inflate(1.0D);
                    List<Entity> entities = player.level().getEntities(player, searchBox, e -> e instanceof LivingEntity && e.isAlive());

                    Entity closestHit = null;
                    double closestDistance = maxDistance;

                    // Comprobamos cuál es la entidad más cercana que choca exactamente con la línea
                    for (Entity entity : entities) {
                        AABB hitBox = entity.getBoundingBox().inflate(0.3D); // Hacemos la hitbox un pelín más grande para que sea fácil acertar
                        Optional<Vec3> hit = hitBox.clip(eyePos, endPos);
                        if (hit.isPresent()) {
                            double distToHit = eyePos.distanceTo(hit.get());
                            if (distToHit < closestDistance) {
                                closestDistance = distToHit;
                                closestHit = entity;
                            }
                        }
                    }

                    // --- DIBUJAR LAS PARTÍCULAS DEL LÁSER ---
                    ServerLevel serverLevel = (ServerLevel) player.level();
                    // Si golpeamos a alguien, el rayo se corta en él. Si no, llega hasta el final.
                    double actualDistance = closestHit != null ? closestDistance : maxDistance;

                    // Dibujamos una partícula cada 0.5 bloques
                    for (double d = 0; d < actualDistance; d += 0.5) {
                        Vec3 particlePos = eyePos.add(lookVec.scale(d));
                        // Usamos END_ROD porque hace un efecto brillante blanco/amarillento muy chulo
                        serverLevel.sendParticles(ParticleTypes.END_ROD, particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);
                    }

                    // --- HACER DAÑO ---
                    if (closestHit instanceof LivingEntity target) {
                        // Hace 8 puntos de daño (4 corazones) de tipo mágico
                        target.hurt(player.damageSources().magic(), 8.0F);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}