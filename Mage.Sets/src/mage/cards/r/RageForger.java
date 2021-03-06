
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class RageForger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other Shaman creature you control");
    private static final FilterControlledCreaturePermanent filterAttack = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        filter.add(SubType.SHAMAN.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filterAttack.add(CounterType.P1P1.getPredicate());
    }

    public RageForger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rage Forger enters the battlefield, put a +1/+1 counter on each other Shaman creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false));
        // Whenever a creature you control with a +1/+1 counter on it attacks, you may have that creature deal 1 damage to target player.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new RageForgerDamageEffect(), true, filterAttack, true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

    }

    public RageForger(final RageForger card) {
        super(card);
    }

    @Override
    public RageForger copy() {
        return new RageForger(this);
    }
}

class RageForgerDamageEffect extends OneShotEffect {

    public RageForgerDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may have that creature deal 1 damage to target player or planeswalker";
    }

    public RageForgerDamageEffect(final RageForgerDamageEffect effect) {
        super(effect);
    }

    @Override
    public RageForgerDamageEffect copy() {
        return new RageForgerDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent attackingCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && attackingCreature != null) {
            game.damagePlayerOrPlaneswalker(source.getFirstTarget(), 1, attackingCreature.getId(), source, game, false, true);
            return true;
        }
        return false;
    }
}
