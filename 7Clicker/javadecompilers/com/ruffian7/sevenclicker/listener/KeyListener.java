/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.jnativehook.keyboard.NativeKeyEvent
 *  org.jnativehook.keyboard.NativeKeyListener
 */
package com.ruffian7.sevenclicker.listener;

import com.ruffian7.sevenclicker.AutoClicker;
import com.ruffian7.sevenclicker.gui.ClickerGui;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener
implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent event) {
        List<String> modifiers1 = Arrays.asList(NativeKeyEvent.getModifiersText((int)event.getModifiers()).split("\\+"));
        List<String> modifiers2 = Arrays.asList(AutoClicker.toggleKey[1].split("\\+"));
        if (NativeKeyEvent.getKeyText((int)event.getKeyCode()).equals(AutoClicker.toggleKey[0]) && modifiers1.containsAll(modifiers2) && !AutoClicker.gui.focused) {
            AutoClicker.toggle();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent event) {
    }

    public void nativeKeyTyped(NativeKeyEvent event) {
    }
}

