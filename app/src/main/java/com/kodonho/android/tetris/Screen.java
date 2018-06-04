package com.kodonho.android.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Screen extends View {
    Stage stage;
    Preview preview;
    Point point;

    public Screen(Context context, Stage stage, Preview preview, Point point) {
        super(context);
        this.stage = stage;
        this.preview = preview;
        this.point = point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        stage.onDraw(canvas);
        preview.onDraw(canvas);
        point.onDraw(canvas);
    }
}
