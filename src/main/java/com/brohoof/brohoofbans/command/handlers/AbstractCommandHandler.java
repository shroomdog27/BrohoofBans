package com.brohoof.brohoofbans.command.handlers;

import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import com.brohoof.brohoofbans.Ban;
import com.brohoof.brohoofbans.Data;

public abstract class AbstractCommandHandler implements CommandExecutor {

    public static UUID CONSOLE_UUID = UUID.fromString("3c879ef9-95c2-44d1-98f9-2824610477c8");
    public static String NO_PERMISSION = ChatColor.RED + "You do not have permission.";
    protected Data data;

    public AbstractCommandHandler(Data data) {
        this.data = data;
    }

    protected Optional<Ban> getBan(String uuidorName) {
        return data.getBan(getPlayer(uuidorName).getUniqueId());
    }

    protected String getIP(InetSocketAddress address) {
        return address.getAddress().getHostAddress();
    }

    @SuppressWarnings("deprecation")
    protected OfflinePlayer getPlayer(String playerORuuid) {
        try {
            UUID uuid = UUID.fromString(playerORuuid);
            Player p = Bukkit.getPlayer(uuid);
            if (p == null)
                return Bukkit.getOfflinePlayer(uuid);
            return p;
        } catch (IllegalArgumentException e) {
            // Not a UUID
            for (Player p : Bukkit.getOnlinePlayers())
                if (p.getName().equalsIgnoreCase(playerORuuid))
                    return p;
            return Bukkit.getServer().getOfflinePlayer(playerORuuid);
        }
    }

    protected String getReason(String[] args, boolean hasFlag) {
        String reason = "";
        int j = 1;
        if (hasFlag)
            j = 3;
        for (int i = j; i < args.length; i++) {
            if (i != 1)
                reason += " ";
            reason += args[i];
        }
        if (reason.startsWith(" "))
            return reason.substring(1, reason.length());
        return reason;
    }
}
