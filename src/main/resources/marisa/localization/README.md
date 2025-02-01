# Localization

directories below are named after the language code used in the game.

- `ENG`: English
- `FRA`: French
- `JPN`: Japanese
- `KOR`: Korean
- `ZHS`: Simplified Chinese
- `ZHT`: Traditional Chinese
- `SPA`: Spanish

## Validation

### Schema generation

running `deno run -A schema.ts` will generate a [json schema][json-schema] file and [zod object][zod] in `schemas/` directory based on localizations in `ENG` directory.

### Schema Validation

run `deno run -A validate.ts` to check if all localization files are valid using schemas generated from [schema generation](#schema-generation)

### Vscode json schema generation

running `deno run -A vscode.ts` will populate `settings.json` file with schema paths that will validate json files in vscode.

[json-schema]: https://json-schema.org
[zod]: https://zod.dev
