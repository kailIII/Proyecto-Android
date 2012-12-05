/**
 * 
 */
package sri.facture;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sri.facture.R;

/**
 * @author Ricardo X. Campozano
 *
 */
public class Menu extends Activity{
	
	String id_user="0";
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_menu);
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_user = extras.getString("id_user");
        }    
    }
	
	public void Administrar_Facturas(View view){
		Intent i = new Intent(this, ListaFactura.class);
    	i.putExtra("id_user", id_user);
        startActivity(i);
    }

	public void calTrib(View view){
		Intent i = new Intent(this, Grafico.class);
		i.putExtra("id_user", id_user);
        startActivity(i);

	}
	
	public void Reportes(View view){
		Intent i = new Intent(this, Reportes.class);
    	i.putExtra("id_user", id_user);
        startActivity(i);
	}
	
	public void Cuentas_de_usuario(View view){
		Intent i = new Intent(this, ViewUser.class);
    	i.putExtra("id_user", id_user);
        startActivity(i);

	}
	
	public void Salir(View view){
		Intent i = new Intent(this, Login.class);
	    startActivity(i);
	    finish();
	}
	

}
