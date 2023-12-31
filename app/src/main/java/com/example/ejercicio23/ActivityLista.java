package com.example.ejercicio23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ejercicio23.Configuraciones.Fotografia;
import com.example.ejercicio23.Configuraciones.SQLiteConexion;
import com.example.ejercicio23.Configuraciones.Transacciones;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
    Button btnAtras;
    ArrayList<Fotografia> listaFotos= new ArrayList<Fotografia>();
    ImageView imageView;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        SQLiteDatabase db = conexion.getWritableDatabase();
        String sql = "SELECT * FROM fotografia";
        Cursor cursor = db.rawQuery(sql, new String[] {});

        while (cursor.moveToNext()){
            listaFotos.add(new Fotografia(cursor.getInt(0),cursor.getString(1) , cursor.getBlob(2)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        AdaptadorFotografia adaptador = new AdaptadorFotografia(this);
        list = findViewById(R.id.lista);
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerFoto(i);
            }
        });

        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    private void obtenerFoto( int id) {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Fotografia lista_Fotografia = null;
        listaFotos = new ArrayList<Fotografia>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tblFotografias,null);

        while (cursor.moveToNext())
        {
            lista_Fotografia = new Fotografia();
            lista_Fotografia.setId(cursor.getInt(0));
            lista_Fotografia.setDescripcion(cursor.getString(1));
            listaFotos.add(lista_Fotografia);
        }
        cursor.close();
        Fotografia fotografia = listaFotos.get(id);

    }

    class AdaptadorFotografia extends ArrayAdapter<Fotografia> {

        AppCompatActivity appCompatActivity;

        AdaptadorFotografia(AppCompatActivity context) {
            super(context, R.layout.fotografia, listaFotos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.fotografia, null);

            imageView = item.findViewById(R.id.imageView);

            SQLiteDatabase db = conexion.getWritableDatabase();

            String sql = "SELECT * FROM fotografia";

            Cursor cursor = db.rawQuery(sql, new String[] {});
            Bitmap bitmap = null;
            TextView textView1 = item.findViewById(R.id.textView);

            if (cursor.moveToNext()){
                textView1.setText(listaFotos.get(position).getDescripcion());
                byte[] blob = listaFotos.get(position).getFoto();
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                bitmap = BitmapFactory.decodeStream(bais);
                imageView.setImageBitmap(bitmap);
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();

            return(item);
        }
    }
}