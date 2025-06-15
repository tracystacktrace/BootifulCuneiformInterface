package net.tracystacktrace.bootifulcuneiforminterface.gui;

import net.minecraft.client.gui.GuiEditSign;
import net.minecraft.common.block.tileentity.TileEntitySign;
import net.minecraft.common.util.ChatAllowedCharacters;
import net.minecraft.common.util.i18n.StringTranslate;
import net.tracystacktrace.bootifulcuneiforminterface.mixins.AccessorGuiEditSign;

public class SignInterface extends InterfaceHelper {

    public SignInterface(GuiEditSign screen) {
        super(screen);
    }

    public void modifyOriginalDoneButton(int x, int y) {
        this.getButton(0).xPosition = x;
        this.getButton(0).yPosition = y;
        this.getButton(0).width = 32;
        this.getButton(0).height = 16;
        this.getButton(0).displayString = "§e✎";
        this.getButton(0).displayInfo = StringTranslate.getInstance().translateKey("bootifulcuneiforminterface.finish");
        this.getButton(0).canDisplayInfo = true;
    }

    @Override
    protected void setText(String text) {
        //just cleanup code
        if (text.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                this.getEntitySign().signText[i] = "";
            }
            return;
        }

        String limited = text.length() > 60 ? text.substring(0, 60) : text;
        for (int i = 0; i < 4; i++) {
            int start = i * 15;
            this.getEntitySign().signText[i] = limited.substring(start, Math.min(start + 15, limited.length()));
        }
    }

    @Override
    protected void appendText(String text) {
        if (text.isEmpty()) {
            return;
        }

        String[] textArray = this.getEntitySign().signText;
        for (char c : text.toCharArray()) {
            if (ChatAllowedCharacters.isAllowedCharacter(c) && textArray[this.getEditLine()].length() < 15) {
                textArray[this.getEditLine()] = textArray[this.getEditLine()] + c;
            }
        }
    }

    @Override
    protected String getText() {
        return getEntitySign().signText[this.getEditLine()];
    }

    @Override
    protected void finishWriting() {
        this.getEntitySign().updateTileEntityIThink();
    }

    private int getEditLine() {
        return ((AccessorGuiEditSign) screen).bci$getEditLine();
    }

    private TileEntitySign getEntitySign() {
        return ((AccessorGuiEditSign) screen).bci$getEntitySign();
    }

}
