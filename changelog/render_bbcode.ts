import { outdent } from "$dax/src/deps.ts"
import { Commit } from "./mod.ts"
import { ChangelogRenderer, renderSections, SectionFormatter } from "./render.ts"

const fmtCommit = (x: Commit) =>
  `    [*] ${x.subject} ([url=https://github.com/scarf005/marisa/pull/${x.pr}]#${x.pr}[/url])`
    .replace(/`([^`]+)`/g, "[i]$1[/i]")

const fmtSection: SectionFormatter = ([section, commits]) =>
  `[h2]${section}[/h2]\n` + "[list]\n" + commits.map(fmtCommit).join("\n") + "\n[/list]"

export const renderBBCode: ChangelogRenderer = ({ version, date, sections }) =>
  outdent`
    [h1]${version} (${date})[/h1]

    ${renderSections({ fmtSection, sections })}
  `
