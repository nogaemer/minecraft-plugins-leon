package org.bukkit.craftbukkit.v1_20_R1.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Cat.Type;

public class CraftCat extends CraftTameableAnimal implements Cat {

    public CraftCat(CraftServer server, net.minecraft.world.entity.animal.Cat entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.animal.Cat getHandle() {
        return (net.minecraft.world.entity.animal.Cat) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftCat";
    }

    @Override
    public Type getCatType() {
        return Type.values()[BuiltInRegistries.CAT_VARIANT.getId(this.getHandle().getVariant())];
    }

    @Override
    public void setCatType(Type type) {
        Preconditions.checkArgument(type != null, "Cannot have null Type");

        this.getHandle().setVariant(BuiltInRegistries.CAT_VARIANT.byId(type.ordinal()));
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) this.getHandle().getCollarColor().getId());
    }

    @Override
    public void setCollarColor(DyeColor color) {
        this.getHandle().setCollarColor(net.minecraft.world.item.DyeColor.byId(color.getWoolData()));
    }
    // Paper Start - More cat api
    @Override
    public void setLyingDown(boolean lyingDown) {
        this.getHandle().setLying(lyingDown);
    }

    @Override
    public boolean isLyingDown() {
        return this.getHandle().isLying();
    }

    @Override
    public void setHeadUp(boolean headUp) {
        this.getHandle().setRelaxStateOne(headUp);
    }

    @Override
    public boolean isHeadUp() {
        return this.getHandle().isRelaxStateOne();
    }
    // Paper End - More cat api
}
