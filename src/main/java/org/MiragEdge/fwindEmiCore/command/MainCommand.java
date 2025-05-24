package org.MiragEdge.fwindEmiCore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.command.CommandSourceStack;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MainCommand {
    private final String mainCommandName = "fwindemicore";
    private final String alias = "fec";
    private final Permission usePermission = new Permission("fwindemicore.use");
    private final Permission infoPermission = new Permission("fwindemicore.info");
    private final Permission reloadPermission = new Permission("fwindemicore.reload");

    /**
     * 注册主命令及子命令
     * @param dispatcher 命令调度器
     */
    public void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        // 构建主命令节点
        LiteralArgumentBuilder<CommandSourceStack> mainCommand = Commands.literal(mainCommandName)
                .aliases(alias) // 别名
                .requires(source -> source.hasPermission(usePermission.getName())) // 权限校验
                .description("核心管理命令") // 命令描述
                .then(buildInfoSubcommand()) // 子命令：info
                .then(buildReloadSubcommand()); // 子命令：reload

        // 注册主命令节点
        LiteralCommandNode<CommandSourceStack> mainNode = dispatcher.register(mainCommand.build());

        // 为根命令添加Tab补全（可选，显示子命令列表）
        dispatcher.register(Commands.literal("").redirect(mainNode));
    }

    /**
     * 构建 info 子命令
     */
    private LiteralArgumentBuilder<CommandSourceStack> buildInfoSubcommand() {
        return Commands.literal("info")
                .requires(source -> source.hasPermission(infoPermission.getName()))
                .description("查看核心信息")
                .executes(context -> {
                    context.getSource().sendMessage("FwinderCore v1.0 - 核心信息：模块化命令已加载");
                    return 1;
                })
                // Tab补全：无参数时不提供建议（可选）
                .suggests((context, builder) ->
                        CompletableFuture.completedFuture(builder.build())
                );
    }

    /**
     * 构建 reload 子命令（含自定义Tab补全示例）
     */
    private LiteralArgumentBuilder<CommandSourceStack> buildReloadSubcommand() {
        return Commands.literal("reload")
                .requires(source -> source.hasPermission(reloadPermission.getName()))
                .description("重载核心配置")
                .executes(context -> {
                    context.getSource().getServer().getPluginManager().getPlugin("fwindemicore")
                            .ifPresent(plugin -> plugin.reloadConfig());
                    context.getSource().sendMessage("核心配置已重载");
                    return 1;
                })
                // Tab补全示例：假设支持"config"和"lang"参数
                .then(Commands.argument("type", Commands.string())
                        .suggests((context, builder) -> {
                            String input = builder.getRemaining();
                            SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(input, builder);
                            // 添加可选补全值
                            if (input.isEmpty() || input.startsWith("c")) {
                                suggestionsBuilder.suggest("config");
                            }
                            if (input.isEmpty() || input.startsWith("l")) {
                                suggestionsBuilder.suggest("lang");
                            }
                            return suggestionsBuilder.buildFuture();
                        })
                        .executes(context -> {
                            String type = context.getArgument("type", String.class);
                            if ("config".equals(type)) {
                                context.getSource().sendMessage("重载配置文件");
                            } else if ("lang".equals(type)) {
                                context.getSource().sendMessage("重载语言文件");
                            }
                            return 1;
                        })
                );
    }
}
