# [Semantic Versioning](https://semver.org)

[![en][icon-en]][en]
[![ja][icon-ja]][ja]
[![ko][icon-ko]][ko]

[en]: ./semver.md
[icon-en]: https://img.shields.io/badge/lang-en-red?style=flat-square
[ja]: ./semver.ja.md
[icon-ja]: https://img.shields.io/badge/lang-ja-orange?style=flat-square
[ko]: ./semver.ko.md
[icon-ko]: https://img.shields.io/badge/lang-ko-yellow?style=flat-square

feature: card, relic, potion, event, etc
scopes:

- `github`: changes related to repository (mostly [`.github`](/.github))
- `README`: changes to [`README.md`](/README.md)
- `L10n`: localization changes

## major(`!`)

> incompatible changes to API or player experience

- `feature` addition, removal, rework
- `modthespire` related changes

```
feat!: rework `Foo`

Foo now does bar
```

```
refactor!: change `ModId`
```

## minor(`feat`)

> backwards compatible changes

- `feature` stat changes (attack, energy, etc)
- `feature` effect changes (e.g. remove `Exhaust`)
- artwork addition, changes
- localization addition

```
feat: decrease `Foo` energy cost
```

## patch(`fix`)

> bugfixes

- feature bugfix
- fix localization

```
fix(L10n): missing `DESCRIPTION` on ENG/`Foo`
```

## unversioned

> changes unimportant to player experience

- other subjects

```
docs(README): add links
```

```
build(github): add gradle dependency
```
