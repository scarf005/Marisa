# [유의적 버전 관리](https://semver.org/lang/ko/)

기능: 카드, 유물, 물약, 이벤트 등등
변경 범위:

- `github`: 저장소 자체에 관련된 변경 (주로 [`.github`](/.github))
- `README`: [`README.md`](/README.md)에 관련된 변경
- `L10n`: 현지화(localization) 등등으로 변경 범위를 제한

## 주 버전(`!`)

> API나 플레이어 경험에 변경이 생겼을 때

- 새로운 `기능` 추가, 제거, 리워크
- `modthespire` 관련 변경

```
feat!: rework `Foo`

Foo now does bar
```
```
refactor!: change `ModId`
```

## 부 버전(`feat`)

> 기존 버전과 호환되면서 새로운 기능을 추가

- `기능` 수치를 변경 (공격력, 에너지 등등)
- `기능` 세부 효과 변경 (예: `소멸`을 버리기로 변경)
- 아트워크 추가, 변경
- 번역 추가

```
feat: decrease `Foo` energy cost
```

## 패치(`fix`)

> 기존 버전과 호환되면서 버그를 수정

- 기능 버그 수정
- 번역 수정

```
fix(L10n): missing `DESCRIPTION` on ENG/`Foo`
```

## 버전 관리 대상 아님

> 있어도 플레이어 경험에 영향이 없는 변경

- 다른 subject들

```
docs(README): add links
```
```
build(github): add gradle dependency
```
