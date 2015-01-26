package org.acm.samwhite.interp;


public class Instruction {
    public static final int MAX_ARGS = 4;

    private String instruction;
    private int[] args;

    public Instruction(String instruction, int[] args){
        this.instruction = instruction;
        if(args.length <= this.MAX_ARGS){
            this.args = args;
        } else {
            System.out.println("Instruction has too many arguments");
            System.exit(1);
        }
    }

    public String getInstruction() {
        return instruction;
    }

    public Integer getArg(int i){
        return Integer.valueOf(args[i]);
    }
}
