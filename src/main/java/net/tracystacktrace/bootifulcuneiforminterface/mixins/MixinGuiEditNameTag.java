package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEditNameTag;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.entity.player.EntityPlayer;
import net.tracystacktrace.bootifulcuneiforminterface.gui.NameTagInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiEditNameTag.class)
public class MixinGuiEditNameTag extends GuiScreen {

    @Unique
    private NameTagInterface bootifulCuneiformInterface$helper;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectHelper(EntityPlayer entityPlayer, CallbackInfo ci) {
        this.bootifulCuneiformInterface$helper = new NameTagInterface(GuiEditNameTag.class.cast(this));
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectButtons(CallbackInfo ci) {
        this.bootifulCuneiformInterface$helper.initColorButtons(this.width / 2 - 100, 124);
        this.bootifulCuneiformInterface$helper.initFormatButtons(this.width / 2 - 100 + 2 + 8 * 16, 124);
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void bootifulcuneiforminterface$injectButtons(GuiButton button, CallbackInfo ci) {
        if (this.bootifulCuneiformInterface$helper.onClickColorFormatButtons(button)) {
            ci.cancel();
        }
    }

}
