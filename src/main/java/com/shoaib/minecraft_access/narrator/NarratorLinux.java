package com.shoaib.minecraft_access.narrator;

import com.sun.jna.Library;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class NarratorLinux {

    public interface IDLL extends Library {
        // https://github.com/vladimirvivien/go-cshared-examples
        public class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {
            }

            public String p;
            public long n;

            @SuppressWarnings({ "unchecked", "rawtypes" })
            protected List getFieldOrder() {
                return Arrays.asList("p", "n");
            }
        }

        int Initialize();

        int Speak(GoString.ByValue text, boolean interrupt);

        int Close();
    }
}
