

package me.daniel.SuperSuportePlus.Comandos;

//import me.daniel.supersuporteplus.

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Duvida extends BaseCmd {
    public Duvida() {
        forcePlayer = true;
        cmdName = "duvida";
        usageErr = "§cUso incorreto! Use: §6/duvida <duvida>";
        
        // Permissão do comando
        permission = "SuperSuporte.command.duvida";
    }
    
    @Override
    public boolean run() {
        p = (Player) sender;
        
        if(args.length == 0) {
            List<String> no_args = messages.getConfig().getStringList("duvida_no_args");
            p.sendMessage(prefix+Separator(no_args));
            Play(p, 5);
            return true;
        }
            
        if(reports.getConfig().getString("Player."+p.getName().toLowerCase()) != null ) {
            List<String> duv_exist = messages.getConfig().getStringList("duvida_exist");
            p.sendMessage(prefix+Separator(duv_exist));
            Play(p, 5);
            return true;
        }
        
        String reporter = p.getName().toLowerCase();
        String mensagem = Mensagem(args);
            
        for(Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("SuperSuporte.command.*") || players.hasPermission("SuperSuporte.command.responder") || players.hasPermission("SuperSuporte.command.duvidas") || players.hasPermission("SuperSuporte.*")) {
                players.sendMessage(duvida(0, p, mensagem));
                Play(players, 3);
            }
        }
            
        Date todaydate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy '|' hh:mm:ss a");
        reports.set("Player."+reporter+".duvida", mensagem);
        reports.set("Player."+reporter+".expira", ft.format(todaydate));
        p.sendMessage(duvida(1, p, mensagem));
        reports.saveConfig();
        
        return true;
    }
    
    public String duvida(int i, Player p, String msg) {
        List<String> base;
        List<String> fim = new ArrayList();
        
        if(i == 0) {
            base = messages.getConfig().getStringList("duvida_staff_message");
        } else {
            base = messages.getConfig().getStringList("duvida_player_message");
        }
        
        for(String line : base) {
            String linha = line.toLowerCase();
            if(linha.length() < 2) {
                fim.add(line);
            } else {
                line = prefix+line;   
                fim.add(line);
            }
        }
        
        String result = Separator(fim);
        result = result.replaceAll("<player>", p.getDisplayName()).replaceAll("<duvida>", msg);
        return result;
    }
}
