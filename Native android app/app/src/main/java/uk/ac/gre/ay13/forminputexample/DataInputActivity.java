package uk.ac.gre.ay13.forminputexample;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.lang.Object;

public class DataInputActivity extends Activity {

DBHelper myDB;
Button btnPicture;
final  int  REQUEST_CODE_GALLERY = 999;
ImageView imageView;
//validation for picture
boolean isclicked = false;
    // this shows the values that will be entered into the spinner
    private String[] status = {"Flat", "House", "Bungalow", "Apartment"};
    private String[] room = {"Studio", "One bedroom", "Two bedrooms", "Three bedrooms", "Four bedrooms"};
    private String[] type = {"n/a", "unfurnished", "furnished" , "semi - furnished"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);
        myDB = new DBHelper(this);
        DatePicker dp = (DatePicker) this.findViewById(R.id.propertyDate);
        dp.init(2018, 3, 1, null); // set to 1st June 1990 - note months start at 0
        Button btnNext = (Button) findViewById(R.id.ButtonNext);
        Button btnPicture = (Button) findViewById(R.id.pictureBtn);
        imageView  = (ImageView) findViewById(R.id.picture);

btnPicture.setOnClickListener(new View.OnClickListener() {
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
       ActivityCompat.requestPermissions(

            DataInputActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALLERY
        );
isclicked = true;
    }
});



/* Handle the event generated when the user clicks the next button */
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayNextAlert(); // call method defined later in the program
            }
        });

        Spinner sp = (Spinner) findViewById(R.id.propertyList);  // get reference
        sp.setAdapter(new ArrayAdapter<String>(this,
                R.layout.spinner_list_item, status));

        Spinner sp2 = (Spinner) findViewById(R.id.roomList);  // get reference
        sp2.setAdapter(new ArrayAdapter<String>(this,
                R.layout.spinner_list_item, room));

        Spinner sp3 = (Spinner) findViewById(R.id.typeList);  // get reference
        sp3.setAdapter(new ArrayAdapter<String>(this,
                R.layout.spinner_list_item, type));




    }

    //code borrowed from tutorial: https://www.youtube.com/watch?v=4bU9cZsJRLI&t=297s
    //gets permission in order to access the devices gallery
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
   Intent intent = new Intent(Intent.ACTION_PICK);
   intent.setType("image/*");
   startActivityForResult(intent, REQUEST_CODE_GALLERY);
}
else {
    Toast.makeText(getApplicationContext(), "you dont have any pictures", Toast.LENGTH_SHORT).show();
}
return;
        }

        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    //code borrowed from tutorial: https://www.youtube.com/watch?v=4bU9cZsJRLI&t=297s
//checks if galley has infomration
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_input, menu);
        return true;
    }

    @Override
/* Process the user's selection from the menu */
    public boolean onOptionsItemSelected(MenuItem item) {
// Find which menu item was selected
        switch (item.getItemId()) {  // item.getitemId() returns id of selected option

            case R.id.itemNext:  // Next item was selected
                displayNextAlert(); // call the same method as for the Next Button
                return true;




            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Utility method created to display a popup "toast" alert */
    private void popupToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //code borrowed from tutorial: https://www.youtube.com/watch?v=4bU9cZsJRLI&t=297s
    //this method converts the image into a byte so that it can be entered into the database as an byte


    private byte[] imageViewGobyte(ImageView picture) {


        Bitmap bitmap = ((BitmapDrawable) picture.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;


    }


    @TargetApi(Build.VERSION_CODES.M)
    private void displayNextAlert() {

        // Get what the user entered
        EditText forenameInput = (EditText) findViewById(R.id.EditTextName); // get reference
        DatePicker dobInput = (DatePicker) findViewById(R.id.propertyDate); // get reference
        TimePicker timeInput = (TimePicker) findViewById(R.id.propertyTime); // get reference
        EditText emailInput = (EditText) findViewById(R.id.EditTextSurname); // get reference
        EditText rentInput = (EditText) findViewById(R.id.RentText); // get reference
        EditText noteInput = (EditText) findViewById(R.id.NoteText); // get reference
        Spinner sp = (Spinner) findViewById(R.id.propertyList);  // get reference
        Spinner sp2 = (Spinner) findViewById(R.id.roomList);    // get reference
        Spinner sp3 = (Spinner) findViewById(R.id.typeList); // get reference
        imageView  = (ImageView) findViewById(R.id.picture);





        // check values entered before button



        final String strForename = forenameInput.getText().toString();
        final String strDOB = dobInput.getDayOfMonth() +
                "/" + (dobInput.getMonth() + 1) +
                "/" + dobInput.getYear();



        final String strTime = timeInput.getCurrentHour() +  ":"  + timeInput.getCurrentMinute();
        final String strEmail = emailInput.getText().toString();
        final String strRent = rentInput.getText().toString();
        final String strNote = noteInput.getText().toString();
        int row = sp.getSelectedItemPosition();  // get index of selected item
        final String strStatus = status[row];   // get text associated with selected item



        int row2 = sp2.getSelectedItemPosition();  // get index of selected item
        final String strRoom = room[row2];   // get text associated with selected item

        int row3 = sp3.getSelectedItemPosition();  // get index of selected item
        final String strType = type[row3];   // get text associated with selected item

        // this is the validation if the user does not enter the details that are required
        if (strForename.matches("")){
            Toast.makeText(this, "You did not enter a forename", Toast.LENGTH_SHORT).show();
            return;
        }
// this is the validation if the user does not enter the details that are required
        if (strEmail.matches("")){
            Toast.makeText(this, "You did not enter a surname", Toast.LENGTH_SHORT).show();
            return;
        }
// this is the validation if the user does not enter the details that are required
        if (strRent.matches("")){
            Toast.makeText(this, "You did not enter a rent rate", Toast.LENGTH_SHORT).show();
            return;
        }
// this is enters a automatic answer to the notes if the user has not put anything in the table.
        if (strNote.matches("")){
           noteInput.setText("n/a");
           return;
        }

       if ( isclicked == false){
           Toast.makeText(this, "You did not enter picture", Toast.LENGTH_SHORT).show();
            return;
       }




        // Create and display the Alert dialog
        new AlertDialog.Builder(this).setTitle("Details entered").setMessage(
                " Reporter named entered:\n  " + strForename + " " + strEmail  + "\n \n " + "Property type entered: \n " +  strStatus + "\n  \n"
                        + "Room type entered: \n " + strRoom + "\n \n "+ "Furniture type entered \n " + strType + "\n \n" + "Date of property entered: \n " + strDOB + "\n \n" + "Time of property entered: \n  "
                        + strTime + "\n \n " + "Rent of property entered: \n  " + strRent + "\n \n " + "Note on property entered: \n " + strNote + "\n " ).setNegativeButton("Back",


                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing - it will just close when clicked
                    }
                }).setPositiveButton("submit information",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        boolean isInserted =  myDB.insertProperty(strForename, strEmail, strStatus,strRoom, strType,  strDOB, strTime, strRent, strNote, imageViewGobyte(imageView));
//if the submit infomration is pressed it will enter all the information into the database and will make isinserted == true which will then express a message saying data inserted
                        if(isInserted = true){
                            Toast.makeText(DataInputActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        }
                        else   Toast.makeText(DataInputActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }).show();
    }



}
