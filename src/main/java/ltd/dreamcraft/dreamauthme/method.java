package ltd.dreamcraft.dreamauthme;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class method {
    public static void forcePlayerToRegister(Player player, String password) {
        String command = "register " + password + " " + password;
        Bukkit.dispatchCommand(player, command);
    }
}
