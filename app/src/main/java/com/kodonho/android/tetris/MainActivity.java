package com.kodonho.android.tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Stage.Control{

    float displayWidth;
    FrameLayout layout;
    // 화면을 그리는 역할
    Screen screen;
    // 스테이지
    Stage stage;
    Preview preview;
    // 그리드 가로칸의 개수
    final float WIDTH_SIZE = 17;
    // 그리드의 크기 단위
    float unit;
    Button rotate, right, down, left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        displayWidth = metrics.widthPixels;
        unit = displayWidth/WIDTH_SIZE;

        layout = findViewById(R.id.layout);
        rotate = findViewById(R.id.btnRotate);
        right = findViewById(R.id.btnRight);
        left = findViewById(R.id.btnLeft);
        down = findViewById(R.id.btnDown);

        right.setOnClickListener(this);
        left.setOnClickListener(this);
        down.setOnClickListener(this);
        rotate.setOnClickListener(this);

        //스테이지와 프리뷰를 생성해서 스크린 클래스에 담아준다.
        stage = new Stage(unit, 0, 0, this);
        preview = new Preview(unit, 0, 13);
        screen = new Screen(this, stage, preview);
        layout.addView(screen);

        //block setting
        setNewBlock();
        //move preview block to stage
        moveBlockToStage();

        new Thread(){
            public void run(){
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stage.down();
                    screen.invalidate();
                }
            }
        }.start();
    }

    public void setNewBlock(){
        Block newBlock = BlockFactory.newBlock();
        preview.setBlock(newBlock);
    }

    @Override
    public void moveBlockToStage(){
        Block block = preview.getBlock();
        stage.setBlock(block);
        setNewBlock();
        screen.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRotate:
                stage.rotate();
                break;
            case R.id.btnLeft:
                stage.left();
                break;
            case R.id.btnRight:
                stage.right();
                break;
            case R.id.btnDown:
                stage.down();
                break;
        }
        screen.invalidate();
    }
}
