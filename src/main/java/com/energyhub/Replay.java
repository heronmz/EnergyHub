package com.energyhub;

public class Replay{
    public static void main( String... args )  {
        if(args.length < 3){
            throw new IllegalArgumentException("I need 3 arguments");
        }
        System.out.println(new DataProcessor().process(args[0], args[1], args[2]));
    }
}
