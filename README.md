# Turret Auto Fill

A client-side Mindustry Java mod that **automatically fills nearby item turrets with ammo** — from the items you carry, or fetched straight from the closest core in transfer range. Toggle it with `F4` and stop hand-feeding your turrets.

![icon](icon.png)

## Features

- Fills nearby item turrets from your carried items, topping up the emptiest compatible turret first.
- Near a core it runs a full refill session: your held item is parked in the core, the best ammo for each turret is withdrawn and distributed, and your original item is handed back at the end.
- Remembers your held item even if you deposit it into the core (or toss it into the air) yourself — the mod picks it back up the next time you are in core range. Toggling the mod off and on clears the memory.
- **Ammo priorities**: a drag-and-drop editor in **Settings → Turret Auto Fill** to reorder or disable ammo types per turret. Defaults are sorted by estimated DPS.
- **Server-friendly**: in multiplayer the mod rate-limits its own item transfers (configurable, default 15 actions per 6 seconds) to stay under the vanilla server interaction limit instead of getting you warned or kicked.
- Purely client-side (`hidden: true`): not required on servers, so you can join any server with this installed.

## Usage

1. Press `F4` in game to toggle Turret Fill on or off (rebindable in the keybind settings).
2. Move near item turrets while carrying ammo — they get topped up automatically.
3. Stand near a core to refill every turret in transfer range from the core's stock.
4. Tune ammo priorities and the multiplayer action limit in **Settings → Turret Auto Fill**.

## Installation

Grab `Turretautofill.jar` from the [releases](../../releases) (or build it yourself, below) and either:

- import it in-game via **Mods → Import mod → Import file**, or
- drop it into your Mindustry `mods` folder.

Requires Mindustry **build 154 or newer**.

## Building

Desktop-only jar (no Android SDK needed):

```
./gradlew jar
```

Output: `build/libs/TurretautofillDesktop.jar`

Combined desktop + Android jar (requires an Android SDK with `d8` on your PATH and `ANDROID_HOME` set):

```
./gradlew deploy
```

Output: `build/libs/Turretautofill.jar` — this is the one that works on all platforms. The included GitHub Actions workflow builds it automatically on every push.

## How it works

Every frame (while enabled and in-game) the mod scans your team's buildings within item-transfer range for item turrets. When you hold ammo, it sends the same `Call.transferInventory` request a manual transfer would, aimed at the emptiest compatible turret. Inside core range it also withdraws the highest-priority ammo each turret still accepts, and it waits for the server to confirm (or time out) every transfer before sending the next one. In multiplayer, a rolling 6-second action budget keeps the total request rate under the server's interaction rate limit.

## Known quirks

- The core refill only uses items the core actually contains — if someone drains your parked item from the core before the session finishes, there is nothing left to restore.
- An item tossed into the air is gone; the mod can only re-fetch it if the core has more of it in stock.
- On servers with an interaction limit stricter than vanilla's 25 actions per 6 seconds, lower the action limit slider accordingly.
