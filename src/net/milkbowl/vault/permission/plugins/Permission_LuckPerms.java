package net.milkbowl.vault.permission.plugins;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.milkbowl.vault.permission.Permission;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * LuckPerms Permission Provider implementation.
 * 
 * <p>This class provides complete integration with the LuckPerms permission system
 * through the Vault API, allowing existing plugins to work without modifications.</p>
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Full support for contextual permissions</li>
 *   <li>Integration with LuckPerms groups</li>
 *   <li>Real-time permission checking</li>
 *   <li>World support</li>
 *   <li>Read-only implementation for mutations</li>
 * </ul>
 * 
 * @author VaultLite Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public final class Permission_LuckPerms extends Permission {

	/**
	 * LuckPerms API instance for permission operations.
	 */
    private final LuckPerms luckPerms;

    /**
     * Returns the permission provider name.
     * 
     * @return the provider name
     */
    @Override
    public String getName() {
        return "LuckPerms";
    }

    /**
     * Checks if the provider is enabled and working.
     * 
     * @return {@code true} if LuckPerms is available, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return this.luckPerms != null && Bukkit.getServer() != null;
    }

    /**
     * Checks if the provider supports groups.
     * 
     * @return always {@code true} since LuckPerms supports groups
     */
    @Override
    public boolean hasGroupSupport() {
        return true;
    }

    /**
     * Returns the list of available groups.
     * 
     * <p>Note: This implementation returns an empty array since group
     * retrieval should be done directly through the LuckPerms API.</p>
     * 
     * @return empty array of groups
     */
    @Override
    public String[] getGroups() {
        return new String[0];
    }

    /**
     * Checks if the provider is compatible with SuperPerms.
     * 
     * @return always {@code false} since there's no SuperPerms compatibility
     */
    @Override
    public boolean hasSuperPermsCompat() {
        return false;
    }

    /**
     * Checks if a player has a specific permission.
     * 
     * @param world the world to check the permission in (can be null)
     * @param playerName the player name
     * @param permission the permission to check
     * @return {@code true} if the player has the permission, {@code false} otherwise
     */
    @Override
    public boolean playerHas(String world, String playerName, String permission) {
        if (playerName == null || permission == null || luckPerms == null) {
            return false;
        }
        
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            return false;
        }
        
        User luckPermsUser = luckPerms.getPlayerAdapter(Player.class)
                .getUser(player);
        return luckPermsUser.getCachedData()
                .getPermissionData(QueryOptions.defaultContextualOptions())
                .checkPermission(permission)
                .asBoolean();
    }

    /**
     * Checks if a player has a specific permission in a world.
     * 
     * @param world the world to check the permission in
     * @param playerName the player name
     * @param permission the permission to check
     * @return {@code true} if the player has the permission, {@code false} otherwise
     */
    @Override
    public boolean playerHas(World world, String playerName, String permission) {
        return playerHas(world == null ? null : world.getName(), playerName, permission);
    }

    /**
     * Checks if an offline player has a specific permission.
     * 
     * @param player the offline player
     * @param permission the permission to check
     * @return {@code true} if the player has the permission, {@code false} otherwise
     */
    public boolean playerHas(OfflinePlayer player, String permission) {
        if (player == null || permission == null) {
            return false;
        }
        
        Player online = player.getPlayer();
        if (online == null) {
            return false;
        }
        
        User luckPermsUser = luckPerms.getPlayerAdapter(Player.class)
                .getUser(online);
        return luckPermsUser.getCachedData()
                .getPermissionData(QueryOptions.defaultContextualOptions())
                .checkPermission(permission)
                .asBoolean();
    }

    /**
     * Returns all groups of a player.
     * 
     * @param world the world (not used in LuckPerms)
     * @param playerName the player name
     * @return array with the player's group names
     */
    @Override
    public String[] getPlayerGroups(String world, String playerName) {
        if (playerName == null) {
            return new String[0];
        }
        
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            return new String[0];
        }
        
        User luckPermsUser = luckPerms.getPlayerAdapter(Player.class)
                .getUser(player);
        List<String> playerGroups = luckPermsUser.getInheritedGroups(QueryOptions.defaultContextualOptions())
                .stream()
                .map(group -> group.getName())
                .toList();
        return playerGroups.toArray(new String[0]);
    }

    /**
     * Returns the primary group of a player.
     * 
     * @param world the world (not used in LuckPerms)
     * @param playerName the player name
     * @return the primary group name or null if not found
     */
    @Override
    public String getPrimaryGroup(String world, String playerName) {
        if (playerName == null) {
            return null;
        }
        
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            return null;
        }
        
        return luckPerms.getPlayerAdapter(Player.class)
                .getUser(player)
                .getPrimaryGroup();
    }

    /**
     * Returns the primary group of an offline player.
     * 
     * @param player the offline player
     * @return the primary group name or null if not found
     */
    public String getPrimaryGroup(OfflinePlayer player) {
        if (player == null) {
            return null;
        }
        
        Player online = player.getPlayer();
        if (online == null) {
            return null;
        }
        
        return luckPerms.getPlayerAdapter(Player.class)
                .getUser(online)
                .getPrimaryGroup();
    }

    /**
     * Checks if a player is in a specific group.
     * 
     * @param world the world (not used in LuckPerms)
     * @param playerName the player name
     * @param group the group name
     * @return {@code true} if the player is in the group, {@code false} otherwise
     */
    @Override
    public boolean playerInGroup(String world, String playerName, String group) {
        if (playerName == null || group == null) {
            return false;
        }
        
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            return false;
        }
        
        User luckPermsUser = luckPerms.getPlayerAdapter(Player.class)
                .getUser(player);
                
        return luckPermsUser.getInheritedGroups(QueryOptions.defaultContextualOptions())
                .stream()
                .anyMatch(playerGroup -> playerGroup.getName().equalsIgnoreCase(group));
    }

    /**
     * Adds a permission to a player (not implemented - read-only).
     * 
     * @param world the world
     * @param player the player name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean playerAdd(String world, String player, String permission) {
        return false;
    }

    /**
     * Adds a transient permission to a player (not implemented - read-only).
     * 
     * @param world the world
     * @param player the player name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean playerAddTransient(String world, String player, String permission) {
        return false;
    }

    /**
     * Removes a permission from a player (not implemented - read-only).
     * 
     * @param world the world
     * @param player the player name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean playerRemove(String world, String player, String permission) {
        return false;
    }

    /**
     * Removes a transient permission from a player (not implemented - read-only).
     * 
     * @param world the world
     * @param player the player name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean playerRemoveTransient(String world, String player, String permission) {
        return false;
    }

    /**
     * Checks if a group has a permission (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean groupHas(String world, String group, String permission) {
        return false;
    }

    /**
     * Adds a permission to a group (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean groupAdd(String world, String group, String permission) {
        return false;
    }

    /**
     * Removes a permission from a group (not implemented - read-only).
     * 
     * @param world the world
     * @param group the group name
     * @param permission the permission
     * @return always {@code false} since it's read-only
     */
    public boolean groupRemove(String world, String group, String permission) {
        return false;
    }

    /**
     * Adds a player to a group (not implemented - read-only).
     * 
     * @param world the world
     * @param playerName the player name
     * @param group the group name
     * @return always {@code false} since it's read-only
     */
    public boolean playerAddGroup(String world, String playerName, String group) {
        return false;
    }

    /**
     * Removes a player from a group (not implemented - read-only).
     * 
     * @param world the world
     * @param playerName the player name
     * @param group the group name
     * @return always {@code false} since it's read-only
     */
    public boolean playerRemoveGroup(String world, String playerName, String group) {
        return false;
    }

    /**
     * Returns the UUID of a player (not implemented).
     * 
     * @param playerName the player name
     * @return always {@code null} since not implemented
     */
    public UUID getUUID(String playerName) {
        return null;
    }
}
