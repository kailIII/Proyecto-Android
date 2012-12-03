/**
 * 
 */
package sri.facture;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.DeducibleProvider;
import sri.facture.providers.FacturaProvider;
import sri.facture.providers.UsuarioProvider;

import com.sri.facture.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class NuevaFactura extends Activity {
	String id_user="0";
	String talimentacion="0";
    String teducacion="0"; 
    String tsalud="0"; 
    String tvestimenta="0"; 
    String tvivienda="0"; 
    
    private TextView mDateDisplay;    
    private Button mPickDate;    
    private int mYear;    
    private int mMonth;    
    private int mDay;    
    static final int DATE_DIALOG_ID = 0;
    
 // the callback received when the user "sets" the date in the dialog    
    private DatePickerDialog.OnDateSetListener mDateSetListener =            
    	new DatePickerDialog.OnDateSetListener() {                
    	public void onDateSet(DatePicker view, int year,                                       
    			int monthOfYear, int dayOfMonth) {                    
    		mYear = year;                    
    		mMonth = monthOfYear;                    
    		mDay = dayOfMonth;                    
    		updateDisplay();                
    		}            
    	};
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_nueva_factura);  
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	        	id_user=extras.getString("id_user");                        
	        }
	        
	     // capture our View elements        
	    	mDateDisplay = (TextView) findViewById(R.id.dateDisplay);        
	    	mPickDate = (Button) findViewById(R.id.pickDate);        
	    	// add a click listener to the button        
	    	mPickDate.setOnClickListener(new View.OnClickListener() 
	    		{            
	    		public void onClick(View v) {                
	    			showDialog(DATE_DIALOG_ID);            
	    			}        
	    		});        
	    	// get the current date        
	    	final Calendar c = Calendar.getInstance();        
	    	mYear = c.get(Calendar.YEAR);        
	    	mMonth = c.get(Calendar.MONTH);        
	    	mDay = c.get(Calendar.DAY_OF_MONTH);        
	    	// display the current date (this method is below)        
	    	updateDisplay();    
	        	        
	    }
	 
	 	public void Save(View view){
	 		String numero=((EditText) findViewById(R.id.numero)).getText().toString();
	 		String fecha=((TextView) findViewById(R.id.dateDisplay)).getText().toString();
	 		Log.d("fecha", "aqui la fecha: "+fecha);
	 		
	 		String tgasto=((EditText) findViewById(R.id.gasto)).getText().toString();
	 		//String tdeducible=((EditText) findViewById(R.id.deducible)).getText().toString();
	 		//String ciudad=((EditText) findViewById(R.id.ciudad)).getText().toString();
	 		String rucp=((EditText) findViewById(R.id.rprov)).getText().toString();
	 		//String prov=((EditText) findViewById(R.id.prov)).getText().toString();
	 		//insert into factura values('1','22','22','2007/05/12','333','44','ciud','nlo','1');
	 		if(numero.trim().isEmpty()){
				Toast.makeText(this,"Faltan campos por completar", Toast.LENGTH_SHORT).show();
			}
	 		else{
	 			ContentValues values = new ContentValues();
				
    			values.put(
    					FacturaProvider.NUMERO,numero);
    			values.put(
    					FacturaProvider.FECHA,fecha);
    			values.put(
    					FacturaProvider.TOTAL_GASTO,tgasto);
    			values.put(
    					FacturaProvider.CIUDAD,"");
    			values.put(
    					FacturaProvider.RUC_PROVEEDOR,rucp);
    			values.put(
    					FacturaProvider.N_PROVEEDOR,"");
    			values.put(
    					FacturaProvider.ID_USUARIO,id_user);
    			
    			Uri uriNuew = getContentResolver().insert(FacturaProvider.CONTENT_URI, values);
    			
    			
    			//IO implemento
    			managedQuery(uriNuew, null, null, null, null);
    			
    			guardarDeducibles();
    			Intent dato = new Intent();
		        setResult(android.app.Activity.RESULT_OK,dato );
		        finish();
	 		}
	 		
	 	}
	 	
	 	public void detDeducibles(View view){
	 		Intent i = new Intent(this, NuevoDeducible.class);
	 		
		    startActivityForResult(i, 13);
		    
	 	}
	 	
	 	public void guardarDeducibles(){
	 		
	 		int id=ultimoId();
	        Log.d("my tag" ,id+"");
	        
	        if(!(talimentacion.equals("0"))){
	        	deducibleItem(talimentacion,id,"1");
	        }
	        if(!(teducacion.equals("0"))){
	        	deducibleItem(teducacion,id,"2");
	        }
	        if(!(tsalud.equals("0"))){
	        	deducibleItem(tsalud,id,"3");
	        }
	        if(!(tvestimenta.equals("0"))){
	        	deducibleItem(tvestimenta,id,"4");
	        }
	        if(!(tvivienda.equals("0"))){
	        	deducibleItem(tvivienda,id,"5");
	        }
	        
	        
	 	}
	 	
	 	public void deducibleItem(String item,int factura,String categoria){
	 		ContentValues values = new ContentValues();
			
			values.put(
					DeducibleProvider.TOTAL,item);
			values.put(
					DeducibleProvider.ID_FACTURA,factura+"");
			values.put(
					DeducibleProvider.ID_CATEGORIA,categoria);
			
			Uri uriNuew = getContentResolver().insert(DeducibleProvider.CONTENT_URI, values);
			managedQuery(uriNuew, null, null, null, null);
	 	}
	 	
	 	public int ultimoId(){
	 		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select _id from factura where id_usuario='"+id_user+"'", null);
        	String proxima="1";
        	if ( c.moveToLast()) {
        		proxima=c.getString(c.getColumnIndex("_id"));
        		Log.d("my tag" ,"contar "+proxima);
        	}
        	usdbh.close();
        	return Integer.parseInt(proxima);
	 		
	 	}
	 	
	 	protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
	    {
	        if ( requestCode == 13 )//Si el código de respuesta es igual al requestCode
	            {
	            if (resultCode == Activity.RESULT_OK )//Si resultCode es igual a ok
	                {
		            	talimentacion = pData.getExtras().getString("ta");
		            	teducacion = pData.getExtras().getString("te");
		            	tsalud = pData.getExtras().getString("ts");
		            	tvestimenta = pData.getExtras().getString("tve");
		            	tvivienda = pData.getExtras().getString("tvi");
	            	    String tdeducible = pData.getExtras().getString("tded");//Obtengo el string de la subactividad
	            		TextView ded=(TextView)findViewById(R.id.tdeducible);
	            		ded.setText(tdeducible);
	                }
	            }
	    }
	 	
	 // updates the date in the TextView    
	    private void updateDisplay() {        
	    	mDateDisplay.setText(            
	    			new StringBuilder()
	    			.append(mYear).append("/")
	    			// Month is 0 based so add 1 
	    			.append(mMonth + 1).append("/") 
	    			.append(mDay).append(" "));   
	    			                   
	    			                                  
	    			  
	    }
	    
	    
	    @Override
	    protected Dialog onCreateDialog(int id) {    
	    	switch (id) {   
	    		case DATE_DIALOG_ID:        
	    			return new DatePickerDialog(this,                    
	    					mDateSetListener,                    
	    					mYear, mMonth, mDay);    
	    			}    
	    	return null;
	    	}
	 	
	 	
	 	
	 	

}
