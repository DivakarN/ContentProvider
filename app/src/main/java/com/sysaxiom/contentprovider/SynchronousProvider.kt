package com.sysaxiom.contentprovider

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.provider.ContactsContract

val mColumnProjection = arrayOf(
    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
    ContactsContract.Contacts.CONTACT_STATUS,
    ContactsContract.Contacts.HAS_PHONE_NUMBER
)

val mSelectionCluse = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?"

val mSelectionArguments = arrayOf("Amma")

val mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY

fun Activity.getContactWithProjection(){
    val contentResolver = contentResolver
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        mColumnProjection, null, null, null
    )
    if (cursor != null && cursor.count > 0) {
        val stringBuilderQueryResult = StringBuilder("")
        while (cursor.moveToNext()) {
            stringBuilderQueryResult.append(
                cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(
                    2
                ) + "\n"
            )
        }
        cursor.close()
        println("Projection $stringBuilderQueryResult")
    } else {
        println("No Contacts in device")
    }
}

fun Activity.getContactWithSelection(){
    val contentResolver = contentResolver
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        mColumnProjection, mSelectionCluse, mSelectionArguments, null
    )
    if (cursor != null && cursor.count > 0) {
        val stringBuilderQueryResult = StringBuilder("")
        while (cursor.moveToNext()) {
            stringBuilderQueryResult.append(
                cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(
                    2
                ) + "\n"
            )
        }
        cursor.close()
        println("Selection $stringBuilderQueryResult")
    } else {
        println("No Contacts in device")
    }
}

fun Activity.getContactWithSorting(){
    val contentResolver = contentResolver
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        mColumnProjection, null, null, mOrderBy
    )
    if (cursor != null && cursor.count > 0) {
        val stringBuilderQueryResult = StringBuilder("")
        while (cursor.moveToNext()) {
            stringBuilderQueryResult.append(
                cursor.getString(0) + " , " + cursor.getString(1) + " , " + cursor.getString(
                    2
                ) + "\n"
            )
        }
        cursor.close()
        println("Orderby $stringBuilderQueryResult")
    } else {
        println("No Contacts in device")
    }
}

fun Activity.removeContacts() {
    val whereClause =
        ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = 'Vishnu Tcs'"
    getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, whereClause, null)
}

fun Activity.addContactsViaIntents() {
    val tempContactText = "Unknown"
    if (tempContactText != null && tempContactText != "" && tempContactText!!.length > 0) {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.putExtra(ContactsContract.Intents.Insert.NAME, tempContactText)
        startActivity(intent)
    }
}

fun Activity.updateContact() {

    val oldValue = "Unknown"
    val newValue = "Unknown2"

    val where = ContactsContract.RawContacts.ACCOUNT_NAME + " = ? "
    val params = arrayOf(oldValue)

    val contentResolver = getContentResolver()
    val contentValues = ContentValues()
    contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, newValue)
    contentResolver.update(
        ContactsContract.RawContacts.CONTENT_URI,
        contentValues,
        where,
        params
    )
}

fun Activity.addContact() {
    val newValue = "Unknown3"
    val contentResolver = getContentResolver()
    val contentValues = ContentValues()
    contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, newValue)
    contentResolver.insert(
        ContactsContract.RawContacts.CONTENT_URI,
        contentValues
    )
}