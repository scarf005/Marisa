package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType

object LibraryTypeEnum {
    @SpireEnum
    var MARISA_COLOR: LibraryType? = null

    @SpireEnum
    var MARISA_DERIVATIONS: LibraryType? = null
}
