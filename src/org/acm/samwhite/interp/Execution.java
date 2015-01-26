package org.acm.samwhite.interp;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;


public class Execution {
    private int pc;
    private ArrayList<Instruction> instructions;
    private Deque<Integer> stack;
    private HashMap<String, Integer> labels = new HashMap<String, Integer>();

    public Execution(File f){
        this.instructions = new ArrayList<Instruction>();
        this.stack = new ArrayDeque<Integer>();
        this.pc = 0;
        //load instructions from file
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            int instructionNumber = 0;
            while ((line = br.readLine()) != null) {
                //check for label
                //labels will be of the format L1: PRINT (note space)
                if(line.contains(":")){
                    String label = line.substring(0, line.indexOf(':'));
                    labels.put(label, instructionNumber);
                    //now parse as normal
                    String s = line.substring(line.indexOf(':') + 2);
                    //check for arg
                    if(s.contains(" ")){
                        //argument
                        String[] split = s.split("\\s+");
                        String instruction = split[0];
                        String argument = split[1];
                        this.instructions.add(new Instruction(instruction, argument));
                    } else {
                        //no arg
                        this.instructions.add(new Instruction(s, null));
                    }
                } else {
                    //no label, parse normally
                    //check for arg
                    if(line.contains(" ")){
                        //argument
                        String[] split = line.split("\\s+");
                        String instruction = split[0];
                        String argument = split[1];
                        this.instructions.add(new Instruction(instruction, argument));
                    } else {
                        //no arg
                        this.instructions.add(new Instruction(line, null));
                    }
                }
                instructionNumber++;
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
            if((pc == instructions.size()) || pc == -1){
                //reached the end of the list, or EXIT called
                break;
            }
        }
    }

    private void executeInstruction(Instruction i){
        String instruction = i.getInstruction();
        if(instruction.equals("INT")){
            //add int to stack
            stack.push(i.getArgAsInteger());
            pc++;
        } else if(instruction.equals("ADD")){
            //pop last 2 elements, add together, push result
            stack.push(stack.pop() + stack.pop());
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
            //x may be a label
            if(stack.peek() >= 0){
                if(i.argIsInteger()){
                    pc = i.getArgAsInteger();
                } else {
                    //jump to label x
                    pc = labels.get(i.getArgAsString());
                }
            } else {
                pc++;
            }
        } else if(instruction.equals("JEQ")) {
            //if peek is == 0, jump to x, else continue
            //x may be a label
            if (stack.peek() == 0) {
                if (i.argIsInteger()) {
                    pc = i.getArgAsInteger();
                } else {
                    //jump to label x
                    pc = labels.get(i.getArgAsString());
                }
            } else {
                pc++;
            }
        } else if(instruction.equals("SWAP")){
            //swap two top-most elements of stack
            int e1 = stack.pop();
            int e2 = stack.pop();
            stack.push(e1);
            stack.push(e2);
            pc++;
        } else if(instruction.equals("CALL")){
            //push pc+1 to the top of stack, jump to x
            stack.push(pc + 1);
            pc = i.getArgAsInteger();
        } else if(instruction.equals("RET")){
            //pops top value, jumps to it
            pc = stack.pop();
            pc++;
        } else if(instruction.equals("EXIT")){
            //exit program; implemented here as setting pc to -1
            pc = -1;
        } else if(instruction.equals("DUP")){
            //duplicate top of stack
            stack.push(stack.peek());
            pc++;
        } else if(instruction.equals("POP")){
            //pop
            stack.pop();
            pc++;
        } else {
            //illegal instruction
            System.out.println("Illegal Instruction. Exiting.");
            System.exit(1);
        }

    }

}
