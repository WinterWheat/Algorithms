package es.source.code.activity;

import android.R.color;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class SCOSEntry extends ActionBarActivity implements OnGestureListener {

	private Intent intent = new Intent();
	private GestureDetector detector;  
    private ViewFlipper flipper;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        
        
        
        flipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);  
        
        //添加子控件
        //flipper.addView(child);
        flipper.addView(addImageByText(),
        		new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        
        
        detector = new GestureDetector(this);
       // Log.d("test", "this is log test");
       // System.out.println("this is ok");
       
        
        
    }


    private View addImageByText() {
		// TODO Auto-generated method stub
    	ImageView img = new ImageView(this);
    	img.setBackgroundColor(color.white);
    	img.setImageResource(R.drawable.n16);
    	return img;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scosentry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.entry, container, false);
            return rootView;
        }
    }

    
    
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        Log.i("Fling", "Activity onTouchEvent!");  
        Log.i("test",event.getAction()+"");
        return true;//this.detector.onTouchEvent(event);  
    }
    
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		//System.out.println("我还是我~");
		if (e1.getX() - e2.getX() > 80) {  
			//设置跳转activity
			//intent.setClass(this, MainScreen.class);
			intent.setClass(this, MainScreen.class);
	       /// intent.putExtra("fromentry", "FromEntry");
	       // intent.putExtra("loginorregister","loginfalse");
			intent.putExtra("data", "FromEntry");
			startActivity(intent);
        	//finish();
            return true; 
		}
		return false;
	}
}

//this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));  
//  this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));  
 // this.flipper.showNext();  
	//System.out.println("我就是我~");
	//关闭页面