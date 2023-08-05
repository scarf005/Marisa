import { z } from "https://deno.land/x/zod@v3.20.5/mod.ts"

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
