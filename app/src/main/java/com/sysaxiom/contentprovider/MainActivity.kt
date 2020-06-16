package com.sysaxiom.contentprovider

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

const val REQUEST_CONTACT_PERMISSION = 0

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CONTACT_PERMISSION)
        }

        if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_CONTACTS), REQUEST_CONTACT_PERMISSION)
        }

        //Synchronous action
        getContactWithProjection()
        getContactWithSelection()
        getContactWithSorting()

        //Asynchronous Action
        LoaderManager.getInstance(this).initLoader(1,null,this)

        //Delete Action
        removeContacts()

        //Add Action
        addContact()

        //Update Action
        updateContact()

        //Add via intent
        addContactsViaIntents()
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this@MainActivity, ContactsContract.Contacts.CONTENT_URI,
            mColumnProjection, null, null, null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            val stringBuilderQueryResult = StringBuilder("")
            //cursor.moveToFirst();
            while (cursor.moveToNext()) {
                stringBuilderQueryResult.append(
                    cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(
                        2
                    ) + "\n"
                )
            }
            println(stringBuilderQueryResult.toString())
        } else {
            println("No Contacts in device")
        }
        cursor.close()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

}
