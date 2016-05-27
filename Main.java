package com.drake;

import com.drake.automaton.finite.FiniteAutomaton;
import com.drake.automaton.finite.FiniteState;
import com.drake.automaton.pushdown.PushdownAutomaton;
import com.drake.automaton.pushdown.PushdownAutomatonTest;
import com.drake.automaton.pushdown.PushdownState;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        if(type.toLowerCase().equals("fsa")){
            FSA();
        }else if(type.toLowerCase().equals("pda")){
            PDA();
        }else{
            System.out.println("ERROR");
        }
    }

    static void FSA(){
        ArrayList<String> test = new ArrayList<String>();
        //System.out.println("fsa");
        int jumlahState = 0;
        String start = null;
        //Scanner scanner = new Scanner(System.in);
        FiniteState[] z;
        FiniteAutomaton automat = null;
        String inputz = "";
        if(scanner.hasNextLine()){
            inputz = scanner.nextLine();
        }
        String delims = "[ ]+";
        String[] tokens = inputz.split(delims);
        String command = tokens[0];
        if(command.toLowerCase().equals("n") && jumlahState==0){
            jumlahState = Integer.parseInt(tokens[1]);
            //System.out.println();
            //System.out.println(jumlahState);
        }else{
            System.out.println("error");
        }

        z = new FiniteState[jumlahState];
        String namaState = "";
        if(scanner.hasNextLine()){
            namaState = scanner.nextLine();
        }
        String[] nS = namaState.split(delims);

        for(int i = 0; i < nS.length; i++){
            z[i] = new FiniteState(nS[i]);
        }
        /*for(int i = 0; i < jumlahState; i++){
            System.out.println(z[i].getName());
        }*/

        while(!command.toLowerCase().equals("end")){
            String inputan = scanner.nextLine();
            String[] tokenz = inputan.split(delims);
            command = tokenz[0];
            if(command.toLowerCase().equals(("final"))){
                String fin = tokenz[1];
                for(int i=0;i<jumlahState;i++){
                    if(z[i].getName().equals(fin)){
                        z[i].setFinal();
                        //System.out.println(z[i].getName());
                    }
                }
            }else if(command.toLowerCase().equals("transisi")){
                if(tokenz.length==4){
                    String StateAwal = tokenz[1];
                    char input = tokenz[2].charAt(0);
                    String StateAkhir = tokenz[3];
                    for(int i=0;i<jumlahState;i++){
                        if(z[i].getName().equals(StateAwal)){
                            for(int j=0;j<jumlahState;j++){
                                if(z[j].getName().equals(StateAkhir)){
                                    z[i].addTransition(z[j],input);
                                    //System.out.println(z[i].getName() + "," + z[j].getName() + "," + input);
                                }
                            }
                        }
                    }
                }else if(tokenz.length==5){
                    String StateAwal = tokenz[1];
                    char input1 = tokenz[2].charAt(0);
                    char input2 = tokenz[3].charAt(0);
                    String StateAkhir = tokenz[4];
                    for(int i=0;i<jumlahState;i++){
                        if(z[i].getName().equals(StateAwal)){
                            for(int j=0;j<jumlahState;j++){
                                if(z[j].getName().equals(StateAkhir)){
                                    z[i].addTransition(z[j],input1,input2);
                                    //System.out.println(z[i].getName() + "," + z[j].getName() + "," + input1 + "," + input2);
                                }
                            }
                        }
                    }
                }
            }else if(command.toLowerCase().equals("start")){
                start = tokenz[1];
            }else if(command.toLowerCase().equals("string")){
                test.add(tokenz[1]);
            }else{
                //System.out.println("error");
            }
        }

        for (int i=0;i<jumlahState;i++){
            if(z[i].getName().equals(start)){
                automat = new FiniteAutomaton(z[i]);
            }
        }

        for(String x:test){
            System.out.println(automat.testWord(x).toString());
            if(automat.testWord(x).isValid()){
                System.out.println("diterima");
            }else{
                System.out.println("ditolak");
            }
        }
    }

    static void PDA(){
        //System.out.println("pda");
        PushdownAutomaton pda = null;
        int jumlahState = 0;
        String input = scanner.nextLine();
        String delims = "[ ]+";
        String[] tokens = input.split(delims);
        String command = tokens[0];
        String start = null, inputAlpha = null;
        ArrayList<String> test = new ArrayList<String>();
        if(command.toLowerCase().equals("n") && jumlahState==0){
            jumlahState = Integer.parseInt(tokens[1]);
            //System.out.println();
            //System.out.println(jumlahState);
        }else{
            System.out.println("error");
        }

        String inputStates = scanner.nextLine();
        String[] states = inputStates.split(delims);
        PushdownState[] ps = new PushdownState[jumlahState];
        for(int i=0;i<jumlahState;i++){
            ps[i] = new PushdownState(states[i]);
            //System.out.println(ps[i].getName());
        }

        while(!command.toLowerCase().equals("end")) {
            String inputan = scanner.nextLine();
            String[] tokenz = inputan.split(delims);
            command = tokenz[0];
            if (command.toLowerCase().equals(("final"))) {
                String fin = tokenz[1];
                for (int i = 0; i < jumlahState; i++) {
                    if (ps[i].getName().equals(fin)) {
                        ps[i].setFinal();
                        //System.out.println(ps[i].getName());
                    }
                }
            } else if (command.toLowerCase().equals("transisi")) {
                String stateAwal = tokenz[1];
                String read = tokenz[2];
                if(tokenz[2].equals("[]")){
                    read = "";
                }
                String top = tokenz[3];
                if(tokenz[3].equals("[]")){
                    top = "";
                }
                String push = tokenz[4];
                if(tokenz[4].equals("[]")){
                    push = "";
                }
                String stateAkhir = tokenz[5];
                for (int i = 0; i < jumlahState; i++) {
                    if (ps[i].getName().equals(stateAwal)) {
                        for (int j = 0; j < jumlahState; j++) {
                            if (ps[j].getName().equals(stateAkhir)) {
                                ps[i].addTransition(read, top, push, ps[j]);
                                //System.out.println(ps[i].getName() + "," + ps[j].getName());
                            }
                        }
                    }
                }
            } else if (command.toLowerCase().equals("start")) {
                start = tokenz[1];
            } else if (command.toLowerCase().equals("string")) {
                test.add(tokenz[1]);
            } else if (command.toLowerCase().equals("input")) {
                inputAlpha = tokenz[1];
            } else {
                //System.out.println("error");
            }
        }

        char[] iA = new char[inputAlpha.length()];
        for(int i=0;i<inputAlpha.length();i++){
            iA[i] = inputAlpha.charAt(i);
        }
        for (int i=0;i<jumlahState;i++){
            if(ps[i].getName().equals(start)){
                pda = new PushdownAutomaton(ps[i],iA);
            }
        }
        //System.out.println(pda.testWord(test).toString());
        for(String x : test){
            if(pda.testWord(x).isValid()){
                System.out.println("diterima");
            }else{
                System.out.println("ditolak");
            }
        }
    }
}
