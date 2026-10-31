# VileKingdoms

Version 1.0.6

VileKingdoms turns the world of the server into a land of kingdoms fighting each other. The players found kingdoms
and towns, claim the world land by land, build the structures that produce and keep the emeralds, grow their towns
through the ranks, make alliances and enmities, and conquer or destroy the towns of their enemies in timed wars.

This guide explains how the plugin works, how to install and configure it, and every command, permission and
placeholder. The API for the addons is described in [API.md](API.md).

## Contents

- [1. Installation](#1-installation)
- [2. How the plugin works](#2-how-the-plugin-works)
- [3. Quick start](#3-quick-start)
- [4. Building the structures](#4-building-the-structures)
- [5. Commands](#5-commands)
- [6. Permissions](#6-permissions)
- [7. Role permissions](#7-role-permissions)
- [8. Placeholders](#8-placeholders)
- [9. WorldGuard flags](#9-worldguard-flags)
- [10. Configuration](#10-configuration)
- [11. Addons](#11-addons)

## 1. Installation

### Requirements

A Paper server for Minecraft 26.3 (or a fork of Paper), running on Java 25. Put `VileKingdoms.jar` in the `plugins`
folder and start the server.

### Required plugins

Without them VileKingdoms does not start: it writes the error in the console and disables itself.

| Plugin | Used for |
|---|---|
| Vault | the link with the economy of the server |
| An economy plugin hooked into Vault (e.g. EssentialsX, CMI) | the banks of the towns, the taxes and the stakes of the wars |

### Optional plugins

VileKingdoms finds the installed ones by itself, with nothing to configure; without them the features below are
missing or done by the plugin itself.

| Plugin | Used for |
|---|---|
| FastAsyncWorldEdit, WorldEdit | pasting the structures; without them the plugin edits the world by itself. The first one installed is used, in this order |
| WorldGuard | the flags of the regions, see [WorldGuard flags](#9-worldguard-flags) |
| Multiverse-Core | creating the world where the staff builds the structures; without it the world is created by the server |
| PlaceholderAPI | the placeholders of the plugin, and the placeholders of the other plugins in the chat format |
| ShopGUIPlus, EconomyShopGUI, zShop, CMI, Essentials | the prices of the items, used for the worth of the towns |
| ItemsAdder, Oraxen, Nexo | custom items in the GUIs and in the items of the structures (`custom-id`) |
| HeadDatabase | heads in the GUIs (`head-id`) |
| MythicMobs | MythicMobs mobs as defenders of the structures |
| Parties | the party access mode of the towns |
| Geyser with Floodgate | Bedrock forms instead of the GUIs and the dialogs for the Bedrock players |

### First start

At the first start the plugin creates its folder, `plugins/VileKingdoms`:

| File | Content |
|---|---|
| `settings.yml` | the main settings: storage, towns, claims, protection, worth, taxes, extractors, wars, defenses, chat |
| `ranks.yml` | the ranks of the towns and what each one allows |
| `structures.yml` | the default properties of each level of the structures, and their items |
| `default-roles.yml` | the roles given to every new kingdom and town, and the permissions of the outsiders |
| `guis.yml` | the layout and the items of every GUI |
| `locales/` | the messages, one file for each language (`lang_en_us.yml`, `lang_it_it.yml`) |
| `structures/` | the schematics of the structures, by style, type and level |

The data is saved in a SQLite database in the folder of the plugin, or in a MySQL database (`storage` in
`settings.yml`). When the plugin is updated, the new keys are added to the configuration files and the keys not used
anymore are removed, keeping your values.

Before founding the first town choose the size of the **lands** (see [Lands](#lands)): `lands.size` in
`settings.yml`, or `/kingdom admin land-size <size>`, then restart the server. Once the first town is founded the
size never changes.

The towns work only when the default style (`structures.default-style`) has every structure type, at least at level 1:
the town hall, the extractor, the collector, the residence and the depot. The structures included in the jar are
extracted into the `structures` folder at the first start; the missing ones are built by the staff (see
[Building the structures](#4-building-the-structures)). `/structure list` shows the structures of a style and the
missing ones.

## 2. How the plugin works

### Kingdoms and towns

A **kingdom** is created with `/kingdom create`: its creator becomes its king. A kingdom has no land by itself: it is
made of **towns**, founded by the king (or by the members whose role has the `found_towns` permission) with
`/town found` in the land where they stand. The founder chooses the name and the style of the town: the town claims
that land, its town hall is built there, and the founder becomes its mayor.

The players join a kingdom by joining one of its towns. A player can be a member of several towns, also of different
kingdoms, as long as those kingdoms are not enemies: when two of their kingdoms become enemies, the player has
`towns.enemy-conflict-hours` to leave the towns of one of them, otherwise they leave the towns joined last.

- The kingdom commands act on the **primary kingdom** of the player: the kingdom of the town they manage, otherwise
  the kingdom they rule, otherwise the first one of theirs by name.
- The town commands act on the town chosen with `/town manage`, or on the town written as their last argument.
- The king chooses the **capital** of the kingdom with `/town capital set`, at most once every
  `towns.capital-cooldown-seconds`.
- The values of a kingdom (emeralds, worth, bank, claims) are the sums of the ones of its towns.
- `/town reach` teleports to the spawn of a town hall, `/town capital` to the capital, for the members with the
  `teleport` permission: they wait `teleports.warmup-seconds` without moving, then `teleports.cooldown-seconds` before
  the next teleport. With `teleports.during-wars: false` they do not work while the player or the town is in a war.
- `/kingdom who` shows the kingdom, the towns, the first join and the playtime of a player.

A player joins a town with `/town join`, by its **access mode** (`/town privacy`): **public** (anyone can join), **private**
(only the invited players) or **party** (the invited players, with their party of the Parties plugin). Who joins
needs a free seat in a residence of the town: without free seats nobody can join. The invites expire after
`towns.invite-expiration-seconds`.

`/town release` releases a town: its members leave it and its structures are destroyed. `/kingdom disband` disbands
the kingdom, releasing all its towns.

### Roles

Each kingdom and each town has its roles, listed from the lowest to the highest. Every new kingdom and town gets a copy
of the roles of `default-roles.yml`, then its leaders change them from the kingdom panel and from the town hall.

- The **global roles** belong to the kingdom: a member has one in the kingdom, and its permissions act in every town of
  the kingdom. The highest one (the king) has every permission.
- The **town roles** belong to a town: a member has one in each town, and its permissions act in that town. The
  highest one (the mayor) has every permission.
- The lowest role of each list is given to the new members.

A member can promote, demote, kick or ban only the members below them. The permissions of the roles are listed in
[Role permissions](#7-role-permissions).

### Lands

The world is divided into **lands**: squares of `lands.size` x `lands.size` blocks (24 by default, a multiple of 8 from 16 to 64),
aligned to the origin of the world, the same for every world. A land is the unit of the territory: each claim is a
land, and each structure fills a land, so the size of the lands is also the largest size of a structure.

The size is chosen once, before the towns exist: in `settings.yml` (`lands.size`) or with
`/kingdom admin land-size <size>`, then the server must be restarted. When the first town is founded the size becomes
definitive and is saved in `data.yml`: from then on `lands.size` is ignored, because the claims and the structures
depend on it. `/kingdom admin land-size` shows the current size. A server with towns founded before VileKingdoms 1.0.4
keeps lands of 16 blocks, the chunks of Minecraft, so its claims stay valid.

### Claims and the territory

The territory of a town is made of **claims**, one land each. A town claims only the free lands touching its claims,
at least `towns.claim-distance` lands away from the towns of the other kingdoms, and up to the `max-claims` of its
rank. A claim cannot be released when it holds a structure, or when it is the only link between other claims and the
town hall.

- `/town claim` claims the land where the player stands, `/town claim auto` claims every free land they walk into.
- `/town unclaim` releases the land where the player stands, `/town unclaim auto` every land of their town they walk
  into.
- The automatic claim and release stop when the command is run again, after `claims.auto.duration-minutes`, when the
  player quits or loses the `manage_lands` permission, or when the other one starts. Their progress is shown in the
  action bar.
- `/town map` shows the claims around the player, colored by their relation with the kingdom of the player.

In the claims of a town, who is not a member cannot break or place blocks, open the containers, use doors, trapdoors
and redstone components, pick up items or kill the animals and the neutral mobs (`protection.neutral-mobs`), unless the
town gives the permission to the allies, the neutrals or the enemies (the outsiders button of the town hall). The
members can do it only when their role has the permission. In the lands with a structure nobody can break or place
blocks, in peace and in war. The territory is invulnerable to the explosions (TNT, mobs, beds, end crystals) and to the attacks of the mobs, such as
the fireballs of the ghasts and the withers, also during a war: no block is broken or set on fire, and the decorations,
the animals and the items are not hurt. The players, the hostile mobs and the defenders still take the damage.

The PvP in the territory of the towns is denied between players of different kingdoms; the members of the same kingdom
can hit each other only when the friendly fire of the kingdom is on (`/kingdom settings`). Outside the territory every
PvP is allowed. The members of a town can always hit the players banned from it.

The staff with `vilekingdoms.admin.bypass` ignores the protection. The admin claims (`/town admin claim-for`,
`/town admin release`) ignore the rules of the members.

### Structures

The structures are the buildings of the towns. Each one fills a claimed land and has a level; its look comes from the
**style** of the town, chosen at its foundation.

| Structure | What it does |
|---|---|
| Town hall | the heart of the town, built at its foundation: right clicking it opens the town hall GUI, it holds the spawn of the town and keeps its emeralds. Its level is the rank of the town |
| Extractor | produces emeralds for the town hall |
| Collector | raises the limit of the emeralds of the town |
| Residence | gives seats to the members: each member gets a seat in a residence |
| Depot | many containers, whose content counts in the worth of the town |

The structures are bought from the shop of the town (`/town shop`, or the town hall), paid with the emeralds of the
town, and given as an item: using the item on a block of a claim of the town places the structure there, its floor
in place of the clicked block, when no block of the world is in the way above the floor. Right clicking a placed
structure opens its GUI, to upgrade it to the next level, to pick it up again as an item, or to buy its defenses. The
structure items never drop on death, cannot be thrown and do not burn.

Before a structure is placed, the plugin saves the terrain it takes (`terrain/` in the plugin folder): when the
structure is removed (picked up, upgraded, or its town released or destroyed) the terrain is put back as it was. The
structures placed before VileKingdoms 1.0.4 have no saved terrain: theirs is generated again by the generator of the
world through FastAsyncWorldEdit or WorldEdit, or replaced by air without them.

The rank of the town decides how many structures of each type work and up to which level. The structures placed over
the limit are **in excess**: their extractors stop, their collectors do not raise the limit, their residences are not given to
the new members and their depots cannot be used. A structure of a level higher than the rank allows works as the
highest level allowed.

The properties of each level (cost, extractor boost, capacity of the town hall and of the collector, residence seats, defender NPCs) are written in
`structures.yml`, and can be changed for a single schematic with `/structure set`.

### Emeralds, worth and bank

A town has three values:

- **Emeralds**: the treasury of the town, kept by its town hall. A new town starts with `towns.starting-emeralds`, to
  buy its first structures. The emeralds pay the structures, their upgrades, the ranks and the defenses. Every
  `extractors.interval-minutes` each extractor produces `extractors.base-amount` emeralds, multiplied by the boost of
  its level and by the modifier of its biome (`extractors.biomes`), sent to the town hall. An extractor stays at least
  `extractors.min-distance-lands` lands from the other extractors.

  The town hall has a limit for each level (`capacity` in `structures.yml`), and each working collector raises the
  limit of the town by its own `capacity`. An extractor without a collector stops at the limit of the town hall alone;
  an extractor linked to a collector goes on up to the limit of the town. To link an extractor press **Link to a
  collector** in its GUI, then within `extractors.link-seconds` shift + right click the collector: of the same town, at
  most `extractors.max-collector-distance-lands` lands away, with less than two extractors already linked. Picking up
  the collector removes its links. The GUI of an extractor shows whether it is linked, the one of a collector whether
  it is operational.

  Real emeralds become emeralds of the town with `/town converter`, on the town the commands act on, for who has the
  `deposit_emeralds` permission. In its GUI place emeralds and emerald blocks, then press **Convert**: each emerald
  gives `converter.rate` emeralds of the town, each block nine times as many. Only what fits within the limit of the
  town is converted, and whatever is left goes back to the player when the GUI closes. Renamed emeralds and custom items
  are not accepted, and the converter does not work during a war of the town. `converter.rate: 0` disables it.
- **Worth**: the value of the items in the containers of its claims (chests, barrels, hoppers, furnaces, shulker boxes
  and the others), also inside the structures. The emeralds are not counted. The prices are read from the first shop
  plugin installed, otherwise from `worth.prices`; the worth is updated every `worth.update-minutes`.
- **Bank**: money of the Vault economy. The members with the `manage_economy` permission move money between their
  account and the bank with `/town balance deposit|withdraw`. During a war the banks of both towns are locked.

`/kingdom top` ranks the kingdoms by their emeralds. `/kingdom stats` and `/town stats` show the values of a kingdom and
of a town to everyone. The emeralds and the worth of a town are shown only to its members, in `/town stats` and in the
town hall GUI.

### Ranks

The rank of a town is the level of its town hall. A town reaches the next rank with `/town rankup` (or from the town
hall) when its emeralds and its worth reach the `required-emeralds` and the `required-worth` of the rank, paying its
`cost` in emeralds. Each rank of `ranks.yml` sets the claims of the town and how many structures of each type it can
have, and up to which level.

A town must keep the requirements of its rank: when its emeralds or its worth go below them (for example after a
theft), it receives a warning every `rank-loss.warning-hours`, for `rank-loss.warnings` times. Still below after the last
one, it goes back a rank: its most recent claims over the new limit are released.

### Taxes

The members with the `manage_taxes` permission set the tax of the town with `/town tax set <amount> <days>`: every
that many days each member pays it into the bank of the town with `/town tax pay`, at most one period in advance. The
members with `manage_taxes` do not pay it, and the players joining the server are reminded of their debts. Who does not pay by the due date is in debt, and is kicked from the town after `taxes.grace-days`.
`taxes.enabled: false` turns the taxes off for everyone.

### Relations and chat

Two kingdoms are allies, neutral or enemies, always the same for both:

- an alliance is proposed with `/kingdom relation ally`, and holds only when the other kingdom accepts it;
- an enmity is declared immediately with `/kingdom relation enemy`, without any answer;
- allies become neutral immediately, while enemies become neutral only when the other kingdom accepts the peace.

The requests can be cancelled by their sender, and expire after `relations.request-expiration-hours`. Only enemies can
fight each other in the wars.

The plugin formats the chat (`chat` in `settings.yml`): every player sees the kingdom of the sender with the color of
the relation between their kingdoms (`relations.colors`). `/town chat` turns on the chat of a town, read only by its
members; the admins read every town chat with `/town admin chat-spy`.

### Wars

The wars are fought between the towns of enemy kingdoms, only during the **periods of war**: the periods of peace
(`wars.peace-days`) and of war (`wars.war-days`) alternate, and the admins control the cycle with `/wartime`.

1. **Declaration.** A member with the `manage_wars` permission declares the war from the town they manage, with
   `/town war declare <town> <stake>`, choosing the attack squad among the online members of their town. The chosen
   members join the squad with `/town war join` within `wars.squad-join-seconds`.
2. **The war.** For `wars.duration-minutes` the banks of both towns are locked and the extractors of the defender
   stop. When half of the squad stays in a land of the defender for `wars.capture-seconds`, the land becomes
   **contested**. In the contested lands the attackers open the containers and take the items of the depots; when the
   land of the town hall is contested they steal the emeralds of the town by right clicking it (`wars.steal`). The
   stolen emeralds are kept until the attacker deposits them by right clicking a town hall of their kingdom or of an
   allied one: an attacker dying loses them.
3. **The end.** The attacker wins by contesting every land of the defender, town hall included, before the end of the
   war; otherwise the defender wins. `/town war abandon` gives up the war, losing it.

During a war the squad can break and place blocks and use doors in the lands of the defender without structures, and
pick up the items in its whole territory. The squad and the members of the defender can hit each other; the members
of the attacker left out of the squad can neither hit nor be hit there.

**The stake** is never paid by the server: when the attacker wins, the bank of the defender pays the stake to the bank
of the attacker; otherwise the bank of the attacker pays the stake times the attackers to the bank of the defender, up
to the money it has. The stake must stay within `wars.stake.min` and `wars.stake.max`.

**When the attacker wins**, the defender is treated by `wars.conquest`:

| Mode | What happens to the defender |
|---|---|
| `CONQUEST` | the town joins the kingdom of the attacker; its members are kicked |
| `TIMED_DESTRUCTION` | the town cannot be used until `wars.destruction-hours` pass |
| `PERMANENT_DESTRUCTION` | the town is deleted; its lands become free when `wars.griefable-ruins` is true, otherwise ruins nobody can edit |

**Who can be attacked.** A town cannot be attacked while it is in another war, during its immunity, when it is
destroyed, without online members (unless its rank is at least `wars.offline-defenders-min-rank`; 0: never), or when its
worth does not compare with the one of the attacker as `wars.worth-comparison` requires. The attacker only learns that
the town is immune to its attacks, never why. The attacker must also wait `wars.declare-cooldown-hours` between two
wars.

**Immunity.** After a war the defender is immune for `wars.immunity-hours`; a new town is immune for
`wars.founding-immunity-hours`. With `wars.lose-immunity-on-declare` a town declaring a war gives up its immunity when
the war starts: the plugin warns the player before declaring.

A war interrupted by a restart of the server ends in a draw: nobody pays the stake. The admins list, inspect and end
the wars with `/town admin war`.

### Defenses

A structure can have **defenders**: mobs that appear when an enemy (an attacker of a war against the town, or a player
banned from it) walks into its land, and attack them. They disappear without drops when the enemy leaves.

- The staff sets the spawn points of the defenders while building the schematic (`/structure defense add`).
- The members with the `manage_structures` permission buy and upgrade the defenses with a single button, in the GUI
  of the structure and in the town hall GUI, up to the highest level: the level N costs N times `defenses.cost` emeralds.
  The button tells when the structure has no spawn points.
- The defense level N spawns the mob at the position N of the ranking built by the staff with
  `/structure defense rank` (vanilla or MythicMobs mobs; zombies without a ranking). The `npcs` property of the level
  sets how many defenders appear, 2 when not set.
- A peaceful vanilla mob (a cow, a sheep, a villager...) keeps its look and its stats, but its AI is replaced by a melee
  attack against the enemy, with `defenses.peaceful-damage` damage when it has no attack of its own. Like every defender
  it can be killed, whatever the permissions of the town. The bats cannot chase a target, so they are refused. The water
  mobs fight only in the water, so they stay still on land.
- The highest level a structure can buy is the `defense-cap` of its schematic, lowered to the cap of a higher level of
  the same structure when that one is lower. Upgrading the structure to a level with a lower cap lowers its defenses,
  without refunds.

### Interfaces

The plugin uses GUIs for the panels (kingdom panel, town hall, shop, structures, roles, members, relations, map) and
dialogs for the questions (names, confirmations, amounts). Some clients cannot show dialogs: `interfaces.dialogs` sets
the default, and with `interfaces.editable` the players choose for themselves with `/kingdom settings`. Without dialogs
the questions are shown as GUIs whose values are written in chat; `/kingdom cancel` cancels the answer waited in chat.
The Bedrock players (Floodgate) always receive the Bedrock forms.

## 3. Quick start

For the players:

- `/kingdom create <name>` creates your kingdom.
- Stand where you want your first town and run `/town found <name>`: choose the style, and the town hall appears.
- `/town hall` (or right clicking the town hall) opens the management of the town: shop, members, roles, access mode.
- Claim the land around the town hall with `/town claim auto`, walking around.
- Buy an extractor, a collector and a residence from the shop with the starting emeralds of the town, and place them
  in your claims: the extractor starts producing emeralds for the town hall.
- Open the GUI of the extractor, press **Link to a collector** and shift + right click the collector: the extractor can
  now fill the higher limit.
- Invite your friends with `/town invite <player>`, or open the town with `/town privacy public`.
- Fill the containers of your claims to raise the worth, then `/town rankup` to unlock more claims and structures.
- Make allies with `/kingdom relation ally <kingdom>`, enemies with `/kingdom relation enemy <kingdom>`, and during a
  period of war attack them with `/town war declare <town> <stake>`.

For the staff:

- Check that every structure of the default style exists with `/structure list`, and build the missing ones.
- Set where the staff goes back after building with `/kingdom admin setspawn`.
- Build the ranking of the defenders with `/structure defense rank add`.
- Adjust `settings.yml` and `ranks.yml`, then `/kingdom admin reload`.

## 4. Building the structures

The schematics of the structures are saved in `structures/<style>/<type>/<level>.schem`, with their properties in
`<level>.yml` next to them. A style can have any of them: when a style misses a structure, the one of the default
style is used. The staff builds them in game, in an empty world (`structures.world`) created by the plugin.

1. `/structure create <type> <style> <level>` sends you to an area as large as a land, closed by walls. The line of
   `structures.editor.ground-block` on the walls marks the **floor**: the layer of the structure placed on the surface
   of the terrain, the block the players stand on. From the floor up there are `structures.editor.height` blocks,
   below it `structures.editor.underground-depth` blocks for an underground floor.
   Everyone in the area can build in it and use the editing commands, which act on its structure; nobody can break or
   place blocks outside the areas, nor break the walls.
2. Build the structure, and set its points where you stand:
   - `/structure spawn set` sets the spawn of the town, in a town hall;
   - `/structure defense add [max-level]` adds a spawn point of the defenders, `/structure defense remove` removes the
     nearest one;
   - `/structure set <property> <value>` overrides a property of `structures.yml` for this schematic: `cost`, `boost`,
     `capacity`, `seats`, `npcs`, `defense-cap`.
3. `/structure save` saves the schematic and sends everyone in the area back to the spawn of
   `/kingdom admin setspawn`; `/structure cancel` sends you back without saving.

When the structure is saved its faces are projected: from the four sides, from the bottom and from the top down to the
floor, the air is skipped until a block. That outer air is never pasted, so the terrain stays around the structure;
the inner air is pasted, digging the underground floors. The air of the floor open to the sky keeps the terrain, unless
an underground floor is below it.

`/structure edit` opens a saved structure again, `/structure info` shows its size, points and properties,
`/structure delete` deletes it and `/structure give` gives its item, for the tests. The structures are placed with the
corner of the schematic on the corner of the land, and their floor on the surface of the terrain: the town hall in
place of the block the founder stands on, the others in place of the block clicked with their item.

## 5. Commands

The arguments between `< >` are required, the ones between `[ ]` optional. The town commands without the town argument
act on the town chosen with `/town manage`. The commands asking for a confirmation open a dialog, or run directly with
`confirm` as their last argument. `/kingdom help`, `/town help` and `/structure help` list the commands each player can
use.

### Kingdom commands

Aliases of `/kingdom`: `/kingdoms`, `/king`.

| Command | Description | Permission | Role permission |
|---|---|---|---|
| `/kingdom panel` | Opens the panel of your kingdom. | `vilekingdoms.kingdom.panel` | any kingdom permission |
| `/kingdom help [page]` | Shows the commands you can use. | `vilekingdoms.kingdom.help` |  |
| `/kingdom who [player]` | Shows the kingdom, the towns, the first join and the playtime of a player. | `vilekingdoms.kingdom.who` |  |
| `/kingdom stats [kingdom]` | Shows the statistics of a kingdom. Alias: `/kingdom info`. | `vilekingdoms.kingdom.stats` |  |
| `/kingdom top [page]` | Shows the leaderboard of the kingdoms by emeralds. Alias: `/kingdom leaderboard`. | `vilekingdoms.kingdom.top` |  |
| `/kingdom settings [dialogs\|friendly-fire] [on\|off]` | Opens the settings of your kingdom (friendly fire) and of your interfaces. | `vilekingdoms.settings` |  |
| `/kingdom cancel` | Cancels the operation waiting for your answer. | `vilekingdoms.kingdom.cancel` |  |
| `/kingdom create [name]` | Creates your kingdom: you become its king. | `vilekingdoms.kingdom.create` | not a king |
| `/kingdom rename [name]` | Renames your kingdom. | `vilekingdoms.kingdom.rename` | `change_name` |
| `/kingdom disband [confirm]` | Disbands your kingdom, releasing its towns. | `vilekingdoms.kingdom.disband` | `disband` |
| `/kingdom kick <player>` | Kicks a player from every town of your kingdom. | `vilekingdoms.kingdom.kick` | `manage_members` and `manage_invites` |
| `/kingdom ban <player>` | Kicks a player from every town of your kingdom and bans them from it. | `vilekingdoms.kingdom.ban` | `manage_members` and `manage_invites` |
| `/kingdom unban <player>` | Removes the ban of a player from your kingdom. | `vilekingdoms.kingdom.unban` | `manage_members` and `manage_invites` |
| `/kingdom relations` | Opens the relations of your kingdom. | `vilekingdoms.kingdom.relations` | `manage_relations` |
| `/kingdom relation ally <kingdom>` | Proposes an alliance to a kingdom. | `vilekingdoms.kingdom.relation` | `manage_relations` |
| `/kingdom relation enemy <kingdom>` | Declares a kingdom as enemy. | `vilekingdoms.kingdom.relation` | `manage_relations` |
| `/kingdom relation neutral <kingdom>` | Becomes neutral with a kingdom. | `vilekingdoms.kingdom.relation` | `manage_relations` |
| `/kingdom relation accept <kingdom>` | Accepts the relation request of a kingdom. | `vilekingdoms.kingdom.relation` | `manage_relations` |
| `/kingdom relation decline <kingdom>` | Declines the relation request of a kingdom. | `vilekingdoms.kingdom.relation` | `manage_relations` |
| `/kingdom relation cancel <kingdom>` | Cancels the relation request sent to a kingdom. | `vilekingdoms.kingdom.relation` | `manage_relations` |

### Kingdom admin commands

| Command | Description | Permission |
|---|---|---|
| `/kingdom admin help [page]` | Shows the admin commands you can use. | `vilekingdoms.admin.help` |
| `/kingdom admin destroy <kingdom> [confirm]` | Destroys a kingdom, releasing its towns. | `vilekingdoms.admin.destroy` |
| `/kingdom admin transfer <kingdom> <player>` | Gives the crown of a kingdom to a player. | `vilekingdoms.admin.transfer` |
| `/kingdom admin setspawn` | Sets where the staff goes back after building a structure. | `vilekingdoms.admin.setspawn` |
| `/kingdom admin land-size [size]` | Shows the side of the lands, or chooses it until the first town is founded. | `vilekingdoms.admin.land-size` |
| `/kingdom admin reload` | Reloads the plugin. | `vilekingdoms.admin.reload` |

### Town commands

Aliases of `/town`: `/towns`, `/t`.

| Command | Description | Permission | Role permission |
|---|---|---|---|
| `/town help [page]` | Shows the town commands you can use. | `vilekingdoms.town.help` |  |
| `/town manage <town>` | Chooses the town the /town commands act on. | `vilekingdoms.town.manage` |  |
| `/town found [name] [style]` | Founds a town in the land you are in. | `vilekingdoms.town.found` | `found_towns` (global role) |
| `/town hall [town]` | Opens the management GUI of the town. | `vilekingdoms.town.hall` | `open_hall` |
| `/town reach [town]` | Teleports to the spawn of the town hall. | `vilekingdoms.town.reach` | `teleport` |
| `/town capital` | Teleports to the capital of your kingdom. | `vilekingdoms.town.capital` | `teleport` |
| `/town capital set [town]` | Makes the town the capital of the kingdom. Only the king. | `vilekingdoms.town.capital` | the king |
| `/town stats [town]` | Shows the leaders, the members and the bank of a town. Alias: `/town info`. | `vilekingdoms.town.stats` |  |
| `/town rankup [town]` | Brings the town to the next rank. | `vilekingdoms.town.rankup` | `rankup` |
| `/town shop [town]` | Opens the shop of the structures. | `vilekingdoms.town.shop` | `manage_structures` |
| `/town converter` | Opens the GUI converting real emeralds into emeralds of the town. | `vilekingdoms.town.converter` | `deposit_emeralds` |
| `/town claim [one\|auto] [town]` | Claims the land you are in, or with auto the lands you walk into. | `vilekingdoms.town.claim` | `manage_lands` |
| `/town unclaim [one\|auto] [town]` | Releases the land you are in, or with auto the lands you walk into. | `vilekingdoms.town.unclaim` | `manage_lands` |
| `/town map` | Shows the claims around you. | `vilekingdoms.town.map` |  |
| `/town privacy <public\|private\|party> [town]` | Changes how the players join the town. | `vilekingdoms.town.privacy` | `manage_town` |
| `/town join <town>` | Joins a town, by its access mode. | `vilekingdoms.town.join` |  |
| `/town decline <town>` | Declines the invite of a town. | `vilekingdoms.town.decline` |  |
| `/town leave <town> [confirm]` | Leaves a town. | `vilekingdoms.town.leave` | member of the town |
| `/town invite <player> [town]` | Invites a player to the town. | `vilekingdoms.town.invite` | `manage_invites` |
| `/town kick <member> [town]` | Kicks a member from the town. | `vilekingdoms.town.kick` | `manage_members` |
| `/town ban <player> [town]` | Kicks a player from the town and bans them from it. | `vilekingdoms.town.ban` | `manage_members` |
| `/town unban <player> [town]` | Removes the ban of a player from the town. | `vilekingdoms.town.unban` | `manage_members` |
| `/town promote <member> [town]` | Moves a member to the next town role. | `vilekingdoms.town.promote` | `manage_members` |
| `/town demote <member> [town]` | Moves a member to the previous town role. | `vilekingdoms.town.demote` | `manage_members` |
| `/town setrole <member> <role> [town]` | Gives a town role to a member. | `vilekingdoms.town.setrole` | `manage_members` |
| `/town balance [town] deposit\|withdraw <amount>` | Moves money between your account and the bank of the town. | `vilekingdoms.town.balance` | `manage_economy` |
| `/town tax set [town] <amount> <days>` | Sets the tax of the town and every how many days it is paid. | `vilekingdoms.town.tax` | `manage_taxes` |
| `/town tax unset [town]` | Removes the tax of the town. | `vilekingdoms.town.tax` | `manage_taxes` |
| `/town tax pay [town]` | Pays the tax of the town. | `vilekingdoms.town.tax` | member of the town |
| `/town chat [town]` | Turns the chat of the town on or off. | `vilekingdoms.town.chat` |  |
| `/town war declare <town> <stake>` | Declares war on a town of an enemy kingdom (from the town you manage). | `vilekingdoms.town.war` | `manage_wars` |
| `/town war join` | Joins the attack squad you were asked to join. | `vilekingdoms.town.war` | chosen for the squad |
| `/town war abandon [town]` | Abandons the war of the town, losing it. | `vilekingdoms.town.war` | `manage_wars` |
| `/town war list` | Shows the wars in progress. | `vilekingdoms.town.war` |  |
| `/town release [town] [confirm]` | Releases the town: its members leave it and its structures are destroyed. | `vilekingdoms.town.release` | `release_town` |

### Town admin commands

| Command | Description | Permission |
|---|---|---|
| `/town admin help [page]` | Shows the town admin commands you can use. | `vilekingdoms.admin.help` |
| `/town admin hall <town>` | Opens the town hall of a town: every action is forced and free. | `vilekingdoms.admin.town` |
| `/town admin member add <player> <town> [role]` | Adds a player to a town, without invite. | `vilekingdoms.admin.member.add` |
| `/town admin member remove <player> <town>` | Removes a player from a town. | `vilekingdoms.admin.member.remove` |
| `/town admin claim-for <town> [one\|auto]` | Claims the land you are in, or with auto the lands you walk into, for a town. | `vilekingdoms.admin.claim` |
| `/town admin release [one\|auto]` | Releases the land you are in, or with auto the lands you walk into, of any town. | `vilekingdoms.admin.release` |
| `/town admin release-all-for <town> [confirm]` | Releases all the claims of a town but the town hall one. | `vilekingdoms.admin.release` |
| `/town admin tax set <town> <amount> <days>` | Sets the tax of a town. | `vilekingdoms.admin.town` |
| `/town admin tax unset <town>` | Removes the tax of a town. | `vilekingdoms.admin.town` |
| `/town admin chat-spy` | Shows the messages of the town chats. | `vilekingdoms.admin.chat-spy` |
| `/town admin war list <town>` | Lists the wars declared or received by a town, with their id. | `vilekingdoms.admin.war` |
| `/town admin war stats <id>` | Shows the statistics of a war, in progress or ended. | `vilekingdoms.admin.war` |
| `/town admin war stop <id> win <town> [reason]` | Ends a war, giving the victory to a town. | `vilekingdoms.admin.war` |

### Structure commands (staff)

Aliases of `/structure`: `/structures`.

| Command | Description | Permission |
|---|---|---|
| `/structure help` | Shows the commands of the structures. | `vilekingdoms.admin.structure` |
| `/structure create <type> <style> <level>` | Sends you to an area as large as a land, closed by walls, to build a structure, with the floor on the red line and an underground floor below it. | `vilekingdoms.admin.structure` |
| `/structure edit <type> <style> <level>` | Opens a saved structure to modify it. | `vilekingdoms.admin.structure` |
| `/structure spawn set` | Sets the spawn point of the town in the town hall, where you are. | `vilekingdoms.admin.structure` |
| `/structure defense add [max-level] \| remove` | Adds a spawn point of the defenses where you are, with the highest defense level, or removes the nearest one. | `vilekingdoms.admin.structure` |
| `/structure defense rank add <vanilla\|mythic> <mob> [position]` | Adds a mob to the ranking of the defenses, at the end or at the position. | `vilekingdoms.admin.structure` |
| `/structure defense rank remove <position>` | Removes the mob of a position from the ranking of the defenses. | `vilekingdoms.admin.structure` |
| `/structure defense rank move <position> <new-position>` | Moves a mob of the ranking of the defenses to another position. | `vilekingdoms.admin.structure` |
| `/structure defense rank list` | Lists the ranking of the mobs of the defenses. | `vilekingdoms.admin.structure` |
| `/structure set <property> <value>` | Sets a property of the structure: cost, boost, capacity, seats, npcs, defense-cap. | `vilekingdoms.admin.structure` |
| `/structure save` | Saves the schematic and sends you back. | `vilekingdoms.admin.structure` |
| `/structure cancel` | Sends you back without saving. | `vilekingdoms.admin.structure` |
| `/structure delete <type> <style> <level> [confirm]` | Deletes a structure. | `vilekingdoms.admin.structure` |
| `/structure list [style]` | Lists the structures of a style, and the missing ones. | `vilekingdoms.admin.structure` |
| `/structure info <type> <style> <level>` | Shows the size, the spawn points and the properties of a structure. | `vilekingdoms.admin.structure` |
| `/structure give <player> <type> [style] [level]` | Gives the item of a structure, for the tests. | `vilekingdoms.admin.structure` |

### War periods (staff)

| Command | Description | Permission |
|---|---|---|
| `/wartime` | Shows the current period. | `vilekingdoms.admin.wartime` |
| `/wartime start [days]` | Starts a period of war now. | `vilekingdoms.admin.wartime` |
| `/wartime stop [permanent]` | Ends the period of war and starts a period of peace. | `vilekingdoms.admin.wartime` |
| `/wartime restart` | Starts the period of war again from its beginning. | `vilekingdoms.admin.wartime` |

## 6. Permissions

`vilekingdoms.user` gives every user command and is given to everyone; `vilekingdoms.admin` gives every admin command
and the user ones, and is given to the operators. Each command keeps its own permission, so a single command can still
be given or denied.

### `vilekingdoms.user`

Gives every user command of the kingdoms and of the towns. Given by default to everyone.

| Permission | Commands |
|---|---|
| `vilekingdoms.kingdom.ban` | `/kingdom ban` |
| `vilekingdoms.kingdom.cancel` | `/kingdom cancel` |
| `vilekingdoms.kingdom.create` | `/kingdom create` |
| `vilekingdoms.kingdom.disband` | `/kingdom disband` |
| `vilekingdoms.kingdom.help` | `/kingdom help` |
| `vilekingdoms.kingdom.stats` | `/kingdom stats` |
| `vilekingdoms.kingdom.kick` | `/kingdom kick` |
| `vilekingdoms.kingdom.panel` | `/kingdom panel` |
| `vilekingdoms.kingdom.relation` | `/kingdom relation ally`, `/kingdom relation enemy`, `/kingdom relation neutral`, `/kingdom relation accept`, `/kingdom relation decline`, `/kingdom relation cancel` |
| `vilekingdoms.kingdom.relations` | `/kingdom relations` |
| `vilekingdoms.kingdom.rename` | `/kingdom rename` |
| `vilekingdoms.kingdom.top` | `/kingdom top` |
| `vilekingdoms.kingdom.unban` | `/kingdom unban` |
| `vilekingdoms.kingdom.who` | `/kingdom who` |
| `vilekingdoms.settings` | `/kingdom settings` |
| `vilekingdoms.town.balance` | `/town balance` |
| `vilekingdoms.town.ban` | `/town ban` |
| `vilekingdoms.town.capital` | `/town capital`, `/town capital set` |
| `vilekingdoms.town.chat` | `/town chat` |
| `vilekingdoms.town.claim` | `/town claim` |
| `vilekingdoms.town.decline` | `/town decline` |
| `vilekingdoms.town.converter` | `/town converter` |
| `vilekingdoms.town.demote` | `/town demote` |
| `vilekingdoms.town.found` | `/town found` |
| `vilekingdoms.town.hall` | `/town hall` |
| `vilekingdoms.town.help` | `/town help` |
| `vilekingdoms.town.invite` | `/town invite` |
| `vilekingdoms.town.join` | `/town join` |
| `vilekingdoms.town.kick` | `/town kick` |
| `vilekingdoms.town.leave` | `/town leave` |
| `vilekingdoms.town.manage` | `/town manage` |
| `vilekingdoms.town.map` | `/town map` |
| `vilekingdoms.town.privacy` | `/town privacy` |
| `vilekingdoms.town.promote` | `/town promote` |
| `vilekingdoms.town.rankup` | `/town rankup` |
| `vilekingdoms.town.reach` | `/town reach` |
| `vilekingdoms.town.release` | `/town release` |
| `vilekingdoms.town.setrole` | `/town setrole` |
| `vilekingdoms.town.shop` | `/town shop` |
| `vilekingdoms.town.stats` | `/town stats` |
| `vilekingdoms.town.tax` | `/town tax set`, `/town tax unset`, `/town tax pay` |
| `vilekingdoms.town.unban` | `/town unban` |
| `vilekingdoms.town.unclaim` | `/town unclaim` |
| `vilekingdoms.town.war` | `/town war declare`, `/town war join`, `/town war abandon`, `/town war list` |

### `vilekingdoms.admin`

Gives every admin command of the kingdoms and of the towns, and the user ones. Given by default to the operators.

| Permission | Commands |
|---|---|
| `vilekingdoms.admin.chat-spy` | `/town admin chat-spy` |
| `vilekingdoms.admin.claim` | `/town admin claim-for` |
| `vilekingdoms.admin.destroy` | `/kingdom admin destroy` |
| `vilekingdoms.admin.help` | `/kingdom admin help`, `/town admin help` |
| `vilekingdoms.admin.land-size` | `/kingdom admin land-size` |
| `vilekingdoms.admin.member.add` | `/town admin member add` |
| `vilekingdoms.admin.member.remove` | `/town admin member remove` |
| `vilekingdoms.admin.release` | `/town admin release`, `/town admin release-all-for` |
| `vilekingdoms.admin.reload` | `/kingdom admin reload` |
| `vilekingdoms.admin.setspawn` | `/kingdom admin setspawn` |
| `vilekingdoms.admin.structure` | `/structure help`, `/structure create`, `/structure edit`, `/structure spawn set`, `/structure defense add`, `/structure defense rank add`, `/structure defense rank remove`, `/structure defense rank move`, `/structure defense rank list`, `/structure set`, `/structure save`, `/structure cancel`, `/structure delete`, `/structure list`, `/structure info`, `/structure give` |
| `vilekingdoms.admin.town` | `/town admin hall`, `/town admin tax set`, `/town admin tax unset` |
| `vilekingdoms.admin.transfer` | `/kingdom admin transfer` |
| `vilekingdoms.admin.war` | `/town admin war list`, `/town admin war stats`, `/town admin war stop` |
| `vilekingdoms.admin.wartime` | `/wartime`, `/wartime start`, `/wartime stop`, `/wartime restart` |

### `vilekingdoms.admin.bypass`

Ignores the protection of the territory of the towns. Not given by vilekingdoms.admin.

## 7. Role permissions

The permissions of the roles are given from the GUIs of the roles, and are separate from the permissions of the
server: a player must have both the permission of the command and the role permission it requires.

| Role permission | Scope | Allows |
|---|---|---|
| `change_name` | kingdom | renaming the kingdom |
| `manage_roles` | kingdom | creating, editing and deleting the roles |
| `manage_relations` | kingdom | the relations with the other kingdoms |
| `manage_kingdom` | kingdom | the settings of the kingdom, as the friendly fire |
| `found_towns` | kingdom | founding the towns of the kingdom |
| `disband` | kingdom | disbanding the kingdom |
| `open_hall` | town | opening the town hall GUI |
| `manage_town` | town | the town roles, the permissions of the outsiders and the access mode |
| `manage_members` | town | promoting, demoting, kicking, banning and unbanning the members |
| `manage_invites` | town | inviting the players and cancelling the invites |
| `manage_lands` | town | claiming and releasing the lands |
| `manage_structures` | town | buying, placing, upgrading and picking up the structures, and their defenses |
| `rankup` | town | bringing the town to the next rank |
| `manage_economy` | town | depositing and withdrawing money from the bank |
| `manage_taxes` | town | setting the taxes; it also exempts from them |
| `manage_wars` | town | declaring and abandoning the wars |
| `deposit_emeralds` | town | depositing the emeralds stolen in a war in the town hall, and converting real emeralds with `/town converter` |
| `teleport` | town | `/town reach` and `/town capital` |
| `release_town` | town | releasing the town |
| `build` | territory | breaking and placing blocks |
| `containers` | territory | opening the containers |
| `doors` | territory | doors, trapdoors and similar blocks |
| `redstone` | territory | the redstone components, as buttons and levers |
| `kill_animals` | territory | killing the animals and the neutral mobs |
| `pickup_items` | territory | picking up the items |

The kingdom permissions belong to the global roles only. The town permissions are given by the town roles in their
town, and by the global roles in every town of the kingdom. The territory permissions are given to the members by
their roles, and to the allies, the neutrals and the enemies by the outsiders button of the town hall
(`outsider-permissions` of `default-roles.yml` for the new towns).

## 8. Placeholders

With PlaceholderAPI installed the plugin registers the `vk` placeholders. The kingdom placeholders use the primary
kingdom of the player, the town placeholders the town they manage; they are empty when the player has none.

| Placeholder | Value |
|---|---|
| `%vk_kingdom%` | display name of the kingdom |
| `%vk_kingdom_name%` | name of the kingdom, without colors |
| `%vk_kingdom_king%` | name of the king |
| `%vk_kingdom_role%` | global role of the player |
| `%vk_kingdom_towns%` | number of towns |
| `%vk_kingdom_claims%` | number of claims |
| `%vk_kingdom_members%` | number of members |
| `%vk_kingdom_emeralds%` | emeralds of the kingdom (compact, e.g. 1.5k) |
| `%vk_kingdom_worth%` | worth of the kingdom (compact) |
| `%vk_kingdom_bank%` | bank of the kingdom (compact) |
| `%vk_town%` | display name of the town |
| `%vk_town_rank%` | rank of the town |
| `%vk_town_role%` | town role of the player |
| `%vk_town_members%` | number of members |
| `%vk_town_claims%` | number of claims |
| `%vk_town_max_claims%` | claims allowed by the rank |
| `%vk_town_bank%` | bank of the town (compact) |
| `%vk_town_emeralds%` | emeralds of the town (compact) |
| `%vk_town_max_emeralds%` | limit of the emeralds of the town (compact) |
| `%vk_town_worth%` | worth of the town (compact) |
| `%vk_town_tax%` | tax of the town |
| `%vk_town_immunity%` | time left of the immunity, empty when not immune |
| `%vk_town_war%` | the enemy town of the war in progress, empty without a war |
| `%vk_wartime%` | the current period: war, peace or permanent peace |
| `%vk_wartime_left%` | time left of the current period, empty in a permanent peace |
| `%rel_vk_relation%` | relation between the kingdoms of the two players (own, ally, neutral, enemy) |
| `%rel_vk_relation_color%` | color of that relation (`relations.colors`) |

The relational placeholders (`%rel_...%`) are resolved with the viewer as first player and the other player as the
second one, as in the chat format.

## 9. WorldGuard flags

With WorldGuard installed the plugin registers these state flags, all allowed by default:

| Flag | When denied |
|---|---|
| `kingdoms-enabled` | no `/kingdom` and `/town` command works for the players in the region |
| `towns-enabled` | no `/town` command works for the players in the region |
| `town-claims-enabled` | the lands touching the region, even in part, cannot be claimed nor released; the automatic claim and release stop entering them |

The admin commands (`/kingdom admin`, `/town admin`) and the console are never blocked. A land is also refused when a
region touching it denies `kingdoms-enabled` or `towns-enabled`, and the global region (`__global__`) counts too. For
example, to keep the towns out of the spawn:

```text
/rg flag spawn kingdoms-enabled deny
/rg flag spawn town-claims-enabled deny
```

## 10. Configuration

Every file is commented: this section explains what is not obvious. `/kingdom admin reload` reloads the configuration
files and the locales.

### Messages and languages

Each player reads the messages in the language of their client, when its file exists in `locales/`; the console and
the players of the other languages read `default-locale`. A new language is added by copying a locale file with the
name of the language (for example `lang_de_de.yml`). The messages support the legacy color codes (`&a`, `&#55AAFF`)
and the MiniMessage tags (`<green>`, `<click:...>`, `<hover:...>`). A key missing from a locale file is read from the
`default-locale`, then from the file inside the jar.

In the configuration files, the texts written as `t[key]` (or `translate[key]`) are read from the locale of the player.

### GUIs

`guis.yml` sets the rows, the slots and the items of every GUI. The items are built with `material`, `head-id`
(HeadDatabase) or `custom-id` (ItemsAdder, Oraxen, Nexo), plus `amount`, `display-name`, `description`, `item-flags`,
`enchantments` and `potion-effects`. A clicked item runs its `action`: `CLOSE`, `BACK`, `NEXT_PAGE`, `PREVIOUS_PAGE`,
`SEND_MESSAGE` (locale keys) or `COMMAND` (commands). A command starting with `!` runs from the console, one starting
with `::` runs as the player with the permissions of an operator: the plugin saves the players made operators for the
moment, and removes the operator status left by a crash at the next start. An item that cannot be built (unknown
material, custom item plugin missing) is replaced with `default-error-item`.

### Storage

`storage.type` is `SQLITE` (the default, a file in the folder of the plugin) or `MYSQL`, with the connection in
`storage.options`. Several servers can share a MySQL database: they must use the same `sessions.heartbeat-seconds`.

### Performance

The heavy work runs in background or a little per tick: `thread-pool-size` sets the threads of the plugin,
`workload.max-millis-per-tick` the milliseconds per tick spent pasting and clearing the structures, and
`worth.lands-per-tick` the lands read per tick while updating the worth (a land is read when every chunk of
Minecraft it touches is loaded).

### Default files

The default content of the main configuration files, with their comments.

#### settings.yml

```yaml
debug: false

# How many threads you want to use for this plugin in your server.
thread-pool-size: 4
workload:
  max-millis-per-tick: 2.5 # How many ticks would you to run on a millis?

default-locale: "en_us" # This is the default locales for console returned messages.

storage:
  type: SQLITE # This is the database type method. Possible values: SQLITE, MYSQL
  options: # Configure these options if is a MYSQL storage type.
    host: ""
    port: 3306
    username: ""
    password: ""
    database: ""
    table-prefix: "vk_"
    use-ssl: "false"
    character-encoding: "utf8"

# Item shown in GUIs when a configured item is not valid
# (unknown material, missing custom item, HeadDatabase/ItemsAdder/Oraxen not installed...).
# Texts written as translate[key] (or t[key]) are translation keys resolved from the locales files.
default-error-item:
  material: BARRIER
  display-name: "t[items.error-item.display-name]"
  description:
    - "t[items.error-item.description]"

kingdoms:
  name:
    pattern: "[A-Za-z0-9_]+"
    max-length: 25

# Lands: the unit of the claims of the towns, squares of size x size blocks aligned to the origin of the world. Each
# structure fills a land, so the side is also the largest side of a structure. A multiple of 8 from 16 to 64: any other
# value becomes the valid one just below it.
# The side becomes definitive when the first town is founded, because the claims and the structures depend on it: from
# then on this value is ignored. Until then it can be changed here or with /kingdom admin land-size, then the server
# must be restarted. The servers with towns from before VileKingdoms 1.0.4 keep lands of 16 blocks.
lands:
  size: 24

# Towns of the kingdoms. A kingdom is made of towns: each town controls a territory of claims (lands), all touching
# each other, and has its own emeralds (kept by its town hall), worth (value of its containers) and bank (Vault).
# The values of a kingdom are the sum of the ones of its towns.
towns:
  name:
    pattern: "[A-Za-z0-9_]+"
    max-length: 25
  starting-emeralds: 300 # Emeralds a new town has in its town hall, to buy its first structures.
  claim-distance: 3 # Lands that must stay between a claim and the claims of the towns of other kingdoms.
  capital-cooldown-seconds: 86400 # Seconds between two changes of the capital (/town capital set).
  enemy-conflict-hours: 24 # Hours given to a member of towns of two kingdoms becoming enemies to leave one: then they leave the newest one.
  invite-expiration-seconds: 300 # Seconds before an invite to a town expires.

# /town reach and /town capital, for the players with the teleport permission.
teleports:
  warmup-seconds: 5 # Seconds to wait without moving before the teleport. 0: immediate.
  cooldown-seconds: 60 # Seconds between two teleports. 0: none.
  during-wars: false # Whether the teleports work while the destination town or the player is in a war.

# Territory of the towns: who is not a member cannot break or place blocks, open containers, doors and trapdoors, use
# redstone components, pick up items or kill the animals, unless the town allows it to the allies, the neutrals or the
# enemies. In the lands with a structure nobody can break or place blocks, in peace and in war. The territory is always
# invulnerable to the explosions and to the attacks of the mobs, fireballs included.
protection:
  # Hostile mobs protected like the animals: only the members with the kill_animals permission can kill them.
  neutral-mobs:
    - ENDERMAN
    - WOLF
    - IRON_GOLEM
    - ZOMBIFIED_PIGLIN
    - PIGLIN
    - LLAMA
    - PANDA
    - POLAR_BEAR
    - BEE
    - DOLPHIN
    - GOAT
    - TRADER_LLAMA

# Worth of the towns: the value of the items in the containers of their claims (chests, barrels, hoppers, furnaces,
# crafters, droppers, dispensers, shulker boxes...), also inside the structures. The emeralds are not counted.
worth:
  # Source of the prices: AUTO (the first one installed among ShopGUIPlus, EconomyShopGUI, zShop, CMI and Essentials,
  # otherwise the internal list), SHOPGUIPLUS, ECONOMYSHOPGUI, ZSHOP, CMI, ESSENTIALS or INTERNAL.
  provider: AUTO
  update-minutes: 10 # Minutes between two updates of the worth of every town.
  lands-per-tick: 4 # Loaded lands read in each tick while updating the worth.
  # Internal price of one item of each material (used by the INTERNAL provider and when the shops have no price).
  prices:
    DIAMOND: 100
    DIAMOND_BLOCK: 900
    GOLD_INGOT: 25
    GOLD_BLOCK: 225
    IRON_INGOT: 10
    IRON_BLOCK: 90
    NETHERITE_INGOT: 1000
    COAL: 2
    REDSTONE: 1
    LAPIS_LAZULI: 2

# Taxes of the towns (/town tax): the members pay them into the bank of the town, the ones with the manage_taxes
# permission are exempt. Who does not pay by the due date is in debt, and is kicked after the grace days.
taxes:
  enabled: true # When false nobody pays the taxes.
  grace-days: 3

# Converter of the emeralds (/town converter): real emeralds and emerald blocks become emeralds of the town, within its
# limit. Only for who has the deposit_emeralds permission, never during a war of the town.
converter:
  rate: 1 # Emeralds of the town for each real emerald; a block counts as 9 emeralds. 0 disables the converter.

# Emerald extractors: every interval each extractor produces base-amount x level boost x biome modifier emeralds, sent
# to the town hall. An extractor without a collector stops at the limit of the town hall; linked to a collector (button
# of its GUI, then shift + right click on the collector) it goes on up to the limit of the town, raised by the collectors.
extractors:
  interval-minutes: 10
  base-amount: 5
  min-distance-lands: 3 # Lands that must stay between two extractors.
  max-collector-distance-lands: 8 # Farthest distance from the collector: farther, the extractor is unlinked.
  link-seconds: 30 # Seconds to click the collector after pressing the link button of the extractor.
  biomes: # Modifiers of the production by biome (e.g. DESERT, OCEAN, PLAINS): the other biomes use 1.0.
    bonus:
      BADLANDS: 1.5
      DESERT: 1.25
    malus:
      OCEAN: 0.5
      DEEP_OCEAN: 0.5

# Wars between the towns of enemy kingdoms (/town war). The periods of peace and of war alternate: the wars can be
# declared only during a period of war (/wartime to control the cycle).
wars:
  enabled: true
  # What happens to the defender when the attacker wins: CONQUEST (joins the kingdom of the attacker, its members are
  # kicked), TIMED_DESTRUCTION (unusable until destruction-hours pass) or PERMANENT_DESTRUCTION (deleted).
  conquest: CONQUEST
  destruction-hours: 72
  griefable-ruins: false # PERMANENT_DESTRUCTION: whether the lands of the deleted town become free, otherwise ruins nobody can edit.
  peace-days: 6
  war-days: 1
  duration-minutes: 60 # Duration of a war.
  declare-cooldown-hours: 24 # Hours before the attacker can declare another war.
  immunity-hours: 48 # Hours the defender cannot be attacked after a war.
  founding-immunity-hours: 24 # Hours a new town cannot be attacked after it is founded: 0 for no initial immunity.
  lose-immunity-on-declare: true # Whether a town declaring a war loses its immunity (of a war or of its founding) when the war starts.
  offline-defenders-min-rank: 1 # Lowest rank of the towns that can be attacked with no defender online. 0: never.
  # How the worth of the attacker must compare with the worth of the defender: NONE, GREATER, GREATER_OR_EQUAL, EQUAL,
  # LESS, LESS_OR_EQUAL.
  worth-comparison: NONE
  stake:
    min: 100
    max: 100000
  capture-seconds: 60 # Seconds half of the squad must stay in a land to make it contested.
  squad-join-seconds: 120 # Seconds the members of the attacker have to join the squad.
  steal: # Emeralds stolen from the town hall, when its land is contested.
    interval-seconds: 5
    amount: 10

# Defenses of the towns: NPCs attacking the invaders and the banned players, bought from the town hall for a structure
# with defense spawn points.
# Defenses of the structures: the defense level N spawns the mob at the position N of the ranking built by the staff
# with /structure defense rank (zombies without a ranking), up to the highest level of the structure.
defenses:
  enabled: true
  cost: 500 # Emeralds paid for each defense level: the level N costs N times this amount.
  peaceful-damage: 4 # Attack damage of the defenders of a peaceful mob (e.g. a cow) without an attack of its own.

# Party access mode of the towns (Parties plugin): when the leader of a party joins a town, their party joins with them.
party:
  enabled: true

structures:
  default-style: "default" # Style used when a style misses a structure: the towns work only when it has every structure.
  world: "vk_structures" # Empty world where the staff builds the structures.
  # Areas as large as a land, closed by walls, where the staff builds: a line of ground-block on the walls marks the floor
  # (y 65), the layer of the structure placed on the surface of the terrain, where the players stand. Below it the
  # structures can have underground floors. When saved, the air reached from the sides, from the bottom and from the
  # top down to the floor is not pasted: the inner air is, digging the underground floors.
  # When a structure is removed, the terrain saved before placing it is put back (the structures placed before 1.0.4
  # get the terrain generated again by FastAsyncWorldEdit or WorldEdit, or air without them).
  editor:
    underground-depth: 16 # Blocks below the floor for the underground floors (0 for none).
    height: 64 # Blocks from the floor up, the floor included.
    wall-block: "minecraft:glass"
    ground-block: "minecraft:red_concrete"

# Playtime of the players (/kingdom who): a session is saved for each time a player is online.
# While a player is online, their session is saved every heartbeat: when the server stops without closing the
# sessions (crash), they end at their last heartbeat, so at most a heartbeat of playtime is lost.
# The servers sharing a MySQL database must use the same heartbeat.
sessions:
  heartbeat-seconds: 60 # Seconds between the saves of the open sessions, at least 10. Read when the plugin is enabled.

# Dates and numbers shown in the messages. The date pattern of each language is formats.date of its locale file.
formats:
  timezone: "" # Time zone of the dates, e.g. "Europe/Rome". Empty: the time zone of the server.
  numbers:
    decimals: 2 # Maximum decimals of the plain numbers, e.g. {emeralds}: 1500.25
    compact: # Numbers shortened by their thousands, e.g. {emeralds-formatted}: 1.5k, 2.3M
      decimals: 1 # Maximum decimals, truncated: 1999 is 1.9k, never rounded up to 2k.
      suffixes: # Suffix of each power of 1000: thousands, millions, billions, trillions...
        - "k"
        - "M"
        - "B"
        - "T"
        - "Qa"
        - "Qi"

# Interfaces shown to the players. Some clients (e.g. chat-only mobile clients) cannot show dialogs: without them
# the dialogs are shown as GUIs whose values are written in chat.
# Bedrock players (Floodgate) always receive the Bedrock forms instead.
interfaces:
  dialogs: true # Default for the players who did not choose: show the dialogs.
  editable: false # Whether the players can choose for themselves (/kingdom settings). When false, everyone uses the defaults.

# Colors of the relations between the kingdoms, used by the chat and by the relational placeholders.
# Each color is a legacy color code ("&a", "&#55AAFF") or a MiniMessage tag ("<green>", "<#55aaff>").
relations:
  colors:
    own: "&a" # The kingdom of the viewer itself.
    ally: "&b"
    neutral: "&7" # Also used when the viewer or the sender is not in a kingdom.
    enemy: "&c"
  request-expiration-hours: 168 # Hours before a relation request without answer expires (168 = 7 days). 0: never.

# Claims of the towns: each claim is a land. A town claims only the free lands touching its claims, and releases only
# its own ones. The automatic claim (/town claim auto) claims the free lands the player walks into, the automatic
# release (/town unclaim auto) releases the lands of their town. Both stop when the command is run again, when the
# duration ends, when the player quits or loses the manage_lands permission, or when the other one is started.
claims:
  auto:
    duration-minutes: 5 # Minutes before the automatic claim and release stop by themselves. 0: until stopped.
    action-bar: true # Whether the progress (lands and time left) is shown in the action bar.

# Chat formatted by the plugin. Each player receiving a message sees the kingdom of the sender with the color of
# the relation between their kingdoms.
# The format supports legacy color codes and MiniMessage tags, and the PlaceholderAPI placeholders of the sender
# (e.g. %vault_prefix%, %luckperms_prefix%). The relational placeholders (%rel_...%) are resolved with the
# player receiving the message as first player and the sender as second one.
# Every placeholder is resolved before the colors, so the color codes returned by the placeholders color the chat
# too: only the formats of the plugin are read ("&a", "&#55AAFF", MiniMessage tags), not the section sign ones.
# Placeholders of the plugin:
#   {kingdom-tag}     the kingdom-tag below, empty when the sender is not in a kingdom
#   {player}          name of the sender (use a placeholder such as %player_displayname% for the nicknames)
#   {message}         the message, inserted after the colors: the colors and tags written in it are never parsed
# Placeholders of kingdom-tag:
#   {relation-color}  color of the relation between the receiver and the sender (relations.colors)
#   {kingdom}         name of the kingdom of the sender, without its own colors
chat:
  enabled: true # When false the chat is left to the server or to other plugins.
  format: "{kingdom-tag}%vault_prefix%{player}%vault_suffix% &8>> &f{message}"
  kingdom-tag: "&8[{relation-color}{kingdom}&8] "
  # Chat of a town (/town chat), read by its members. Placeholders: {town}, {player}, {message}.
  town-format: "&2[{town}] &a{player} &8>> &f{message}"
  # Town chats read by the admins with /town admin chat-spy.
  town-spy-format: "&8[Spy] &2[{town}] &7{player} &8>> &7{message}"
```

#### ranks.yml

```yaml
# Ranks of the towns: the rank of a town is the level of its town hall. The staff builds a town hall for each rank
# (/structure create town-hall <style> <rank>); a rank without its own schematic keeps the town hall as it is.
#
# Each rank, numbered from 1 without gaps:
#   required-emeralds / required-worth   needed to reach the rank (/town rankup), and to keep it
#   cost                                 emeralds paid to reach the rank
#   max-claims                           claims the town can have
#   structures.<type>.count / max-level  how many structures of each type (extractor, collector, residence, depot)
#                                        the town can have, and up to which level. The ones placed later are in excess.
ranks:
  1:
    required-emeralds: 0
    required-worth: 0
    cost: 0
    max-claims: 9
    structures:
      extractor:
        count: 1
        max-level: 1
      collector:
        count: 1
        max-level: 1
      residence:
        count: 1
        max-level: 1
      depot:
        count: 0
        max-level: 0
  2:
    required-emeralds: 500
    required-worth: 5000
    cost: 250
    max-claims: 16
    structures:
      extractor:
        count: 2
        max-level: 2
      collector:
        count: 2
        max-level: 2
      residence:
        count: 2
        max-level: 2
      depot:
        count: 1
        max-level: 1
  3:
    required-emeralds: 2000
    required-worth: 25000
    cost: 1000
    max-claims: 25
    structures:
      extractor:
        count: 3
        max-level: 3
      collector:
        count: 3
        max-level: 3
      residence:
        count: 3
        max-level: 3
      depot:
        count: 2
        max-level: 2

# Loss of the rank: when the emeralds or the worth go below the requirements of the rank (e.g. after a theft), the
# town receives a warning every warning-hours, for warnings times. Still below after the last one, it goes back a rank:
# its most recent claims over the new limit are released, its structures over the limits work as the new rank allows.
rank-loss:
  warning-hours: 24
  warnings: 3
```

#### structures.yml

```yaml
# Default properties of each level of the structures. The properties set with /structure set on a schematic override
# these ones. The structures are paid with the emeralds of the town.
#   cost      emeralds paid for level 1 in the shop, or to upgrade to the level
#   boost     multiplier of the production of an extractor
#   capacity  limit of the emeralds of the town hall; for a collector, how much it raises the limit of its town
#   seats     members living in a residence
#   npcs      defenders appearing when an enemy enters the land of a defended structure, 2 when not set
#   defense-cap  highest defense level the structure can buy, 1 when not set
structures:
  town-hall:
    levels:
      1:
        cost: 0
        capacity: 1000
      2:
        cost: 0
        capacity: 3000
      3:
        cost: 0
        capacity: 8000
  extractor:
    levels:
      1:
        cost: 100
        boost: 1.0
      2:
        cost: 300
        boost: 1.5
      3:
        cost: 900
        boost: 2.0
  collector:
    levels:
      1:
        cost: 100
        capacity: 1000
      2:
        cost: 300
        capacity: 2500
      3:
        cost: 900
        capacity: 6000
  residence:
    levels:
      1:
        cost: 100
        seats: 4
      2:
        cost: 300
        seats: 8
      3:
        cost: 900
        seats: 12
  depot:
    levels:
      1:
        cost: 200
      2:
        cost: 600

# Items of the structures, bought from the shop or picked up. Placeholders: {type}, {level}, {style}, {emeralds}.
# Each item is built like the items of the GUIs: head-id, custom-id or material, display-name, description...
items:
  town-hall:
    material: BELL
    display-name: "t[structure-translation.item.display-name]"
    description:
      - "t[structure-translation.item.description]"
  extractor:
    material: EMERALD_ORE
    display-name: "t[structure-translation.item.display-name]"
    description:
      - "t[structure-translation.item.description]"
  collector:
    material: EMERALD_BLOCK
    display-name: "t[structure-translation.item.display-name]"
    description:
      - "t[structure-translation.item.description]"
  residence:
    material: RED_BED
    display-name: "t[structure-translation.item.display-name]"
    description:
      - "t[structure-translation.item.description]"
  depot:
    material: BARREL
    display-name: "t[structure-translation.item.display-name]"
    description:
      - "t[structure-translation.item.description]"
```

#### default-roles.yml

```yaml
# Default roles of the kingdoms and of the towns: every new kingdom and town gets a copy of them (the system roles).
# The roles are listed from the lowest weight to the highest: the starter role is given to the new members, the leader
# role (the king of a kingdom, the leaders of a town) has every permission.
#
# Permissions of the kingdom (global roles only):
#   change_name, manage_roles, manage_relations, manage_kingdom (settings, friendly fire), found_towns, disband
# Permissions of the towns (town roles in their town, global roles in every town of the kingdom):
#   open_hall, manage_town (town roles, permissions of the outsiders, access mode), manage_members, manage_invites,
#   manage_lands, manage_structures, rankup, manage_economy, manage_taxes (also exempts from the taxes), manage_wars,
#   deposit_emeralds, teleport, release_town
# Permissions of the territory (also given to the allies, the neutrals and the enemies of a town):
#   build, containers, doors, redstone, kill_animals, pickup_items
#
# A global role with a town permission has it in every town of the kingdom: e.g. a role with manage_structures
# manages the structures of every town.

kingdom-roles:
  leader-role-name: "king"
  starter-role-name: "resident"
  roles:
    resident:
      display: "Resident"
      permissions:
        - "teleport"
    noble:
      display: "Noble"
      permissions:
        - "teleport"
        - "manage_invites"
        - "manage_members"
        - "manage_relations"
    king:
      display: "King"
      permissions: []

town-roles:
  leader-role-name: "mayor"
  starter-role-name: "citizen"
  roles:
    citizen:
      display: "Citizen"
      permissions:
        - "teleport"
        - "build"
        - "containers"
        - "doors"
        - "redstone"
        - "kill_animals"
        - "pickup_items"
    officer:
      display: "Officer"
      permissions:
        - "teleport"
        - "build"
        - "containers"
        - "doors"
        - "redstone"
        - "kill_animals"
        - "pickup_items"
        - "open_hall"
        - "manage_invites"
        - "manage_members"
        - "manage_lands"
        - "manage_structures"
        - "deposit_emeralds"
    mayor:
      display: "Mayor"
      permissions: []

# Territory permissions of the players who are not members of a new town, by the relation of their kingdom with the
# kingdom of the town. The players without a kingdom are neutral, the members of the same kingdom are allies.
outsider-permissions:
  ally:
    - "doors"
    - "pickup_items"
  neutral: []
  enemy: []
```

## 11. Addons

The addons read the kingdoms, the towns, the structures and the wars, and listen to the events of the plugin through
the VileKingdoms API. See [API.md](API.md).
