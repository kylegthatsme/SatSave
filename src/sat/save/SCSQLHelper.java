package sat.save;
/**
 * @author Kyle Gehrman 
 * 
 * SCSQLHelper
 * 
 * class is used to insert, view and delete data in the SatSave Database
 * 
 * http://www.sqlite.org/
 * http://www.sqlite.org/docs.html
 * http://www.sqlite.org/copyright.html
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

import java.util.LinkedList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SCSQLHelper extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "SatDB";
    
    // Sat table name
    private static final String TABLE_SAT = "sat";

    // Sat Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERID = "userid";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_PROVIDER = "provider";
    private static final String KEY_ACCURACY = "accuracy";
    private static final String KEY_ALTITUDE = "altitude";
    private static final String KEY_BEARING = "bearing";
    
    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_USERID,KEY_LAT,KEY_LNG,KEY_PROVIDER,KEY_ACCURACY,KEY_ALTITUDE,KEY_BEARING};
    
    SatInstance satinstance;
    
    public SCSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create instance table
        String CREATE_SAT_TABLE = "CREATE TABLE sat ( " +
                "id INTEGER PRIMARY KEY autoincrement, " + 
                "name TEXT, "+
                "userid INTEGER, "+
                "lat REAL, "+
                "lng REAL, "+
                "provider TEXT, "+
                "accuracy REAL, "+
                "altitude REAL, "+
                "bearing REAL )";
 
        // create books table
        db.execSQL(CREATE_SAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older instances table if existed
        db.execSQL("DROP TABLE IF EXISTS sat");
 
        // create fresh instance table
        this.onCreate(db);
    }
    
    public void addSatInstance(SatInstance satinstance){
        //for logging
    	Log.d("addSAT-INSTANCE", satinstance.toString()); 

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		//values.put(KEY_ID, satinstance.getId()); 
		values.put(KEY_NAME, satinstance.getName());
		values.put(KEY_USERID, satinstance.getUserId());
		values.put(KEY_LAT, satinstance.getLat());
		values.put(KEY_LNG, satinstance.getLng());
		values.put(KEY_PROVIDER, satinstance.getProvider());
		values.put(KEY_ACCURACY, satinstance.getAccuracy());
		values.put(KEY_ALTITUDE, satinstance.getAltitude());
		values.put(KEY_BEARING, satinstance.getBearing());
		
		// 3. insert
		db.insert(TABLE_SAT, null, values); 
		
		// 4. close
		db.close(); 
    }
    
    public SatInstance getSatInstance(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = 
                db.query(TABLE_SAT, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections aregs
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        // 4. build book object
        SatInstance satinstance = new SatInstance();
        satinstance.setId(Integer.parseInt(cursor.getString(0)));
        satinstance.setName(cursor.getString(1));
        satinstance.setUserId(Integer.parseInt(cursor.getString(2)));
        satinstance.setLat(Double.parseDouble(cursor.getString(3)));
        satinstance.setLng(Double.parseDouble(cursor.getString(4)));
        satinstance.setProvider(cursor.getString(5));
        satinstance.setAccuracy(Float.parseFloat(cursor.getString(6)));
        satinstance.setAltitude(Double.parseDouble(cursor.getString(7)));
        satinstance.setBearing(Float.parseFloat(cursor.getString(8)));
     
        //log 
        Log.d("getBook("+id+")", satinstance.toString());
     
        // 5. return book
        return satinstance;
    }	
    
    public List<SatInstance> getSatInstance() {
        List<SatInstance> satInstanceList = new LinkedList<SatInstance>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_SAT;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        SatInstance satinstance = null;
        if (cursor.moveToFirst()) {
            do {
            	 satinstance = new SatInstance();
                 satinstance.setId(Integer.parseInt(cursor.getString(0)));
                 satinstance.setName(cursor.getString(1));
                 satinstance.setUserId(Integer.parseInt(cursor.getString(2)));
                 satinstance.setLat(Double.parseDouble(cursor.getString(3)));
                 satinstance.setLng(Double.parseDouble(cursor.getString(4)));
                 satinstance.setProvider(cursor.getString(5));
                 satinstance.setAccuracy(Float.parseFloat(cursor.getString(6)));
                 satinstance.setAltitude(Double.parseDouble(cursor.getString(7)));
                 satinstance.setBearing(Float.parseFloat(cursor.getString(8)));
  
                // Add instance
                satInstanceList.add(satinstance);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAll_SatInstance()", satInstanceList.toString());
  
        // return instance
        return satInstanceList;
    }
    
    public int updateDB(SatInstance satinstance) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", satinstance.getName()); // get name 
        values.put("userid", satinstance.getUserId()); // get userid
        values.put("lat", satinstance.getLat()); // get lat
        values.put("lng", satinstance.getLng()); // get lng
        values.put("provider", satinstance.getProvider()); // get provider
        values.put("accuracy", satinstance.getAccuracy()); // get accuracy
        values.put("altitude", satinstance.getAltitude()); // get altitude
        values.put("bearing", satinstance.getBearing()); // get bearing
        
     
        // 3. updating row
        int i = db.update(TABLE_SAT, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(satinstance.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
     
    }
    public void deleteAll(SatInstance satinstance) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        // 2. delete
        db.delete(TABLE_SAT, null, null);
        //db.execSQL("delete from sqlite_sequence where name='TABLE_SAT'");
 
        // 3. close*/
        db.close();
        
 
        //log
    Log.d("deleteAll", satinstance.toString());
 
    }
    
    public void deleteInstance(int id) {
   	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        
        satinstance = new SatInstance();
        // 2. delete
        //db.delete(TABLE_SAT, null, null);
        db.delete(TABLE_SAT, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(satinstance.getId()) }); //selections args
 
        // 3. close
        db.close();
 
        //log
    Log.d("delete", satinstance.toString());
 
    }


	

}
