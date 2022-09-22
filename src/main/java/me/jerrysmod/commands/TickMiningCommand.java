package me.jerrysmod.commands;

import me.jerrysmod.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.HashMap;
import java.util.Map;

public class TickMiningCommand extends CommandBase {
	
	private static final HashMap<Integer, String> blockHardness = new HashMap<>();
	
	private static void initHashmap(){
		blockHardness.put(2500, "Ruby");
		blockHardness.put(3200, "Jade");
		blockHardness.put(4000, "Topaz");
		blockHardness.put(5000, "Jasper");
	}
	
	@Override
	public String getCommandName() {
		return "getspeed";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName();
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0){
			initHashmap();
			String ticks = args[0];
			if(ticks.matches("[0-9]+")){
				float tick = Float.parseFloat(ticks);
				if(tick > 4){
					Utils.sendMessageWithPrefix("§7For §a" + (int)tick + " ticks.");
					for(Map.Entry<Integer, String> set : blockHardness.entrySet()) {
						float speed;
						int hardness = set.getKey();
						String type = set.getValue();
						
						if(tick%1 != 0)
						{
							return;
						}
						
						speed = ((30f * hardness)/(tick + 0.49999f));
						
						if(speed%1 != 0)
						{
							speed = speed+1;
						}
						
						Utils.sendMessageWithPrefix("§e" + type + ": §a" + (int)speed + " §emining speed.");
						
					}
					
				}else{
					Utils.sendMessageWithPrefix("§cTicks cant be lower than 4.");
				}
			}
		}
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}
