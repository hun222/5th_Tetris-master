package com.kodonho.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Preview {
    float unit;
    int top, left;
    Paint grid;
    Block block;
    int preview_map[][] = {
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
    };

    public Preview(float unit, int top, int left){
        this.unit = unit;
        this.top = top;
        this.left = left;
        grid = new Paint();
        grid.setColor(Color.parseColor("#FFEEEEEE"));
        grid.setStyle(Paint.Style.STROKE);
        grid.setStrokeWidth(3);
    }

    public void onDraw(Canvas canvas) {

        block.onDraw(canvas, left);

        // 프리뷰 그리기
        for(int y=0; y<preview_map.length; y++){
            for(int x=0; x<preview_map[0].length; x++){
                int current = preview_map[y][x];
                // allways draw stroke on the rectangles
                canvas.drawRect(
                        (left+x) * unit,
                        (top+y) * unit,
                        (left+x) * unit + unit,
                        (top+y) * unit + unit,
                        grid
                );
            }
        }

    }

    public void setBlock(Block block) {
        this.block = block;
        block.setUnit(unit);
    }

    public Block getBlock() {
        return block;
    }
}
