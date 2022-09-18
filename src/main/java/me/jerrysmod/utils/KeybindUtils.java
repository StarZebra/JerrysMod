package me.jerrysmod.utils;

import me.jerrysmod.JerrysMod;
import net.minecraft.client.settings.KeyBinding;

import java.util.HashMap;

public class KeybindUtils {
	
	public static HashMap<String, KeyBinding> keyBindings = new HashMap<>();
	
	public static void register(String name, int key) {
		keyBindings.put(name, new KeyBinding(name, key, JerrysMod.MOD_NAME));
	}
	
	public static boolean isPressed(String name) {
		return get(name).isPressed();
	}
	
	public static KeyBinding get(String name) {
		return keyBindings.get(name);
	}
	
	public static void rightClick() {
		if(!ReflectionUtils.invoke(JerrysMod.mc, "rightClickMouse")) {
			ReflectionUtils.invoke(JerrysMod.mc, "rightClickMouse");
		}
	}
	
	public static void leftClick() {
		if(!ReflectionUtils.invoke(JerrysMod.mc, "clickMouse")) {
			ReflectionUtils.invoke(JerrysMod.mc, "clickMouse");
		}
	}
	
	public static void middleClick() {
		if(!ReflectionUtils.invoke(JerrysMod.mc, "middleClickMouse")) {
			ReflectionUtils.invoke(JerrysMod.mc, "middleClickMouse");
		}
	}
	
}