package com.kodonho.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Point {
    Paint wall, word;
    float unit;
    int point;
    int top,left;

    String[][] map = {
            {"점","수","판"},
            {"9","0","9"},
            {"9","9","9"}
    };
    public Point(float unit, int top, int left){
        this.unit = unit;
        this.point = 0;
        this.top = top;
        this.left = left;

        wall = new Paint();
        wall.setStyle(Paint.Style.STROKE);
        wall.setStrokeWidth(3);

        word = new Paint();
        word.setColor(Color.RED);
        word.setTextSize(36);
    }

    public void onDraw(Canvas canvas){
        for(int y=0; y<map.length; y++){
            for(int x=0; x<map[0].length; x++){
                String current = map[y][x];
                canvas.drawRect(
                        (left+x) * unit,
                        (top+y) * unit,
                        (left+x) * unit + unit,
                        (top+y) * unit + unit,
                        wall
                );
                if(!current.equals("9"))
                canvas.drawText(
                        current,
                        (left+x) * unit + 0.3f*unit,
                        (top+y) * unit + 0.8f*unit,
                        word
                );

            }
        }
    }
}
