package ltd.dreamcraft.dreamauthme;

import fr.xephi.authme.command.PlayerCommand;
import ltd.dreamcraft.dreamauthme.event.PlayerAuthMeEvent;
import ltd.dreamcraft.dreamauthme.event.handleCustomPacketEvent;
import ltd.dreamcraft.dreamauthme.manager.DataManager;
import ltd.dreamcraft.dreamauthme.manager.command;
import ltd.dreamcraft.dreamauthme.tools.CheckPluginUpdate;
import ltd.dreamcraft.dreamauthme.tools.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import priv.seventeen.artist.dreampainter.api.DreamPainterScreenAPI;
import priv.seventeen.artist.dreampainter.api.screen.DreamPainterScreen;

import static org.bukkit.Bukkit.getConsoleSender;

public final class DreamAuthMe extends JavaPlugin {
    public static DreamAuthMe in() {
        return getPlugin(DreamAuthMe.class);
    }

    public static DreamPainterScreen DreamAuthMeLogin;
    public static DreamPainterScreen DreamAuthMeReg;
    public static DreamPainterScreen DreamAuthMeChangePsd;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new handleCustomPacketEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerAuthMeEvent(), this);
        getConsoleSender().sendMessage("§3 ____                              _         _   _     __  __    ");
        getConsoleSender().sendMessage("§3|  _ \\ _ __ ___  __ _ _ __ ___    / \\  _   _| |_| |__ |  \\/  | ___ ");
        getConsoleSender().sendMessage("§3| | | | '__/ _ \\/ _` | '_ ` _ \\  / _ \\| | | | __| '_ \\| |\\/| |/ _ \\");
        getConsoleSender().sendMessage("§3| |_| | | |  __/ (_| | | | | | |/ ___ \\ |_| | |_| | | | |  | |  __/");
        getConsoleSender().sendMessage("§3|____/|_|  \\___|\\__,_|_| |_| |_/_/   \\_\\__,_|\\__|_| |_|_|  |_|\\___|");
        getConsoleSender().sendMessage("§b|> §e欢迎使用DreamAuthMe绘梦师附属登录插件!");
        getConsoleSender().sendMessage("§b|> §e当前版本为：" + getDescription().getVersion());
        //检查更新 如果配置文件设置了true则 检查插件是否需要更新 且 注册监听玩家登录事件，如果是op就发送更新提示
        CheckPluginUpdate.checkPluginUpdate("https://pluginapi.dreamcraft.ltd/api/plugins/");
        Plugin authMePlugin = getServer().getPluginManager().getPlugin("AuthMe");
        if (authMePlugin != null && authMePlugin.isEnabled()) {
            // AuthMe前置插件都存在并启用，初始化 AuthMe API
//            authMeApi = AuthMeApi.getInstance();
        } else {
            getLogger().warning("AuthMe 插件未找到或未启用。DragonAuthMe 将不会启用。");
            getServer().getPluginManager().disablePlugin(this);
        }
        this.getCommand("DreamAuthMe").setExecutor(new command());
        //初始化配置文件
        DataManager d = new DataManager();
        d.init();
        //在插件中注册screen
        DreamAuthMeLogin = new DreamPainterScreen(this, "screen/DreamAuthMeLogin");
        DreamPainterScreenAPI.registerScreen(DreamAuthMeLogin);
        DreamAuthMeReg = new DreamPainterScreen(this, "screen/DreamAuthMeReg");
        DreamPainterScreenAPI.registerScreen(DreamAuthMeReg);
        DreamAuthMeChangePsd = new DreamPainterScreen(this, "screen/DreamAuthMeChangePsd");
        DreamPainterScreenAPI.registerScreen(DreamAuthMeChangePsd);
        DreamAuthMeReg.reload();
        DreamAuthMeChangePsd.reload();
        DreamAuthMeLogin.reload();
        getConsoleSender().sendMessage("§b|> §e成功载入3个screen");
        //统计
        Metrics metrics = new Metrics(this, 19743);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DreamPainterScreenAPI.unRegisterScreen(DreamAuthMeLogin);
        DreamPainterScreenAPI.unRegisterScreen(DreamAuthMeReg);
        DreamPainterScreenAPI.unRegisterScreen(DreamAuthMeChangePsd);
        getLogger().info("插件被卸载,screen页面已被删除");
    }
}
