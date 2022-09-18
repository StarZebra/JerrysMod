package me.jerrysmod.features;

import me.jerrysmod.JerrysMod;
import me.jerrysmod.events.BlockChangeEvent;
import me.jerrysmod.events.TickEndEvent;
import me.jerrysmod.utils.KeybindUtils;
import me.jerrysmod.utils.LocationUtils;
import me.jerrysmod.utils.RenderUtils;
import me.jerrysmod.utils.Utils;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ColoredGlassFinder {
	
	public ColoredGlassFinder() {
		KeybindUtils.register("Glass Scanner", Keyboard.KEY_G);
	}
	
	private static boolean toggled = false;
	
	private ConcurrentHashMap<BlockPos, Gemstone> gemstones = new ConcurrentHashMap<>();
	private HashSet<BlockPos> checked = new HashSet<>();
	private BlockPos lastChecked = null;
	private boolean isScanning = false;
	
	enum Gemstone {
		RUBY(new Color(188, 3, 29), EnumDyeColor.RED),
		AMETHYST(new Color(135, 38, 194), EnumDyeColor.PURPLE),
		JADE(new Color(157, 249, 32), EnumDyeColor.LIME),
		SAPPHIRE(new Color(60, 121, 224), EnumDyeColor.LIGHT_BLUE),
		AMBER(new Color(237, 139, 35), EnumDyeColor.ORANGE),
		TOPAZ(new Color(249, 215, 36), EnumDyeColor.YELLOW);
		
		private final java.awt.Color color;
		public final EnumDyeColor dyeColor;
		
		Gemstone(Color color, EnumDyeColor dyeColor) {
			this.color = color;
			this.dyeColor = dyeColor;
		}
		
		Gemstone(Gemstone gemstone) {
			this.color = gemstone.color;
			this.dyeColor = gemstone.dyeColor;
		}
	}
	
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event){
		if(KeybindUtils.get("Glass Scanner").isPressed()){
			toggled = !toggled;
			if(toggled){
				Utils.sendMessageWithPrefix("§aScanner activated.");
			} else {
				Utils.sendMessageWithPrefix("§cScanner deactivated.");
			}
		}
	}
	
	@SubscribeEvent
	public void onTick(TickEndEvent event) {
		if(!toggled) {
			gemstones.clear();
			checked.clear();
			lastChecked = null;
			return;
		}
		if(isEnabled() && !isScanning && (lastChecked == null || !lastChecked.equals(JerrysMod.mc.thePlayer.playerLocation))) {
			isScanning = true;
			new Thread(()->{
				
				BlockPos playerPosition = JerrysMod.mc.thePlayer.getPosition();
				lastChecked = playerPosition;
				
				for(int x = playerPosition.getX()-20; x < playerPosition.getX()+20; x++) {
					for(int y = playerPosition.getY()-20; y < playerPosition.getY()+20; y++) {
						for(int z = playerPosition.getZ()-20; z < playerPosition.getZ()+20; z++) {
							
							BlockPos position = new BlockPos(x, y, z);
							
							if(!checked.contains(position) && !JerrysMod.mc.theWorld.isAirBlock(position)) {
								Gemstone gemstone = getGemstone(JerrysMod.mc.theWorld.getBlockState(position));
								if(gemstone != null) gemstones.put(position, gemstone);
							}
							checked.add(position);
							
						}
					}
				}
				
				isScanning = false;
				
			}, "JerrysMod-ScanningForGlass").start();
		}
	}
	
	@SubscribeEvent
	public void onRenderWorld(RenderWorldLastEvent event) {
		if(isEnabled()) {
			for(Map.Entry<BlockPos, Gemstone> gemstone : gemstones.entrySet()) {
				if(!isGemstoneEnabled(gemstone.getValue())) continue;
				double distanceSq = gemstone.getKey().distanceSq(JerrysMod.mc.thePlayer.posX, JerrysMod.mc.thePlayer.posY, JerrysMod.mc.thePlayer.posZ);
				if(distanceSq > Math.pow(20 + 2, 2)) continue;
				
				RenderUtils.outlineBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks); //outline
				
				// filled RenderUtils.highlightBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks);
				
			}
		}
	}
	
	private static boolean isGemstoneEnabled(Gemstone gemstone) {
		switch(gemstone) {
			case RUBY:
				return true;
			case AMETHYST:
				return true;
			case JADE:
				return true;
			case SAPPHIRE:
				return true;
			case AMBER:
				return true;
			case TOPAZ:
				return true;
			default:
				return false;
		}
	}
	
	private static Gemstone getGemstone(IBlockState block) {
		if(block.getBlock() != Blocks.stained_glass && block.getBlock() != Blocks.stained_glass_pane) return null;
		
		EnumDyeColor color = Utils.firstNotNull(block.getValue(BlockStainedGlass.COLOR), block.getValue(BlockStainedGlassPane.COLOR));
		
		if(color == Gemstone.RUBY.dyeColor) return Gemstone.RUBY;
		if(color == Gemstone.AMETHYST.dyeColor) return Gemstone.AMETHYST;
		if(color == Gemstone.SAPPHIRE.dyeColor) return Gemstone.SAPPHIRE;
		if(color == Gemstone.JADE.dyeColor) return Gemstone.JADE;
		if(color == Gemstone.AMBER.dyeColor) return Gemstone.AMBER;
		if(color == Gemstone.TOPAZ.dyeColor) return Gemstone.TOPAZ;
		
		return null;
	}
	
	@SubscribeEvent
	public void onBlockChange(BlockChangeEvent event) {
		if(event.newBlock.getBlock() == Blocks.air) {
			gemstones.remove(event.position);
		}
	}
	
	private static boolean isEnabled() {
		return JerrysMod.mc.thePlayer != null &&
				JerrysMod.mc.theWorld != null &&
				Utils.inSkyBlock &&
				LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS);
	}
	
	@SubscribeEvent
	public void onWorldChange(WorldEvent.Load event) {
		gemstones.clear();
		checked.clear();
		toggled = false;
		lastChecked = null;
	}
}
