package net.tracystacktrace.bootifulcuneiforminterface.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.bootifulcuneiforminterface.BootifulCuneiformInterface;
import net.tracystacktrace.bootifulcuneiforminterface.mixins.AccessorGuiScreen;

public abstract class InterfaceHelper {

    protected final GuiScreen screen;

    protected GuiButton eraseButton;

    protected boolean eraseProceed = false;
    protected boolean finishProceed = false;

    public InterfaceHelper(GuiScreen screen) {
        this.screen = screen;
    }

    /* getters/setters */

    public boolean isEraseProceed() {
        return eraseProceed;
    }

    public boolean isFinishProceed() {
        return finishProceed;
    }

    /* initialization of things */

    public void initReset() {
        this.eraseProceed = false;
        this.finishProceed = false;
    }

    public void initColorButtons(int x, int y) {
        for (int i = 0; i < BootifulCuneiformInterface.COLORS.length; i++) {
            this.addButton(new GuiButton(
                    i + 100,
                    x + (i / 8) * 16,
                    y + (i % 8) * 16,
                    16, 16,
                    "§" + BootifulCuneiformInterface.COLORS[i] + BootifulCuneiformInterface.COLORS[i]
            ));
        }
    }

    public void initFormatButtons(int x, int y) {
        for (int i = 0; i < BootifulCuneiformInterface.FORMATS.length; i++) {
            this.addButton(new GuiButton(
                    i + BootifulCuneiformInterface.COLORS.length + 100,
                    x + (i / 3) * 16,
                    y + (i % 3) * 16,
                    16, 16,
                    "§" + BootifulCuneiformInterface.FORMATS[i] + BootifulCuneiformInterface.FORMATS[i]
            ));
        }
    }

    public void initHelperButtons(int x, int y) {
        final StringTranslate translate = StringTranslate.getInstance();

        //general custom buttons
        GuiButton buttonPaste = new GuiButton(122, x, y, 16, 16, "§a\u2193", translate.translateKey("bootifulcuneiforminterface.paste"));
        buttonPaste.canDisplayInfo = true;
        GuiButton buttonCopy = new GuiButton(124, x, y + 16, 16, 16, "§e↑", translate.translateKey("bootifulcuneiforminterface.copy"));
        buttonCopy.canDisplayInfo = true;
        GuiButton insertEscape = new GuiButton(125, x + 16, y + 16, 16, 16, "§d\\n", translate.translateKey("bootifulcuneiforminterface.escapechar"));
        insertEscape.canDisplayInfo = true;

        this.eraseButton = new GuiButton(123, x + 16, y, 16, 16, "§c\u274C", translate.translateKey("bootifulcuneiforminterface.erase"));
        this.eraseButton.canDisplayInfo = true;

        this.addButton(insertEscape);
        this.addButton(buttonCopy);
        this.addButton(this.eraseButton);
        this.addButton(buttonPaste);
    }

    /* button action handlers */

    public boolean onClickColorFormatButtons(GuiButton button) {
        //insert color
        if (button.id >= 100 && button.id <= 115) {
            this.appendText("§" + BootifulCuneiformInterface.COLORS[button.id - 100]);
            return true;
        }

        //insert format
        if (button.id >= 116 && button.id <= 121) {
            this.appendText("§" + BootifulCuneiformInterface.FORMATS[button.id - 116]);
            return true;
        }

        return false;
    }

    public void onClickFinishButton() {
        if (!this.finishProceed) {
            this.finishProceed = true;
        } else {
            this.finishWriting();
            Minecraft.getInstance().displayGuiScreen(null);
        }
    }

    public void onClickToResetState(GuiButton button, int closeId) {
        //reset erase trigger
        if (button.enabled && button.id != 123 && this.eraseProceed) {
            this.eraseProceed = false;
            this.eraseButton.displayString = "§c\u274C";
        }

        //reset finish trigger
        if (button.enabled && button.id != closeId && this.finishProceed) {
            this.finishProceed = false;
        }
    }

    public boolean onClickHelperButtons(GuiButton button) {
        //insert clipboard text
        if (button.id == 122) {
            final String clipboardText = GuiScreen.getClipboardString();
            if (clipboardText != null && !clipboardText.isEmpty()) {
                int availableSpace = 256 - this.getText().length();
                if (availableSpace > 0) {
                    this.appendText(clipboardText.substring(0, Math.min(availableSpace, clipboardText.length())).replaceAll("[\n\r\t\u0000\f\ufffd]", ""));
                }
            }
            return true;
        }

        //erase everything
        if (button.id == 123) {
            if (!this.eraseProceed) {
                button.displayString = "§4\u274C";
                this.eraseProceed = true;
            } else {
                button.displayString = "§c\u274C";
                this.setText("");
                this.eraseProceed = false;
            }
            return true;
        }

        //copy to clipboard
        if (button.id == 124) {
            GuiScreen.setClipboardString(this.getText());
            return true;
        }

        if (button.id == 125) {
            this.appendText("\n");
            return true;
        }

        return false;
    }

    public void onKeyPressedToResetState() {
        //reset erase trigger
        if (this.eraseProceed) {
            this.eraseProceed = false;
            this.eraseButton.displayString = "§c\u274C";
        }

        //reset finish trigger
        if (this.finishProceed) {
            this.finishProceed = false;
        }
    }

    /* inner methods */

    protected void addButton(GuiButton button) {
        ((AccessorGuiScreen) this.screen).getControlListElements().add(button);
    }

    protected GuiButton getButton(int index) {
        return ((AccessorGuiScreen) this.screen).getControlListElements().get(index);
    }

    protected abstract void setText(String text);

    protected abstract void appendText(String text);

    protected abstract String getText();

    protected abstract void finishWriting();
}
