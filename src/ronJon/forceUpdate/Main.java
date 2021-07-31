package ronJon.forceUpdate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin implements CommandExecutor {
    public void onEnable() {
        getLogger().info("Plugin enabled");
        getCommand("updateJar").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return false;
        }

        if(cmd.getName().equalsIgnoreCase("updateJar")) {
            Player p = (Player) sender;
            if (p.isOp() || (sender instanceof ConsoleCommandSender) || (sender instanceof RemoteConsoleCommandSender)) {
                boolean delete = ronJon.forceUpdate.Main.removeFile("version");
                ronJon.forceUpdate.Main.msgAdmins(ChatColor.GREEN + "Forcing update on next restart!");
                ronJon.forceUpdate.Main.msgAdmins(ChatColor.GREEN + "Version file delete result was: "+ delete);
                getLogger().info("Player issued force update command, result was: "+ delete);
            }
            else {
                p.sendMessage(ChatColor.RED + "You are not able to preform this command!");
                getLogger().warning("Player is not op!");
            }
        }
        return false;
    }

    public static boolean removeFile(final String fileName) {
        File file = new File(fileName);
        String path = file.getAbsolutePath();
        return file.delete();
    }

    public static void msgAdmins(final String msg) {
        for (final World w : Bukkit.getWorlds()) {
            for (final Player p : w.getPlayers()) {
                if (p.isOp()) {
                    p.sendMessage(msg);
                }
            }
        }
    }
}