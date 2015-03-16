package es.source.code.activity;

import es.source.code.model.User;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainScreenAdapter extends BaseAdapter{

	private Context mContext;
	private LinearLayout line = null;
	private User mUser;
	//private boolean isHide;
    public MainScreenAdapter(Context c, User user) {
        mContext = c;
        this.mUser = user;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	if(convertView == null)
    	{	
	    	line = new LinearLayout(mContext);
	    	//设置垂直放组件
	    	line.setOrientation(1);
	        ImageView image = new ImageView(mContext);
	        image.setImageResource(mThumbIds[position]);
	        TextView text = new TextView(mContext);
	        text.setText(mText[position]);
	        //设置字符宽度
	        text.setTextSize(15);
	        //居中
	        text.setGravity(Gravity.CENTER);
	        //黑色
	        text.setTextColor(Color.BLACK);
	        line.addView(image);
	        line.addView(text);
	        if(mUser.isHide() && position<2)
	        	line.setVisibility(4);
    	}
    	else {
    		line = (LinearLayout)convertView;
    	}
        
        return line;
    }

    // references to our images
    private Integer[] mThumbIds = {
    		R.drawable.order,R.drawable.view,
    		R.drawable.login,R.drawable.help
    };
    private int[] mText = {
    		R.string.order,R.string.view,
    		R.string.loginorreg,R.string.help
    };

}
