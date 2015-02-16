package net.skink.sqlsample;

import android.provider.BaseColumns;

/**
 * Created by john on 2/15/2015.
 */

/*
Define a Schema and Contract

One of the main principles of SQL databases is the schema: a formal declaration of how the
database is organized.  The schema is reflectd in the SQL statements that you use to create your
database.  You may find it helpful to create a companion class, known as a contract class, which
explicitly specifies the layout of your schema in a systematic and self-documenting way.

A contract class is a container for constants that define names for URIs, tables, and columns.
The contract class allows you to use the same constants across all the other classes
in the same package.  This lets you change a column name in one place and have it propagate
throughout your code.

A good way to organize a contract class is to put definitions that are global to your whole
database in the root level of the class.  Then create an inner class for each table that
enumerates its columns.

Note: By implementing the BaseColumns interface, your inner class can inherit a primary key
field called _ID that some Android classes such as cursor adapters will expect it to have.  Its
not required, but this can help your database work harmoniously with the Android Framework.

For example, this snippet defines the table name and column names for a single table:
 */
public final class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {};

    // Inner class that defines the tables contents
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    }

    /* Create a Database Using a SQL Helper
     Once you have defined how your database looks, you should implement methods  that
     create and maintain the database tables.  Here are some typical statements that create
     and delete a table:
      */

    /* I fscked up.  This was not supposed to be in its own class. It was supposed
    to be in the other class.  Rather than putting this class an inner class in
    the FeedReaderDbHelper, I'm just changing all the privates below which it uses
    to public so I can keep it like it is in a seperate file.
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // this one was private.
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_SUBTITLE + TEXT_TYPE +
            ")";
    // This one was private
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


}
