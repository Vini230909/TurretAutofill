# AGENTS.md

## Project Goal

This repo is the standalone `TurretAutoFill` mod: a client-side Mindustry Java mod that automatically fills nearby item turrets. It contains only the Turret Fill feature; other tools live in the separate `Vini-Client` repo. Repo presentation (README, icon, workflow, license) follows the style of the `Vinicator` repo.

## Current Layout

- `src/vini/turretautofill/TurretAutoFill.java` is the Mindustry `Mod` entry point.
- `src/vini/turretautofill/TurretFillFeature.java` contains the auto-fill logic, the held-item memory, and the multiplayer action rate limiter.
- `src/vini/turretautofill/AmmoPriorities.java` contains the settings category (action limit slider + ammo priority editor) and priority storage.
- `src/vini/turretautofill/TurretFillKeybinds.java` registers the toggle keybind.
- `assets/bundles/bundle.properties` holds display names for the keybind and settings.
- `mod.hjson` points to `vini.turretautofill.TurretAutoFill` and carries the in-game metadata.
- `icon.png` is the mod icon, packed into the jar and shown in the README.
- `.github/workflows/build.yml` builds the combined jar on every push and updates the rolling `latest` release used by in-game GitHub import.

## Build And Verification

Use the Gradle wrapper:

```powershell
.\gradlew jar
```

The project targets Mindustry `v157` and Java 8 bytecode through Jabel. Keep code compatible with Java 8 APIs.

`deploy` also builds an Android jar, but it requires a configured Android SDK. `rootProject.name` must stay equal to the GitHub repo name (`Turretautofill`) because the workflow uploads `build/libs/<repo-name>.jar`.

## Coding Guidelines

- Keep the repo focused on the Turret Fill feature; do not add unrelated tools here.
- Register UI, settings, keybinds, and update hooks inside `TurretFillFeature.init()`.
- Avoid routine console/log output for player-client features; prefer quiet gameplay behavior.
- Guard client logic with Mindustry state checks such as `Vars.state.isGame()`, `Vars.player != null`, and `!Vars.player.dead()`.
- Every `Call` that reaches the server must go through `tryAction()` so the multiplayer rate limit holds.
- Keep settings keys feature-scoped, and preserve legacy keys when renaming old features.
- Do not add server-side requirements for client features. Features should fail quietly when the needed game state is unavailable.
- Update `mod.hjson`, `README.md`, and this file when the project identity, feature layout, or build process changes.
