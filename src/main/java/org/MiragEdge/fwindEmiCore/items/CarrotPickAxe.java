package org.MiragEdge.fwindEmiCore.items;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class CarrotPickAxe implements Listener {

    private static final String CUSTOM_ITEM_ID = "items_skin:carrot_pickaxe";
    private final JavaPlugin plugin;
    private boolean isItemsAdderLoaded = false;

    public CarrotPickAxe(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // 定义 register() 方法
    public void register() {
        // 注册本类的所有事件监听器
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("胡萝卜镐 模块 已初始化");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled() || !isItemsAdderLoaded) return;


        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

        // 检测自定义物品
        CustomStack customStack = CustomStack.byItemStack(tool);
        if (customStack == null || !customStack.getId().equals(CUSTOM_ITEM_ID)) return;
        // 概率计算（25%）
        if (new Random().nextInt(100) >= 25) return;
        // 掉落物生成
        event.setDropItems(false);
        event.getBlock().getWorld().dropItemNaturally(
                event.getBlock().getLocation().add(0.5, 0.5, 0.5),
                new ItemStack(Material.CARROT, new Random().nextInt(3) + 1)
        );
    }
    // ItemsAdder 加载事件
    @EventHandler
    public void onItemsAdderLoad(ItemsAdderLoadDataEvent event) {
        isItemsAdderLoaded = true;
        plugin.getLogger().info("ItemsAdder数据加载完成");
    }
}