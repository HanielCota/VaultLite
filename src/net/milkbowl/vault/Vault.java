package net.milkbowl.vault;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.papi.VaultExpansion;

import lombok.extern.slf4j.Slf4j;

/**
 * VaultLite - Modern and optimized version of the Vault API.
 * 
 * <p>This class represents the main plugin that provides modern integration
 * with LuckPerms for permissions and chat, plus native PlaceholderAPI support
 * for economy placeholders.</p>
 * 
 * <p>Key features:</p>
 * <ul>
 *   <li>100% compatible with the original Vault API</li>
 *   <li>Direct integration with LuckPerms (permissions and chat)</li>
 *   <li>Automatic PlaceholderAPI support</li>
 *   <li>Optimized performance and clean code</li>
 *   <li>No legacy plugin dependencies</li>
 * </ul>
 * 
 * @author VaultLite Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class Vault extends JavaPlugin {

	/**
	 * Bukkit services manager for provider registration.
	 */
	private ServicesManager servicesManager;

	/**
	 * Initializes the plugin and registers available services.
	 * 
	 * <p>This method is called when the plugin is enabled and performs:</p>
	 * <ul>
	 *   <li>Basic services initialization</li>
	 *   <li>Automatic LuckPerms registration (if available)</li>
	 *   <li>Automatic PlaceholderAPI registration (if available)</li>
	 * </ul>
	 */
    @Override
    public void onEnable() {
		if (!initializeServices()) {
			return;
		}
		
		registerLuckPermsServices();
		registerPlaceholderAPIServices();
		
		log.info("[VaultLite] ✓ Inicializado com sucesso");
		log.info("[VaultLite] → Modo: Moderno (LuckPerms)");
		log.info("[VaultLite] → API: 100% Compatível com Vault");
	}

	/**
	 * Cleans up plugin resources when disabled.
	 * 
	 * <p>Unregisters all services registered by this plugin
	 * to prevent memory leaks.</p>
	 */
	@Override
	public void onDisable() {
		if (getServer() != null && getServer().getServicesManager() != null) {
			getServer().getServicesManager().unregisterAll(this);
		}
		log.info("[VaultLite] ✗ Desabilitado");
	}

	/**
	 * Initializes basic services required for plugin operation.
	 * 
	 * @return {@code true} if initialization was successful, {@code false} otherwise
	 */
	private boolean initializeServices() {
		if (getServer() == null) {
			log.error("Servidor indisponível durante onEnable.");
			return false;
		}
		
		this.servicesManager = getServer().getServicesManager();
		
		if (this.servicesManager == null) {
			log.error("ServicesManager indisponível, abortando registro de serviços.");
			return false;
		}
		
            return true;
        }

	/**
	 * Registers LuckPerms services if the plugin is available.
	 * 
	 * <p>This method checks if LuckPerms is loaded and registers:</p>
	 * <ul>
	 *   <li>Permission Provider for permissions</li>
	 *   <li>Chat Provider for prefixes/suffixes</li>
	 * </ul>
	 */
	private void registerLuckPermsServices() {
		if (getServer().getPluginManager() == null || getServer().getPluginManager().getPlugin("LuckPerms") == null) {
			log.info("[VaultLite] → Hook: LuckPerms não encontrado");
            return;
        }

		net.luckperms.api.LuckPerms luckPermsApi = Bukkit.getServicesManager()
				.load(net.luckperms.api.LuckPerms.class);
		
		if (luckPermsApi == null) {
			log.warn("[VaultLite] ⚠ LuckPerms plugin detectado mas API não disponível");
            return;
        }

		// Registra Permission Provider
		Permission luckPermsPermissionProvider = new net.milkbowl.vault.permission.plugins.Permission_LuckPerms(luckPermsApi);
		if (servicesManager != null) {
			servicesManager.register(
				Permission.class, 
				luckPermsPermissionProvider, 
				this, 
				ServicePriority.Highest
			);
			log.info("[VaultLite] → Hook: LuckPerms Permission Provider ✓");
		}

		// Registra Chat Provider
		Chat luckPermsChatProvider = new net.milkbowl.vault.chat.plugins.Chat_LuckPerms(
			this, 
			luckPermsPermissionProvider, 
			luckPermsApi
		);

		if (servicesManager != null) {
			servicesManager.register(
				Chat.class, 
				luckPermsChatProvider, 
				this, 
				ServicePriority.Highest
			);
			log.info("[VaultLite] → Hook: LuckPerms Chat Provider ✓");
		}
	}

	/**
	 * Registers PlaceholderAPI expansion if the plugin is available.
	 * 
	 * <p>Provides economy placeholders like {@code %vault_balance%} and
	 * {@code %vault_balance_formatted%}.</p>
	 */
	private void registerPlaceholderAPIServices() {
		if (getServer().getPluginManager() == null || 
			getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
			log.info("[VaultLite] → Hook: PlaceholderAPI não encontrado");
			return;
		}
		
		new VaultExpansion().register();
		log.info("[VaultLite] → Hook: PlaceholderAPI Expansion ✓");
    }
}
