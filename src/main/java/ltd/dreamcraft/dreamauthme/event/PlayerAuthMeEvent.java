package ltd.dreamcraft.dreamauthme.event;

import fr.xephi.authme.api.v3.AuthMeApi;
import ltd.dreamcraft.dreamauthme.DreamAuthMe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class PlayerAuthMeEvent implements Listener {
    private final AuthMeApi authMeApi = AuthMeApi.getInstance();
    private Set<String> safeUsers = new HashSet<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // 获取玩家对象
        Player player = event.getPlayer();
        System.out.println("玩家加入了服务器" + player.getName());
        // 使用延时任务打开 GUI
        new BukkitRunnable() {
            @Override
            public void run() {
                if (authMeApi.isRegistered(player.getName())) {
                    openLoginGui(player);
                } else {
                    openRegGui(player);
                }
                System.out.println(player.getPlayer() + "加入了服务器并且打开了gui界面");
            }
        }.runTaskLater(DreamAuthMe.in(), 40);
    }

    private void openRegGui(Player player) {
        // 检查玩家是否在线
        if (!player.isOnline()) {
            return;
        }

        // 检查玩家是否已通过身份验证
        if (authMeApi.isAuthenticated(player)) {
            return;
        }

        // 发送打开 GUI 的数据包
        DreamAuthMe.DreamAuthMeReg.open(player);
    }

    private void openLoginGui(Player player) {
        // 检查玩家是否在线
        if (!player.isOnline()) {
            return;
        }

        // 检查玩家是否已通过身份验证
        if (authMeApi.isAuthenticated(player)) {
            return;
        }

        // 发送打开 GUI 的数据包
        DreamAuthMe.DreamAuthMeLogin.open(player);
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                if (!safeUsers.contains(player.getName())) {
//                    openGui(player);
//                }
//            }
//        }.runTaskLater(DreamAuthMe.in(), 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // 从 safeUsers 集合中移除离开的玩家
        safeUsers.remove(event.getPlayer().getName());
    }

}
