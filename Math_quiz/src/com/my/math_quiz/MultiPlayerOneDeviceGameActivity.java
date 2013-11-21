package com.my.math_quiz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.my.math_quiz.interfaces.LevelDataIN;
import com.my.math_quiz.utils.LevelDescripction;
import com.my.math_quiz.utils.Task;
import com.my.math_quiz.views.InGameBottomButtoms;
import com.my.math_quiz.views.InGameBottomButtoms.BottomButtonListener;

public class MultiPlayerOneDeviceGameActivity extends Activity  {

	LevelDescripction levelDescripction;
	LevelDataIN levelData;
	ArrayList<Task> tasks;
	LayoutInflater inflater;
	
	int numberOfTasksInRound;
	int selectedLevel;
	
	RelativeLayout pageContainer=null;
	int currentTaskPosition;
	
	Bitmap taskIndicatorCorrectAnswer;
	Bitmap taskIndicatorWrongAnswer;
	Bitmap taskIndicatorNotSelectedAnswer;
	Bitmap taskIndicatorCurrent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		setContentView(R.layout.activity_multi_player_one_device_game);
		pageContainer=(RelativeLayout)this.findViewById(R.id.MPODGContainer);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		Intent myIntent = getIntent();
		selectedLevel = myIntent.getIntExtra("EXTRA_SELECTED_LEVEL",0);
		levelDescripction= ApplicationClass.getLevelDescription(selectedLevel);
		levelData=levelDescripction.getLevelData();

		taskIndicatorCorrectAnswer=BitmapFactory.decodeResource(getResources(), R.drawable.task_indicator_correct_answer);
		taskIndicatorWrongAnswer=BitmapFactory.decodeResource(getResources(), R.drawable.task_indicator_wrong_answer);
		taskIndicatorNotSelectedAnswer=BitmapFactory.decodeResource(getResources(), R.drawable.task_indicator_not_selected);
		taskIndicatorCurrent=BitmapFactory.decodeResource(getResources(), R.drawable.task_indicator_current);
		
		setGameViewToContainerAndRestart();
	}
	
//	ProgressBar progressBar1=null;
//	ProgressBar progressBar2=null;
	TextView texView1=null;
	TextView texView2=null;
	
	InGameBottomButtoms bottomButtons1=null;
	InGameBottomButtoms bottomButtons2=null;
	
	boolean hasAnyoneAnswerAtThisTask=false;
	boolean wasAnswerAnswerCorrectAnswer=false;
	
	LinearLayout layoutForIndicators1;
	LinearLayout layoutForIndicators2;
	
	ImageView[] imageViews1;
	ImageView[] imageViews2;
	
	
	private void setGameViewToContainerAndRestart(){
		pageContainer.removeAllViews();
		View v = inflater.inflate(R.layout.view_multi_player_game_one_device_one_page, null);
		pageContainer.addView(v);
		numberOfTasksInRound=ApplicationClass.getMPCurrentNumberOfGamesInOneRound();
		levelData.clearLevelData();
		tasks=	levelData.getTests(numberOfTasksInRound);
		
		
		bottomButtons1=(InGameBottomButtoms)v.findViewById(R.id.MPODGVbootomButtons1);
		bottomButtons2=(InGameBottomButtoms)v.findViewById(R.id.MPODGVbootomButtons2);
		
		texView1=(TextView)v.findViewById(R.id.MPODGVtextView1);
		texView2=(TextView)v.findViewById(R.id.MPODGVtextView2);
		
//		progressBar1=(ProgressBar)v.findViewById(R.id.MPODGVprogressBar1);
//		progressBar2=(ProgressBar)v.findViewById(R.id.MPODGVprogressBar2);
		
		layoutForIndicators1=(LinearLayout)v.findViewById(R.id.MPODGlayoutForIndicator1);
		layoutForIndicators2=(LinearLayout)v.findViewById(R.id.MPODGlayoutForIndicator2);
		
//		progressBar1.setMax(numberOfTasksInRound);
//		progressBar2.setMax(numberOfTasksInRound);
		
		layoutForIndicators1.removeAllViews();
		imageViews1=new ImageView[numberOfTasksInRound];
		layoutForIndicators2.removeAllViews();
		imageViews2=new ImageView[numberOfTasksInRound];
		int oneIndicatorWidth=ApplicationClass.getDisplaySize().x/numberOfTasksInRound;
		int oneIndicatorHeight=ApplicationClass.getDisplaySize().x/ApplicationClass.getMaximumNumberOfGamesInOneRound();
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(oneIndicatorWidth,oneIndicatorHeight);
	
		for(int i=0; i<imageViews1.length; i++){
			imageViews1[i]=new ImageView(this);
			imageViews1[i].setLayoutParams(layoutParams);
			imageViews1[i].setImageBitmap(taskIndicatorNotSelectedAnswer);
			imageViews1[i].setScaleType(ScaleType.CENTER_INSIDE);
			layoutForIndicators1.addView(imageViews1[i]);
			
			imageViews2[i]=new ImageView(this);
			imageViews2[i].setLayoutParams(layoutParams);
			imageViews2[i].setImageBitmap(taskIndicatorNotSelectedAnswer);
			imageViews2[i].setScaleType(ScaleType.CENTER_INSIDE);
			layoutForIndicators2.addView(imageViews2[i]);
		}
		imageViews1[0].setImageBitmap(taskIndicatorCurrent);
		imageViews2[0].setImageBitmap(taskIndicatorCurrent);
		
		
		bottomButtons1.setListener(bottomButtonListener1);
		bottomButtons2.setListener(bottomButtonListener2);
		
		displayDataFromSpecificTest(0);
	}
	
	
	/**This method display text of task and set answers to buttons and also set progress bars to right position*/
	private void displayDataFromSpecificTest(int position){
		currentTaskPosition=position;
//		progressBar1.setProgress(position);
//		progressBar2.setProgress(position);	
		
		Task currentTask=tasks.get(currentTaskPosition);
		texView1.setText(currentTask.getText());
		texView2.setText(currentTask.getText());
		
		bottomButtons1.seButtontTexts(currentTask.getAnswers());
		bottomButtons2.seButtontTexts(currentTask.getAnswers());

		bottomButtons1.resetFirstSelectedAnswer();
		bottomButtons2.resetFirstSelectedAnswer();
		
		
		hasAnyoneAnswerAtThisTask=false;
		wasAnswerAnswerCorrectAnswer=false;
	}
	private BottomButtonListener bottomButtonListener1=new BottomButtonListener() {
		/**@param position the position of button on which user clicked*/
		@Override
		public void onButtonClick(InGameBottomButtoms buttoms, int position) {
			if(wasAnswerAnswerCorrectAnswer==false){
				Task t=tasks.get( currentTaskPosition);
				if(buttoms.setCollors(position, t.getCorrectAnswer())){
					onAnswer(1,position==t.getCorrectAnswer());
				}
			}
		}
	};
	
	private BottomButtonListener bottomButtonListener2=new BottomButtonListener() {
		/**@param position the position of button on which user clicked*/
		@Override
		public void onButtonClick(InGameBottomButtoms buttoms, int position) {
			if(wasAnswerAnswerCorrectAnswer==false){
				Task t=tasks.get( currentTaskPosition);
				if(buttoms.setCollors(position, t.getCorrectAnswer())){
					onAnswer(2,position==t.getCorrectAnswer());
				}
			}
			
		}
	};
	
	private void onAnswer(int player,boolean wasCorrect){
//		Log.d("onbuttonclick","buttonclickes");
//		displayDataFromSpecificTest(++currentTaskPosition);
		handler.removeCallbacks(runablePageSwitching);
		if(wasCorrect){
			hasAnyoneAnswerAtThisTask=true;
			wasAnswerAnswerCorrectAnswer=true;
			//we clicke correct solution and even if we frt clicked or second we go to next round
			moveDelayToPage(ApplicationClass.getMPDelayOnCorrectAnswerInMiliS());
			
		}else if(hasAnyoneAnswerAtThisTask==false){
			hasAnyoneAnswerAtThisTask=true;
			wasAnswerAnswerCorrectAnswer=false;
			//we clicked first and wrong
			moveDelayToPage(ApplicationClass.getMPRemainTimeToAnswer());
		}
		else{
			wasAnswerAnswerCorrectAnswer=false;
			//we clicked second and wrong
			moveDelayToPage(ApplicationClass.getMPDelayOnWrongAnswerInMiliS());
		}
		//TODO check if anyone was answerd yet and who is first and that so on
//		if()
//		if(levelData.getNumberOfUnsolvedTests()==0){
//			//now we finish all tasks in that level so we can calculate results
//			levelData.stopTimingLevel();
//			pager.setCurrentItem(numberOfTasksInRound,true);
//			
//		}else {
//			int delay=0;
//			if(t.getSelectedAnswer()==t.getCorrectAnswer()){
//				//we answer correct to answer an we move forward to next task  or back to previous if isn't solved already
//				delay=ApplicationClass.getMPDelayOnCorrectAnswerInMiliS();
//			}else{
//				//we answer wrong and it is other delay sett
//				delay=ApplicationClass.getMPDelayOnWrongAnswerInMiliS();
//			}
//			moveDelayToPage(delay);
//		}
	}
	
	
	final Handler handler = new Handler();
	private void moveDelayToPage(int delay){
		handler.postDelayed(runablePageSwitching, delay);
	}
	final Runnable runablePageSwitching=new Runnable() {
		  @Override
		  public void run() {
			displayDataFromSpecificTest(++currentTaskPosition);
		  }
	};


}
