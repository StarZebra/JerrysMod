package me.jerrysmod.utils;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.events.TickEndEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Utils {
	
	public static boolean inSkyBlock = false;
	
	public static String removeFormatting(String input) {
		return input.replaceAll("§[0-9a-fk-or]", "");
	}
	
	public static void sendMessageWithPrefix(String message) {
		(Minecraft.getMinecraft()).thePlayer.addChatMessage(new ChatComponentText("§8[§bJerryAddons§8] §7" + message));
	}
	
	private int ticks = 0;
	@SubscribeEvent
	public void onTick(TickEndEvent event) {
		if(ticks % 20 == 0) {
			if(JerrysMod.mc.thePlayer != null && JerrysMod.mc.theWorld != null) {
				ScoreObjective scoreboardObj = JerrysMod.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
				if(scoreboardObj != null) {
					inSkyBlock = removeFormatting(scoreboardObj.getDisplayName()).contains("SKYBLOCK");
					if(!inSkyBlock){
						System.out.println("IN SKYBLOCK: " + inSkyBlock);
					}
					
				}
				
			}
			ticks = 0;
		}
		ticks++;
	}
	
	public static String getInventoryName() {
		if(JerrysMod.mc.thePlayer == null || JerrysMod.mc.theWorld == null) return "null";
		return JerrysMod.mc.thePlayer.openContainer.inventorySlots.get(0).inventory.getName();
	}
	
}
