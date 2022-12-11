package marisa.relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.cards.curses.Parasite
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect

class ShroomBag : CustomRelic(
    ID,
    Texture(IMG),
    Texture(IMG_OTL),
    RelicTier.COMMON,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun makeCopy(): AbstractRelic {
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasRelic("ShroomBag")) {
                return BigShroomBag()
            }
        }
        return ShroomBag()
    }

    override fun onEquip() {
        AbstractDungeon.effectList.add(
            ShowCardAndObtainEffect(
                Parasite(),
                Settings.WIDTH / 2.0f,
                Settings.HEIGHT / 2.0f
            )
        )
        AbstractDungeon.effectList.add(
            ShowCardAndObtainEffect(
                Parasite(),
                Settings.WIDTH / 2.0f,
                Settings.HEIGHT / 2.0f
            )
        )
    } /*
    @Override
    public void onDrawOrDiscard() {
    	ThMod.logger.info("ShroomBag : onDrawOrDiscard : replaceParasite");
    	replaceParasite();
    }

    @Override
    public void onRefreshHand() {
    	ThMod.logger.info("ShroomBag : onRefreshHand : replaceParasite");
    	replaceParasite();
    }

    @Override
    public void atTurnStartPostDraw() {
    	ThMod.logger.info("ShroomBag : atTurnStartPostDraw : replaceParasite");
    	replaceParasite();
    }

	private void replaceParasite() {
		ArrayList<AbstractCard> temp = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof Parasite) {
				temp.add(c);
			}
		}
		while (temp.size() > 0){
	    	ThMod.logger.info("ShroomBag : replaceParasite : Replacing");
			this.flash();
			AbstractCard c = temp.remove(0);
			AbstractDungeon.player.hand.removeCard(c);
			AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Parasite_MRS(), 1));
		}
	}
	*/

    companion object {
        const val ID = "ShroomBag"
        private const val IMG = "img/relics/ShroomBag.png"
        private const val IMG_OTL = "img/relics/outline/ShroomBag.png"
    }
}
