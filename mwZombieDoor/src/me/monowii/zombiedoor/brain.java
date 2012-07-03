package me.monowii.zombiedoor;

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
    	config.addDefault("ZombieCanBreakDoor", false);
        getConfig().options().copyDefaults(true);
        saveConfig();
	}
	
    public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) 
    {
    	if (cmd.getName().equalsIgnoreCase("zombiedoor"))
    	{
    		
    		boolean door = getConfig().getBoolean("ZombieCanBreakDoor");
    		if (door)
    		{
    			getConfig().set("ZombieCanBreakDoor", false);
    			sender.sendMessage("§aThe zombies can't break doors now");
    		}
    		else
    		{
    			getConfig().set("ZombieCanBreakDoor", true);
    			sender.sendMessage("§aZombies can now break down the doors");
    		}
    		saveConfig();
    		
    	}
		return false;
    }
    
	@EventHandler (priority = EventPriority.HIGH)
	public void onDoorBreak(EntityChangeBlockEvent e) 
	{
		if (e.getBlock().getType().equals(Material.WOODEN_DOOR) && e.getEntity() instanceof Zombie && !getConfig().getBoolean("ZombieCanBreakDoor"))
		{
			e.setCancelled(true);
		}
	}
}
