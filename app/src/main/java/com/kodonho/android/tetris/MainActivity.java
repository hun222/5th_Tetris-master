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
    Point point;
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
        preview = new Preview(unit, 0, 13);
        point = new Point(unit,6,13);
        stage = new Stage(unit, 0, 0, this, point);
        screen = new Screen(this, stage, preview, point);
        layout.addView(screen);


        //block setting
        setNewBlock();
        //move preview block to stage
        moveBlockToStage();
       //동작시작
       runThread();


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
        screen.postInvalidate();
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

    boolean runFlag = true;
    public void runThread(){
        new Thread(new Runnable() {
            int time = 0;
            @Override
            public void run() {
                while(runFlag) {
                    try {
                        Thread.sleep(1000);
                        time++;
                        stage.down();
                        screen.postInvalidate();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(time==30){
                                    runFlag = false;
                                    Toast.makeText(getBaseContext(), "Game Over!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runFlag = false;
    }
}

