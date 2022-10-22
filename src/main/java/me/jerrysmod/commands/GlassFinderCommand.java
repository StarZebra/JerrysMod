package me.jerrysmod.commands;

import me.jerrysmod.tempconfig.Config;
import me.jerrysmod.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class GlassFinderCommand extends CommandBase {
	
	private static boolean gemStatus = false;
	
	@Override
	public String getCommandName() {
		return "toggle";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName() + "<gemstone>";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0){
			switch(args[0]) {
				case "jade":
					Config.setJadeEsp(!Config.jadeEsp);
					gemStatus = Config.jadeEsp;
					break;
				case "ruby":
					Config.setRubyEsp(!Config.rubyEsp);
					gemStatus = Config.rubyEsp;
					break;
				case "amethyst":
					Config.setAmethystEsp(!Config.amethystEsp);
					gemStatus = Config.amethystEsp;
					break;
				case "amber":
					Config.setAmberEsp(!Config.amberEsp);
					gemStatus = Config.amberEsp;
					break;
				case "topaz":
					Config.setTopazEsp(!Config.topazEsp);
					gemStatus = Config.topazEsp;
					break;
				case "jasper":
					Config.setJasperEsp(!Config.jasperEsp);
					gemStatus = Config.jasperEsp;
					break;
				case "sapphire":
					Config.setSapphireEsp(!Config.sapphireEsp);
					gemStatus = Config.sapphireEsp;
				case "petrules":
					Config.setBudgetPetRules(!Config.budgetPetRules);
					gemStatus = Config.budgetPetRules;
					break;
			}
			String gem = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
			Utils.sendMessageWithPrefix("§e" + gem + ": §b" + gemStatus);
		}
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
}
