package me.jerrysmod.features;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.events.TickEndEvent;
import me.jerrysmod.utils.LocationUtils;
import me.jerrysmod.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RatFinder {
	
	private static final List<Entity> rats = new ArrayList<>();
	private static boolean scanned = false;
	
	@SubscribeEvent
	public void onTick(TickEndEvent event){
		if(Utils.inSkyBlock) {
			if(LocationUtils.onIsland(LocationUtils.Island.HUB)){
				if(!scanned){
					rats.clear();
					for(Entity entity : JerrysMod.mc.theWorld.getLoadedEntityList()){
						if(entity instanceof EntityArmorStand){
							if(entity.hasCustomName() && entity.getCustomNameTag().contains("Rat")){
								rats.add(entity);
								//TODO: add waypoint or highlight block at rat
							}
						}
					}
					scanned = true;
					if(!rats.isEmpty() && JerrysMod.mc.theWorld != null && JerrysMod.mc.thePlayer != null){
						Utils.sendMessageWithPrefix("§7Found " + "§c" + rats.size() + " §7rats in this hub.");
					} else if (rats.isEmpty()){
						Utils.sendMessageWithPrefix("§cCouldn't find any rats!");
					}
				}
			}
		}
		if(event.every(400)){
			scanned = false;
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		rats.clear();
		scanned = false;
	}
}
