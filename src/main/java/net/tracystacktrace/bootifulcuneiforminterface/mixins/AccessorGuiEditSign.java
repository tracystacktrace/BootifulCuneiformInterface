package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiEditSign;
import net.minecraft.common.block.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiEditSign.class)
public interface AccessorGuiEditSign {

    @Accessor("editLine")
    int bci$getEditLine();

    @Accessor("entitySign")
    TileEntitySign bci$getEntitySign();

}
