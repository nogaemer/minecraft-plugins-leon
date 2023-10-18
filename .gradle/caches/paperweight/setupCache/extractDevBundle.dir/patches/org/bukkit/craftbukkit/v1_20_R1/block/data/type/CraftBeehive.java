package org.bukkit.craftbukkit.v1_20_R1.block.data.type;

import org.bukkit.block.data.type.Beehive;
import org.bukkit.craftbukkit.v1_20_R1.block.data.CraftBlockData;

public abstract class CraftBeehive extends CraftBlockData implements Beehive {

    private static final net.minecraft.world.level.block.state.properties.IntegerProperty HONEY_LEVEL = getInteger("honey_level");

    @Override
    public int getHoneyLevel() {
        return get(CraftBeehive.HONEY_LEVEL);
    }

    @Override
    public void setHoneyLevel(int honeyLevel) {
        set(CraftBeehive.HONEY_LEVEL, honeyLevel);
    }

    @Override
    public int getMaximumHoneyLevel() {
        return getMax(CraftBeehive.HONEY_LEVEL);
    }
}
