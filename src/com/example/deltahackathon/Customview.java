package com.example.deltahackathon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Customview extends View 
{
	String[][] data= new String[10][2];
	int[][] loc= new int[10][2];
	int flag=0;
	public Customview(Context context, String res[][],int locations[][]) {
		super(context);
		data=res;
		loc=locations;
	}

	public Customview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDraw(Canvas canvas) 
	{	 
		for(int i=0;i<10;i++)
		{
			Log.i("--------","In Here!");
			/*
			paint2.setColor(Color.parseColor(colr));*/
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);  
			Rect rect = new Rect();
			canvas.drawRect(rect, paint);
			Paint paint2=new Paint();
			paint2.setTextSize(40);
			paint2.setColor(Color.parseColor(data[i][1]));
			canvas.drawText(data[i][0], loc[i][0], loc[i][1], paint2);
		}
	}

}
