package com.example.androidlocais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ListActivity {

    @Override
     public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Log.d("Estado atual de ", getClass().getName() + "= .onCreate");
        String[] Menu = new String[] { "Minha casa na cidade natal", "Minha casa em Viçosa", "Meu departamento",
                "Fechar aplicação"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Menu);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (position == 3) {
            finish();//opcao de fechar
            return;
        }
        String item = l.getItemAtPosition(position).toString();
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();

        Intent it = new Intent(getBaseContext(),Tela2.class);
        Bundle option = new Bundle();//criar um bundle com o numero da opcao pra realizar um switch
        option.putInt("opcao",position);     //no oncreate da activity com mapa
        option.putString("texto",item);

        it.putExtras(option);
        startActivity(it);
    }

    protected void onStart(){
        super.onStart();
        Log.d("Estado atual de ", getClass().getName() + "= .onStart");
    }

    protected void onResume(){
        super.onResume();
        Log.d("Estado atual de ", getClass().getName() + "= .onResume");
    }

    protected void onRestart(){
        super.onRestart();
        Log.d("Estado atual de ", getClass().getName() + "= .onRestart");
    }

    protected void onPause(){
        super.onPause();
        Log.d("Estado atual de ", getClass().getName() + "= .onPause");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.d("Estado atual de ", getClass().getName() + "= .onDestroy");
    }

}