package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author ddzn
 */
public final class LeyLine extends CardImpl {
	
	public LeyLine(UUID ownerId, CardSetInfo setInfo) {
		super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
		
	}
}


class LeyLineEffect extends OneShotEffect {

    public LeyLineEffect() {
        super(Outcome.UnboostCreature);
        staticText = "that creature's controller may have target creature of their choice get -3/-3 until end of turn.";
    }

    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getTargetController());
        if (player != null) {
            if (player.chooseUse(outcome, "Put a +1/+1 counter on target creature ?", source, game)) {
                game.addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }

    public LeyLineEffect(final LeyLineEffect effect) {
        super(effect);
    }

    public LeyLineEffect copy() {
        return new LeyLineEffect(this);
    }
}


/**
 * @author LoneFox
 */
public final class DeathMatch extends CardImpl {

    public DeathMatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever a creature enters the battlefield, that creature's controller may have target creature of their choice get -3/-3 until end of turn.
        // NOTE: The ability being optional is implemented in the subclass to give the choice to correct player.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new DeathMatchEffect(),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, SetTargetPointer.PLAYER, "");
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(DeathMatchAdjuster.instance);
        this.addAbility(ability);
    }

    public DeathMatch(final DeathMatch card) {
        super(card);
    }

    @Override
    public DeathMatch copy() {
        return new DeathMatch(this);
    }
}

enum DeathMatchAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID controllerId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        if (controllerId != null) {
            ability.getTargets().get(0).setTargetController(controllerId);
            ability.getEffects().get(0).setTargetPointer(new FirstTargetPointer());
        }
    }
}

class DeathMatchEffect extends OneShotEffect {

    public DeathMatchEffect() {
        super(Outcome.UnboostCreature);
        staticText = "that creature's controller may have target creature of their choice get -3/-3 until end of turn.";
    }

    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getTargetController());
        if (player != null) {
            if (player.chooseUse(outcome, "Give targeted creature -3/-3 ?", source, game)) {
                game.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), source);
            }
            return true;
        }
        return false;
    }

    public DeathMatchEffect(final DeathMatchEffect effect) {
        super(effect);
    }

    public DeathMatchEffect copy() {
        return new DeathMatchEffect(this);
    }
}
