import { outdent } from "$dax/src/deps.ts"
import { Commit } from "./mod.ts"
import { ChangelogRenderer, renderSections, SectionFormatter } from "./render.ts"

const fmtCommit = (x: Commit) => `- ${x.subject} (#${x.pr})`

const fmtSection: SectionFormatter = ([section, commits]) =>
  `## ${section}\n\n` + commits.map(fmtCommit).join("\n")

export const renderMarkdown: ChangelogRenderer = ({ version, date, sections }) =>
  outdent`
    # ${version} (${date})

    ${renderSections({ fmtSection, sections })}
  `
