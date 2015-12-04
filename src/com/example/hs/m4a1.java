package com.example.hs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class M4a1 extends View implements OnTouchListener {

	private Drawable image;
	private MediaPlayer player;
	
	public M4a1(Context context) {
		super(context);
		
		image = getResources().getDrawable(R.drawable.m4a1);
		player = MediaPlayer.create(context, R.raw.m4a1_single);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int left, top, right, bottom;
		
		left = canvas.getWidth()/2 - 150;
		top = canvas.getHeight() - 168;
		right = canvas.getWidth()/2 + 150;
		bottom = canvas.getHeight();
		
		image.setBounds(left, top, right, bottom);
		image.draw(canvas);
	}

	public void shoot(){
		player.start();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		player.start();
		return false;
	}
}
