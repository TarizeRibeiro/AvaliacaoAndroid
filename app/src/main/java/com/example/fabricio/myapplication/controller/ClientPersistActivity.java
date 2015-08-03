package com.example.fabricio.myapplication.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fabricio.myapplication.R;
import com.example.fabricio.myapplication.Util.AppUtil;
import com.example.fabricio.myapplication.Util.FormHelper;
import com.example.fabricio.myapplication.Util.Masks;
import com.example.fabricio.myapplication.model.entities.Client;
import com.example.fabricio.myapplication.model.entities.ClientAddress;
import com.example.fabricio.myapplication.model.services.CepService;
import com.melnykov.fab.FloatingActionButton;

public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";
    private Client client;

    EditText editTextName;
    EditText editTextAge;
    EditText editTextPhone;

    EditText editTextPostalCode;
    //Button buttonFindAddress;

    EditText editTextTypePatio;
    EditText editTextPatio;
    EditText editTextDistrict;
    EditText editTextCity;
    EditText editTextCountry;
    EditText editTextNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);

        setElements();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client = (Client) extras.getParcelable(CLIENT_PARAM);
            if (client == null) {
                throw new IllegalArgumentException();
            }
            bindClient(client);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSave) {
            if (FormHelper.requiredValidate(ClientPersistActivity.this, editTextAge, editTextName, editTextPhone, editTextPostalCode, editTextTypePatio, editTextPatio, editTextDistrict, editTextCity, editTextCountry, editTextNumber)) {
                bindClient();
                client.save();

                Toast.makeText(ClientPersistActivity.this, R.string.success, Toast.LENGTH_SHORT).show();

                ClientPersistActivity.this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void setElements() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPhone.addTextChangedListener(Masks.insert("(##)#####-####", editTextPhone));

        editTextPostalCode = (EditText) findViewById(R.id.editTextPostalCode);
        editTextPostalCode.addTextChangedListener(Masks.insert("##.###-###", editTextPostalCode));
        editTextTypePatio = (EditText) findViewById(R.id.editTextTypePatio);
        editTextPatio = (EditText) findViewById(R.id.editTextPatio);
        editTextDistrict = (EditText) findViewById(R.id.editTextDistrict);
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        editTextCountry = (EditText) findViewById(R.id.editTextCountry);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        editTextPostalCode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_search, 0);

        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //TODO: Explanation 2:
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });

        editTextPostalCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextPostalCode.getRight() - editTextPostalCode.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String postalWithoutMask = Masks.unmask(editTextPostalCode.getText().toString().trim());
                        if(postalWithoutMask.length() == 8) {
                            new getAddressByPostalCode().execute(postalWithoutMask);
                        }
                        else {
                            editTextTypePatio.setText("");
                            editTextPatio.setText("");
                            editTextDistrict.setText("");
                            editTextCity.setText("");
                            editTextCountry.setText("");
                            dlgCepInvalido();
                        }
                    }
                }
                return false;
            }
        });

        //bindButtonFindAddress();

    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*private void bindButtonFindAddress() {
        buttonFindAddress = (Button) findViewById(R.id.buttonFindAddress);
        buttonFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getAddressByPostalCode().execute(editTextPostalCode.getText().toString());
            }
        });
    }*/
    private void dlgCepInvalido(){
        new AlertDialog.Builder(ClientPersistActivity.this)
                .setMessage(getString(R.string.cep_invalid))
                .setTitle(getString(R.string.try_again))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();


    }

    private void bindClient() {
        if (client == null) {
            client = new Client();
        }
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setName(editTextName.getText().toString());
        client.setPhone(editTextPhone.getText().toString());

        client.setZipCode(editTextPostalCode.getText().toString());
        client.setTypePatio(editTextTypePatio.getText().toString());
        client.setPatio(editTextPatio.getText().toString());
        client.setDistrict(editTextDistrict.getText().toString());
        client.setCity(editTextCity.getText().toString());
        client.setCountry(editTextCountry.getText().toString());
        client.setNumber(Integer.valueOf(editTextNumber.getText().toString()));

    }

    private void bindClient(Client client) {
        editTextAge.setText(client.getAge().toString());
        editTextName.setText(client.getName());
        editTextPhone.setText(client.getPhone());

        editTextPostalCode.setText(client.getZipCode());
        editTextTypePatio.setText(client.getTypePatio());
        editTextPatio.setText(client.getPatio());
        editTextDistrict.setText(client.getDistrict());
        editTextCity.setText(client.getCity());
        editTextCountry.setText(client.getCountry());
        editTextNumber.setText(client.getNumber().toString());

    }


    private class getAddressByPostalCode extends AsyncTask<String, Void, ClientAddress> {

        private ProgressDialog progressDialog;

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            super.onPostExecute(clientAddress);

            editTextTypePatio.setText(clientAddress.getTipoDeLogradouro());
            editTextPatio.setText(clientAddress.getLogradouro());
            editTextDistrict.setText(clientAddress.getBairro());
            editTextCity.setText(clientAddress.getCidade());
            editTextCountry.setText(clientAddress.getEstado());

            progressDialog.dismiss();

        }


    }
}
