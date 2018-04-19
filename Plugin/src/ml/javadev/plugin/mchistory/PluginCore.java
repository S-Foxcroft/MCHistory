package ml.javadev.plugin.mchistory;

import io.netty.handler.logging.LogLevel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginCore extends JavaPlugin {
	
	@Override
	public void onEnable(){
		getLogger().info("MCHistory 1.0 enabled successfully!");
	}
	@Override
	public void onDisable(){
		getLogger().info("MCHistory 1.0 disabled successfully!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args){
		if(cmd.getName().equalsIgnoreCase("names") || cmd.getName().equalsIgnoreCase("lookup")){
			if(args.length >1 || args.length == 0){
				sender.sendMessage("Revise your argument count!");
				sender.sendMessage("/names <username>");
			}
			else{
				String username = args[0];
				String result="";
				// Domain adjustment: files from javadev.ml now hosted at jd.ytfox.co.uk
				String uri = "http://jd.ytfox.co.uk/apps/dataStore/minecraftHistory/getData.php?arg0=" + username;
				URLConnection con;
				try {
					con = new URL(uri).openConnection();
					con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					con.connect();
					BufferedReader r  = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
					StringBuilder sb = new StringBuilder();
					String returned, newline="";
					boolean uuidPerm = false;
					if(sender == getServer().getConsoleSender()){ newline = "\r\n"; uuidPerm = true; }
					else {
						newline = "\n";
						if(sender.hasPermission("mchistory.uuid")){uuidPerm = true;}
					}
					while ((returned = r.readLine()) != null) {
						if(returned.startsWith("UUID:") && !uuidPerm){
							sender.sendMessage("UUID: You do not have the required permissions.");
						}
						else{
							sender.sendMessage(returned.replace("<br />","").replace("<br/>","") + newline);
						}
					}
				}
				catch(Exception e){
					getLogger().warning("Name lookup failed with the following error:");
					e.printStackTrace();
				}
			}
			return true;
		}
		
		return false;
	}
}
