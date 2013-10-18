package sean.k.sixes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class SixesSplash extends Activity{
	

	Intent startIntent;
	int screenWidth;
	int screenHeight;
	final int FADE_IN_TIME = 1000;
	final int SPLASH_TIME = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sixes_splash);
		TextView splashText = (TextView) findViewById(R.id.splashText);
		AlphaAnimation fadeIn = new AlphaAnimation(0f,1f);
		fadeIn.setDuration(FADE_IN_TIME);
		fadeIn.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				initialize();
			}
			
		});

		splashText.startAnimation(fadeIn);
		
		
		
		
		splashText.postDelayed(new Runnable(){

			@Override
			public void run() {
				startIntent = new Intent(getApplicationContext(),SixesOfflineCreate.class);
				//startIntent.putExtra("sean.k.sixes.screenWidth",screenWidth);
				//startIntent.putExtra("sean.k.sixes.screenHeight",screenHeight);
				startActivity(startIntent);
				finish();
			}
			
		}, SPLASH_TIME);

			
		
	}
	
	
	private void initialize(){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenWidth = displaymetrics.widthPixels;
		screenHeight = displaymetrics.heightPixels;
	}
	

}
