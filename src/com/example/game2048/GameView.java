package com.example.game2048;

import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class GameView extends GridLayout {

	private Card[][] cardmap = new Card[4][4];

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGame();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGame();
	}

	public GameView(Context context) {
		super(context);
		initGame();
	}

	private void initGame() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);

		setOnTouchListener(new View.OnTouchListener() {

			float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = arg1.getX();
					startY = arg1.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = arg1.getX() - startX;
					offsetY = arg1.getY() - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX > 5) {
							// Toast.makeText(getContext(), "right",
							// Toast.LENGTH_SHORT).show();
							swipeRight();
							if (isClose()) {
								Toast.makeText(getContext(), "All Close",
										Toast.LENGTH_SHORT).show();
								StartGame();
							} 
						}
						if (offsetX < -5) {
							// Toast.makeText(getContext(), "left",
							// Toast.LENGTH_SHORT).show();
							swipeLeft();
							if (isClose()) {
								Toast.makeText(getContext(), "All Close",
										Toast.LENGTH_SHORT).show();
								StartGame();
							} 
						}
					} else {
						if (offsetY > 5) {
							// Toast.makeText(getContext(), "down",
							// Toast.LENGTH_SHORT).show();
							swipeDown();
							if (isClose()) {
								Toast.makeText(getContext(), "All Close",
										Toast.LENGTH_SHORT).show();
								StartGame();
							} 
						}
						if (offsetY < -5) {
							// Toast.makeText(getContext(), "up",
							// Toast.LENGTH_SHORT).show();
							swipeUp();
							if (isClose()) {
								Toast.makeText(getContext(), "All Close",
										Toast.LENGTH_SHORT).show();
								StartGame();
							} 
						}
					}

					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO 自动生成的方法存根
		super.onSizeChanged(w, h, oldw, oldh);

		int CardWidth = (Math.min(w, h) - 10) / 4;
		System.out.println("CardWidth:" + CardWidth);
		// int a = 4 * CardWidth + 30;

		// Params只能是RelativeLayout或Linearlayout？？？？
		// GridLayout.LayoutParams GridParams = new
		// com.example.game2048.GameView.LayoutParams();
		// System.out.println("获取params成功");
		// GridParams.height = a;
		// System.out.println("设置params长度成功");
		// setLayoutParams(GridParams);
		// System.out.println("绑定params成功");

		addCard(CardWidth);

		StartGame();
	}

	private void StartGame() {
		MainActivity.mainActivity.clearScore();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cardmap[i][j].setNumber(0);
			}
		}
		addRandomNumber();
		addRandomNumber();
		// test();
	}

	private void addCard(int CardWidth) {
		Card c;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				c = new Card(getContext());
				c.setNumber(0);
				addView(c, CardWidth, CardWidth);

				cardmap[i][j] = c;
			}
		}
	}

	private void addRandomNumber() {
		Vector<Card> list = new Vector<Card>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cardmap[i][j].getNumber() == 0) {
					list.add(cardmap[i][j]);
				}
			}
		}
		int a = (int) (Math.random() * list.size());
		list.get(a).setNumber(Math.random() > 0.1 ? 2 : 4);
	}

	private void test() {
		System.out.println("~~~~~~~~~~~~");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 1) {
					j--;
					break;
				}
				System.out.println("i:" + i + ";j:" + j);
			}
		}

	}

	private void swipeLeft() {
		boolean next = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int j1 = j + 1; j1 < 4; j1++) {
					if (cardmap[i][j1].getNumber() > 0) {
						if (cardmap[i][j].getNumber() == 0) {
							cardmap[i][j].setNumber(cardmap[i][j1].getNumber());
							cardmap[i][j1].setNumber(0);
							j--;
							next = true;
							break;
						} else if (cardmap[i][j].isNumberSame(cardmap[i][j1])) {
							cardmap[i][j]
									.setNumber(cardmap[i][j].getNumber() * 2);
							MainActivity.mainActivity.addScore(cardmap[i][j]
									.getNumber());
							cardmap[i][j1].setNumber(0);
							j--;
							next = true;
							break;
						}
						else{
							break;
						}
					}
				}
			}
		}
		if(next == true){
			addRandomNumber();
//			addRandomNumber();
		}
	}

	private void swipeRight() {
		boolean next = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 3; j > -1; j--) {
				for (int j1 = j - 1; j1 > -1; j1--) {
					if (cardmap[i][j1].getNumber() > 0) {
						if (cardmap[i][j].getNumber() == 0) {
							cardmap[i][j].setNumber(cardmap[i][j1].getNumber());
							cardmap[i][j1].setNumber(0);
							next = true;
							j++;
							break;
						} else if (cardmap[i][j].isNumberSame(cardmap[i][j1])) {
							cardmap[i][j]
									.setNumber(cardmap[i][j].getNumber() * 2);
							MainActivity.mainActivity.addScore(cardmap[i][j]
									.getNumber());
							cardmap[i][j1].setNumber(0);
							next = true;
							j++;
							break;
						}
						else{
							break;
						}
					}
				}
			}
		}
		if(next == true){
			addRandomNumber();
//			addRandomNumber();
		}
	}

	private void swipeUp() {
		boolean next = false;
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				for (int i1 = i + 1; i1 < 4; i1++) {
					if (cardmap[i1][j].getNumber() > 0) {
						if (cardmap[i][j].getNumber() == 0) {
							cardmap[i][j].setNumber(cardmap[i1][j].getNumber());
							cardmap[i1][j].setNumber(0);
							next = true;
							i--;
							break;
						} else if (cardmap[i][j].isNumberSame(cardmap[i1][j])) {
							cardmap[i][j]
									.setNumber(cardmap[i][j].getNumber() * 2);
							MainActivity.mainActivity.addScore(cardmap[i][j]
									.getNumber());
							cardmap[i1][j].setNumber(0);
							next = true;
							i--;
							break;
						}
						else{
							break;
						}
					}
				}
			}
		}
		if(next == true){
			addRandomNumber();
//			addRandomNumber();
		}
	}

	private void swipeDown() {
		boolean next = false;
		for (int j = 0; j < 4; j++) {
			for (int i = 3; i > -1; i--) {
				for (int i1 = i - 1; i1 > -1; i1--) {
					if (cardmap[i1][j].getNumber() > 0) {
						if (cardmap[i][j].getNumber() == 0) {
							cardmap[i][j].setNumber(cardmap[i1][j].getNumber());
							cardmap[i1][j].setNumber(0);
							next = true;
							i++;
							break;
						} else if (cardmap[i][j].isNumberSame(cardmap[i1][j])) {
							cardmap[i][j]
									.setNumber(cardmap[i][j].getNumber() * 2);
							MainActivity.mainActivity.addScore(cardmap[i][j]
									.getNumber());
							cardmap[i1][j].setNumber(0);
							next = true;
							i++;
							break;
						}
						else{
							break;
						}
					}
				}
			}
		}
		if(next == true){
			addRandomNumber();
//			addRandomNumber();
		}
	}

	private Boolean isClose() {
		boolean isclose = true;
		All: for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cardmap[i][j].getNumber() == 0 || (j > 0)
						&& cardmap[i][j].isNumberSame(cardmap[i][j - 1])
						|| (j < 3)
						&& cardmap[i][j].isNumberSame(cardmap[i][j + 1])
						|| (i > 0)
						&& cardmap[i][j].isNumberSame(cardmap[i - 1][j])
						|| (i < 3)
						&& cardmap[i][j].isNumberSame(cardmap[i + 1][j])) {
					isclose = false;
					break All;
				}
			}
		}
		return isclose;
	}
}
