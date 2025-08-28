package pl.pawelusz.spawn;

import org.bukkit.plugin.java.JavaPlugin;
import pl.pawelusz.spawn.command.AdminSpawnCommand;
import pl.pawelusz.spawn.command.SpawnCommand;

import java.io.File;

public final class SpawnPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin succesfully enabled! " + getDescription().getVersion());
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("adminspawn").setExecutor(new AdminSpawnCommand(this));

        getDataFolder().mkdirs();
        if (!getLocationFile(this).exists()) {
            saveResource("location.json", false);
        }
    }

    public static File getLocationFile(JavaPlugin plugin) {
        return new File(plugin.getDataFolder(), "location.json");
    }
}
