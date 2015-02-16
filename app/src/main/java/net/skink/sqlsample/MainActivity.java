package net.skink.sqlsample;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        // Load the database with AsyncTask
        new LoadDatabase().execute();
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }


    private class LoadDatabase extends AsyncTask {
        // Do the long-running work here


        public void instantiateMyDb() {
            // This code needs to be put in a background thread
            // this is the context of the Activity.
            // Views do not have a context, they call the getContext() routine
            // to get the activity context.
            // JFD This calls the FeedReaderDbHelper() constructor
            FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getBaseContext());
            // At this point, the /data/data/net.skink.sqlsample/databases directory does
            // not exist

            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            // JFD This calls the FeedReaderDbHelper.onCreate() routine which
            // actually creates the sqlite database.  Note, before the
            // Once this occurs
            // you can use terminal to get an adb shell and change directories
            // to /data/data/net.skink.sqlsample/databases/
            //  Prior to the FileReaderDbHelper.onCreate() call the
            // databases subdir does not exist.
            //
            // Once the line above completes, you can do sqlite3 FeedReader.db
            // and see the entry table with sqlite> .tables command.
            // Earlier I was getting an exception in the OnCreate routine because
            // I had a mistake in CREATE_TABLES entry.
            // sqlite> .tables
            // sqlite> pragma table_info(entry);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, 1);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "my title");
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "my subtitle");

            // Insert the new row, returning the primary key value of the new row
            long newRowID;
            newRowID = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,
                    "null",
                    values);
            // JFD,    FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
            // The example code had this as the second parameter but it did not
            // have a value for this define in the sample code.  I'll add it later.


            // Now, you can do sqlite> select * from entry; and see the values we just
            // put.  Also, you can have the database open at the same time this code is running.

            db.close();
        }


        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Object doInBackground(Object[] params) {
            instantiateMyDb();
            return null;
        }
    }
}
