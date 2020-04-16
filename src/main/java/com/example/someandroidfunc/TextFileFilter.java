package com.example.someandroidfunc;

import java.io.File;
import java.io.FilenameFilter;

public class TextFileFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        if(name.endsWith(".txt")){
            return true;
        }
        return false;
    }
}
