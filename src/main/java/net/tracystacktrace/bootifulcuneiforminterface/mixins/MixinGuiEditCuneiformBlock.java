package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEditCuneiformBlock;
import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.bootifulcuneiforminterface.BootifulCuneiformInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnnecessaryUnicodeEscape")
@Mixin(GuiEditCuneiformBlock.class)
public class MixinGuiEditCuneiformBlock extends GuiScreen {

    @Shadow
    private int b_width;

    @Shadow
    private String text;

    @Shadow
    private boolean uneditable;

    /* ERASE BUTTON RELATED FIELDS */

    @Unique
    private boolean bootifulcuneiforminterface$eraseProceed = false;

    @Unique
    private GuiButton bootifulcuneiforminterface$eraseButton;

    /* END */

    @Inject(method = "initGui", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectButtons(CallbackInfo ci) {
        if (!this.uneditable) {
            final int offsetX = this.width / 2 - this.b_width / 2 - 12;
            final int offsetY = 4;

            //colors
            for (int i = 0; i < BootifulCuneiformInterface.COLORS.length; i++) {
                this.controlList.add(new GuiButton(
                        i + 100,
                        offsetX + (i / 8) * 16,
                        offsetY + (i % 8) * 16,
                        16, 16,
                        "§" + BootifulCuneiformInterface.COLORS[i] + BootifulCuneiformInterface.COLORS[i]
                ));
            }

            //formats (bold, underline, etc)
            for (int i = 0; i < BootifulCuneiformInterface.FORMATS.length; i++) {
                this.controlList.add(new GuiButton(
                        i + BootifulCuneiformInterface.COLORS.length + 100,
                        offsetX + (i / 3) * 16,
                        offsetY + (i % 3) * 16 + 8 * 16 + 2,
                        16, 16,
                        "§" + BootifulCuneiformInterface.FORMATS[i] + BootifulCuneiformInterface.FORMATS[i]
                ));
            }

            final int offsetXRight = this.width / 2 + this.b_width / 2 - 26;

            //\u2193

            this.controlList.add(new GuiButton(122, offsetXRight, offsetY, 16, 16, "§a\u2193"));
            this.controlList.add(this.bootifulcuneiforminterface$eraseButton = new GuiButton(123, offsetXRight + 16, offsetY, 16, 16, "§c\u274C"));
        }
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void bootifulcuneiforminterface$injectButtonActions(GuiButton button, CallbackInfo ci) {
        //reset erase trigger
        if (button.enabled && !this.uneditable && button.id != 123 && this.bootifulcuneiforminterface$eraseProceed) {
            this.bootifulcuneiforminterface$eraseProceed = false;
            this.bootifulcuneiforminterface$eraseButton.displayString = "§c\u274C";
        }

        if (button.enabled && !this.uneditable) {
            //insert color
            if (button.id >= 100 && button.id <= 115) {
                this.text += "§" + BootifulCuneiformInterface.COLORS[button.id - 100];
                ci.cancel();
            }

            //insert format
            if (button.id >= 116 && button.id <= 121) {
                this.text += "§" + BootifulCuneiformInterface.FORMATS[button.id - 116];
                ci.cancel();
            }

            //insert clipboard text
            if (button.id == 122) {
                final String clipboardText = GuiScreen.getClipboardString();
                if (clipboardText != null && !clipboardText.isEmpty()) {
                    int availableSpace = 256 - this.text.length();
                    if (availableSpace > 0) {
                        this.text += clipboardText.substring(0, Math.min(availableSpace, clipboardText.length())).replaceAll("[\n\r\t\u0000\f\ufffd]", "");
                    }
                }
                ci.cancel();
            }

            //erase everything
            if (button.id == 123) {
                if (!this.bootifulcuneiforminterface$eraseProceed) {
                    button.displayString = "§4\u274C";
                    this.bootifulcuneiforminterface$eraseProceed = true;
                } else {
                    button.displayString = "§c\u274C";
                    this.text = "";
                    this.bootifulcuneiforminterface$eraseProceed = false;
                }
                ci.cancel();
            }
        }
    }

    @Inject(method = "keyTyped", at = @At("HEAD"))
    private void bootifulcuneiforminterface$injectResetErase(char eventChar, int eventKey, CallbackInfo ci) {
        //reset erase trigger
        if (!this.uneditable && this.bootifulcuneiforminterface$eraseProceed) {
            this.bootifulcuneiforminterface$eraseProceed = false;
            this.bootifulcuneiforminterface$eraseButton.displayString = "§c\u274C";
        }
    }
}
