package net.tracystacktrace.bootifulcuneiforminterface.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEditNameTag;
import net.minecraft.client.gui.GuiTextField;
import net.tracystacktrace.bootifulcuneiforminterface.BootifulCuneiformInterface;
import net.tracystacktrace.bootifulcuneiforminterface.mixins.AccessorGuiEditNameTag;

public class NameTagInterface extends InterfaceHelper {

    public NameTagInterface(GuiEditNameTag screen) {
        super(screen);
    }

    @Override
    public void initColorButtons(int x, int y) {
        for (int i = 0; i < BootifulCuneiformInterface.COLORS.length; i++) {
            this.addButton(new GuiButton(
                    i + 100,
                    x + (i % 8) * 16,
                    y + (i / 8) * 16,
                    16, 16,
                    "§" + BootifulCuneiformInterface.COLORS[i] + BootifulCuneiformInterface.COLORS[i]
            ));
        }
    }

    @Override
    public void initFormatButtons(int x, int y) {
        for (int i = 0; i < BootifulCuneiformInterface.FORMATS.length; i++) {
            this.addButton(new GuiButton(
                    i + BootifulCuneiformInterface.COLORS.length + 100,
                    x + (i % 3) * 16,
                    y + (i / 3) * 16,
                    16, 16,
                    "§" + BootifulCuneiformInterface.FORMATS[i] + BootifulCuneiformInterface.FORMATS[i]
            ));
        }
    }

    @Override
    protected void appendText(String text) {
        final GuiTextField textField = ((AccessorGuiEditNameTag) screen).bci$getTextField();
        textField.setText(textField.getText() + text);
        textField.setCursorPosition(128);
        ((AccessorGuiEditNameTag) screen).bci$setMessage(textField.getText());
    }

    @Override
    protected String getText() {
        return ((AccessorGuiEditNameTag) screen).bci$getMessage();
    }

    @Override
    protected void setText(String text) {
    }

    @Override
    protected void finishWriting() {
    }

}
