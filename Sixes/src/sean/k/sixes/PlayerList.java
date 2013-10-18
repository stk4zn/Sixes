package sean.k.sixes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class PlayerList extends ListView{

	public PlayerList(Context context) {
		super(context);
	}
	
	public PlayerList(Context context, AttributeSet attr){
		super(context, attr);

	}
	
	public PlayerList(Context context, AttributeSet attr, int num){
		super(context, attr, num);

	}
	
	
	/*
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
	   if(ev.getAction()==MotionEvent.ACTION_MOVE){
	      return true;
	   }
	   return super.dispatchTouchEvent(ev);
	}
	*/
	

}
