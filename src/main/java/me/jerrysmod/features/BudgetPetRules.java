package me.jerrysmod.features;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFishingRod;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class BudgetPetRules {
	
	private static boolean shouldOpenPet = false;
	
	private static void openPetMenu(){
		shouldOpenPet = true;
		Utils.executeCommand("/pets");
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent event){
		if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && JerrysMod.mc.thePlayer.getHeldItem() != null) {
			if(JerrysMod.mc.thePlayer.getHeldItem() != null && JerrysMod.mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod){
				openPetMenu();
			}
		}
	}
	
	@SubscribeEvent
	public void onDrawScreen(GuiScreenEvent.BackgroundDrawnEvent event){
		if(event.gui instanceof GuiChest && Utils.inSkyBlock && shouldOpenPet){
			new Thread(() -> {
				Container container = ((GuiChest) event.gui).inventorySlots;
				if(container instanceof ContainerChest){
					String invName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					if(invName.endsWith("Pets")){
						if(getSlotWithLore("Squid", container) > 0){
							// this mf does not click
							try {
								clickSlot(Objects.requireNonNull(findPet("Ammonite", container)));
							}catch (NullPointerException e){
								e.printStackTrace();
							}
							
						}else if(JerrysMod.mc.thePlayer.fishEntity != null && getSlotWithLore("Ammonite", container) > 0){
							try {
								clickSlot(Objects.requireNonNull(findPet("Squid", container)));
							}catch (NullPointerException e){
								e.printStackTrace();
							}
						}
						JerrysMod.mc.thePlayer.closeScreen();
					}
					//MinecraftForge.EVENT_BUS.unregister(this);
					
					shouldOpenPet = false;
				}
			}, "fortnite").start();
		}
	}
	
	private Slot findPet(String pet, Container inventory){
		for (Slot inventorySlot : inventory.inventorySlots) {
			if(inventorySlot.getHasStack() && Utils.removeFormatting(inventorySlot.getStack().getDisplayName()).contains(pet)){
				return inventorySlot;
			}
		}
		return null;
	}
	
	private int getSlotWithLore(String lore, Container inventory){
		for(Slot slot : inventory.inventorySlots){
			if(!slot.getHasStack()) continue;
			if((slot.getStack().getDisplayName().contains("Pets")) && slot.slotNumber < 54){
				if(Utils.getItemLore(slot.getStack()).toString().contains(lore)){
					return slot.slotNumber;
				}
			}
		}
		return -1;
	}
	
	
	private void clickSlot(Slot slot) {
		JerrysMod.mc.playerController.windowClick(JerrysMod.mc.thePlayer.openContainer.windowId, slot.slotNumber, 1, 0, JerrysMod.mc.thePlayer);
	}
	
}
