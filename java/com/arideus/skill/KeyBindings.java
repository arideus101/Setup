package com.arideus.skill;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class KeyBindings {
	 public static boolean shiftHeld()
	    {
	        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	    }
	     public static boolean ctrlHeld()
	    {
	        boolean isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
	        if (!isCtrlKeyDown && Minecraft.isRunningOnMac)
	            isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA);

	        return isCtrlKeyDown;
	    }
}
