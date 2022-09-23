package me.jerrysmod.features;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.events.TickEndEvent;
import me.jerrysmod.utils.LocationUtils;
import me.jerrysmod.utils.Utils;
import me.jerrysmod.utils.WaypointUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RatFinder {
	
	private static final List<Entity> rats = new ArrayList<>();
	private static boolean scanned = false;
	private static boolean foundRat = false;
	
	@SubscribeEvent
	public void onTick(TickEndEvent event){
		if(Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.HUB)) {
			if(!scanned){
				rats.clear();
				if(JerrysMod.mc.theWorld == null) return;
				for(Entity entity : JerrysMod.mc.theWorld.getLoadedEntityList()){
					if(entity instanceof EntityZombie ){
						EntityZombie rat = (EntityZombie) entity;
						if(rat.isChild()) {
							List<Entity> possibleEntities = rat.getEntityWorld().getEntitiesInAABBexcluding(rat, rat.getEntityBoundingBox().expand(0, 3, 0), e -> e instanceof EntityArmorStand);
							if(!possibleEntities.isEmpty()){
								EntityArmorStand nameTag = (EntityArmorStand) possibleEntities.get(0);
								if(nameTag.hasCustomName()) return;
								ItemStack itemStack = nameTag.getCurrentArmor(3);
								if(itemStack != null && itemStack.getItem() instanceof ItemSkull){
									String nbt = itemStack.serializeNBT().getCompoundTag("tag").getCompoundTag("SkullOwner").getCompoundTag("Properties").getTag("textures").toString();
									if(nbt.contains("ewogICJ0aW1lc3RhbXAiIDogMTYxODQxOTcwMTc1MywKICAicHJvZmlsZUlkIiA6ICI3MzgyZGRmYmU0ODU0NTVjODI1ZjkwMGY4OGZkMzJmOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJCdUlJZXQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThhYmI0NzFkYjBhYjc4NzAzMDExOTc5ZGM4YjQwNzk4YTk0MWYzYTRkZWMzZWM2MWNiZWVjMmFmOGNmZmU4IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=")) {
										rats.add(rat);
									}
								}
							}
						}
					}
				}
				scanned = true;
				if(!rats.isEmpty() && JerrysMod.mc.theWorld != null && JerrysMod.mc.thePlayer != null && !foundRat){
					Utils.sendMessageWithPrefix("§7Found §c" + rats.size() + " §7rats in this hub.");
					foundRat = true;
				}else if(rats.isEmpty()){
					foundRat = false;
				}
			}
			
		}
		if(event.every(20)){
			scanned = false;
		}
	}
	
	@SubscribeEvent
	public void onKillRat(LivingDeathEvent event){
		if(Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.HUB)){
			if(event.entity instanceof EntityZombie && ((EntityZombie) event.entity).isChild()){
				rats.remove(event.entity);
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderLast(RenderWorldLastEvent event){
		for (Entity rat : rats) {
			WaypointUtils.renderBeaconBeam(rat.getPosition(), 0x1fd8f1, 1.0F, event.partialTicks);
			WaypointUtils.renderWayPoint("RAT", rat.getPosition(), event.partialTicks);
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		rats.clear();
		scanned = false;
		foundRat = false;
	}
}
