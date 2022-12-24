package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import marisa.MarisaContinued
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.WitchOfGreedGold
import marisa.powers.Marisa.WitchOfGreedPotion

class WitchOfGreed : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.POWER,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.SELF
) {
    init {
        magicNumber = STC
        baseMagicNumber = magicNumber
        tags.add(CardTags.HEALING)
    }

    /*
    public void use(AbstractPlayer p, AbstractMonster m) {

      if (ThMod.Amplified(this, AMP)) {
        addToBot(
            new ApplyPowerAction(
                p,
                p,
                new WitchOfGreedPotion(p, 1), 1)
        );
      }

      ThMod.logger.info("WitchOfGreed : Applying power : gold ;");

      addToBot(
          new ApplyPowerAction(
              p,
              p,
              new WitchOfGreedGold(p, this.magicNumber),
              this.magicNumber
          )
      );
    }
  */
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            AbstractDungeon.getCurrRoom().addGoldToRewards(magicNumber)
            addToBot(
                ApplyPowerAction(
                    p,
                    p,
                    WitchOfGreedGold(p, magicNumber),
                    magicNumber
                )
            )
            if (isAmplified(AMP)) {
                val po = AbstractDungeon.returnRandomPotion()
                AbstractDungeon.getCurrRoom().addPotionToRewards(po)
                addToBot(
                    ApplyPowerAction(
                        p,
                        p,
                        WitchOfGreedPotion(p, 1),
                        1
                    )
                )
                MarisaContinued.logger.info("WitchOfGreed : use : Amplified : adding :" + po.ID)
            }
        }
    }

    override fun makeCopy(): AbstractCard = WitchOfGreed()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
        }
    }

    companion object {
        const val ID = "WitchOfGreed"
        const val IMG_PATH = "img/cards/Greed.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val STC = 15
        private const val UPG_STC = 10
        private const val AMP = 1
    }
}
