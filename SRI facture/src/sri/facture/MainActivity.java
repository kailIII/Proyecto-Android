package sri.facture;


/**
 * @author Ricardo X. Campozano
 * 			Vanessa Robles
 * 			Ana Mora
 *
 */

import sri.facture.bd.DatabaseHelper;

import com.sri.facture.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_login);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void ingreso(View view){
    	String usuario=((EditText) findViewById(R.id.user)).getText().toString();
    	String pass=((EditText) findViewById(R.id.pass)).getText().toString();
    	if(usuario.trim().isEmpty()||pass.trim().isEmpty()){
			Toast.makeText(this,"Faltan campos por completar", Toast.LENGTH_SHORT).show();
		}
    	
    	else{
    		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select _id from usuario where user='"+usuario+"' and pass='"+pass+"';", null);
        	if ( c.moveToFirst() ) {
        		String idUser=c.getString(c.getColumnIndex("_id"));
        		usdbh.close();
        		Intent i = new Intent(this, sri.facture.Menu.class );
        		i.putExtra("id_user", idUser);
                startActivity(i);
                finish();
        	}
        	else{
        		usdbh.close();
        		Toast.makeText(this,"Usuario o clave incorrecta", Toast.LENGTH_SHORT).show();
        	}
        	

    	}

    }
    
    public void registro(View view){
    	//Toast.makeText(this,"Redirigiendo a recuperar contraseña", Toast.LENGTH_SHORT).show();
    	Intent i = new Intent(this, NewUser.class);
        startActivityForResult(i, 1);
    }
    
    protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
    {
        if ( requestCode == 1 )//Si el código de respuesta es igual al requestCode
            {
            if (resultCode == Activity.RESULT_OK )//Si resultCode es igual a ok
                {
            	DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
            	SQLiteDatabase db = usdbh.getWritableDatabase();
            	Cursor c =  db.rawQuery( "select _id from usuario;", null);
            	if ( c.moveToLast() ) {
            		String idUser=c.getString(c.getColumnIndex("_id"));
            		usdbh.close();
            		Intent i = new Intent(this, sri.facture.Menu.class );
            		i.putExtra("id_user", idUser);
                    startActivity(i);
                    finish();
            	}
            	else{
            		usdbh.close();
            	}
                }
            }
    }
    
    
}
