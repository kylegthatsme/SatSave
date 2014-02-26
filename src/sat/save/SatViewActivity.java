package sat.save;
/**
 * @author Kyle Gehrman 
 * 
 * SetViewActivity
 * 
 * This activity displays the list that shows all the saved "SatInstance"'s
 * 
 * All right reserved 2014
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

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SatViewActivity extends Activity {
	
	private ListView lv;
	SCSQLHelper db = new SCSQLHelper(this);
	List<SatInstance> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sat_view);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		list = db.getSatInstance();
		lv = (ListView) findViewById(R.id.instancelist);
		ArrayAdapter<SatInstance> arrayAdapter = new ArrayAdapter<SatInstance>(this,android.R.layout.simple_list_item_1,list);

        lv.setAdapter(arrayAdapter);
        
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {
    	    @Override
    	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	    	Intent i = new Intent(v.getContext(), InstanceView.class);
    	    	i.putExtra("InstanceSelected",(int)id);
    	    	startActivity(i);	
    	    }
    	});
	}
	protected void onPause(Bundle savedInstanceState) {
		list.clear();
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
		getMenuInflater().inflate(R.menu.sat_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
