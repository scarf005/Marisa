import { z } from "$zod/mod.ts"

export const schema = z.object({
  "keywords": z.tuple([
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
    z.object({ "NAMES": z.array(z.string()), "DESCRIPTION": z.string() })
      .strict(),
  ]),
}).strict().required()
