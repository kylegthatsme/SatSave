package sat.save;
/**
 * @author kgehrman
 * 
 * Instance View
 * 
 * activity that displays a single instance from the db 
 * 
 * All right reserved 2014
 * This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 *
 */
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Button;
import android.widget.TextView;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class InstanceView extends Activity {
	//test views for page
	TextView latitudeField;
	TextView longitudeField;
	TextView ProviderField;
	TextView AccuracyField;
	TextView AltitudeField;
	TextView BearingField;
	Button b_deleteThis;
	
    
	SatInstance satinstance = new SatInstance();
	SCSQLHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instance_view);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent mIntent = getIntent();
		int Id = mIntent.getIntExtra("InstanceSelected",0);
		SCSQLHelper db = new SCSQLHelper(this);
		db.getReadableDatabase();
		satinstance = db.getSatInstance(Id);
		
		b_deleteThis = (Button)findViewById(R.id.deletethis);
		b_deleteThis.setOnClickListener(l_deleteThis);
	
		latitudeField = (TextView) findViewById(R.id.LatField);
	    longitudeField = (TextView) findViewById(R.id.LongField);
	    ProviderField = (TextView) findViewById(R.id.ProviderField);
	    AccuracyField = (TextView) findViewById(R.id.AccuracyField);
	    AltitudeField = (TextView) findViewById(R.id.AltitudeField);
	    BearingField = (TextView) findViewById(R.id.BearingField);
	    //display on screen
	    latitudeField.setText(String.valueOf(satinstance.getLat()));
        longitudeField.setText(String.valueOf(satinstance.getLng()));
        ProviderField.setText("generic " + satinstance.getProvider() +" system");
        AccuracyField.setText("+/- "+String.valueOf(satinstance.getAccuracy())+" Meters");
        AltitudeField.setText(String.valueOf(satinstance.getAltitude())+ " Meters");
        BearingField.setText(String.valueOf(satinstance.getBearing()) + " Degrees");
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instance_view, menu);
		return true;
	}
	private OnClickListener l_deleteThis = new OnClickListener() {
	    public void onClick(View v) {
	    	satinstance = new SatInstance();
	    	//db.deleteInstance(satinstance.getId());
	    	Intent i = new Intent(v.getContext(), MainActivity.class);
	    	startActivity(i);
	    	
	    	Context context = getApplicationContext();
	    	CharSequence text = "Location Deleted";
	    	int duration = Toast.LENGTH_LONG;
	    	Toast toast = Toast.makeText(context, text, duration);
	    	toast.show();
	    
	    }
	};


}
