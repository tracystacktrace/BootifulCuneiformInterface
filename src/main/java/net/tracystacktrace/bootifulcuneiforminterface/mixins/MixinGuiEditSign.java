package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEditSign;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.block.tileentity.TileEntitySign;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.bootifulcuneiforminterface.gui.SignInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiEditSign.class)
public class MixinGuiEditSign extends GuiScreen {

    @Unique
    private String bootifulcuneiforminterface$info1;

    @Unique
    private String bootifulcuneiforminterface$info2;

    @Unique
    private SignInterface bootifulCuneiformInterface$helper;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectHelper(TileEntitySign tileEntitySign, CallbackInfo ci) {
        this.bootifulCuneiformInterface$helper = new SignInterface(GuiEditSign.class.cast(this));
        bootifulcuneiforminterface$info1 = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.erase.confirmation");
        bootifulcuneiforminterface$info2 = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.finish.confirmation");
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectButtons(CallbackInfo ci) {
        bootifulCuneiformInterface$helper.initReset();

        final int offsetXLeft = this.width / 2 - 100;
        final int offsetXRight = this.width / 2 + 100 - 32;

        final int offsetY = this.height / 2 - 64;

        bootifulCuneiformInterface$helper.initColorButtons(offsetXLeft, offsetY);
        bootifulCuneiformInterface$helper.initFormatButtons(offsetXRight, offsetY);
        bootifulCuneiformInterface$helper.initHelperButtons(offsetXRight, offsetY + 2 + 16 * 3);

        bootifulCuneiformInterface$helper.modifyOriginalDoneButton(offsetXRight, offsetY + 4 + 16 * 5);
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void bootifulcuneiforminterface$injectButtonActions(GuiButton button, CallbackInfo ci) {
        bootifulCuneiformInterface$helper.onClickToResetState(button, 0);

        if(button.enabled) {
            if (button.id == 0) {
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
        bootifulCuneiformInterface$helper.onKeyPressedToResetState();
    }

    @Inject(method = "drawScreen", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(FFF)V"))
    private void bootifulcuneiforminterface$injectRenderInfo(float mouseX, float mouseY, float deltaTicks, CallbackInfo ci) {
        final int offsetXRight = this.width / 2 + 100 + 2;
        final int offsetY = this.height / 2 - 64;

        if (bootifulCuneiformInterface$helper.isEraseProceed()) {
            this.drawString(fontRenderer, bootifulcuneiforminterface$info1, offsetXRight, offsetY + 3 * 16 + 5, 0xFFFFFFFF);
        }

        if (bootifulCuneiformInterface$helper.isFinishProceed()) {
            this.drawString(fontRenderer, bootifulcuneiforminterface$info2, offsetXRight, offsetY + 5 * 16 + 7, 0xFFFFFFFF);
        }
    }
}
