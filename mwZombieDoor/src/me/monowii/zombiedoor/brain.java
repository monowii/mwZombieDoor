package me.monowii.zombiedoor;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class brain extends JavaPlugin implements Listener
{
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		
    	FileConfiguration config = getConfig();
    	String[] worlds = {"SuperHardCoreWorld"};
    	config.addDefault("IgnoreWorlds", Arrays.asList(worlds));
        config.options().copyDefaults(true);
        saveConfig();
	}
	
    public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("zombiedoor")) {
    		if (args.length == 0) {
           		sender.sendMessage("§8-------------------§6mwZombieDoor§8-§6Help§8------------------");
           		sender.sendMessage("§7/zombiedoor [add/remove] <WorldName> §0-§f Set if zombie can break door in a world");
           		sender.sendMessage("§7/zombiedoor worlds §0-§f Set if zombie can break door in a world");
    		}
    		else {
    			if (args[0].equalsIgnoreCase("worlds")) {
    				List<String> worlds = getConfig().getStringList("IgnoreWorlds");
    				sender.sendMessage("§2Zombies can break door in these worlds :");
    				sender.sendMessage("§7"+worlds.toString());
    			}
    			else if (args[0].equalsIgnoreCase("remove")) {
    				if (args.length == 1) {
    					sender.sendMessage("You don't have specified a world !");
    				}
    				else {
        				sender.sendMessage("World "+args[1]+" Removed !");
        				
        				List<String> list = getConfig().getStringList("IgnoreWorlds");
        				list.remove(args[1]);
        			    getConfig().set("IgnoreWorlds", list);
        	    		saveConfig();
    				}
    			}
    			else if (args[0].equalsIgnoreCase("add")) {
    				if (args.length == 1) {
    					sender.sendMessage("You don't have specified a world !");
    				}
    				else  {
        				if (args[1] != null) {
            				sender.sendMessage("World "+args[1]+" Added !");
            				
            				List<String> list = getConfig().getStringList("IgnoreWorlds");
            				list.add(args[1]);
            			    getConfig().set("IgnoreWorlds", list);
            	    		saveConfig();
        				}
    				}
    			}
    		}
    	}
		return false;
    }
    
	@EventHandler (priority = EventPriority.HIGH)
	public void onDoorBreak(EntityChangeBlockEvent e) 
	{
		if (e.getBlock().getType().equals(Material.WOODEN_DOOR) && e.getEntity() instanceof Zombie && !getConfig().getStringList("IgnoreWorlds").contains(e.getBlock().getWorld().getName()))
		{
			e.setCancelled(true);
		}
	}
}
