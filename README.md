# VaultLite

> A modern, lightweight fork of the classic Vault plugin - optimized for today's Minecraft servers.

## 🎯 What is VaultLite?

VaultLite is a streamlined version of the original Vault plugin that maintains 100% API compatibility while removing decades of technical debt. It's designed for modern Minecraft servers that use LuckPerms and want maximum performance.

## ⚡ Why VaultLite?

The original Vault plugin was created in 2011 and still carries code for 35+ dead plugins like PermissionsEx, bPermissions, GroupManager, mChat, iChat, and countless others that haven't been updated since 2015.

**VaultLite removes the bloat and keeps what matters.**

## ✨ What's Different?

### ❌ Removed (The Bloat)
- **35+ legacy plugin hooks** - No more wasted startup time detecting dead plugins
- **Update checker** - No unnecessary network calls to abandoned APIs
- **Outdated metrics** - No tracking of unused implementations
- **Dead economy plugins** - iConomy, BOSEconomy, CurrencyCore, etc.
- **Ancient permission systems** - PEX, bPermissions, GroupManager, etc.
- **Legacy chat plugins** - mChat, iChat, bPermissions chat, etc.

### ✅ Kept (The Essentials)
- **Full Vault API** - 100% compatible with all plugins that use Vault
- **LuckPerms integration** - The modern standard for permissions & chat
- **Modern economy plugins** - EssentialsX, CMI, PlayerPoints
- **Null safety** - Proper error handling throughout
- **Clean logging** - Clear, informative messages

## 🚀 Performance Benefits

- **Faster startup** - No scanning for 35+ dead plugins
- **Less memory** - Smaller JAR, less loaded classes
- **No network overhead** - No update checks or metrics
- **Cleaner logs** - Only relevant information

## 📋 Requirements

- **Minecraft:** 1.16.5+
- **Java:** 17+
- **Permissions:** LuckPerms (recommended)
- **Economy:** EssentialsX, CMI, or PlayerPoints (optional)

## 📦 Installation

1. Remove original Vault from your `plugins/` folder
2. Download VaultLite
3. Place in `plugins/` folder
4. Install LuckPerms if you haven't already
5. Restart server

**That's it!** All plugins that used Vault will continue working.

## 🔌 Supported Plugins

### Permissions & Chat
- ✅ LuckPerms (recommended)

### Economy
- ✅ EssentialsX
- ✅ CMI
- ✅ PlayerPoints

## ⚠️ Migration from Original Vault

VaultLite is a **drop-in replacement**. Simply:
1. Stop your server
2. Replace `Vault.jar` with `VaultLite.jar`
3. Start your server

**No configuration changes needed!**

## 🛠️ For Developers

VaultLite maintains 100% API compatibility with original Vault.
```java
// Works exactly the same
Economy economy = getServer().getServicesManager()
    .getRegistration(Economy.class).getProvider();
    
Permission permission = getServer().getServicesManager()
    .getRegistration(Permission.class).getProvider();
    
Chat chat = getServer().getServicesManager()
    .getRegistration(Chat.class).getProvider();
```

## 📊 Comparison

| Feature | Original Vault | VaultLite |
|---------|---------------|-----------|
| Permission Plugins | 15+ | 1 (LuckPerms) |
| Chat Plugins | 12+ | 1 (LuckPerms) |
| Economy Plugins | 20+ | 3 (Modern) |
| Update Checker | ✅ | ❌ |
| Metrics | ✅ | ❌ |
| JAR Size | ~400KB | ~150KB |
| Startup Time | Slower | Faster |
| API Compatible | ✅ | ✅ |

## 🤝 Contributing

Contributions are welcome! Please:
- Keep the codebase clean and minimal
- Maintain API compatibility
- Add null checks for safety
- Update tests

## 📜 License

VaultLite is licensed under the [LGPL v3](LICENSE) - same as original Vault.

## 🙏 Credits

- Original Vault by cereal, Sleaker, and contributors
- LuckPerms by Luck
- All contributors to this fork

## 💬 Support

- 🐛 [Report Issues](https://github.com/[seu-usuario]/VaultLite/issues)
- 💡 [Feature Requests](https://github.com/[seu-usuario]/VaultLite/discussions)
- 📖 [Wiki](https://github.com/[seu-usuario]/VaultLite/wiki)

---

**VaultLite** - Modern. Lightweight. Compatible.
