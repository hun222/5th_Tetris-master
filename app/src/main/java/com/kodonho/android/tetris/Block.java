package com.kodonho.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {

    public static final int colors[] ={
            Color.RED, Color.GREEN, Color.BLUE, Color.CYAN,
            Color.MAGENTA, Color.YELLOW, Color.parseColor("#380B61")
    };

    float unit;
    int index; //block number(0~6)
    int x,y;
    int rotation;
    int rotaion_imit;
    int block[][][];
    int paint_index;
    Paint paint;

    public Block(int block[][][], int index){
        this.block = block;
        this.index = index;
        this.x = 0;
        this.y = 0;
        this.rotation = 0;  //회전상태 -> 그리기 전에 결정해야함
        this.rotaion_imit = block.length; //회전단위 몇개인지
        this.paint_index = index;
        paint = new Paint();
        paint.setColor(colors[index]);
    }
    public int[][] currentBlock(){
        return block[rotation];
    }

    public void rotate(){
        rotation++;
        if(rotation >= rotaion_imit)
            rotation = 0;
    }

    public void right(){
        x++;
    }

    public  void left(){
        x--;
    }

    public void down(){
        y++;
    }

    public void setUnit(float unit){
        this.unit = unit;
    }
    //stage
    public void onDraw(Canvas canvas){
        onDraw(canvas, 0);
    }
    //preview
    public void onDraw(Canvas canvas, float left){
        //draw block
        float tempX=0;
        for(int i=0; i<block[rotation].length; i++){
            for(int j=0; j<block[rotation].length; j++){
                int current = block[rotation][i][j];

                if(left>0)
                    tempX = left+j;
                else
                    tempX = j;

                if(current>0) {
                    canvas.drawRect(
                            (x + tempX) * unit,
                            (i+y) * unit,
                            (x + tempX) * unit + unit,
                            (i+y) * unit + unit,
                            paint
                    );
                }
            }
        }
    }
}
