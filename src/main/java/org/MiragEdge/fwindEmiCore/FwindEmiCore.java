package org.MiragEdge.fwindEmiCore;

import org.MiragEdge.fwindEmiCore.items.CarrotPickAxe;
import org.bukkit.plugin.java.JavaPlugin;

public class FwindEmiCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // 注册模块
        new CarrotPickAxe(this).register();
        getLogger().info("插件已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已关闭！");
    }
}