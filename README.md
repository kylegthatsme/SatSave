# SatSave

Page created by Kyle Gehrman 2014. <br>
Email Questions or Comments to: kgehrma@g.clemson.edu
<br> 
<br>
SatSave is an Android applicaion that is primarly used as a tool to save all location information that is currently availble to an internet connected Android device. It stores these values locally and allows for them to be accessed and deleted individually or alltogether. 

### Technical Summary
   SatSave is a relatively simple android location that uses the Android Location Services to access the devices current or last known latitude and longitude, along with the provider, the accuracy of the values, the devices bearing and the name of the location that you are storing. These values are then injected into a local SQLite database. The database is then dynamically loaded to a listview to view the points and allow for them to be selected for further view or deletion. 

#### Technologies used: 
Java, Android SDK, Eclipse, SQL, PHP, PHPMyAdmin and XML

#### Data: 
  These are the fields that are "SatSaved" in each instance in the database
  -String name <br>
  -int userid <br>
  -double lat <br>
  -double lng <br>
  -String provider <br>
  -float accuracy <br>
  -double altitude <br>
  -float bearing <br>
  -int tableid <br>




