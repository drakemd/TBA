package com.drake.automaton.pushdown;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Indraga on 5/27/2016.
 */
public class PushdownAutomaton {
    public static final char START_CHARACTER = 'Z';

    private Stack<String> stack;
    private final List<String> alphabet;
    private final PushdownState initial;

    public PushdownAutomaton(PushdownState initial, char... alphabet) {
        this.alphabet = new LinkedList<String>();
        this.initial = initial;

        for (char character : alphabet) {
            this.alphabet.add(String.valueOf(character));
            //System.out.println(character);
        }
    }

    public Result testWord(String input) {
        boolean valid = true;
        this.stack = new Stack<String>();
        stack.push(String.valueOf(START_CHARACTER));

        PushdownState current = initial;

        System.out.println("test word [" + input + "]");

        if (!checkInput(input)) {
            System.out.println("not valid");
            valid = false;
        }

        for (int i = 0; i < input.length()+1 && valid; i++) {
            String read = "";
            if(i<input.length()){
                read = String.valueOf(input.charAt(i));
                String pop = stack.pop();

                System.out.print("im state [" + current.getName() + "] read: [" + read + "] top: [" + pop + "] \t");

                PushdownTransition transition = current.getNext(read, pop);

                if (transition == null) {
                    stack.push(pop);

                    System.out.println("not valid no transition");

                    valid = false;
                } else {
                    String push = transition.getPush();

                    if (!push.isEmpty()) {
                        for (int h = 0; h < push.length(); h++) {
                            stack.push(push.substring(push.length() - 1 - (h), push.length() - h));
                        }
                    }

                    current = transition.getNext();

                    System.out.println(current.getName() + ", " + stack + ", " + input.substring(i));
                }
            }else{
                String pop = stack.pop();

                if (current.getNext(read,pop) == null) {

                } else {
                    System.out.print("im state [" + current.getName() + "] read: [" + read + "] top: [" + pop + "] \t");

                    PushdownTransition transition = current.getNext(read, pop);
                    String push = transition.getPush();

                    if (!push.isEmpty()) {
                        for (int h = 0; h < push.length(); h++) {
                            stack.push(push.substring(push.length() - 1 - (h), push.length() - h));
                        }
                    }

                    current = transition.getNext();

                    System.out.println(current.getName() + ", " + stack + ", " + input.substring(i));
                }
            }
        }

        if (!current.isFinal() || stack.empty()) {
            valid = false;
        }

        return new Result(stack, input, valid);
    }

    private boolean checkInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            String read = String.valueOf(input.charAt(i));
            if (!alphabet.contains(read)) {
                return false;
            }
        }

        return true;
    }

    public class Result {
        private final Stack<String> stack;
        private final String input;
        private final boolean valid;

        public Result(Stack<String> stack, String input, boolean valid) {
            this.stack = new Stack<String>();

            this.stack.addAll(stack);
            this.input = input;
            this.valid = valid;
        }

        public Stack<String> getStack() {
            return stack;
        }

        public String getInput() {
            return input;
        }

        public boolean isValid() {
            return valid;
        }
    }
}
