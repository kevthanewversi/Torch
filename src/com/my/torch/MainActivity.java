package com.my.torch;

//So @kevthanewversi saw this torch Program on Kevin's phone 
//& decided well KuvF it,He's gonna make a torch.
//Day later,A chic decided she'll get him a drunk at a club(Linda's her name BTW)
//But she wasn't HOT so @kevthanewversi delclined
//he told her he's going to work on his project, she tells him to stick around - even offers him 
//LIQOUR 
//But he turns Her down. He goes back to his place & now he has a torch
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	public static Camera cam = null;// static so onDestroy won't destroy it
	public boolean isFlashOn;//if flash is on
	public boolean hasFlash; // if phone has an LED 
	public ImageButton imgon;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hasFlash = getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH);
				if(!hasFlash)
				{ AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
				alert.setTitle("Error");
				alert.setMessage("Your device does not support Torch");
				alert.setButton("OK", new DialogInterface.OnClickListener(){
					
				public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
				}});
				
		final ToggleButton torvh = (ToggleButton)findViewById(R.id.toggleButton);
		torvh.setOnClickListener (new View.OnClickListener(){
     		@Override 
    		public void onClick(View v) { {
    		    if (isFlashOn/*torvh.isChecked()*/ ){
    			TorvhOn();}
    			else {
    			 TorvhOff();}
     }}});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void TorvhOn() {
	  playSound();
		{
			cam = Camera.open();// returns null if device does not have a Back
								// camera
			Parameters p = cam.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);// torch you know =]
			cam.setParameters(p);
			cam.startPreview();
			isFlashOn= true;
		}

	}

	public void TorvhOff() {
		 playSound();
	 {
			cam.stopPreview();
			cam.release();// you call this once you're done with camera object
			cam = null;// nullifying cam since we're done with it
			isFlashOn = false;
			
			//change button image
			toggleImage();
		}

	}

	//use image of your choice to show on the main screen as the torch's UI
	private void toggleImage() {
		if (isFlashOn){
			imgon.setImageResource(R.drawable.btn_switch_on);
		}
		else{
			imgon.setImageResource(R.drawable.btn_switch_off);
			
		}
		
	}
	private void playSound(){
        if(isFlashOn){
            mp = MediaPlayer.create(MainActivity.this, R.raw.torcchclick);
        }else{
            mp = MediaPlayer.create(MainActivity.this, R.raw.torcchclick);
        }
        mp.setOnCompletionListener(new OnCompletionListener() {
 
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }
	 @Override
	    protected void onDestroy() {
	        super.onDestroy();
	    }
	 
	    @Override
	    protected void onPause() {
	        super.onPause();
	         
	        // on pause turn off the flash
	        TorvhOff();
	    }
	 
	    @Override
	    protected void onRestart() {
	        super.onRestart();
	    }
	 
	    @Override
	    protected void onResume() {
	        super.onResume();
	         
	        // on resume turn on the flash
	        if(hasFlash)
	            TorvhOn();
	    }
	 
	    @Override
	    protected void onStop() {
	        super.onStop();
	         
	        // on stop release the camera
	        if (cam != null) {
	            cam.release();
	            cam = null;
	        }
	    }
	 
	}


