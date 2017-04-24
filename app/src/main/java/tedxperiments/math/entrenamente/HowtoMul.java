package tedxperiments.math.entrenamente;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import tedxperiments.math.entrenamente.R;

public class HowtoMul extends FragmentActivity {
    static final int NUM_ITEMS = 3;
    PlanetFragmentPagerAdapter planetFragmentPagerAdapter;
    ViewPager viewPager;
    Toast  toastswipe;
    Button youtubebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);
        planetFragmentPagerAdapter = new PlanetFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(planetFragmentPagerAdapter);
        youtubebtn = (Button)findViewById(R.id.btn_youtube);
        youtubebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
            	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_link_mul))));
            }
        });
        
        ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#ED1566'>"+getString(R.string.tutorial_mul_title)+"</font>"));
		//AB.setSubtitle("Multiplicación");
		AB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0F1F2")));
		
		
     
		//toastswipe=Ftoastswipe();
		//toastswipe.show();
		
    }
    
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; return!
	            Intent intent = new Intent(this, HowToPlay.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

    @Override
    protected void onPause () {
        super.onPause();
        //if (toastswipe != null)toastswipe.cancel();
    }      

    public static class PlanetFragmentPagerAdapter extends FragmentPagerAdapter {
        public PlanetFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            SwipeFragment fragment = new SwipeFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            TextView tv = (TextView)swipeView.findViewById(R.id.text);
            ImageView img = (ImageView)swipeView.findViewById(R.id.imageView);
            ImageView img2 = (ImageView)swipeView.findViewById(R.id.dots);
            
            Bundle args = getArguments();
            int position = args.getInt("position");
            
            String planet = Planet.PLANETS[position+2];
            int imgResId = getResources().getIdentifier(planet, "drawable", "tedxperiments.math.entrenamente");
            img.setImageResource(imgResId);
            
            String planet2 = Planet.PLANETS2[position+2];
            int imgResId2 = getResources().getIdentifier(planet2, "drawable", "tedxperiments.math.entrenamente");
            img2.setImageResource(imgResId2);
            
            //tv.setText(Html.fromHtml(Planet.PLANET_DETAIL.get(planet)));
            CharSequence[] mulTexts = getResources().getTextArray(R.array.tutorial_mul_message_array);
            if (position<mulTexts.length)
                tv.setText(mulTexts[position]);
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            swipeFragment.setArguments(args);
            return swipeFragment;
        }
    }
    
    private Toast Ftoastswipe() {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
		
		layout.setBackgroundResource(R.drawable.gesture_swipe);
				
		Toast thistoast = new Toast(getApplicationContext());
		thistoast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
		thistoast.setDuration(Toast.LENGTH_SHORT);
		thistoast.setView(layout);
		return thistoast;
	}
}