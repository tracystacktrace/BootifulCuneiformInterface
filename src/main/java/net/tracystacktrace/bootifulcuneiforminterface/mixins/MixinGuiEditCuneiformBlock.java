package net.tracystacktrace.bootifulcuneiforminterface.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEditCuneiformBlock;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.block.tileentity.TileEntityCuneiformBlock;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.bootifulcuneiforminterface.BootifulCuneiformInterface;
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
    private String text;

    @Shadow
    private boolean uneditable;

    /* ERASE BUTTON RELATED FIELDS */

    @Shadow protected abstract void sendTextToServer(boolean finished);

    @Unique
    private boolean bootifulcuneiforminterface$eraseProceed = false;

    @Unique
    private boolean bootifulcuneiforminterface$finishProceed = false;

    @Unique
    private GuiButton bootifulcuneiforminterface$eraseButton;

    @Unique
    private String bootifulcuneiforminterface$info1;

    @Unique
    private String bootifulcuneiforminterface$info2;

    /* END */

    @Inject(method = "<init>", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectConstructor(TileEntityCuneiformBlock block, CallbackInfo ci) {
        bootifulcuneiforminterface$info1 = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.erase.confirmation");
        bootifulcuneiforminterface$info2 = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.finish.confirmation");
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    private void bootifulcuneiforminterface$injectButtons(CallbackInfo ci) {
        if (!this.uneditable) {
            this.bootifulcuneiforminterface$finishProceed = false;
            this.bootifulcuneiforminterface$eraseProceed = false;

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

            final StringTranslate translate = StringTranslate.getInstance();

            //general custom buttons
            GuiButton buttonPaste = new GuiButton(122, offsetXRight, offsetY, 16, 16, "§a\u2193", translate.translateKey("bootifulcuneiforminterface.paste"));
            buttonPaste.canDisplayInfo = true;
            GuiButton buttonCopy = new GuiButton(124, offsetXRight, offsetY + 16, 16, 16, "§e↑", translate.translateKey("bootifulcuneiforminterface.copy"));
            buttonCopy.canDisplayInfo = true;
            GuiButton insertEscape = new GuiButton(125, offsetXRight + 16, offsetY + 16, 16, 16, "§d\\n", translate.translateKey("bootifulcuneiforminterface.escapechar"));
            insertEscape.canDisplayInfo = true;

            this.bootifulcuneiforminterface$eraseButton = new GuiButton(123, offsetXRight + 16, offsetY, 16, 16, "§c\u274C", translate.translateKey("bootifulcuneiforminterface.erase"));
            this.bootifulcuneiforminterface$eraseButton.canDisplayInfo = true;

            this.controlList.add(insertEscape);
            this.controlList.add(buttonCopy);
            this.controlList.add(this.bootifulcuneiforminterface$eraseButton);
            this.controlList.add(buttonPaste);


            //change original two buttons
            ((GuiButton)this.controlList.get(0)).xPosition = offsetXRight;
            ((GuiButton)this.controlList.get(0)).yPosition = offsetY + 10 * 16 + 2;
            ((GuiButton)this.controlList.get(0)).width = ((GuiButton)this.controlList.get(0)).height = 16;
            ((GuiButton)this.controlList.get(0)).displayString = "§a✔";
            ((GuiButton)this.controlList.get(0)).displayInfo = translate.translateKey("bootifulcuneiforminterface.save");
            ((GuiButton)this.controlList.get(0)).canDisplayInfo = true;

            ((GuiButton)this.controlList.get(1)).xPosition = offsetXRight + 16;
            ((GuiButton)this.controlList.get(1)).yPosition = offsetY + 10 * 16 + 2;
            ((GuiButton)this.controlList.get(1)).width = ((GuiButton)this.controlList.get(1)).height = 16;
            ((GuiButton)this.controlList.get(1)).displayString = "§e✎";
            ((GuiButton)this.controlList.get(1)).displayInfo = translate.translateKey("bootifulcuneiforminterface.finish");
            ((GuiButton)this.controlList.get(1)).canDisplayInfo = true;
        }
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void bootifulcuneiforminterface$injectButtonActions(GuiButton button, CallbackInfo ci) {
        //reset erase trigger
        if (button.enabled && !this.uneditable && button.id != 123 && this.bootifulcuneiforminterface$eraseProceed) {
            this.bootifulcuneiforminterface$eraseProceed = false;
            this.bootifulcuneiforminterface$eraseButton.displayString = "§c\u274C";
        }

        //reset finish trigger
        if (button.enabled && !this.uneditable && button.id != 1 && this.bootifulcuneiforminterface$finishProceed) {
            this.bootifulcuneiforminterface$finishProceed = false;
        }

        if (button.enabled && !this.uneditable) {
            if(button.id == 1) {
                if(!this.bootifulcuneiforminterface$finishProceed) {
                    this.bootifulcuneiforminterface$finishProceed = true;
                } else {
                    this.mc.displayGuiScreen(null);
                    this.sendTextToServer(true);
                }
                ci.cancel();
            }

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

            //copy to clipboard
            if(button.id == 124) {
                GuiScreen.setClipboardString(this.text);
                ci.cancel();
            }

            if(button.id == 125) {
                this.text = this.text + "\n";
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

        if(!this.uneditable && this.bootifulcuneiforminterface$finishProceed) {
            this.bootifulcuneiforminterface$finishProceed = false;
        }
    }

    @Inject(method = "drawScreen", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiScreen;drawScreen(FFF)V"))
    private void bootifulcuneiforminterface$injectRenderInfo(float mouseX, float mouseY, float deltaTicks, CallbackInfo ci) {
        final int offsetXRight = this.width / 2 + this.b_width / 2 - 26;

        if(this.bootifulcuneiforminterface$eraseProceed) {
            this.drawString(fontRenderer, bootifulcuneiforminterface$info1, offsetXRight + 34, 8, 0xFFFFFFFF);
        }

        if(this.bootifulcuneiforminterface$finishProceed) {
            this.drawString(fontRenderer, bootifulcuneiforminterface$info2, offsetXRight + 34, 10 + 10 * 16, 0xFFFFFFFF);
        }
    }
}
