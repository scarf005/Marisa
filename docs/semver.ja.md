# [セマンティック バージョニング](https://semver.org/lang/ja/)

[![en][icon-en]][en] [![ja][icon-ja]][ja] [![ko][icon-ko]][ko]

[en]: ./semver.md
[icon-en]: https://img.shields.io/badge/lang-en-red?style=flat-square
[ja]: ./semver.ja.md
[icon-ja]: https://img.shields.io/badge/lang-ja-orange?style=flat-square
[ko]: ./semver.ko.md
[icon-ko]: https://img.shields.io/badge/lang-ko-yellow?style=flat-square

機能：カード、アーティファクト、ポーション、イベントなど 変更範囲：

- `github`: リポジトリ自体に関連する変更（主に [`.github`](/.github))
- `README`: [`README.md`](/README.md)に関連する変更
- `L10n`: ローカリゼーション(localization)などで変更範囲を限定

## メジャーバージョン(`!`)

> APIやプレイヤー体験に変更が生じた場合

- 新しい`機能`の追加、削除、リワーク
- `modthespire` 関連の変更

```
feat!: rework `Foo`

Foo now does bar
```

```
refactor!: change `ModId`
```

## マイナーバージョン(`feat`)

> 既存のバージョンと互換性があり、新機能が追加される場合

- `機能`の数値を変更（攻撃力、エネルギーなど）
- `機能`の詳細効果の変更（例：`廃棄`を捨てるに変更）
- アートワークの追加、変更
- 翻訳の追加

```
feat: decrease `Foo` energy cost
```

## パッチバージョン(`fix`)

> 既存のバージョンと互換性があり、バグが修正される場合

- 機能のバグ修正
- 翻訳の修正

```
fix(L10n): missing `DESCRIPTION` on ENG/`Foo`
```

## バージョン管理対象外

> プレイヤー体験に影響がない変更があっても

- その他のsubject

```
docs(README): add links
```

```
build(github): add gradle dependency
```
