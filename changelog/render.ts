import { Commit, Sections } from "./mod.ts"

export type ChangelogRenderer = (option: {
  version: string
  date: string
  sections: Sections
}) => string

export type SectionFormatter = (tup: [section: string, commits: Commit[]]) => string
export type SectionsFormatter = { fmtSection: SectionFormatter; sections: Sections }

export const renderSections = (
  { fmtSection, sections }: SectionsFormatter,
) =>
  Object.entries(sections)
    .filter(([, commits]) => commits.length > 0)
    .map(fmtSection)
    .join("\n\n")
