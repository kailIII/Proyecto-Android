/**
 * 
 */
package sri.facture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sri.facture.R;

/**
 * @author Ricardo X. Campozano
 *
 */
public class Menu extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_menu);
            
    }
	
	public void Administrar_Facturas(View view){
    	Intent i = new Intent(this, ListaFactura.class);
        startActivity(i);
    }

	public void calTrib(View view){
		Toast.makeText(this,"Redirigiendo", Toast.LENGTH_SHORT).show();

	}
	
	public void Reportes(View view){
		Toast.makeText(this,"Redirigiendo", Toast.LENGTH_SHORT).show();
	}
	
	public void Cuenta(View view){
		Toast.makeText(this,"Redirigiendo", Toast.LENGTH_SHORT).show();

	}
	
	public void Salir(View view){
		Intent i = new Intent(this, MainActivity.class);
	    startActivity(i);
	}

}
