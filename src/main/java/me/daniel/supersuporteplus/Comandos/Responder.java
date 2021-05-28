package me.daniel.SuperSuportePlus.Comandos;

import java.util.List;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Responder extends BaseCmd {
    public Responder() {
        forcePlayer = true;
        cmdName = "responder";
        usageErr = "§cUso incorreto! Use: §6/responder <jogador> <resposta>";
        
        // Permissão do comando
        permission = "SuperSuporte.command.responder";
    }
    
    @Override
    public boolean run() {
        p = (Player) sender;
        if(args.length == 0) {
            List<String> no_args = messages.getConfig().getStringList("responder_no_args");
            p.sendMessage(prefix+Separator(no_args));
            Play(p, 5);
            return true;
        }
            
        String reporter = args[0];
        String mensagem = Mensagem2(args);
        Player players = Bukkit.getPlayer(reporter);
            
        if(reports.getConfig().getString("Player."+reporter.toLowerCase()) != null) {
            if(!players.isOnline()) {
                p.sendMessage(prefix+messages.getConfig().getString("responder_offline").replaceAll("&", "§"));
                Play(p, 5);
                return true;
            }
            String duvida = reports.getConfig().getString("Player."+reporter.toLowerCase()+".duvida");
                String resp = messages.getConfig().getString("responder_player_message").replaceAll("&", "§");
                resp = resp.replaceAll("<duvida>", reports.getConfig().getString("Player."+reporter.toLowerCase()+".duvida"));
                resp = resp.replaceAll("<staff>", p.getDisplayName());
                resp = resp.replaceAll("<resposta>", mensagem);
                players.sendMessage(prefix+resp);
                
                Play(players, 4);
                PlayResp(p);
                
                reports.set("Player."+reporter.toLowerCase(), null);
                reports.saveConfig();
                
                for(Player plsa : Bukkit.getOnlinePlayers()) {
                    if(plsa.hasPermission("supersuporte.staff")) { // Staffs
                        if(!configs.getConfig().getBoolean("configs.responder.staffs.resp-message.show")) return true;
                        
                        String content = Separator(configs.getConfig().getStringList("messages.responder.staffs.resp-message.message"));
                        
                        content = content.replaceAll("<duvida>", duvida);
                        content = content.replaceAll("<staff>", p.getDisplayName()); 
                        content = content.replaceAll("<resposta>", mensagem);
                        content = content.replaceAll("<player>", players.getDisplayName());
                        
                        TextComponent msg = new TextComponent(prefix+content);
                        
                        if(configs.getConfig().getBoolean("configs.responder.staffs.mousehover.allow")) {
                            String mousehover = configs.getConfig().getString("configs.responder.staffs.mousehover.text").replaceAll("&", "§");
                            
                            mousehover = mousehover.replaceAll("<duvida>", duvida);
                            mousehover = mousehover.replaceAll("<staff>", p.getDisplayName()); 
                            mousehover = mousehover.replaceAll("<resposta>", mensagem);
                            mousehover = mousehover.replaceAll("<player>", players.getDisplayName());
                            
                            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mousehover).create()));
                        }
                        plsa.spigot().sendMessage(msg);
                    }else { // Players
                        if(!configs.getConfig().getBoolean("configs.responder.players.resp-message.show")) return true;
                        
                        String content = Separator(configs.getConfig().getStringList("configs.responder.players.resp-message.message"));
                        
                        content = content.replaceAll("<duvida>", duvida);
                        content = content.replaceAll("<staff>", p.getPlayerListName());
                        content = content.replaceAll("<resposta>", mensagem);
                        content = content.replaceAll("<player>", players.getPlayerListName());
                        
                        TextComponent msg = new TextComponent(prefix+content);
                        
                        if(configs.getConfig().getBoolean("messages.responder.players.mousehover.allow")) {
                            String mousehover = configs.getConfig().getString("messages.responder.players.mousehover.text").replaceAll("&", "§");
                            
                            mousehover = mousehover.replaceAll("<duvida>", duvida);
                            mousehover = mousehover.replaceAll("<staff>", p.getPlayerListName());
                            mousehover = mousehover.replaceAll("<resposta>", mensagem);
                            mousehover = mousehover.replaceAll("<player>", players.getPlayerListName());
                            
                            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(mousehover).create()));
                        }
                        plsa.spigot().sendMessage(msg);
                    }
                }
            }
        
        return false;
    }
    
}
