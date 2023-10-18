package org.bukkit.craftbukkit.v1_20_R1.entity;

import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class AbstractProjectile extends CraftEntity implements Projectile {

    private boolean doesBounce;

    public AbstractProjectile(CraftServer server, net.minecraft.world.entity.Entity entity) {
        super(server, entity);
        this.doesBounce = false;
    }

    @Override
    public boolean doesBounce() {
        return this.doesBounce;
    }

    @Override
    public void setBounce(boolean doesBounce) {
        this.doesBounce = doesBounce;
    }
    // Paper start
    @Override
    public boolean hasLeftShooter() {
        return this.getHandle().leftOwner;
    }

    @Override
    public void setHasLeftShooter(boolean leftShooter) {
        this.getHandle().leftOwner = leftShooter;
    }

    @Override
    public boolean hasBeenShot() {
        return this.getHandle().hasBeenShot;
    }

    @Override
    public void setHasBeenShot(boolean beenShot) {
        this.getHandle().hasBeenShot = beenShot;
    }

    @Override
    public boolean canHitEntity(org.bukkit.entity.Entity entity) {
        return this.getHandle().canHitEntity(((CraftEntity) entity).getHandle());
    }

    @Override
    public void hitEntity(org.bukkit.entity.Entity entity) {
        this.getHandle().preOnHit(new net.minecraft.world.phys.EntityHitResult(((CraftEntity) entity).getHandle()));
    }

    @Override
    public void hitEntity(org.bukkit.entity.Entity entity, org.bukkit.util.Vector vector) {
        this.getHandle().preOnHit(new net.minecraft.world.phys.EntityHitResult(((CraftEntity) entity).getHandle(), new net.minecraft.world.phys.Vec3(vector.getX(), vector.getY(), vector.getZ())));
    }

    @Override
    public net.minecraft.world.entity.projectile.Projectile getHandle() {
        return (net.minecraft.world.entity.projectile.Projectile) entity;
    }

    @Override
    public final org.bukkit.projectiles.ProjectileSource getShooter() {
        this.getHandle().refreshProjectileSource(true); // Paper
        return this.getHandle().projectileSource;
    }

    @Override
    public final void setShooter(org.bukkit.projectiles.ProjectileSource shooter) {
        if (shooter instanceof CraftEntity craftEntity) {
            this.getHandle().setOwner(craftEntity.getHandle());
        } else {
            this.getHandle().setOwner(null);
        }
        this.getHandle().projectileSource = shooter;
    }

    @Override
    public java.util.UUID getOwnerUniqueId() {
        return this.getHandle().ownerUUID;
    }
    // Paper end

}
