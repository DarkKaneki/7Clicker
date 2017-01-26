/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.jnativehook.mouse.NativeMouseEvent
 *  org.jnativehook.mouse.NativeMouseListener
 */
package com.ruffian7.sevenclicker.listener;

import com.ruffian7.sevenclicker.AutoClicker;
import com.ruffian7.sevenclicker.gui.ClickerGui;
import java.awt.Point;
import javax.swing.JLabel;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class MouseListener
implements NativeMouseListener {
    private boolean leftClick;
    private boolean rightClick;
    private long lastClickTime = 0;

    public void nativeMousePressed(NativeMouseEvent event) {
        if (event.getButton() == AutoClicker.toggleMouseButton && !AutoClicker.gui.focused) {
            AutoClicker.toggle();
        }
        if (AutoClicker.toggled && !AutoClicker.skipNext) {
            if (event.getButton() == 1) {
                this.leftClick = true;
            } else if (event.getButton() == 2) {
                this.rightClick = true;
            }
            if (this.leftClick && this.rightClick) {
                AutoClicker.blockHit = true;
            }
            if (event.getButton() == AutoClicker.button) {
                AutoClicker.mousePos = event.getPoint();
                AutoClicker.activated = true;
                AutoClicker.lastTime = System.currentTimeMillis();
            }
        }
        if (event.getButton() == AutoClicker.button) {
            if (System.currentTimeMillis() - this.lastClickTime > 1000 && this.lastClickTime != 0) {
                this.lastClickTime = 0;
            }
            if (this.lastClickTime == 0) {
                this.lastClickTime = System.currentTimeMillis();
            } else if (System.currentTimeMillis() != this.lastClickTime) {
                int cps = Math.round(1000.0f / (float)(System.currentTimeMillis() - this.lastClickTime));
                AutoClicker.gui.cpsNumber.setText(cps < 10 ? "0" + cps : String.valueOf(cps));
                this.lastClickTime = 0;
            }
        }
    }

    public void nativeMouseReleased(NativeMouseEvent event) {
        if (!AutoClicker.skipNext) {
            if (event.getButton() == AutoClicker.button) {
                this.leftClick = false;
                AutoClicker.activated = false;
            } else if (event.getButton() == (AutoClicker.button == 1 ? 2 : 1)) {
                this.rightClick = false;
                AutoClicker.blockHit = false;
            }
        } else {
            AutoClicker.skipNext = event.getButton() == AutoClicker.button && AutoClicker.blockHit;
        }
    }

    public void nativeMouseClicked(NativeMouseEvent event) {
    }
}

