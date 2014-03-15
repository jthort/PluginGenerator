package com.daawsomest.plugingenerator.plugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PluginWriter {
	
	private JavaWriter w;
	
	private BufferedWriter yml;
	
	private String name;
	private int version;
	private String dir;
	
	
	/*
	 * Used to store the commands and actions. May need to be changed to a List
	 */
	private ArrayList<String> commands = new ArrayList<String>();
	private ArrayList<String> actions = new ArrayList<String>();
	private ArrayList<String> args1 = new ArrayList<String>();
	private ArrayList<String> args2 = new ArrayList<String>();
	private ArrayList<String> args3 = new ArrayList<String>();
	
	
	/*
	 * Id gets increased every time event other the command is added.
	 * id gets added to the event name to ensure name is unique
	 */
	private int id = 0;
	
	public PluginWriter(String name, int version, String dir){
		this.name = name;
		this.version = version;
		this.dir = dir;
	}
	
	public void beginPlugin() throws IOException {
		
		w = new JavaWriter(name, dir);
		
		w.declarePackage("com.pack");
		w.addImport("org.bukkit.*");
		w.addImport("org.bukkit.inventory.*");
		w.addImport("org.bukkit.command.*");
		w.addImport("org.bukkit.entity.*");
		w.addImport("org.bukkit.plugin.*");
		w.addImport("org.bukkit.event.*");
		w.addImport("org.bukkit.event.player.*");
		w.addImport("org.bukkit.plugin.java.JavaPlugin");
		w.addClass(name, "extends JavaPlugin implements Listener");
			
			w.method(Modifiers.PUBLIC, Modifiers.VOID, "onEnable");
				w.write("PluginManager pm = getServer().getPluginManager();");
				w.write("pm.registerEvents(this, this);");
				w.write("getLogger().info(\"Successfully Enabled\");");
			w.end();
			
			w.method(Modifiers.PUBLIC, Modifiers.VOID, "onDisable");
				w.write("getLogger().info(\"Successfully Disabled\");");
			w.end();
	}
	
	public void addCommand(String command, String action, String arg1, String arg2, String arg3) {
		commands.add(command);
		actions.add(action);
		args1.add(arg1);
		args2.add(arg2);
		args3.add(arg3);
	}
	
	public void generateCommandMethod() throws IOException {
		w.method(Modifiers.PUBLIC, Modifiers.BOOLEAN, "onCommand", "CommandSender sender, Command cmd, String lbl, String[] args");
			w.write("Player player = (Player) sender;");
			
			for(int i = 0; i < commands.size(); i ++) {
				w.write("if(cmd.getName().equalsIgnoreCase(\"" + commands.get(i) + "\")) {");
				
				if(actions.get(i).equals("message")) {
					message(args1.get(i));
				}
				
				if(actions.get(i).equals("broadcast")) {
					broadcast(args1.get(i));
				}
				
				if(actions.get(i).equals("teleport")) {
					teleportPlayer(args1.get(i), args2.get(i), args3.get(i));
				}
				
				if(actions.get(i).equals("giveitem")) {
					giveItem(args1.get(i), args2.get(i));
				}
				
				w.end();
				addYmlCommand(commands.get(i));
			}
			
			w.addReturn(true);
			w.end();
	}
	

	
	public void beginPlayerJoinEvent() throws IOException {
		id++;
		
		w.write("@EventHandler");
		w.method(Modifiers.PUBLIC, Modifiers.VOID, "onPlayerJoin" + Integer.toString(id), "PlayerJoinEvent event");
		w.write("Player player = event.getPlayer();");
	}
	
	public void endPlayerJoinEvent() throws IOException {
		w.end();
	}
	
	public void message(String msg) throws IOException {
		w.write("player.sendMessage(\"" + msg + "\");");
	}
	
	public void broadcast(String msg) throws IOException {
		w.write("for(Player p: player.getWorld().getPlayers()) {");
			w.write("p.sendMessage(\"" + msg + "\");");
		w.end();
	}
	
	public void teleportPlayer(String x, String y, String z) throws IOException {
		w.write("player.teleport(new Location(player.getWorld()," + x + ", " + y + ", " + z + "));");
	}
	
	public void giveItem(String id, String amount) throws IOException {
		w.write("player.getInventory().addItem(new ItemStack(Material.getMaterial(" + id +"), "+ amount +"));");
	}
	
	public void endPlugin() throws IOException {
		w.end();
		w.close();
	}
	

	

	
	public void beginYml() throws IOException {
		yml = new BufferedWriter(new FileWriter(dir + name + "/data/plugin.yml"));
		yml.write("name: " + name);
		yml.newLine();
		yml.write("version: " + version);
		yml.newLine();
		yml.write("main: com.pack." + name);
		yml.newLine();
		yml.write("commands:");
		yml.newLine();
	}
	
	public void addYmlCommand(String command) throws IOException {
		yml.write("  " + command + ":");
		yml.newLine();
		yml.write("    description: description");
		yml.newLine();
	}
	
	public void endYml() throws IOException {
		yml.close();
	}
	
	
	
	public void writeData() throws IOException {
		BufferedWriter cw = new BufferedWriter(new FileWriter(dir + "/data.txt"));
		cw.write(name);
		cw.close();
	}
	
	public void writeCompilerBatch() throws IOException {
		BufferedWriter cw = new BufferedWriter(new FileWriter(dir + "compiler.bat"));
		cw.write("@echo off");
		cw.newLine();
		cw.write("java -jar jdt.jar -source 1.7 -cp Bukkit.jar " + name + "\\data\\com\\pack\\" + name + ".java");
		cw.newLine();
		//cw.write("pause");
		cw.close();
		//Remove proccess.waitFor() before enabling pause
	}
	
	public String getName() {
		return name;
	}
	
	public int getVersion() {
		return version;
	}
	
}
