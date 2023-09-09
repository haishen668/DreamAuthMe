package ltd.dreamcraft.dreamauthme.manager;

import ltd.dreamcraft.dreamauthme.DreamAuthMe;
import ltd.dreamcraft.dreamauthme.tools.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender.hasPermission("DreamAuthMe.help")) {
            sender.sendMessage("  §f§lDreamAuthMe[Help] " + DreamAuthMe.in().getDescription().getVersion());
            sender.sendMessage("");
            sender.sendMessage("  §7[命令]: §f/da [...]");
            sender.sendMessage("§7     -§f help");
            sender.sendMessage("§7       查看指令帮助");
            sender.sendMessage("§7     -§f reload");
            sender.sendMessage("§7       重载screen");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("DreamAuthMe.reload")) {
                DreamAuthMe.DreamAuthMeReg.reload();
                DreamAuthMe.DreamAuthMeChangePsd.reload();
                DreamAuthMe.DreamAuthMeLogin.reload();
                sender.sendMessage(Lang.success("screen已经成功重新载入"));
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("  §f§lDreamAuthMe[Help] " + DreamAuthMe.in().getDescription().getVersion());
                sender.sendMessage("");
                sender.sendMessage("  §7[命令]: §f/da [...]");
                sender.sendMessage("§7     -§f help");
                sender.sendMessage("§7       查看指令帮助");
                sender.sendMessage("§7     -§f reload");
                sender.sendMessage("§7       重载screen");
            }
        } else {
            sender.sendMessage(Lang.error("&7参数错误"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> cmd = new ArrayList<>();
        //当玩家输入/da [参数1]  准备输入第一个参数时
        if (args.length == 1) {
            cmd.add("help");
            cmd.add("reload");
        }
        return cmd.size() > 0 ? cmd : null;
    }
}
