package com.example.fabricio.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fabricio.myapplication.model.entities.Client;
import com.example.fabricio.myapplication.R;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.protocol.HTTP;

import java.util.List;


public class ClientListAcitvity extends AppCompatActivity {

    private ListView listViewClients;
    private Client client;
    private FloatingActionButton fabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        bindClientList();
        bindFab();

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshClientList();

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_list, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuEdit:
                Intent intent = new Intent(ClientListAcitvity.this, ClientPersistActivity.class);
                intent.putExtra(ClientPersistActivity.CLIENT_PARAM, (Parcelable) client);
                startActivity(intent);
                break;

            case R.id.menuDelete:
                new AlertDialog.Builder(ClientListAcitvity.this)
                        .setMessage(R.string.confirm_progress)
                        .setTitle(R.string.confirm_delete)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                client.delete();
                                refreshClientList();
                                Toast.makeText(ClientListAcitvity.this, R.string.success, Toast.LENGTH_LONG).show();
                            }
                        }).create().show();


                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuNewClient) {
            //Intent goToMainAcitivity = new Intent(ClientListAcitvity.this, ClientPersistActivity.class);
            //startActivity(goToMainAcitivity);
            // Create the text message with a string
            final Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Seu texto aqui...");
            sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

            // Create intent to show the chooser dialog
            final Intent chooser = Intent.createChooser(sendIntent, "Titulo Chooser");

            // Verify the original intent will resolve to at least one activity
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void refreshClientList() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClients.getAdapter();
        adapter.setClients(getClients());
        adapter.notifyDataSetInvalidated();
    }

    private void bindClientList() {
        List<Client> clients = getClients();
        listViewClients = (ListView) findViewById(R.id.listViewClients);
        final ClientListAdapter clientAdapter = new ClientListAdapter(ClientListAcitvity.this, clients);
        listViewClients.setAdapter(clientAdapter);

        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false;
            }
        });
        listViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getItemAtPosition(position);
                // Best Practices: http://stackoverflow.com/questions/4275678/how-to-make-phone-call-using-intent-in-android
                final Intent goToSOPhoneCall = new Intent(Intent.ACTION_CALL /* or Intent.ACTION_DIAL (no manifest permission needed) */);
                goToSOPhoneCall.setData(Uri.parse("tel:" + client.getPhone()));
                startActivity(goToSOPhoneCall);
            }
        });
        registerForContextMenu(listViewClients);
    }

    private List<Client> getClients() {
        List<Client> clientNames = Client.getAll();
        return clientNames;
    }

    private void bindFab() {
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(ClientListAcitvity.this, ClientPersistActivity.class);
                startActivity(goToMainActivity);
            }
        });
    }
}
