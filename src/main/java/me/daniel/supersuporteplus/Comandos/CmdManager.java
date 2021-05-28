
package me.daniel.SuperSuportePlus.Comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.daniel.SuperSuportePlus.Comandos.BaseCmd;

public class CmdManager implements CommandExecutor {
    private List<BaseCmd> cmds = new ArrayList<BaseCmd>();
    
    public CmdManager() {
        cmds.add(new Duvida()); // Comando /duvida (player)
        cmds.add(new Duvidas()); // Comando /duvidas (staff)
        cmds.add(new SuperSuporte()); // Comando /supersuporte (Adm)
        cmds.add(new Responder()); // Comando /responder (staff)
    }
    
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        for (BaseCmd cmd : cmds) {
            if(cmd.cmdName.equalsIgnoreCase(command.getName())) {
                //try {
                    if(!(s.hasPermission(cmd.permission) || s.hasPermission("SuperSuporte.command.*") || s.hasPermission("SuperSuporte.*"))) {
                        s.sendMessage("§cVocê não tem permissão para executar este comando.");
                    } else {
                        getCommands(command.getName()).proccesCmd(s, args);
                    }
                //} catch(Exception e) {
                //    s.sendMessage("§cAlgo de errado aconteceu ao executar este comando.");
                //}
            }
        }
        
        return true;
    }
    
    public BaseCmd getCommands(String s) {
        for(BaseCmd cmd : cmds) {
            if(cmd.cmdName.equalsIgnoreCase(s)) {
                return cmd;
            }
        }
        return null;
    }
}
