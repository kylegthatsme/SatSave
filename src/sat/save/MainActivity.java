/**
 * @author kgehrma
 * 
 * This is the main activity for SatSave, A utility written by Kyle Gehrman 
 * to be used by android developers for determining the functionality of the location
 * services on their device. 
 * 
 * You can also save all location on on a SQLite database and view them for later for comparison
 * 
 * The database can be purged from the "settings" menu
 * 
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
package sat.save;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;



public class MainActivity extends Activity {
	TextView latitudeField;
	TextView longitudeField;
	TextView AltitudeField;
	TextView BearingField;
	
	Button b_check;
	Button b_save;
	Button b_info;
	Button b_list;
	Button b_settings;
	
	//primary location manager, used to get Satalite info
	LocationManager lm;
	Location location;
	SCSQLHelper db = new SCSQLHelper(this);
	SatInstance satinstance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		latitudeField = (TextView) findViewById(R.id.LatField);
	    longitudeField = (TextView) findViewById(R.id.LongField);
	    AltitudeField = (TextView) findViewById(R.id.AltitudeField);
	    BearingField = (TextView) findViewById(R.id.BearingField);
	    
	    
	    //declare main vew buttons
	    b_check = (Button)findViewById(R.id.check);
		b_check.setOnClickListener(l_check);
		
		b_save = (Button)findViewById(R.id.save);
		b_save.setOnClickListener(l_save);
		
		b_list = (Button)findViewById(R.id.list);
		b_list.setOnClickListener(l_list);
		
		b_info = (Button)findViewById(R.id.info);
		b_info.setOnClickListener(l_info);
		
		b_settings = (Button)findViewById(R.id.settings);
		b_settings.setOnClickListener(l_settings);
		
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
    	location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	private OnClickListener l_save = new OnClickListener() {
	    public void onClick(View v) {

	    	location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    	// if location is not null than real values are used
		    if(location != null) {
		    	
		    	Context context = getApplicationContext();
		    	CharSequence text = "Values Saved";
		    	int duration = Toast.LENGTH_LONG;
		    	Toast toast = Toast.makeText(context, text, duration);
		    	toast.show();
		    	
		    	SatInstance satinstance = new SatInstance();
		    	
		    	satinstance.setLat(location.getLatitude());
		    	satinstance.setLng(location.getLongitude());
		    	satinstance.setProvider(location.getProvider());
		    	satinstance.setAccuracy(location.getAccuracy());
		    	satinstance.setAltitude(location.getAltitude());
		    	satinstance.setBearing(location.getBearing());
		    	
		    	db.addSatInstance(satinstance);   
		        
		        latitudeField.setText(String.valueOf(satinstance.getLat()));
		        longitudeField.setText(String.valueOf(satinstance.getLng()));
		        AltitudeField.setText(String.valueOf(satinstance.getAltitude())+ " Meters");
		        BearingField.setText(String.valueOf(satinstance.getBearing()) + " Degrees");
		        
	    	} else {
		    	// if location is null than the fake 404 values are used to still display the app functionality
		    	Context context = getApplicationContext();
		    	CharSequence text = "Test Instance Saved, No Satalite Connection";
		    	int duration = Toast.LENGTH_LONG;
		    	Toast toast = Toast.makeText(context, text, duration);
		    	toast.show();
		    	
		    	satinstance = new SatInstance();
		    	
		    	satinstance.setLat(404);
		    	satinstance.setLng(404);
		    	satinstance.setProvider("Value made it");
		    	satinstance.setAccuracy(404);
		    	satinstance.setAltitude(404);
		    	satinstance.setBearing(404);
		    	db.addSatInstance(satinstance);
		    	
		    	latitudeField.setText(String.valueOf(satinstance.getLat()));
		        longitudeField.setText(String.valueOf(satinstance.getLng()));
		        AltitudeField.setText(String.valueOf(satinstance.getAltitude())+ " Meters");
		        BearingField.setText(String.valueOf(satinstance.getBearing()) + " Degrees");
		    	
		    }
	    }
	};
	private OnClickListener l_check = new OnClickListener() {
	    public void onClick(View v) {
	    	//check is just a simply loction check, not saved to db
	    	location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	        
	        if(location != null){
	        	// here if satalite connection
		        latitudeField.setText(String.valueOf(location.getLatitude()));
		        longitudeField.setText(String.valueOf(location.getLongitude()));
		        AltitudeField.setText(String.valueOf(location.getAltitude())+ " Meters");
		        BearingField.setText(String.valueOf(location.getBearing()) + " Degrees");
		        
	        } else {
	        	//here if no satalite connection
	        	Context context = getApplicationContext();
		    	CharSequence text = "Your Location is Null. Please activate an internet connection.";
		    	int duration = Toast.LENGTH_LONG;
		    	Toast toast = Toast.makeText(context, text, duration);
		    	toast.show();
	        	
		    	latitudeField.setText(String.valueOf(405));
		        longitudeField.setText(String.valueOf(405));
		        AltitudeField.setText(String.valueOf(405)+ " Meters");
		        BearingField.setText(String.valueOf(405) + " Degrees");
	        }
	        
	    }
	};
	private OnClickListener l_info = new OnClickListener() {
	    public void onClick(View v) {
	    	//takes you to the info page
	    	 setContentView(R.layout.info);
   
	    }
	};
	private OnClickListener l_list = new OnClickListener() {
	    public void onClick(View v) {
	    	// takes you to the list of locations 
	    	Intent i = new Intent(v.getContext(), SatViewActivity.class);
	    	startActivity(i);
	    
	    }
	};
	private OnClickListener l_settings = new OnClickListener() {
	    public void onClick(View v) {
	    	//takes you to the settings pages where the sql database can be deleted
	    	Intent i = new Intent(v.getContext(), SettingsActivity.class);
	    	startActivity(i);
	    
	    }
	};
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
