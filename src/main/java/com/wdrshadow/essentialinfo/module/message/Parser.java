package com.wdrshadow.essentialinfo.module.message;

import java.util.HashMap;

public abstract class Parser {

    private static volatile Parser singletonParser;

    protected Parser(){
    }

    public static Parser getParser() {
        Parser parser = singletonParser;
        if (parser != null)
        {
            return parser;
        }
        synchronized (MessageParser.class){
            if (parser == null){
                singletonParser = new MessageParser();
            }
            return singletonParser;
        }
    }

    public HashMap<String, Object> parse(String message){
        return new HashMap<>(4);
    }

}
