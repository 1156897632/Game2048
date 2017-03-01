package com.example.game2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
	private int number = 0;
	TextView tv;

	public Card(Context context) {
		super(context);

		tv = new TextView(getContext());
		LayoutParams lp = new LayoutParams(-1, -1);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundColor(Color.WHITE);
		lp.setMargins(10, 10, 0, 0);
		addView(tv, lp);
		setNumber(0);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		if (number == 0) {
			tv.setText("");
		} else {
			tv.setText(number + "");
		}
	}

	public boolean isNumberSame(Card c) {
		return getNumber() == c.getNumber();
	}
}
