package org.acm.samwhite.interp;


public class Instruction {

    private String instruction;
    private String arg;

    public Instruction(String instruction, String arg){
        this.instruction = instruction;
        this.arg = arg;
    }

    public String getInstruction() {
        return instruction;
    }

    public boolean argIsInteger(){
        return arg.matches("\\d+");
    }

    public String getArgAsString(){
        return arg;
    }

    public Integer getArgAsInteger(){
        return Integer.valueOf(arg);
    }

}
