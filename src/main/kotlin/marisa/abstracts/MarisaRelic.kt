package marisa.abstracts

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.helpers.ImageMaster
import marisa.modPath
import marisa.texture

internal fun relicTexture(image: String) = texture("img/relics/$image.png".modPath(), ImageMaster::loadImage)

/** A relic drawn with `img/relics/[image].png` and its outline `img/relics/outline/[image].png`. */
abstract class MarisaRelic(id: String, image: String, tier: RelicTier, sound: LandingSound) :
    CustomRelic(id, relicTexture(image), relicTexture("outline/$image"), tier, sound)
