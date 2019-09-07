package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EquipFilterAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrGwynHeroOfAshenvale extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("an equipped creature you control");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterControlledCreaturePermanent filter3
            = new FilterControlledCreaturePermanent(SubType.KNIGHT, "Knight");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public SyrGwynHeroOfAshenvale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever an equipped creature you control attacks, you draw a card and you lose 1 life.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card and"), false, filter
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability);

        // Equipment you control have equip Knight {0}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new EquipFilterAbility(filter3, new GenericManaCost(0)), Duration.WhileOnBattlefield, filter2
        )));
    }

    private SyrGwynHeroOfAshenvale(final SyrGwynHeroOfAshenvale card) {
        super(card);
    }

    @Override
    public SyrGwynHeroOfAshenvale copy() {
        return new SyrGwynHeroOfAshenvale(this);
    }
}