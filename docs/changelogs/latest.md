[//]: # (Manually copy the latest.md to /docs/changelog.md, then copy the default.md to the latest.md at every release time.)

### New Features

- Speak current perspective when switched to [#314](https://github.com/khanshoaib3/minecraft-access/issues/314)
- Add basic aim assist for bows (temporarily lock onto the nearest hostile mob when drawing a bow, play sounds indicating how much the bow has been drawn and if the target can be shot)
Closes #212
Edit: https://github.com/khanshoaib3/minecraft-access-i18n/pull/39 should be merged before this because it contains fields required by this feature
- Implement mouse simulation on MacOS
- Implement speech support on MacOS with queuing and interruption support
- Add a speech settings config menu with a speech rate option for changing the speech rate on MacOS
- Warn the user if Minecraft has not been granted the accessibility permission, which is needed for mouse simulation to work

### Feature Updates

- [Vault](https://minecraft.wiki/w/Vault) and [Trial Spawner](https://minecraft.wiki/w/Trial_Spawner) are added as POI blocks [#306](https://github.com/khanshoaib3/minecraft-access/issues/306)
- Remove `Position Narrator Format` config since it seems duplicate with single number narrating formats

### Bug Fixes

- Let `Enable Facing Direction` config controls auto direction speaking in `Camera Controls` [#327](https://github.com/khanshoaib3/minecraft-access/issues/327)
- Make the `Look Straight Back` key combination (left alt + numpad 5) works again [#328](https://github.com/khanshoaib3/minecraft-access/issues/328)

### Translation Changes

- Add four items for speaking perspectives [I18N PR 37](https://github.com/khanshoaib3/minecraft-access-i18n/pull/37)
- Add translation identifiers for the speech settings and speech rate buttons in the configuration menu
- Add a translation identifier for the warning about the accessibility permission not being granted on MacOS

### Others

Updated modpack setup instructions to describe when users might want or need a screenreader such as NVDA or JAWS.

### Development Chores

- Refactor `PlayerPositionUtils`
- Enhance `build` workflow's edge case handling
- My pull request to the minecraft-access-i18n repository is needed for the extra text strings I added in this pull request
I have implemented speech and mouse simulation support for MacOS using JNA calls to the Objective C runtime and other native MacOS libraries, so no external libraries or tools need to be installed.
