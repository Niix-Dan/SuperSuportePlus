package me.daniel.SuperSuportePlus.Comandos;

import java.util.ArrayList;
import java.util.List;
import me.daniel.SuperSuportePlus.Events;
import static me.daniel.SuperSuportePlus.SuperSuportePlus.plugin;
import me.daniel.SuperSuportePlus.T_Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class SuperSuporte extends BaseCmd {
    public SuperSuporte() {
        forcePlayer = true;
        cmdName = "supersuporte";
        
        // Permissão do comando
        permission = "SuperSuporte.command.supersuporte";
    }
    
    @Override
    public boolean run() {
        p = (Player) sender;
        
        if(args.length < 1) {
            Inventory inv = Bukkit.createInventory(null, 27, "§e§lSuper§4§lSuporte §c§lv"+me.daniel.SuperSuportePlus.SuperSuportePlus.version);
            inv.clear();
            
            List<String> apoiadores = new ArrayList();
            List<String> comandos = new ArrayList();
            
            apoiadores.add(" ");
            apoiadores.add("§a- §6SrNiix_");
            apoiadores.add("§a- §9DKplayerKS");
            apoiadores.add(" ");
            
            if(p.hasPermission("SuperSuporte.*") || p.hasPermission("SuperSuporte.supersuporte-menu.staffhelp")) {
                comandos.add(" ");
                comandos.add("§c/SuperSuporte §8[<reload>/<version>]");
                comandos.add("§c/duvida §8<duvida>");
                comandos.add("§c/responder §8<player> <resposta>");
                comandos.add("§c/duvidas");
                comandos.add(" ");
            } else if(p.hasPermission("SuperSuporte.*") || p.hasPermission("SuperSuporte.supersuporte-menu.playerhelp")) {
                comandos.add(" ");
                comandos.add("§c/SuperSuporte §8[version]");
                comandos.add("§c/duvida §8<duvida>");
                comandos.add(" ");
            }
            
            ItemStack apoiadores_Item = newItem(new ItemStack(Material.SKULL_ITEM), "§b§lDesenvolvimento", apoiadores, "MHF_Question");
            ItemStack comandos_Item = newItem(new ItemStack(Material.SKULL_ITEM), "§c§lComandos", comandos, "CONSOLE");
            
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 3);
            for(int i = 0 ; i < 9 ; i++) {
                inv.setItem(i, item);
                inv.setItem(i+18, item);
            }
            inv.setItem(9, item);
            inv.setItem(17, item);
            
            inv.setItem(12, apoiadores_Item);
            inv.setItem(14, comandos_Item);
                    
            p.openInventory(inv);
            
        } else {
            T_Config config = new T_Config(me.daniel.SuperSuportePlus.SuperSuportePlus.getPlugin(me.daniel.SuperSuportePlus.SuperSuportePlus.class), "config.yml");
            
            if(args[0].equalsIgnoreCase("reload")) {
                String prefix = config.getConfig().getString("prefix").replaceAll("&", "§")+ " ";
                if(!config.getConfig().getBoolean("allow-prefix")) {
                    prefix = "";
                }
                if(!(p.hasPermission("SuperSuporte.*") || p.hasPermission("SuperSuporte.command.reload"))) {
                    p.sendMessage(prefix+"§cVocê não contém permissões suficientes para executar este comando.");
                    return true;
                }
                
                try {
                    BaseCmd.reloadPlugin();
                    p.sendMessage(prefix+"§cConfigurações recarregadas com sucesso!");
                } catch(Exception e) {
                    p.sendMessage(prefix+"§cErro ao recarregar as configurações!");
                }
                
            }
            if(args[0].equalsIgnoreCase("version")) {
                p.sendMessage("§e§lSuperSuporte §cv"+me.daniel.SuperSuportePlus.SuperSuportePlus.version);
            }
        }
        
        return false;
    }
}
