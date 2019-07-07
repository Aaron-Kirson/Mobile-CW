package uk.ac.gre.ay13.forminputexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

/**
 * Created by aaron on 26/03/2018.
 */

public class DBHelper extends SQLiteOpenHelper{
    //gets the variables
public static final String Database_Name = "Property.db";
    public static final String Table_Name = "Property_table";
    public static final String col_1 = "forename";
    public static final String col_2 = "surname";
    public static final String col_3 = "type";
    public static final String col_4 = "bedroom";
    public static final String col_5 = "furniture";
    public static final String col_6 = "date";
    public static final String col_7 = "time";
    public static final String col_8 = "rent";
    public static final String col_9 = "notes";
    public static final String col_10 = "picture";





//creates settings for database
    public DBHelper(Context context) {
        super(context, Database_Name, null, 1);

    }
//creates table for database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, forename TEXT, surname TEXT, type TEXT, bedroom TEXT, furniture TEXT, date TEXT, time TEXT, rent INTEGER, notes TEXT, picture BLOB)");
    }
//drops table if the table_name already exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +Table_Name);
        onCreate(db);

    }
//code borrow from tutorial: https://www.youtube.com/watch?v=cp2rL3sAFmI

    //add content to database
    public boolean insertProperty(String forename, String surname, String type, String bedroom, String furniture, String date, String time, String rent, String notes, byte[] picture)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1, forename);
        contentValues.put(col_2, surname);
        contentValues.put(col_3, type);
        contentValues.put(col_4, bedroom);
        contentValues.put(col_5, furniture);
        contentValues.put(col_6, date);
        contentValues.put(col_7, time);
        contentValues.put(col_8, rent);
        contentValues.put(col_9, notes);
        contentValues.put(col_10, picture);

       long result = db.insert(Table_Name, null, contentValues);

if (result == -1){
    return  false;
}else {
    return  true;
}
    }
}
