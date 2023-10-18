package org.bukkit.craftbukkit.v1_20_R1.entity;

import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.PolarBear;

public class CraftPolarBear extends CraftAnimals implements PolarBear {

    public CraftPolarBear(CraftServer server, net.minecraft.world.entity.animal.PolarBear entity) {
        super(server, entity);
    }
    @Override
    public net.minecraft.world.entity.animal.PolarBear getHandle() {
        return (net.minecraft.world.entity.animal.PolarBear) entity;
    }

    @Override
    public String toString() {
        return "CraftPolarBear";
    }

    // Paper start
    @Override
    public boolean isStanding() {
        return this.getHandle().isStanding();
    }

    @Override
    public void setStanding(boolean standing) {
        this.getHandle().setStanding(standing);
    }
    // Paper end
}
