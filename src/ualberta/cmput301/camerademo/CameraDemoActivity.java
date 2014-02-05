package ualberta.cmput301.camerademo;

import java.io.File;

import ualberta.cmput301.camerodemo.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CameraDemoActivity extends Activity {
	private final File FOLDER_NAME = new File(Environment.getExternalStorageDirectory()+"/tmp/");
	private final File FILE_NAME = new File(FOLDER_NAME, "hi.jpg");
	
	private TextView textView;
	private ImageButton imageButton;
	private Uri imageFileUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camero_demo);
		
		if(!FOLDER_NAME.exists())
			FOLDER_NAME.mkdir();
		
		// Retrieve handlers
		textView = (TextView) findViewById(R.id.status);
		imageButton = (ImageButton) findViewById(R.id.image);
		
		// Set up the listener
		OnClickListener listener = new OnClickListener() {
			public void onClick(View view) {
				takeAPhoto(); // implement this method
			}
		};
		// Register a callback to be invoked when this view is clicked
		imageButton.setOnClickListener(listener);
	}

	// Implement takeAPhoto() method to allow you to take a photo when you click the ImageButton.
	// Notice that startActivity() method will not return any result when the launched activity 
	// finishes, while startActivityForResult() method will. To retrieve the returned result, you may 
	// need implement onAcitityResult() method.
	public void takeAPhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		Uri uri = Uri.fromFile(FILE_NAME);
		
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		
		startActivityForResult(intent, 0);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK)
		{
			//Bitmap bm = (Bitmap)data.getExtras().getParcelable("data");
			//imageButton.setImageBitmap(bm);
			
			Uri uri = Uri.fromFile(FILE_NAME);
			imageButton.setImageDrawable(Drawable.createFromPath(uri.getPath()));
			
			textView.setText("Photo taken");
		}
		else if(resultCode == RESULT_CANCELED)
		{
			textView.setText("Photo received but also canceled??");
		}
		else
		{
			textView.setText("Unknown error");
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camero_demo, menu);
		return true;
	}

}
