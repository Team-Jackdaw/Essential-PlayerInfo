package com.wdrshadow.essentialinfo.module.message;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MessageParser extends Parser {

    @Override
    public HashMap<String, Object> parse(String message) {
        HashMap<String, Object> parsingResult = super.parse(message);
        parsingResult.put("broadcastTag", null);
        parsingResult.put("content", null);
        allowBroadcasting(message, parsingResult);
        return parsingResult;
    }

    private void allowBroadcasting(String message, HashMap<String, Object> parsingResult) {
        Pattern broadcastTagPattern = Pattern.compile("(\\s*#?\\s*)(.+)");
        Matcher broadcastTagMatcher = broadcastTagPattern.matcher(message);
        if (broadcastTagMatcher.matches()) {
            System.out.println(broadcastTagMatcher.groupCount());
            parsingResult.replace("broadcastTag", !broadcastTagMatcher.group(1).isBlank());
            parsingResult.replace("content", broadcastTagMatcher.group(broadcastTagMatcher.groupCount()));
        }
    }
}
