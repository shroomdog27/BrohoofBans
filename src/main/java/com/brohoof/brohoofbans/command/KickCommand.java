package com.brohoof.brohoofbans.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.sweetiebelle.lib.SweetieLib;

import com.brohoof.brohoofbans.API;
import com.brohoof.brohoofbans.BrohoofBansPlugin;
import com.brohoof.brohoofbans.Settings;

public class KickCommand extends AbstractCommand {

    public KickCommand(BrohoofBansPlugin plugin, API api, Settings settings) {
        super(plugin, api, settings);
    }

    public boolean execute(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "That player is not online.");
        return true;
    }

    public boolean execute(CommandSender sender, Player target, String reason) {
        if (!sender.hasPermission("brohoofbans.kick")) {
            sender.sendMessage(SweetieLib.NO_PERMISSION);
            return true;
        }
        target.kickPlayer(reason);
        broadcastKickMessage(target, reason);
        if (sender instanceof Player)
            plugin.getLogger().info(((Player) sender).getName() + " kicked " + target.getName() + " for " + reason + ".");
        else
            plugin.getLogger().info("CONSOLE kicked " + target.getName() + " for " + reason + ".");
        return true;
    }
}