package com.drake.automaton.pushdown;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Indraga on 5/27/2016.
 */
public class PushdownAutomatonTest {

    @Test
    public void test() {
        PushdownState q0 = new PushdownState("q0");

        PushdownState q1 = new PushdownState("q1");
        PushdownState q2 = new PushdownState("q2");
        q2.setFinal();

        q0.addTransition("a", "Z", "AZ", q0);
        q0.addTransition("b", "Z", "BZ", q0);
        q0.addTransition("a", "A", "AA", q0);
        q0.addTransition("b", "A", "BA", q0);
        q0.addTransition("a", "B", "AB", q0);
        q0.addTransition("b", "B", "BB", q0);
        q0.addTransition("c", "Z", "AA", q1);
        q0.addTransition("c", "A", "A", q1);
        q0.addTransition("c", "B", "B", q1);

        q1.addTransition("a", "A", "", q1);
        q1.addTransition("b", "B", "", q1);
        q1.addTransition("", "Z", "Z", q2);

        PushdownAutomaton pda = new PushdownAutomaton(q0, 'a', 'b', 'c');

        //assertTrue(pda.testWord("abcba").isValid());
        /*pda.testWord("abcba").toString();
        if(pda.testWord("abcba").isValid()){
            System.out.println("valid");
        }else{
            System.out.println("not valid");
        }*/
        //assertTrue(pda.testWord("10101").isValid());
        //pda.testWord("abcba").toString();
        if(pda.testWord("ab").isValid()){
            System.out.println("valid");
        }else{
            System.out.println("not valid");
        }
        //pda.testWord("abcba").isValid();
        /*assertTrue(pda.testWord("1").isValid());
        assertTrue(pda.testWord("011").isValid());

        assertFalse(pda.testWord("0").isValid());
        assertFalse(pda.testWord("01").isValid());
        assertFalse(pda.testWord("10001").isValid());
        assertFalse(pda.testWord("1010101010101010").isValid());
        assertFalse(pda.testWord("10101010010101010").isValid());*/
    }
}
