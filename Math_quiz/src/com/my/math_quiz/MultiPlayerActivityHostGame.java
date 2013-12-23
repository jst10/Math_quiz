/*

    Copyright 2014 Jo�e Kulovic

    This file is part of Math-quiz.

    Math-quiz is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Math-quiz is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Math-quiz.  If not, see http://www.gnu.org/licenses

*/
package com.my.math_quiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.my.math_quiz.views.TitleBar;
import com.my.math_quiz.views.TitleBar.TitleBarListener;
import com.my.math_quiz_multiplayer_stuff.TCPIPServer;
import com.my.math_quiz_multiplayer_stuff.TCPIPServer.TCPIPServerListenerInGame;

public class MultiPlayerActivityHostGame extends Activity implements TitleBarListener,TCPIPServerListenerInGame {

	TitleBar titleBar=null;
	TextView ipAdress;
	EditText portNumber;
	TextView numberOfPlayers;
	
	Button serverButton;
	Button nextButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multi_player_w_host);
		
		titleBar=(TitleBar)findViewById(R.id.TBtitleBar);
		titleBar.setTitleBarListener(this);
		titleBar.setTitle(getString(R.string.multi_player_host));
		titleBar.setRightImage(BitmapFactory.decodeResource(getResources(), R.drawable.action_settings));
		
		

	}

	@Override
	protected void onResume() {
		super.onResume();
		TCPIPServer.setTCPIPServerListener(this);
	}

	/**BEGIN the title bar listener methods*/
	@Override
	public void onLeftButtonClick() {
		this.finish();
	}

	@Override
	public void onRightButtonClick() {
		Intent intent = new Intent(MultiPlayerActivityHostGame.this, PreferenceActivity.class);
		startActivity(intent);
	}
	/**END the title bar listener methods*/

	
	/**BEGIN the TCPIPServerListenerInGame methods*/
	@Override
	public void onNumberOfClientsChanged(int number, boolean accepted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestForNumberOfGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestForPlayerNickname(int playerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestForNumberOfPlayers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelectedAnswerRecived(int taskNumber, int selectedAnsver,
			int clientId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestForTaskDescription(int taskNumber) {
		// TODO Auto-generated method stub
		
	}
	
	/**END the TCPIPServerListenerInGame methods*/

}
