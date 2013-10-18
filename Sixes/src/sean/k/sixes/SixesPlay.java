package sean.k.sixes;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;

public class SixesPlay extends Activity{

	//DrawingPanel sixesPanel;
	int screenWidth;
	int screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sixes_play);
		
		screenWidth = getIntent().getIntExtra("sean.k.sixes.screenWidth",100);
		screenHeight = getIntent().getIntExtra("sean.k.sixes.screenHeight",100);

		//sixesPanel = (DrawingPanel) findViewById(R.id.sixes_panel);
		
		//seat1
		ImageView seat1 = (ImageView) findViewById(R.id.play_seat1);
		int seat1Height = screenHeight/6;
		int seat1Width =  seat1Height;
		seat1.setImageResource(R.drawable.dice1);
		FrameLayout.LayoutParams seat1Params = (LayoutParams) seat1.getLayoutParams();
		seat1Params.setMargins(seat1Width/2, 0, 0, screenHeight/2-seat1Height/2);
		seat1Params.height = seat1Height;
		seat1Params.width = seat1Width;

		//seat2
		ImageView seat2 = (ImageView) findViewById(R.id.play_seat2);
		int seat2Height = screenHeight/6;
		int seat2Width =  seat2Height;
		seat2.setImageResource(R.drawable.dice2);
		FrameLayout.LayoutParams seat2Params = (LayoutParams) seat2.getLayoutParams();
		seat2Params.setMargins(screenWidth/3, 0, 0, screenHeight-seat2Height-seat2Height/2);
		seat2Params.height = seat2Height;
		seat2Params.width = seat2Width;
		
		//seat3
		ImageView seat3 = (ImageView) findViewById(R.id.play_seat3);
		int seat3Height = screenHeight/6;
		int seat3Width =  seat3Height;
		seat3.setImageResource(R.drawable.dice3);
		FrameLayout.LayoutParams seat3Params = (LayoutParams) seat3.getLayoutParams();
		seat3Params.setMargins(screenWidth*2/3 -seat3Width, 0, 0, screenHeight-seat3Height-seat3Height/2);
		seat3Params.height = seat3Height;
		seat3Params.width = seat3Width;
		
		//seat4
		ImageView seat4 = (ImageView) findViewById(R.id.play_seat4);
		int seat4Height = screenHeight/6;
		int seat4Width =  seat4Height;
		seat4.setImageResource(R.drawable.dice4);
		FrameLayout.LayoutParams seat4Params = (LayoutParams) seat4.getLayoutParams();
		seat4Params.setMargins(screenWidth - seat4Width/2 -seat4Width, 0, 0, screenHeight/2-seat4Height/2);
		seat4Params.height = seat4Height;
		seat4Params.width = seat4Width;
		
		//seat5
		ImageView seat5 = (ImageView) findViewById(R.id.play_seat5);
		int seat5Height = screenHeight/6;
		int seat5Width =  seat5Height;
		seat5.setImageResource(R.drawable.dice5);
		FrameLayout.LayoutParams seat5Params = (LayoutParams) seat5.getLayoutParams();
		seat5Params.setMargins(screenWidth*2/3 -seat5Width, 0, 0, seat5Height/2);
		seat5Params.height = seat5Height;
		seat5Params.width = seat5Width;
		
		//seat6
		ImageView seat6 = (ImageView) findViewById(R.id.play_seat6);
		int seat6Height = screenHeight/6;
		int seat6Width =  seat6Height;
		seat6.setImageResource(R.drawable.dice6);
		FrameLayout.LayoutParams seat6Params = (LayoutParams) seat6.getLayoutParams();
		seat6Params.setMargins(screenWidth/3, 0, 0, seat6Height/2);
		seat6Params.height = seat6Height;
		seat6Params.width = seat6Width;
		
		
		
	}
	
	

	
}
