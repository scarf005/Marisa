package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.Circlet
import com.megacrit.cardcrawl.relics.RedCirclet
import marisa.MarisaContinued
import marisa.action.RandomDamageAction
import marisa.patches.AbstractCardEnum

class CollectingQuirk : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.ALL_ENEMY
) {
    init {
        baseDamage = ATK_DMG
        baseMagicNumber = DIVIDER
        magicNumber = baseMagicNumber
        baseBlock = 0
        block = baseBlock
    }

    private val counter: Int
        get() {
            val p = AbstractDungeon.player
            var divider = DIVIDER
            if (upgraded) {
                divider = UPG_DIVIDER
            }
            var counter = p.relics.size
            p.getRelic(Circlet.ID)?.let { counter += it.counter - 1 }
            p.getRelic(RedCirclet.ID)?.let { counter += it.counter - 1 }

            counter /= divider
            return counter
        }

    override fun applyPowers() {
        super.applyPowers()
        modifyBlock()
        rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0]
        initializeDescription()
        MarisaContinued.logger.info(
            """CollectingQuirk : applyPowers : damage :$damage ; counter : ${counter}; block :$block ; magic number :$magicNumber"""
        )
    }

    override fun calculateCardDamage(unused: AbstractMonster?) {
        modifyBlock()
        rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0]
        initializeDescription()
        MarisaContinued.logger.info(
            """CollectingQuirk : applyPowers : damage :$damage ; 
                |counter : $counter ; 
                |block :$block ; 
                |magic number :$magicNumber""".trimMargin()
        )
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(RandomDamageAction(counter) { damage })
    }

    override fun onMoveToDiscard() {
        rawDescription = DESCRIPTION
        initializeDescription()
    }

    override fun makeCopy(): AbstractCard = CollectingQuirk()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        baseMagicNumber = UPG_DIVIDER
        magicNumber = baseMagicNumber
        upgradedMagicNumber = true
    }

    private fun modifyBlock() {
        if (counter > 0) {
            isBlockModified = true
            baseBlock = counter
            block = baseBlock
        } else {
            isBlockModified = false
            baseBlock = 0
            block = baseBlock
        }
    }

    companion object {
        const val ID = "CollectingQuirk"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private val EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION
        const val IMG_PATH = "img/cards/collec.png"
        private const val COST = 2
        private const val DIVIDER = 4
        private const val UPG_DIVIDER = 3
        private const val ATK_DMG = 9
    }
}
