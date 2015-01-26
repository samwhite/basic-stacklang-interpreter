package org.acm.samwhite.interp;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


public class Execution {
    private int pc;
    private ArrayList<Instruction> instructions;
    private Deque<Integer> stack;

    public Execution(File f){
        this.instructions = new ArrayList<Instruction>();
        this.stack = new ArrayDeque<Integer>();
        this.pc = 0;
        //load instructions from file
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((line = br.readLine()) != null) {
                // process the line
                String[] split = line.split("\\s+");
                String instruction = split[0];
                int[] arguments = new int[Instruction.MAX_ARGS];
                for(int i = 1; i < split.length; i++){
                    arguments[i-1] = Integer.valueOf(split[i]);
                }
                this.instructions.add(new Instruction(instruction, arguments));
            }
            br.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Cannot read file (" + f.getName() + ").");
            System.exit(1);
        }

    }

    public void run(){
        //execute instructions
        while(true){
            executeInstruction(instructions.get(pc));
            if(pc == instructions.size()){
                break;
            }
        }
    }

    private void executeInstruction(Instruction i){
        String instruction = i.getInstruction();
        if(instruction.equals("INT")){
            //add int to stack
            stack.push(i.getArg(0));
            pc++;
        } else if(instruction.equals("ADD")){
            //pop last 2 elements, add together, push result
            int x = 0;
            x += stack.pop();
            x += stack.pop();
            stack.push(x);
            pc++;
        } else if(instruction.equals("SUB")){
            //pop last 2 elements, substract, push result
            int e1 = stack.pop();
            int e2 = stack.pop();
            stack.push(e2 - e1);
            pc++;
        } else if(instruction.equals("PRINT")){
            //peek, print
            System.out.println(stack.peek().toString());
            pc++;
        } else if(instruction.equals("JGE")){
            //if peek is >= 0, jump to x, else continue
            if(stack.peek() >= 0){
                pc = i.getArg(0);
            } else {
                pc++;
            }
        }

    }

}
