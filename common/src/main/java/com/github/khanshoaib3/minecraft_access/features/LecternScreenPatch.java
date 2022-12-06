package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.BookScreenAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;

public class LecternScreenPatch {
    String previousContent;

    public LecternScreenPatch() {
        previousContent = "";
    }

    public void update() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return;
        if (minecraftClient.currentScreen == null) {
            previousContent = "";
            return;
        }
        if (!(minecraftClient.currentScreen instanceof BookScreen bookScreen)) {
            previousContent = "";
            return;
        }

        int pageIndex = ((BookScreenAccessor) bookScreen).getPageIndex();
        if (pageIndex < 0 || pageIndex > ((BookScreenAccessor) bookScreen).getContents().getPageCount())
            return; //Return if the page index is out of bounds

        String currentPageContentString = ((BookScreenAccessor) bookScreen).getContents().getPage(pageIndex).getString();
        currentPageContentString = "%s \n\n %s".formatted(((BookScreenAccessor) bookScreen).getPageIndexText().getString(), currentPageContentString);

        if (!previousContent.equals(currentPageContentString)) {
            previousContent = currentPageContentString;
            MainClass.speakWithNarrator(currentPageContentString, true);
        }
    }
}
