package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEditCuneiformBlock;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.block.tileentity.TileEntityCuneiformBlock;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.bootifulcuneiforminterface.gui.CuneiformInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnnecessaryUnicodeEscape")
@Mixin(GuiEditCuneiformBlock.class)
public abstract class MixinGuiEditCuneiformBlock extends GuiScreen {

    @Shadow
    private int b_width;

    @Shadow
    private boolean uneditable;

    @Unique
    private String bootifulcuneiforminterface$info1;

    @Unique
    private String bootifulcuneiforminterface$info2;

    @Unique
    private CuneiformInterface bootifulCuneiformInterface$helper;

    /* END */

    @Inject(method = "<init>", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectConstructor(TileEntityCuneiformBlock block, CallbackInfo ci) {
        this.bootifulCuneiformInterface$helper = new CuneiformInterface(GuiEditCuneiformBlock.class.cast(this));
        bootifulcuneiforminterface$info1 = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.erase.confirmation");
        bootifulcuneiforminterface$info2 = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.finish.confirmation");
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectButtons(CallbackInfo ci) {
        if (!this.uneditable) {
            bootifulCuneiformInterface$helper.initReset();

            final int offsetXLeft = this.width / 2 - this.b_width / 2 - 12;
            final int offsetXRight = this.width / 2 + this.b_width / 2 - 26;

            final int offsetY = 4;

            bootifulCuneiformInterface$helper.initColorButtons(offsetXLeft, offsetY);
            bootifulCuneiformInterface$helper.initFormatButtons(offsetXLeft, offsetY + 2 + 8 * 16);
            bootifulCuneiformInterface$helper.initHelperButtons(offsetXRight, offsetY);
            bootifulCuneiformInterface$helper.modifyLocalButtons(offsetXRight, offsetY + 10 * 16 + 2);
        }
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void bootifulcuneiforminterface$injectButtonActions(GuiButton button, CallbackInfo ci) {
        if (!this.uneditable) {
            bootifulCuneiformInterface$helper.onClickToResetState(button, 1);
        }

        if (button.enabled && !this.uneditable) {
            if (button.id == 1) {
                bootifulCuneiformInterface$helper.onClickFinishButton();
                ci.cancel();
            }

            if (bootifulCuneiformInterface$helper.onClickColorFormatButtons(button)) {
                ci.cancel();
            }

            if (bootifulCuneiformInterface$helper.onClickHelperButtons(button)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "keyTyped", at = @At("HEAD"))
    private void bootifulcuneiforminterface$injectResetErase(char eventChar, int eventKey, CallbackInfo ci) {
        if (!this.uneditable) {
            bootifulCuneiformInterface$helper.onKeyPressedToResetState();
        }
    }

    @Inject(method = "drawScreen", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(FFF)V"))
    private void bootifulcuneiforminterface$injectRenderInfo(float mouseX, float mouseY, float deltaTicks, CallbackInfo ci) {
        final int offsetXRight = this.width / 2 + this.b_width / 2 - 26;

        if (bootifulCuneiformInterface$helper.isEraseProceed()) {
            this.drawString(fontRenderer, bootifulcuneiforminterface$info1, offsetXRight + 34, 8, 0xFFFFFFFF);
        }

        if (bootifulCuneiformInterface$helper.isFinishProceed()) {
            this.drawString(fontRenderer, bootifulcuneiforminterface$info2, offsetXRight + 34, 10 + 10 * 16, 0xFFFFFFFF);
        }
    }
}
