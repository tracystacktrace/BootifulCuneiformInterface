package net.tracystacktrace.bootifulcuneiforminterface.gui;

import net.minecraft.client.gui.GuiEditCuneiformBlock;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.bootifulcuneiforminterface.mixins.AccessorGuiEditCuneiformBlock;

public class CuneiformInterface extends InterfaceHelper {

    public CuneiformInterface(GuiEditCuneiformBlock screen) {
        super(screen);
    }

    public void modifyLocalButtons(int x, int y) {
        final StringTranslate translate = StringTranslate.getInstance();

        //change original two buttons
        this.getButton(0).xPosition = x;
        this.getButton(0).yPosition = y;
        this.getButton(0).width = this.getButton(0).height = 16;
        this.getButton(0).displayString = "§a✔";
        this.getButton(0).displayInfo = translate.translateKey("bootifulcuneiforminterface.save");
        this.getButton(0).canDisplayInfo = true;

        this.getButton(1).xPosition = x + 16;
        this.getButton(1).yPosition = y;
        this.getButton(1).width = this.getButton(1).height = 16;
        this.getButton(1).displayString = "§e✎";
        this.getButton(1).displayInfo = translate.translateKey("bootifulcuneiforminterface.finish");
        this.getButton(1).canDisplayInfo = true;
    }

    @Override
    protected void setText(String text) {
        ((GuiEditCuneiformBlock) this.screen).setText(text);
    }

    @Override
    protected void appendText(String text) {
        final String original = this.getText();
        ((GuiEditCuneiformBlock) this.screen).setText(original + text);
    }

    @Override
    protected String getText() {
        return ((GuiEditCuneiformBlock) this.screen).getText();
    }

    @Override
    protected void finishWriting() {
        ((AccessorGuiEditCuneiformBlock) screen).bci$sendResult(true);
    }

}
