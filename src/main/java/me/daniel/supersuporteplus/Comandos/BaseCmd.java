package me.daniel.SuperSuportePlus.Comandos;

import me.daniel.SuperSuportePlus.Events;


import java.util.List;
import me.daniel.SuperSuportePlus.SuperSuportePlus;
import me.daniel.SuperSuportePlus.T_Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static me.daniel.SuperSuportePlus.SuperSuportePlus.plugin;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class BaseCmd {
    public BaseCmd(){}
    public CommandSender sender;
    public String[] args;
    public String cmdName;
    public boolean forcePlayer = true;
    public String usageErr = "";
    public Player p;
    public String permission = "";
    public static T_Config configs = new T_Config(SuperSuportePlus.getPlugin(SuperSuportePlus.class), "config.yml");
    public static T_Config messages = new T_Config(SuperSuportePlus.getPlugin(SuperSuportePlus.class), "messages.yml");
    public static T_Config reports = new T_Config(SuperSuportePlus.getPlugin(SuperSuportePlus.class), "duvidas.yml");
    public String prefix = configs.getConfig().getBoolean("prefix-allow") ? configs.getConfig().getString("prefix").replaceAll("&", "§")+" " : "";
    public String headname = messages.getConfig().getString("duvidas_head_name");
    public List<String> headdesc = messages.getConfig().getStringList("duvidas_head_description");
    
    
    public ItemStack nameItemi(ItemStack item, String name, List lore, String owner) {
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(owner);
        sm.setLore(lore);
        sm.setDisplayName(name);
        
        item.setDurability((short)3);
        item.setItemMeta(sm);
        
        return item;
    }
    
    public void PlayResp(final Player p) {
        Play(p, 0);
        new BukkitRunnable() {
            public void run() {
                Play(p, 1);
                new BukkitRunnable() {
                    public void run() {
                        Play(p, 2);
                    }
                }.runTaskLater(SuperSuportePlus.plugin, 2);
            }
        }.runTaskLater(SuperSuportePlus.plugin, 2);
    }
    
    public void Play(Player p, int n) {
        if(n == 0) // 0
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, (float) 1.5); //
        else if (n == 1) // 1
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, (float) 1.7); //
        else if (n == 2) // 2
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 1, (float) 1.3); //
        else if (n == 3) // 3
            p.playSound(p.getLocation(), Sound.ENTITY_EGG_THROW, 1, 1); //
        else if (n == 4) // 4
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1); //
        else // 5 ou mais
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 0); //
    }
    
    
    public static void reloadPlugin() {
        Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).reloadConfig();
        configs.reloadConfig();
        reports.reloadConfig();
        messages.reloadConfig();
        Events.configs.reloadConfig();
        Events.reports.reloadConfig();
        plugin.reloadConfig();
        Bukkit.getPluginManager().getPlugin(plugin.getName()).reloadConfig();
        Bukkit.getServer().getPluginManager().enablePlugin(plugin);
    }
    
    public ItemStack newItem(ItemStack item, String name, List lore, String owner) {
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        
        sm.setOwner(owner);
        sm.setLore(lore);
        sm.setDisplayName(name);
        
        item.setDurability((short) 3);
        item.setItemMeta(sm);
        
        return item;
    }
    
    
    public String Separator(List lista) {
        String result = String.join("\n", lista);
        result = result.replaceAll("&", "§");
        return result;
    }
	
    public String Mensagem(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        return sb.toString();
    }

    public String Mensagem2(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1 ; i < args.length ; i++) {
            sb.append(args[i]);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    public boolean proccesCmd(CommandSender s, String[] arg) {
        sender = s;
        args = arg;
        
    	if(forcePlayer){
            if (!(s instanceof Player)) {
		sender.sendMessage(ChatColor.RED + "Apenas jogadores podem executar este comando!");
                return true;
            }
	}
        
        
        run();
        return false;
    }
    
    
    public abstract boolean run();
}

