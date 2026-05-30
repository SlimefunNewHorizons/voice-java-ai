package com.voicejavaai.phoneme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhonemeMapper {
    private static final Map<String, String[]> DICT = new HashMap<>();

    static {
        DICT.put("hola", new String[]{"o", "l", "a"});
        DICT.put("si", new String[]{"s", "i"});
        DICT.put("no", new String[]{"n", "o"});
        DICT.put("yo", new String[]{"i", "o"});
        DICT.put("tu", new String[]{"t", "u"});
        DICT.put("el", new String[]{"e", "l"});
        DICT.put("la", new String[]{"l", "a"});
        DICT.put("de", new String[]{"d", "e"});
        DICT.put("en", new String[]{"e", "n"});
        DICT.put("un", new String[]{"u", "n"});
        DICT.put("una", new String[]{"u", "n", "a"});
        DICT.put("que", new String[]{"k", "e"});
        DICT.put("como", new String[]{"k", "o", "m", "o"});
        DICT.put("donde", new String[]{"d", "o", "n", "d", "e"});
        DICT.put("quien", new String[]{"k", "i", "e", "n"});
        DICT.put("eres", new String[]{"e", "r", "e", "s"});
        DICT.put("soy", new String[]{"s", "o", "i"});
        DICT.put("aqui", new String[]{"a", "k", "i"});
        DICT.put("miedo", new String[]{"m", "i", "e", "d", "o"});
        DICT.put("ayuda", new String[]{"a", "y", "u", "d", "a"});
        DICT.put("corre", new String[]{"k", "o", "r", "e"});
        DICT.put("sal", new String[]{"s", "a", "l"});
        DICT.put("ven", new String[]{"b", "e", "n"});
        DICT.put("hello", new String[]{"e", "l", "o"});
        DICT.put("who", new String[]{"u"});
        DICT.put("are", new String[]{"a", "r"});
        DICT.put("you", new String[]{"i", "u"});
        DICT.put("what", new String[]{"u", "a", "t"});
        DICT.put("where", new String[]{"u", "e", "r"});
        DICT.put("why", new String[]{"u", "a", "i"});
        DICT.put("help", new String[]{"e", "l", "p"});
        DICT.put("run", new String[]{"r", "a", "n"});
        DICT.put("die", new String[]{"d", "a", "i"});
        DICT.put("kill", new String[]{"k", "i", "l"});
        DICT.put("friend", new String[]{"f", "r", "e", "n", "d"});
        DICT.put("enemy", new String[]{"e", "n", "e", "m", "i"});
        DICT.put("i", new String[]{"a", "i"});
        DICT.put("am", new String[]{"a", "m"});
        DICT.put("is", new String[]{"i", "s"});
        DICT.put("are", new String[]{"a", "r"});
        DICT.put("the", new String[]{"d", "e"});
        DICT.put("a", new String[]{"a"});
        DICT.put("an", new String[]{"a", "n"});
        DICT.put("my", new String[]{"m", "a", "i"});
        DICT.put("your", new String[]{"i", "u", "r"});
        DICT.put("name", new String[]{"n", "e", "i", "m"});
        DICT.put("is", new String[]{"i", "s"});
        DICT.put("not", new String[]{"n", "o", "t"});
        DICT.put("real", new String[]{"r", "e", "a", "l"});
        DICT.put("can", new String[]{"k", "a", "n"});
        DICT.put("you", new String[]{"i", "u"});
        DICT.put("see", new String[]{"s", "i"});
        DICT.put("me", new String[]{"m", "i"});
        DICT.put("do", new String[]{"d", "o"});
        DICT.put("it", new String[]{"i", "t"});
        DICT.put("yes", new String[]{"i", "e", "s"});
        DICT.put("no", new String[]{"n", "o"});
        DICT.put("ok", new String[]{"o", "k"});
        DICT.put("come", new String[]{"k", "o", "m"});
        DICT.put("back", new String[]{"b", "a", "k"});
        DICT.put("go", new String[]{"g", "o"});
        DICT.put("away", new String[]{"a", "u", "e"});
        DICT.put("stop", new String[]{"s", "t", "o", "p"});
        DICT.put("look", new String[]{"l", "u", "k"});
        DICT.put("behind", new String[]{"b", "i", "a", "i", "n", "d"});
        DICT.put("you", new String[]{"i", "u"});
    }

    public String[] textToPhonemes(String text) {
        List<String> result = new ArrayList<>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            if (word.isEmpty()) continue;
            String cleaned = word.replaceAll("[^a-z]", "");

            if (DICT.containsKey(cleaned)) {
                for (String p : DICT.get(cleaned)) {
                    result.add(p);
                }
            } else {
                for (int i = 0; i < cleaned.length(); i++) {
                    char c = cleaned.charAt(i);
                    if (i + 1 < cleaned.length()) {
                        String bi = cleaned.substring(i, i + 2);
                        if (DICT.containsKey(bi)) {
                            for (String p : DICT.get(bi)) result.add(p);
                            i++;
                            continue;
                        }
                    }
                    result.add(charToPhoneme(c));
                }
            }
            result.add("_pause");
        }
        return result.toArray(new String[0]);
    }

    private String charToPhoneme(char c) {
        switch (c) {
            case 'a': case 'e': case 'i': case 'o': case 'u': return String.valueOf(c);
            case 'b': case 'v': return "b";
            case 'c': return "k";
            case 'd': return "d";
            case 'f': return "f";
            case 'g': return "g";
            case 'h': return "_silence";
            case 'j': case 'x': return "x";
            case 'k': return "k";
            case 'l': return "l";
            case 'm': return "m";
            case 'n': case 'ñ': return "n";
            case 'p': return "p";
            case 'r': return "r";
            case 's': return "s";
            case 't': return "t";
            case 'y': return "i";
            case 'z': return "s";
            default: return "_silence";
        }
    }
}
