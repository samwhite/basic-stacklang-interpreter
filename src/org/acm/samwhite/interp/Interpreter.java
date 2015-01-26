package org.acm.samwhite.interp;

import java.io.File;

public class Interpreter {

    public static void main(String[] args) {
        File inputfile = new File("");

        if(args.length == 1 || args.length == 2){
                inputfile = new File(args[0]);
        } else {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }

        Execution e = new Execution(inputfile);
        e.run();

    }
}
