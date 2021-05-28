package me.daniel.SuperSuportePlus.Comandos;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Duvidas extends BaseCmd {
    public Duvidas() {
        forcePlayer = true;
        cmdName = "duvidas";
        usageErr = "§cUso incorreto! Use: §6/duvidas";
        
        // Permissão do comando
        permission = "SuperSuporte.command.duvidas";
    }
    @Override
    public boolean run() {
        p = (Player) sender;
        
        List<String> reportsmenu = new ArrayList();
        List<String> base = new ArrayList();
        List<String> base2 = new ArrayList();
        
        if(reports.getConfig().getConfigurationSection("Player") == null) {
            String empty = messages.getConfig().getString("duvidas_empty").replaceAll("&", "§");
            p.sendMessage(empty);
            return true;
        }
        
        reports.reloadConfig();
        String nome = messages.getConfig().getString("duvidas_menuname").replaceAll("&", "§");
        Inventory inv = Bukkit.createInventory(null, 54, nome);
        inv.clear();
    
        ConfigurationSection cs = reports.getConfig().getConfigurationSection("Player");
        for(String report : cs.getKeys(false)){
            reportsmenu.add(report);
        }
    
        for(int n = 0 ; n < reportsmenu.size() ; ++n) {
            String duvida = cs.getString(reportsmenu.get(n)+".duvida").replaceAll("((?:\\w+\\s){4}\\w+)(\\s)", "$1\n");
        
            base.clear();
            base2.clear();
        
            base = messages.getConfig().getStringList("duvidas_head_description");
            for(String line : base) {
                line = line.replaceAll("<player>", reportsmenu.get(n));
                line = line.replaceAll("<duvida>", duvida);
                line = line.replaceAll("<criado>", cs.getString(reportsmenu.get(n)+".expira"));
                line = line.replaceAll("&", "§");
                
                base2.add(line);
            }
        
            ItemStack si = nameItemi(new ItemStack(Material.SKULL_ITEM),"§6§l"+reportsmenu.get(n), base2, reportsmenu.get(n));
            inv.setItem(n, si);
        }
        p.openInventory(inv);
        
        return true;
    }
}
