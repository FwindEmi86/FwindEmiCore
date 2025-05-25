package org.MiragEdge.fwindEmiCore;

import org.MiragEdge.fwindEmiCore.command.MainCommand;
import org.MiragEdge.fwindEmiCore.items.CarrotPickAxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class FwindEmiCore extends JavaPlugin {

    private CarrotPickAxe carrotPickAxeModule;

    @Override
    public void onEnable() {
        // 注册命令（关键修改点）
        MainCommand mainCommand = new MainCommand(this);
        getCommand("fwindemicore").setExecutor(mainCommand);
        getCommand("fwindemicore").setTabCompleter(mainCommand);
        // 首次初始化
        initializePlugin();
        getLogger().info("[核心] 插件已启用！");
    }

    @Override
    public void onDisable() {
        // 关闭插件
        shutdownPlugin();
        getLogger().info("[核心] 插件已关闭！");
    }

    // 首次初始化
    private void initializePlugin() {
        saveDefaultConfig();
        reloadConfig();
        carrotPickAxeModule = new CarrotPickAxe(this);
        carrotPickAxeModule.loadConfig();
        carrotPickAxeModule.register();
    }

    // 关闭插件
    private void shutdownPlugin() {
        if (carrotPickAxeModule != null) {
            carrotPickAxeModule.unregister();
            carrotPickAxeModule = null;
        }
    }

    // 重载插件
    public void reloadPlugin() {
        shutdownPlugin(); // 清理旧模块
        initializePlugin(); // 重新初始化
        getLogger().info("[核心] 配置重载完成！");
    }
}