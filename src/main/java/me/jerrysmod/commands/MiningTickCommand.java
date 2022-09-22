package me.jerrysmod.commands;

import me.jerrysmod.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.HashMap;
import java.util.Map;

public class MiningTickCommand extends CommandBase {
	
	private static final String INVALID_ARGUMENTS = "§cInvalid arguments!";
	private static final HashMap<Integer, String> blockHardness = new HashMap<>();
	
	private static void initHashmap(){
		blockHardness.put(2500, "Ruby");
		blockHardness.put(3200, "Jade");
		blockHardness.put(4000, "Topaz");
		blockHardness.put(5000, "Jasper");
	}
	
	
	@Override
	public String getCommandName() {
		return "getticks";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName() + " <mining speed>";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0){
			initHashmap();
			String speed = args[0];
			if(speed.matches("[0-9]+")){
				float mspeed = Float.parseFloat(speed);
				for(Map.Entry<Integer, String> set : blockHardness.entrySet()) {
					//set.getKey() = block hardness
					//set.getValue() = block type
					float tick, m2;
					int t_int, t2;
					
					int hardness = set.getKey();
					String type = set.getValue();
					
					tick = ((30 * hardness - 0.49999f * mspeed) / mspeed);
					
					if(tick%1 != 0)
					{
						tick = tick+1;
					}
					
					t_int = (int)tick;
					
					if(t_int <= 3 && t_int >= 1)
					{
						t_int = 4;
					}
					
					t2 = (int)tick - 1;
					
					m2 = ((30f * hardness)/(t2 + 0.49999f));
					
					
					if(m2%1 != 0)
					{
						m2 = m2 + 1;
					}
					
					Utils.sendMessageWithPrefix("§e" + type + "§e: §a" + t_int + " ticks!");
					if(t_int >= 5){
						Utils.sendMessageWithPrefix("§7Next tick at §a" + (int)m2);
					}
					
				}
				
			} else{
				Utils.sendMessageWithPrefix(INVALID_ARGUMENTS);
			}
		}else{
			Utils.sendMessageWithPrefix(INVALID_ARGUMENTS);
		}
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}
