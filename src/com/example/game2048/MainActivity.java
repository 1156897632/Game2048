package com.example.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	public MainActivity() {
		mainActivity = this;
	}

	public int score = 0;
	public TextView tv;
	public static MainActivity mainActivity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.Score);
	}
	
	public void clearScore() {
		score = 0;
		showScore();
	}
	public void showScore() {
		tv.setText(""+score);
	}
	public void addScore(int s) {
		score = score + s;
		showScore();
	}
	
	public MainActivity getMainActivity() {
		return mainActivity;
	}

}
