package net.skink.sqlsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by john on 2/15/2015.
 */

/* To create the required methods, move the cursor to the class name
and type CTRL-i to insert stubs or CTRL-o to override
 */

/* Just like files that you save on the device's internal storage, Android stores your
    database in private disk space that's associated application.  Your data is secure,
    because by default this area is not accessible to other
    applications.

    A useful set of APIs is available in the SQLiteOpenHelper Class.  When you use this class
    to obtain references to your database, the system performs the potentially long-running
    operations of creating and updating the database only when needed and not during app
    startup.  All you need to do is call getWritableDatabase() or getReadableDatabase().

    Note: Because they can be long running, be sure that you call getWritableDatabase() or
    getReadableDatabase() in a background thread, such as with AsyncTask or IntentService.

    To use SQLiteOpenHelper, create a subclass that overrides the onCreate(), onUpgrade()
    and onOpen() callback methods.  You may also want to implement onDowngrade(), but its not
    required.

    For example, here's an implementation of SQLiteOpenHelper that uses some sort of the commands
    shown above:

     */


public class FeedReaderDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // JFD at this time, FeedReader.db exists but if you do
        // .tables on it, its empty.
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over.
        db.execSQL(FeedReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    /**
     * Called when the database needs to be downgraded. This is strictly similar to
     * {@link #onUpgrade} method, but is called whenever current version is newer than requested one.
     * However, this method is not abstract, so it is not mandatory for a customer to
     * implement it. If not overridden, default implementation will reject downgrade and
     * throws SQLiteException
     * <p/>
     * <p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
