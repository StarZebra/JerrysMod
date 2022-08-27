package me.jerrysmod.features;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DungeonMeterRuns {
	
	private static String scoreLine;
	
	@SubscribeEvent
	public void onItemChange(ItemTooltipEvent event) {
		if(!Utils.inSkyBlock) return;
		
		String name;
		if(!Utils.getInventoryName().contains("RNG Meter")) return;
		List<String> toolTip = event.toolTip;
		String part1 = "Catacombs ";
		String part2 = " RNG Meter";
		String invName = Utils.getInventoryName();
		if(invName.contains("(1/2)")){
			name = invName.replace("(1/2) ", "");
		}else if (invName.contains("(2/2)")){
			name = invName.replace("(2/2) ", "");
		}else{
			name = invName;
		}
			
		Pattern pattern = Pattern.compile(part1 + "[(\\w)]+" + part2);
		Matcher matcher = pattern.matcher(name);
			
		boolean inGui = JerrysMod.mc.currentScreen instanceof GuiChest && matcher.matches();
			
		if(inGui){
			String unformattedToolTip = Utils.removeFormatting(toolTip.toString());
			int line = 0;
			String[] tooltip = unformattedToolTip.split(", ");
			if(unformattedToolTip.contains("Click to select!")){
				
				for (String element: tooltip) {
					if (element.startsWith("Dungeon Score: ")) {
						scoreLine = toolTip.get(line);
						break;
					}
					line++;
				}
				
				addLore(toolTip, line);
					
			}else if(unformattedToolTip.contains("SELECTED")){
				
				for (String element: tooltip) {
					if (element.startsWith("------")) {
						scoreLine = toolTip.get(line);
						break;
					}
					line++;
				}
				
				addLore(toolTip, line);
			}
		}
		
	}
	
	private static void addLore(List<String> toolTip, int lineNum){
		float finalScore;
		
		String[] test = scoreLine.split("/");
		String score = test[test.length - 1];
		String cleanScore = Utils.removeFormatting(score).replace(",", "");
		String kSplit;
		if (cleanScore.contains("k")) {
			if(cleanScore.contains(".")){
				kSplit = cleanScore.replace(".", "");
				cleanScore = kSplit.replace("k", "00");
			}
			cleanScore = cleanScore.replace("k", "000");
		}
		
		finalScore = Float.parseFloat(cleanScore);
		float amountOfRuns = finalScore/300;
		int ceilingRun = (int) Math.ceil(amountOfRuns);
		String finalString = "§8(" + ceilingRun + "§8 S+ Runs)";
		toolTip.set(lineNum+1, finalString);
		toolTip.add(lineNum+2, "");
	}
}