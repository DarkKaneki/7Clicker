/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.jnativehook.GlobalScreen
 *  org.jnativehook.NativeHookException
 *  org.jnativehook.keyboard.NativeKeyListener
 *  org.jnativehook.mouse.NativeMouseListener
 */
package com.ruffian7.sevenclicker;

import com.ruffian7.sevenclicker.gui.ClickerGui;
import com.ruffian7.sevenclicker.listener.KeyListener;
import com.ruffian7.sevenclicker.listener.MouseListener;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseListener;

public class AutoClicker {
    public static Robot robot;
    public static Point mousePos;
    public static ClickerGui gui;
    public static boolean toggled;
    public static boolean activated;
    public static boolean skipNext;
    public static boolean blockHit;
    private static int delay;
    public static long lastTime;
    public static int minCPS;
    public static int maxCPS;
    public static int button;
    public static String[] toggleKey;
    public static int toggleMouseButton;

    static {
        gui = new ClickerGui();
        toggled = false;
        activated = false;
        skipNext = false;
        blockHit = false;
        delay = -1;
        lastTime = 0;
        minCPS = 8;
        maxCPS = 12;
        button = 1;
        toggleKey = new String[]{"", ""};
        toggleMouseButton = 3;
    }

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
        try {
            robot = new Robot();
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeMouseListener((NativeMouseListener)new MouseListener());
            GlobalScreen.addNativeKeyListener((NativeKeyListener)new KeyListener());
        }
        catch (AWTException | NativeHookException e) {
            e.printStackTrace();
        }
        try {
            do {
                Thread.sleep(1);
                Random random = new Random();
                if (delay == -1) {
                    delay = random.nextInt(1000 / minCPS - 1000 / maxCPS + 1) + 1000 / maxCPS;
                }
                if (!activated || !toggled || AutoClicker.gui.focused || System.currentTimeMillis() - lastTime < (long)delay) continue;
                AutoClicker.click();
                lastTime = System.currentTimeMillis();
                delay = random.nextInt(1000 / minCPS - 1000 / maxCPS + 1) + 1000 / maxCPS;
            } while (true);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void click() {
        skipNext = true;
        robot.mousePress(button == 1 ? 16 : 4);
        robot.mouseRelease(button == 1 ? 16 : 4);
        if (blockHit) {
            robot.mousePress(button == 1 ? 4 : 16);
            robot.mouseRelease(button == 1 ? 4 : 16);
        }
    }

    public static void toggle() {
        if (toggled) {
            toggled = false;
            AutoClicker.gui.powerButton.setIcon(new ImageIcon(AutoClicker.class.getClassLoader().getResource("assets/power_button.png")));
        } else {
            toggled = true;
            AutoClicker.gui.powerButton.setIcon(new ImageIcon(AutoClicker.class.getClassLoader().getResource("assets/power_button_on.png")));
        }
        activated = false;
        skipNext = false;
        blockHit = false;
    }
}

