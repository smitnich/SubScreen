package com.subscreen.Subtitles;

import android.widget.TextView;

import com.subscreen.TextBlock;
import com.subscreen.FrameBlock;
import com.subscreen.UnicodeReader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nick on 12/28/2014.
 */
public class SUBFormat implements SubtitleFormat {
    public TextView writeTo;

    public ArrayList<TextBlock> readFile(String path) {
        ArrayList<TextBlock> blocks = new ArrayList<>();
        UnicodeReader br = new UnicodeReader(path);
        readLines(br, blocks);
        return blocks;
    }
    void readLines(UnicodeReader in, ArrayList<TextBlock> blocks) {
        String[] replace = {"|"};
        String[] replaceWith = {"\n"};
        //Pattern p = Pattern.compile("\\[(\\d*)\\]\\[(\\d*)\\]");
        Pattern p = Pattern.compile("\\{(\\d*)\\}\\{(\\d*)\\}(.*)");
        String buffer;
        Matcher m;
        String text;
        long startFrame, endFrame;
        try {
            while (in.available() > 0) {
                buffer = new String(in.readLine());
                m = p.matcher(buffer);
                if (m.find())
                {
                    startFrame = Integer.parseInt(m.group(1));
                    endFrame = Integer.parseInt(m.group(2));
                    text = m.group(3);
                    for (int i = 0; i < replace.length; i++)
                        text = text.replace(replace[i],replaceWith[i]);
                    blocks.add(new FrameBlock(text,startFrame,endFrame));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
