package pl.pawelusz.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SpawnLocation {
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public SpawnLocation(Location loc) {
        World w = loc.getWorld();
        this.world = (w != null ? w.getName() : "world");
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    public Location correctLocation() {
        World w = Bukkit.getWorld(world);
        if (w == null) return null;
        return new Location(w, x, y, z, yaw, pitch);
    }
}