package sri.facture;




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
        	Cursor c =  db.rawQuery( "select * from usuario where user='"+usuario+"' and pass='"+pass+"';", null);
        	if ( c.moveToFirst() ) {
        		Intent i = new Intent(this, sri.facture.Menu.class );
                startActivity(i);
        	}
        	else{
        		Toast.makeText(this,"Usuario o clave incorrecta", Toast.LENGTH_SHORT).show();
        	}
        	usdbh.close();

    	}

    }
    
    public void registro(View view){
    	//Toast.makeText(this,"Redirigiendo a recuperar contraseña", Toast.LENGTH_SHORT).show();
    	Intent i = new Intent(this, NewUser.class);
        startActivity(i);
    }
    
    
}
