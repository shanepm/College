package pm.shane.alexaclone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by underscorexxxjesus on 10/11/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "virtualcare";

    private static final String TABLE_LOCATION_HISTORY = "locationhistory";
    private static final String TABLE_CONTACTS = "contact";


    private static final String KEY_CONTACTNAME = "contactname";
    private static final String KEY_CONTACTNUMBER = "contactnumber";


    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_ALTITUDE = "altitude";
    private static final String KEY_TIMESTAMP = "timestamp";

    private static final String TAG = DBHandler.class.getSimpleName();



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION_HISTORY+ "("
                + KEY_LONGITUDE + " REAL," + KEY_LATITUDE + " REAL," + KEY_ALTITUDE + " REAL," + KEY_TIMESTAMP + " INTEGER"  + ")";
        db.execSQL(CREATE_TABLE_LOCATION);

        String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS+ "("
                + KEY_CONTACTNAME + " TEXT," + KEY_CONTACTNUMBER + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);


    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_HISTORY);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    }


    public void deleteLocationHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_HISTORY);

        String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION_HISTORY+ "("
                + KEY_LONGITUDE + " REAL," + KEY_LATITUDE + " REAL," + KEY_ALTITUDE + " REAL," + KEY_TIMESTAMP + " INTEGER"  + ")";
        db.execSQL(CREATE_TABLE_LOCATION);

    }

    private int getLocationHistoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION_HISTORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int c = cursor.getCount();
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        // return count
        return c;
    }

    public void addLocationHistory(double longitude, double latitude, double altitude, long timestamp){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_LATITUDE, latitude);
        values.put(KEY_ALTITUDE, altitude);
        values.put(KEY_TIMESTAMP, timestamp);


        db.insert(TABLE_LOCATION_HISTORY, null, values);
        db.close();

    }

    public void addContacts(String name, String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTACTNAME, name);
        values.put(KEY_CONTACTNUMBER, number);



        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }

    public Location getLatestLocationHistory(){
        Location location = null;
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION_HISTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        double longitude;
        double latitude;
        double altitude;
        long timestamp;

        if(cursor.moveToLast()){

            longitude = cursor.getDouble(0);
            latitude = cursor.getDouble(1);
            altitude = cursor.getDouble(2);
            timestamp = cursor.getLong(3);

            location = new Location("custom");
            location.setLongitude(longitude);
            location.setLatitude(latitude);
            location.setAltitude(altitude);
            location.setTime(timestamp);



        }
        else{
            Log.d(TAG, "DATABASEHELPER unable to get latest history");
        }

        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }




        return location;
    }

    public ArrayList<String> getContactNames(){
        ArrayList<String> names = new ArrayList<>();

        String selectQuery = "SELECT "+KEY_CONTACTNAME+" FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {


                names.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }

        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        return names;
    }

    public ArrayList<String> getContactsNumbers(){
        ArrayList<String> numbers = new ArrayList<>();

        String selectQuery = "SELECT "+KEY_CONTACTNUMBER+" FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {


                numbers.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }

        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        return numbers;




    }

    public void deleteContact(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        String delete = "DELETE FROM " + TABLE_CONTACTS + " WHERE " + KEY_CONTACTNAME + " = \"" + name + "\"";
        db.execSQL(delete);



    }


    public ArrayList<Location> getAllLocationHistory(){
        ArrayList<Location> locations = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        double longitude;
        double latitude;
        double altitude;
        long timestamp;
        Location tmp = new Location("");

        if (cursor.moveToFirst()) {
            do {
                tmp = new Location("");

                longitude = cursor.getDouble(0);
                latitude = cursor.getDouble(1);
                altitude = cursor.getDouble(2);
                timestamp = cursor.getLong(3);

                tmp.setLongitude(longitude);
                tmp.setLatitude(latitude);
                tmp.setAltitude(altitude);
                tmp.setTime(timestamp);


                locations.add(tmp);

            } while (cursor.moveToNext());
        }

        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        return locations;
    }



}
