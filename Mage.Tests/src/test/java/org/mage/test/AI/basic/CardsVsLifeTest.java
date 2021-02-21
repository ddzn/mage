package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * @author ddzn
 */
public class CardsVsLifeTest extends CardTestPlayerBaseAI {

    @Test
    public void test_cardsvsdamage_PriorityDrawCards() {
        // Target player draws 2 cards and loses 2 life 
        addCard(Zone.HAND, playerA, "Sign in Blood");// {B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sign in Blood");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    @Test
    public void test_cardsvsdamage_PriorityKillOpponentLifeLoss() {
        // Target player draws 2 cards and loses 2 life 
        addCard(Zone.HAND, playerA, "Sign in Blood");// {B}{B}
        addCard(Zone.HAND, playerA, "Juggernaut", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        setLife(playerA, 20);
        setLife(playerB, 2);

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sign in Blood");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
        assertLife(playerB, 0);
    }

    @Test
    public void test_cardsvsdamage_PriorityDontKillYourself() {
        // Target player draws 2 cards and loses 2 life 
        addCard(Zone.HAND, playerA, "Sign in Blood");// {B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        setLife (playerA, 2);
        setLife (playerB, 20);

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sign in Blood");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 2);
    }

    @Test
    public void test_cardsvsdamage_PriorityKillOpponentTriggers() {
        // Expected: Use damage from triggers to kill opponent
        // Target player draws 2 cards and loses 2 life 
        addCard(Zone.HAND, playerA, "Sign in Blood");// {B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Whenever an opponent draws a card, ~this deals 1 damage to him
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Dreams", 2);
        setLife(playerA, 20);
        setLife (playerB, 6);

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sign in Blood");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
        assertLife(playerB, 0);
    }

    @Test
    public void test_cardsvsdamage_PriorityKillOpponentCombat() {
        // Expected: use damage to get enemy into lethal
        // Target player draws 2 cards and loses 2 life
        addCard(Zone.HAND, playerA, "Sign in Blood");// {B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Blackcleave Goblin", 2);// 2/1 haste
        setLife(playerA, 20);
        setLife (playerB, 6);

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sign in Blood");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerA, 20);
        assertLife(playerB, 0);
    }

}