package me.jerrysmod.utils;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.events.TickEndEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
	
	public static boolean inSkyBlock = false;
	
	public static String removeFormatting(String input) {
		return input.replaceAll("§[0-9a-fk-or]", "");
	}
	
	public static void sendMessageWithPrefix(String message) {
		(Minecraft.getMinecraft()).thePlayer.addChatMessage(new ChatComponentText("§8[§bJerrysMod§8] §7" + message));
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
	
	@SafeVarargs
	public static <T> T firstNotNull(T...args) {
		for(T arg : args) {
			if(arg != null) {
				return arg;
			}
		}
		return null;
	}
	
	public static List<String> getItemLore(ItemStack itemStack) {
		if (itemStack != null) {
			if (itemStack.hasTagCompound()) {
				NBTTagCompound display = itemStack.getSubCompound("display", false);
				
				if (display != null && display.hasKey("Lore", 9)) {
					NBTTagList lore = display.getTagList("Lore", 8);
					
					List<String> loreAsList = new ArrayList<>();
					for (int lineNumber = 0; lineNumber < lore.tagCount(); lineNumber++) {
						loreAsList.add(lore.getStringTagAt(lineNumber));
					}
					
					return Collections.unmodifiableList(loreAsList);
				}
			}
			
			return Collections.emptyList();
		} else {
			throw new NullPointerException("Cannot get lore from null item!");
		}
	}
	
	public static void sendMessageAsPlayer(String message) {
		JerrysMod.mc.thePlayer.sendChatMessage(message);
	}
	
	public static void executeCommand(String command) {
		if(ClientCommandHandler.instance.executeCommand(JerrysMod.mc.thePlayer, command) == 0) {
			sendMessageAsPlayer(command);
		}
	}
	
}
