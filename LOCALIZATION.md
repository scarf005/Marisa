# Localization

The mod text is in `src/main/resources/marisa/localization/`. The `localization/` link in the repository root points to it. Each language has one directory.

Read [CONTRIBUTING.md](CONTRIBUTING.md) first to set up the repository and the tests.

## Directories

The directory name is the name of the game language in `Settings.GameLanguage`. Use these names with capital letters:

`ENG`, `DUT`, `EPO`, `PTB`, `ZHS`, `ZHT`, `FIN`, `FRA`, `DEU`, `GRE`, `IND`, `ITA`, `JPN`, `KOR`, `NOR`, `POL`, `RUS`, `SPA`, `SRP`, `SRB`, `THA`, `TUR`, `UKR`, `VIE`

`ENG` is the source language. The mod uses it when a language has no file. The log then shows `No localization file for <language>, using fallback ENG`.

Each directory has these files:

| File            | Text                                 |
| --------------- | ------------------------------------ |
| `cards.json`    | card names and descriptions          |
| `events.json`   | event text and options               |
| `keywords.json` | keyword names and tooltips           |
| `potions.json`  | potion names and descriptions        |
| `powers.json`   | power names and descriptions         |
| `relics.json`   | relic names, descriptions and flavor |

## Add a language

1. Find the code of your language in the list above.
2. Copy the English files. Use the code as the new directory name:

   ```sh
   cp -r localization/ENG localization/POL
   ```

3. Translate the values in each file. Do not change the keys.
4. Keep the game markers as they are, with one space around them: `NL`, `!D!`, `!B!`, `!M!`, `[E]`, `#b`, `#y`, `#r`, `#g` and `#p`. The game splits descriptions at spaces and reads each marker as a word. A marker with two spaces shows wrongly.
5. Add the code to `langCodes` in `src/main/resources/marisa/localization/common.ts`.
6. Add the code to `languages` in `src/test/kotlin/marisa/LocalizationTest.kt`.
7. Validate the files (see below).
8. Test the language in the game (see below).
9. Open a pull request. Use a title such as `feat(L10n): add Polish`.

Some text is in Kotlin and not in these files. `loadoutText` and `getTitle` in `src/main/kotlin/marisa/characters/Marisa.kt` give the character select text. They show English for a language that is not listed there. Add a branch for the new language if you want to translate them.

## Validate

Run these commands from the repository root. They need only Deno.

1. Check the files of one language against the JSON schemas that come from `ENG`:

   ```sh
   deno task l10n validate POL
   ```

   Without a code, the command checks all languages. A missing or wrong key prints an issue with its `path` and `message`. A file with no issues prints only its name.

2. Run the unit tests. They also check that every `#` color marker is one the game knows:

   ```sh
   deno task test
   ```

3. After a change to the `ENG` keys, write the schemas again and commit the changes:

   ```sh
   deno task l10n schema
   ```

4. To check the files in VS Code, write the schema paths to `.vscode/settings.json`:

   ```sh
   deno task l10n schema:vscode
   ```

To check that every card, upgrade, power and relic builds in each language, run `./gradlew test`.

## Test in the game

1. Build the mod and put it in your mods folder. See [Build from source](CONTRIBUTING.md#build-from-source).
2. Start ModTheSpire with BaseMod and Marisa: Continued.
3. Change the game language in Settings. Or set `"LANGUAGE": "POL"` in the `preferences/STSGameplaySettings` file in the game directory while the game is closed.
4. Start a run as Marisa. Open the [BaseMod console](CONTRIBUTING.md#basemod-console) and add the cards, relics and powers that you changed, for example `hand add marisa:DarkSpark`.
5. Look at each card, tooltip and event. Check for text that is cut off, wrongly colored or has gaps between words.
6. Look at the log for the warning `No localization file for`. It means the game did not find a file.
