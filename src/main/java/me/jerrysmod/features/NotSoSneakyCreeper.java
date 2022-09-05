package me.jerrysmod.features;

import me.jerrysmod.events.RenderEntityModelEvent;
import me.jerrysmod.utils.OutlineUtils;
import me.jerrysmod.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;

public class NotSoSneakyCreeper {
	
	private static HashMap<Entity, Color> highlightedEntities = new HashMap<>();
	
	private static void highlightEntity(Entity entity, Color color) {
		highlightedEntities.put(entity, color);
	}
	
	@SubscribeEvent
	public void onBeforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event){
		if(Utils.inSkyBlock){
			if(event.entity.isInvisible()){
				if(event.entity instanceof EntityCreeper){
					event.entity.setInvisible(false);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event){
		if(Utils.inSkyBlock){
			if(event.entity instanceof EntityCreeper){
				highlightEntity(event.entity, Color.GREEN);
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderEntityModel(RenderEntityModelEvent event){
		if(Utils.inSkyBlock){
			if(!highlightedEntities.isEmpty() && highlightedEntities.containsKey(event.entity)){
				OutlineUtils.outlineEntity(event, highlightedEntities.get(event.entity));
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		highlightedEntities.clear();
	}
}
