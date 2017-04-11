package ngoxuanhuy.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ngoxuanhuy on 4/11/2017.
 */

public class Product extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="productlist.db";
    private static final int SCHEMA_VERSION=1;

    public Product(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE products (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "quantity TEXT, " +
                "price TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAll() {
        return(getReadableDatabase()
                .rawQuery("SELECT _id, name, quantity, price " +
                                "FROM products ORDER BY name",
                        null));
    }

    public void insert(String name, String quantity,
                       String price) {
        ContentValues cv=new ContentValues();

        cv.put("name", name);
        cv.put("quantity", quantity);
        cv.put("price", price);

        getWritableDatabase().insert("products", "name", cv);
    }

    public String getName(Cursor c) {
        return(c.getString(1));
    }

    public String getQuantity(Cursor c) {
        return(c.getString(2));
    }

    public String getPrice(Cursor c) {
        return(c.getString(3));
    }

}
