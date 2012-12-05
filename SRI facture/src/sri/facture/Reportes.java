/**
 * 
 */
package sri.facture;

import com.sri.facture.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * @author Ricardo X. Campozano
 *
 */
public class Reportes extends Activity {
	
	String id_user="0";
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportes);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_user = extras.getString("id_user");
        }   
        
    }
	
	public void Ralimentacion(View view){
		Intent i = new Intent(this, ReportesView.class);
    	i.putExtra("id_user", id_user);
    	i.putExtra("categoria", "1");
        startActivityForResult(i, 131);
	}
	
	public void Reducacion(View view){
		Intent i = new Intent(this, ReportesView.class);
    	i.putExtra("id_user", id_user);
    	i.putExtra("categoria", "2");
    	startActivityForResult(i, 132);
	}
	
	public void Rsalud(View view){
		Intent i = new Intent(this, ReportesView.class);
    	i.putExtra("id_user", id_user);
    	i.putExtra("categoria", "3");
    	startActivityForResult(i, 133);
	}
	
	public void Rvestimenta(View view){
		Intent i = new Intent(this, ReportesView.class);
    	i.putExtra("id_user", id_user);
    	i.putExtra("categoria", "4");
    	startActivityForResult(i, 134);
	}
	
	public void Rvivienda(View view){
		Intent i = new Intent(this, ReportesView.class);
    	i.putExtra("id_user", id_user);
    	i.putExtra("categoria", "5");
    	startActivityForResult(i, 135);
	}
	
	protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
    {
        if ( requestCode == 131||requestCode == 132||requestCode == 133||requestCode == 134||requestCode == 135 )//Si el código de respuesta es igual al requestCode
            {
            if (resultCode == Activity.RESULT_OK )//Si resultCode es igual a ok
                {
            		
                }
            }
    }
	
	
}
