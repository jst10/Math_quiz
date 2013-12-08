package com.my.math_quiz.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.my.math_quiz.R;

public class InGameBottomButtoms extends LinearLayout{
	public interface BottomButtonListener{
		public void onButtonClick(InGameBottomButtoms buttoms,int position);
	}
	
	
	Button[]buttons=null;
	
	BottomButtonListener listener=null;
	int positionInTasks;
	
//	int backgroundCorrect;
//	int backgroundWrong;
	
	Drawable button_normal_background;
	Drawable button_correct_background;
	Drawable button_wrong_background;
	
	int firstSelectedAnswer=-1;
	
	public InGameBottomButtoms(Context context) {
		super(context);
		init();

	}

	public InGameBottomButtoms(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();

	}

	public InGameBottomButtoms(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	private void init(){
//		backgroundCorrect=getResources().getColor(R.color.background_correct);
//		backgroundWrong=getResources().getColor(R.color.background_wrong);
		
		button_normal_background=getResources().getDrawable(R.drawable.button_background_normal);
		button_correct_background=getResources().getDrawable( R.drawable.button_background_correct);
		button_wrong_background=getResources().getDrawable(R.drawable.button_background_wrong);
	}

	private void initViews(){
		if(buttons==null){
			buttons=new Button[4];
			buttons[0]=(Button)findViewById(R.id.BBbutton1);
			buttons[1]=(Button)findViewById(R.id.BBbutton2);
			buttons[2]=(Button)findViewById(R.id.BBbutton3);
			buttons[3]=(Button)findViewById(R.id.BBbutton4);
			
			buttons[0].setOnClickListener(onClickListener);
			buttons[1].setOnClickListener(onClickListener);
			buttons[2].setOnClickListener(onClickListener);
			buttons[3].setOnClickListener(onClickListener);
			
		}
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		initViews();
		
	}
	public void setListener(BottomButtonListener listener){
		this.listener=listener;
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(listener!=null){
				if(v.getId()==R.id.BBbutton1){
					listener.onButtonClick(InGameBottomButtoms.this,0);
				}else if(v.getId()==R.id.BBbutton2){
					listener.onButtonClick(InGameBottomButtoms.this,1);
				}else if(v.getId()==R.id.BBbutton3){
					listener.onButtonClick(InGameBottomButtoms.this,2);
				}else if(v.getId()==R.id.BBbutton4){
					listener.onButtonClick(InGameBottomButtoms.this,3);
				}
				
				
			}
			
		}
	};
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
/**
 * Method set right colors to buttons if we have select one answer before
 * @selectedAnswer the number from 0 to 4 which tell us which button was clicked, -1 mean that no one button was clicked
 * @correctAnswer tell us which answer is correct the value is from 0 to 4, because we have only four options
 * @return if this is first time we clicked button it will set, else it won't and will return false, you reset first selected
 * answer with method resetFirstSelectedAnswer
 * */
	public boolean setCollors(int selectedAnswer, int correctAnswer){
		if(selectedAnswer!=-1&&firstSelectedAnswer==-1){
			firstSelectedAnswer=selectedAnswer;
			if(selectedAnswer!=correctAnswer){
				buttons[selectedAnswer].setBackgroundDrawable(button_wrong_background);
				buttons[correctAnswer].setBackgroundDrawable(button_correct_background);
//				buttons[selectedAnswer].setBackgroundColor(backgroundWrong);
//				buttons[correctAnswer].setBackgroundColor(backgroundCorrect);
			}else{
				buttons[correctAnswer].setBackgroundDrawable(button_correct_background);
			}
			return true;
		}
		return false;
	}
	/**This method reset the first selected answer to -1 and set colors to default color not selected*/
	public void resetFirstSelectedAnswer(){
		firstSelectedAnswer=-1;	
		buttons[0].setBackgroundDrawable(button_normal_background);
		buttons[1].setBackgroundDrawable(button_normal_background);
		buttons[2].setBackgroundDrawable(button_normal_background);
		buttons[3].setBackgroundDrawable(button_normal_background);
	}
	public void seButtontTexts(int[] answers) {
		initViews();
		buttons[0].setText(answers[0]+"");
		buttons[1].setText(answers[1]+"");
		buttons[2].setText(answers[2]+"");
		buttons[3].setText(answers[3]+"");
		
	}

	public int getPositionInTasks() {
		return positionInTasks;
	}

	public void setPositionInTasks(int positionInTasks) {
		this.positionInTasks = positionInTasks;
	}

	
	
	

}
