# ContentProvider

Content Providers:
------------------

A Content Provider component supplies data from one application to others on request. Such requests are handled by the methods of the ContentResolver class.

For example, if Application "A"(Content Resolver) needs a contact information(Content Provider), then Application "A" should send a request by specifying its URI and get back the response as the query results.

A content provider manages access to a central repository of data.

Content providers are primarily intended to be used by other applications, which access the provider using a provider client object.

An application accesses the data from a content provider with a ContentResolver client object.

The ContentProvider uses the path part of the content URI to choose the table to access.

Add a content provider uses permission to the manifest.

Provides methods to insert, update, delete, query

Content URIs :
--------------

Whenever you want to access data from a content provider you have to specify a URI
There are two types of URIs:
	1) directory-based URIs - To access multiple elements of the same type (e.g. all songs of a band) - All CRUD-operations are possible with directory-based URIs.
	2) id-based URIs - If you want to access a specific element. You cannot create objects using an id-based URI — but reading, updating and deleting is possible.

Content Types : 
---------------

A content type consist of a two types such as media type and a subtype divided by a slash. A typical example is “image/png”.	


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

//Delete
fun Activity.removeContacts() {
    val whereClause =
        ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = 'Vishnu Tcs'"
    getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, whereClause, null)
}

//Add via intent
fun Activity.addContactsViaIntents() {
    val tempContactText = "Unknown"
    if (tempContactText != null && tempContactText != "" && tempContactText!!.length > 0) {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.type = ContactsContract.RawContacts.CONTENT_TYPE
        intent.putExtra(ContactsContract.Intents.Insert.NAME, tempContactText)
        startActivity(intent)
    }
}

//Update
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

//Insert
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

