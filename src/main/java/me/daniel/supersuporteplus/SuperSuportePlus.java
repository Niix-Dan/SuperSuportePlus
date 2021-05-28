package me.daniel.SuperSuportePlus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import me.daniel.SuperSuportePlus.Comandos.CmdManager;
import org.bstats.bukkit.Metrics;

public class SuperSuportePlus extends JavaPlugin {
    public static SuperSuportePlus plugin;
    public static String version;
    
    @Override
    public void onEnable() {
        plugin = this;
        version = "1.0 +";
        
        File messagesFile = new File (getDataFolder(), "messages.yml");
        if(!messagesFile.exists()) {
            this.saveResource("messages.yml", false);
        }
        
        
        getConfig().options().copyDefaults(true);
        
        saveDefaultConfig();
        
        
        metrics();
        
        Cmds();
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getConsoleSender().sendMessage("§e[SuperSuporte] §eSuperSuporte §ePlugin §ahabilitado §ecom §esucesso!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§c[SuperSuporte] §cSuperSuporte §cPlugin §4desabilitado §ccom §csucesso!");
    }

    public void Cmds() {
        getCommand("duvida").setExecutor(new CmdManager());
        getCommand("responder").setExecutor(new CmdManager());
        getCommand("supersuporte").setExecutor(new CmdManager());
        getCommand("duvidas").setExecutor(new CmdManager());
    }

    private void metrics() {
        Metrics metrics = new Metrics(this, 9677);
        /*String on = "Ligado";
        String off = "Desligado";*/
        // Envia número de jogadores online ao servidor de status
        
        metrics.addCustomChart(new Metrics.SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
        metrics.addCustomChart(new Metrics.SingleLineChart("servers", () -> 1));
        
    }

}
