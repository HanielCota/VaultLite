# Vault (moderno)

Implementação moderna e enxuta do Vault, focada em Paper 1.20+ com LuckPerms e placeholders nativos.

## O que foi removido
- Hooks legados (PermissionsEx, bPermissions, GroupManager, Privileges, etc.)
- Chat legados (iChat, mChat, mChatSuite, etc.)
- Métricas (bStats) e update-checker
- Comandos antigos (`/vault-info`, `/vault-convert`)

## O que foi mantido
- API original do Vault (Permission, Chat, Economy) — 100% compatível
- `provides: [Vault]` no `plugin.yml`
- Integração LuckPerms para Permission e Chat
- Placeholders de economia via PlaceholderAPI:
  - `vault_balance`
  - `vault_balance_formatted`

## Requisitos
- Java 17+
- Paper 1.20+
- LuckPerms (Permission/Chat)
- Plugin de Economia compatível (EssentialsX, CMI, etc.)
- PlaceholderAPI opcional

## Uso
1. Instale LuckPerms e um plugin de Economia
2. (Opcional) Instale PlaceholderAPI
3. Coloque o JAR no servidor Paper 1.20+
4. Inicie o servidor; serviços são registrados automaticamente

## Performance
- Menos reflexão e detecção — startup mais rápido
- Sem chamadas de rede — sem update-checker
- Código enxuto e modular

