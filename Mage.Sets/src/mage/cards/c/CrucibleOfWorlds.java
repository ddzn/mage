package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class CrucibleOfWorlds extends CardImpl {

    public CrucibleOfWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You may play land cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayLandsFromGraveyardControllerEffect()));
    }

    public CrucibleOfWorlds(final CrucibleOfWorlds card) {
        super(card);
    }

    @Override
    public CrucibleOfWorlds copy() {
        return new CrucibleOfWorlds(this);
    }
}
