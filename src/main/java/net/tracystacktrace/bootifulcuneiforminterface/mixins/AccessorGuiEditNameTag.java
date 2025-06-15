package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiEditNameTag;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiEditNameTag.class)
public interface AccessorGuiEditNameTag {
    @Accessor("message")
    String bci$getMessage();

    @Accessor("message")
    void bci$setMessage(String text);

    @Accessor("nameBox")
    GuiTextField bci$getTextField();
}
