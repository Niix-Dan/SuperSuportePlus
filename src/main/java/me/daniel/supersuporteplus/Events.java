package me.daniel.SuperSuportePlus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.SkullMeta;

public class Events implements Listener {
    public static T_Config configs = new T_Config(SuperSuportePlus.getPlugin(SuperSuportePlus.class), "config.yml");
    public static T_Config reports = new T_Config(SuperSuportePlus.getPlugin(SuperSuportePlus.class), "duvidas.yml");
    public static T_Config messages = new T_Config(SuperSuportePlus.getPlugin(SuperSuportePlus.class), "messages.yml");

    // Variaveis
    String prefix = configs.getConfig().getString("prefix").replaceAll("&", "§")+" ";
    String dplayer = messages.getConfig().getString("duvidap_player").replaceAll("&", "§");
    String dstaff = messages.getConfig().getString("duvidap_staff").replaceAll("&", "§");
    Boolean dallow = configs.getConfig().getBoolean("configs.duvida-pendente.allow");
    String menuname = messages.getConfig().getString("duvidas_menuname").replaceAll("&", "§");

    @EventHandler

    public void onJoin(PlayerJoinEvent e) {
        if(!configs.getConfig().getBoolean("prefix-allow")) {
            prefix = "";
        }
        if(dallow) {
            Player pls = e.getPlayer();
            String nome = pls.getName().toLowerCase();
            
            dstaff = dstaff.replaceAll("<player>", pls.getDisplayName());
            dplayer = dplayer.replaceAll("<player>", pls.getDisplayName());

            if(reports.getConfig().contains("Player."+nome)) {
                pls.sendMessage(prefix+dplayer);
                for(Player plss : Bukkit.getOnlinePlayers()) {
                    if(plss.hasPermission("SuperSuporte.*") || plss.hasPermission("SuperSuporte.command.*")  || (plss.hasPermission("SuperSuporte.command.duvidas") && plss.hasPermission("SuperSuporte.command.responder"))) {
                        plss.sendMessage(prefix+dstaff);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!configs.getConfig().getBoolean("prefix-allow")) {
            prefix = "";
        }
        Player p = (Player) e.getWhoClicked();

        if(e.getView().getTopInventory().getName().equals(menuname)) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null) {
                if (!p.hasPermission("SuperSuporte.command.duvidas")) {
                    return;
                }
                if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                    //p.playSound(p.getLocation(), Sound.ENTITY_CAT_AMBIENT, 0, 0);
                    SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();

                    String skullowner = meta.getOwner();
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
                    reports.reloadConfig();
                    reports.set("Player." + skullowner.toLowerCase(), null);
                    reports.saveConfig();
                    Bukkit.dispatchCommand(p, "duvidas");
                }
            }
        }
        if(e.getView().getTopInventory().getName().equals("§e§lSuper§4§lSuporte §c§lv"+SuperSuportePlus.version)) {
            e.setCancelled(true);
        }
    }
}