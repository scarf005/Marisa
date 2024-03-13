import { outdent } from "$dax/src/deps.ts"
import { Commit } from "./mod.ts"
import { ChangelogRenderer, renderSections, SectionFormatter } from "./render.ts"

export const fmtCommit = (x: Commit) =>
  `- ${x.subject}`
    .replace(/`([^`]+)`/g, "[$1]")
    .replace(/\(#(\d+)\)/g, "")
    .trim()

const fmtSection: SectionFormatter = ([section, commits]) =>
  `* ${section}\n\n` + commits.map(fmtCommit).join("\n")

export const renderStS: ChangelogRenderer = ({ version, date, sections }) =>
  outdent`
    What's new in ${version} (${date})

    ${renderSections({ fmtSection, sections })}

    please visit https://github.com/scarf005/Marisa/releases/latest for more info
  `
