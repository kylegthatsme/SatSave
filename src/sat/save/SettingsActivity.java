package sat.save;
/**
 * @author Kyle Gehrman 
 * 
 * SettingActivity
 * 
 * This activity is used to delete all SatInstances in the db
 * 
 * All right reserved 2014
 * 
 * This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

import android.os.Bundle;
import android.app.Activity;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
//import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class SettingsActivity extends Activity {
	
	
	Button b_delete;
	SCSQLHelper db;
	SatInstance satinstance;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();
		
		db = new SCSQLHelper(this);
		b_delete = (Button)findViewById(R.id.delete);
		b_delete.setOnClickListener(l_delete);
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

	private OnClickListener l_delete = new OnClickListener() {
	    public void onClick(View v) {
	    	
	    	 
	    	 satinstance = new SatInstance();
	    	 db.deleteAll(satinstance);
	    	 
	    	 Context context = getApplicationContext();
		     CharSequence text = "All saved points deleted";
		     int duration = Toast.LENGTH_LONG;
		     Toast toast = Toast.makeText(context, text, duration);
		     toast.show();
   
	    }
	};
	
}
