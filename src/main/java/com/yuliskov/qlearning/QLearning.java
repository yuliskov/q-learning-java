package com.yuliskov.qlearning;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class QLearning {
    private final double learningRate = 0.8;
    private final double discountFactor = 0.9;

    private int stateCount; // obtained from r_matrix.txt

    private int[][] R;       // Reward lookup
    private double[][] Q;    // Q learning


    public static void main(String args[]) {
        QLearning ql = new QLearning();

        ql.init();
        ql.calculateQ();
        ql.printQ();
        ql.printOptimalPaths();
        //ql.printPolicy();
    }

    public void init() {
        File file = new File("resources/r_matrix.txt");
        
        initializeStateCount(file);
        initializeR(file);
        initializeQ();
        printR(R);
    }

    private void initializeStateCount(File file) {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            String nextLine = scanner.nextLine();

            Scanner rowScanner = new Scanner(nextLine);
            while (rowScanner.hasNextInt()) {
                rowScanner.nextInt();
                stateCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeR(File file) {
        R = new int[stateCount][stateCount];

        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            int rowIdx = 0;

            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();

                Scanner rowScanner = new Scanner(nextLine);
                
                int colIdx = 0;

                while (rowScanner.hasNextInt()) {
                    R[rowIdx][colIdx] = rowScanner.nextInt();
                    colIdx++;
                }

                rowIdx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Set Q values to R values
    void initializeQ() {
        Q = new double[stateCount][stateCount];

        for (int i = 0; i < stateCount; i++){
            for(int j = 0; j < stateCount; j++){
                Q[i][j] = (double)R[i][j] == -1 ? 0 : (double)R[i][j];
            }
        }
    }
    // Used for debug
    void printR(int[][] matrix) {
        System.out.println("\nR matrix");
        System.out.printf("%25s", "States: ");
        for (int i = 0; i < stateCount; i++) {
            System.out.printf("%4s", i);
        }
        System.out.println();

        for (int i = 0; i < stateCount; i++) {
            System.out.print("Possible states from " + i + " :[");
            for (int j = 0; j < stateCount; j++) {
                System.out.printf("%4s", matrix[i][j]);
            }
            System.out.println("]");
        }
    }

    void calculateQ() {
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) { // Train cycles
            // Select random initial state
            int crtState = rand.nextInt(stateCount);

            while (!isFinalState(crtState)) {
                int[] actionsFromCurrentState = possibleActionsFromState(crtState);

                // Pick a random action from the ones possible
                int index = rand.nextInt(actionsFromCurrentState.length);
                int nextState = actionsFromCurrentState[index];

                // Q(state,action)= Q(state,action) + learningRate * (R(state,action) + discountFactor * Max(next state, all actions) - Q(state,action))
                double q = Q[crtState][nextState];
                double maxQ = maxQ(nextState);
                int r = R[crtState][nextState];

                double value = q + learningRate * (r + discountFactor * maxQ - q);
                Q[crtState][nextState] = value;

                crtState = nextState;
            }
        }
    }

    /**
     * NOTE: may be incorrect
     */
    void calculateQAlt() {
        Random rand = new Random();

        for (int i = 0; i < stateCount; i++) { // Train cycles
            // Select random initial state
            int crtState = rand.nextInt(stateCount);

            int nextState = crtState;
            while (!isFinalState(nextState)) {
                int[] actionsFromCurrentState = possibleActionsFromState(nextState);

                // Pick a random action from the ones possible
                int index = rand.nextInt(actionsFromCurrentState.length);
                nextState = actionsFromCurrentState[index];

                double q = Q[crtState][nextState];
                double maxQ = maxQ(nextState);
                int r = R[crtState][nextState];

                double value = q + learningRate * (maxQ - q);
                Q[crtState][nextState] = value;

                crtState = nextState;
            }
        }
    }

    boolean isFinalState(int state) {
        return R[state][state] == 100;
    }

    int[] possibleActionsFromState(int state) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < stateCount; i++) {
            if (R[state][i] != -1) {
                result.add(i);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    double maxQ(int nextState) {
        int[] actionsFromState = possibleActionsFromState(nextState);
        //the learning rate and eagerness will keep the W value above the lowest reward
        double maxValue = 0;
        for (int nextAction : actionsFromState) {
            double value = Q[nextState][nextAction];

            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
    }

    void printOptimalPaths() {
        System.out.print("\nOptimal paths");
        for (int i = 0; i < stateCount; i++) {
            System.out.print("\nFrom state " + i + ": ");
            int state = i;
            System.out.print(state);
            while (!isFinalState(state)) {
                state = getPolicyFromState(state);

                System.out.print("-" + state);
            }
        }
        System.out.println();
    }

    void printPolicy() {
        System.out.println("\nPrint policy");
        for (int i = 0; i < stateCount; i++) {
            System.out.println("From state " + i + " goto state " + getPolicyFromState(i));
        }
    }

    int getPolicyFromState(int state) {
        int[] actionsFromState = possibleActionsFromState(state);

        double maxValue = Double.MIN_VALUE;
        int policyGotoState = state;

        // Pick to move to the state that has the maximum Q value
        for (int nextState : actionsFromState) {
            double value = Q[state][nextState];

            if (value > maxValue) {
                maxValue = value;
                policyGotoState = nextState;
            }
        }
        return policyGotoState;
    }

    void printQ() {
        System.out.println("\nQ matrix");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("From state " + i + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.printf("%6.2f ", (Q[i][j]));
            }
            System.out.println();
        }
    }
}
