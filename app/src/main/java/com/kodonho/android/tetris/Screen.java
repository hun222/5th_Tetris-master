package com.kodonho.android.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Screen extends View {
    Stage stage;
    Preview preview;

    public Screen(Context context, Stage stage, Preview preview) {
        super(context);
        this.stage = stage;
        this.preview = preview;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        stage.onDraw(canvas);
        preview.onDraw(canvas);
    }
}
