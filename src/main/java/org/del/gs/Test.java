package org.del.gs;

import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Properties p = new Properties();
        p.setProperty("pirate", "scurvy");
        String s = p.getProperty("argProp") + " ";
        s += p.getProperty("pirate");
        System.out.println(s);
    }
}
