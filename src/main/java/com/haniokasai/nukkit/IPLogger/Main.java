package com.haniokasai.nukkit.IPLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;



public class Main extends PluginBase implements Listener{



	public void onEnable() {

		 this.getServer().getPluginManager().registerEvents(this, this);
		getDataFolder().mkdir();

		Config config = new Config(
                new File(this.getDataFolder(), "data.yml"),Config.YAML,
                new LinkedHashMap<String, Object>() {
                    {
                    	put("name", "iplist");
                    }
                });
        config.save();
        this.getServer().getLogger().info("[AdminNotice] Loaded");

}

	@EventHandler
	public void join(PlayerJoinEvent event){
		Config config = new Config(new File(this.getDataFolder(), "data.yml"),Config.YAML);
		Player player = event.getPlayer();
		String name = player.getName();
		String ip = player.getAddress();
		if(!config.exists(name)){
			ArrayList<String> ipl = new ArrayList<>();
		    ipl.add(ip);
			config.set(name,ipl);
		}else{
			@SuppressWarnings("unchecked")
			ArrayList<String> ipl = (ArrayList<String>) config.get(name);
			ipl.add(ip);
	        HashSet<String> hashSet = new HashSet<String>();
	        hashSet.addAll(ipl);
	        Object[] ipl2 = hashSet.toArray();
			config.set(name,ipl2);
		}
		config.save();
	}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Config config = new Config(new File(this.getDataFolder(), "data.yml"));
        switch (command.getName()) {
            case "ipl":
        		if(!sender.hasPermission("ipl")){
        			sender.sendMessage(TextFormat.RED + "You don't have permission to use this command.");
        			return false;
        		}else{
        			try{
        				if(config.exists(args[0])){
        					sender.sendMessage(TextFormat.RED + "/*/*/*/*/IPLIST/*/*/*/*/");
        					sender.sendMessage(TextFormat.RED + "PlayerName: "+args[0]);
        					@SuppressWarnings("unchecked")
							ArrayList<String> ipl = (ArrayList<String>) config.get(args[0]);
        					sender.sendMessage(TextFormat.RED +ipl );
        				}else{
        					sender.sendMessage(TextFormat.RED + "This player isnt exists!");
        				}
        			} catch (Exception e) {
        				return false;
        			}
        		}
            break;
        }
		return true;}

}
