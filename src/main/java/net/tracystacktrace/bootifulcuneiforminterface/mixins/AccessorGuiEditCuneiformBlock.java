package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiEditCuneiformBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiEditCuneiformBlock.class)
public interface AccessorGuiEditCuneiformBlock {

    @Invoker("sendTextToServer")
    void bci$sendResult(boolean b);

}
