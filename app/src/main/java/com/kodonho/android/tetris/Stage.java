package com.kodonho.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Stage {
    float unit;
    int top, left;
    Paint grid, wall, temp;
    Paint red, green, blue, cyan, magenta, yellow, purple;
    Block block;
    Control control;

    int stage_map[][] = {
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,0,9},
            {9,9,9,9,9,9,9,9,9,9,9,9},
    };
    public Stage(float unit, int top, int left, Control control){
        this.unit = unit;
        this.top = top;
        this.left = left;
        this.control = control;

        grid = new Paint();
        grid.setColor(Color.parseColor("#FFEEEEEE"));
        grid.setStyle(Paint.Style.STROKE);
        grid.setStrokeWidth(3);

        wall = new Paint();
        wall.setColor(Color.BLACK);
        wall.setStyle(Paint.Style.FILL_AND_STROKE);

        red = new Paint(); red.setColor(Block.colors[0]);
        green = new Paint(); green.setColor(Block.colors[1]);
        blue = new Paint(); blue.setColor(Block.colors[2]);
        cyan = new Paint(); cyan.setColor(Block.colors[3]);
        magenta = new Paint(); magenta.setColor(Block.colors[4]);
        yellow = new Paint(); yellow.setColor(Block.colors[5]);
        purple = new Paint(); purple.setColor(Block.colors[6]);
    }
    // 호출되면 그림을 그린다.
    public void onDraw(Canvas canvas){
        //block draw
        block.onDraw(canvas);
        // 스테이지 그리기
        for(int y=0; y<stage_map.length; y++){
            for(int x=0; x<stage_map[0].length; x++){
                int current = stage_map[y][x];
                switch(current){
                    case 1:
                        temp = red;
                        break;
                    case 2:
                        temp = green;
                        break;
                    case 3:
                        temp = blue;
                        break;
                    case 4:
                        temp = cyan;
                        break;
                    case 5:
                        temp = magenta;
                        break;
                    case 6:
                        temp = yellow;
                        break;
                    case 7:
                        temp = purple;
                        break;
                    case 9:
                        temp = wall;
                        break;
                }
                if(current != 0) {
                    canvas.drawRect(
                            x * unit,
                            y * unit,
                            x * unit + unit,
                            y * unit + unit,
                            temp
                    );
                }
                // allways draw stroke on the rectangles
                canvas.drawRect(
                        x * unit,
                        y * unit,
                        x * unit + unit,
                        y * unit + unit,
                        grid
                );
            }
        }
    }

    public void setBlock(Block block) {
        this.block = block;
        this.block.x=4; //가운데 위치
        this.block.y=0;
        this.block.unit=unit;
    }

    public void rotate() {
        if(collisionCheck(0,0,1))
            block.rotate();
    }
    public void left() {
        if(collisionCheck(-1,0,0))
            block.left();
    }

    public void right(){
        if(collisionCheck(1,0,0))
            block.right();
    }

    public void down(){
        if(collisionCheck(0,1,0))
            block.down();
        else {
            pushBlockToStage();
            control.moveBlockToStage();
        }
    }

    private void pushBlockToStage() {
        for(int y=0; y<4; y++){
            for(int x=0; x<4; x++){
                if(block.currentBlock()[y][x]>0)
                    stage_map[y+block.y][x+block.x] = block.currentBlock()[y][x];
            }
        }
    }

    public boolean collisionCheck(int nextX, int nextY,int nextR){
        //스테이지에서 블럭이 다음에 이동할 곳의 값들을 꺼내서 담아둔다.
        int next[][] = new int[4][4];
        for(int y=0; y<4; y++){
            for(int x=0; x<4; x++){
                //stage 범위체크
                int target_x = x+block.x+nextX;
                int target_y = y+block.y+nextY;
                if(target_x > 0 && target_x < stage_map[0].length
                        && target_y>=0 && target_y<stage_map.length) {
                    //셀의 값이 둘다 0보다 큰 경우 충돌
                    if (stage_map[target_y][target_x] > 0 && block.currentBlock()[y][x] > 0)
                        return false;
                }
            }
        }
        return true;
    }

    public interface Control{
        public void moveBlockToStage();
    }
}
