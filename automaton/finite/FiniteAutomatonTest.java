package com.drake.automaton.finite;


/**
 * Created by Indraga on 5/26/2016.
 */

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FiniteAutomatonTest {

    @Test
    public void testA() {
        FiniteState[] z = new FiniteState[3];
        z[0] = new FiniteState("z0");
        z[1] = new FiniteState("z1");
        z[2] = new FiniteState("z2");

        z[0].setFinal();

        z[0].addTransition(z[1], 'p');
        z[0].addTransition(z[2], 'v');

        z[1].addTransition(z[2], 't');

        z[2].addTransition(z[0], 't');
        z[2].addTransition(z[2], 'v');

        FiniteAutomaton automat = new FiniteAutomaton(z[0]);

        assertTrue(automat.testWord("ptvtvvt").isValid());

        System.out.println(automat.testWord("ptvtvvt").toString());

        assertFalse(automat.testWord("pptt").isValid());
        System.out.println(automat.testWord("pptt").toString());
    }

    @Test
    public void testB() {
        FiniteState q0 = new FiniteState("q0");
        FiniteState q1 = new FiniteState("q1");
        FiniteState q2 = new FiniteState("q2");
        FiniteState q3 = new FiniteState("q3");
        FiniteState q4 = new FiniteState("q4");

        q1.setFinal();
        q2.setFinal();

        q0.addTransition(q4, 't', 'p');
        q0.addTransition(q3, 'v');
        q1.addTransition(q2, 't');
        q1.addTransition(q1, 'v');
        q2.addTransition(q3, 't');
        q3.addTransition(q2, 'p');
        q4.addTransition(q1, 'p');
        q4.addTransition(q4, 'v');

        FiniteAutomaton automat = new FiniteAutomaton(q0);

        assertTrue(automat.testWord("vptptptp").isValid());
        System.out.println(automat.testWord("vptptptp").toString());
        assertFalse(automat.testWord("vpp").isValid());
        System.out.println(automat.testWord("vpp").toString());
    }

    @Test
    public void testC() {
		/*
		 * NFA
		 */
        FiniteState q0 = new FiniteState("q0");
        FiniteState q1 = new FiniteState("q1");

        q1.setFinal();

        q0.addTransition(q0, '0');
        q0.addTransition(q1, '0', '1');
        q1.addTransition(q1, '1');
        q1.addTransition(q0, '1');

        FiniteAutomaton automat = new FiniteAutomaton(q0);

        assertTrue(automat.testWord("011000").isValid());
        assertTrue(automat.testWord("0110010").isValid());
    }
}
