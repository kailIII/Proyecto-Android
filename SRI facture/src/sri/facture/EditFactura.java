/**
 * 
 */
package sri.facture;

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
public class EditFactura extends Activity {
	String id_user="0";
	String id_factura="0";
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
	        	id_factura=extras.getString("id_factura");
	        }
	        
	        llenarFormulario();
	        llenarDeducibles();
	        
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
	    	//updateDisplay();  
	    }
	
		public void llenarFormulario(){
			DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select * from factura where _id='"+id_factura+"'", null);
        	
        	if(c.moveToFirst()){
        		EditText numero=(EditText)findViewById(R.id.numero);
        		TextView fecha=(TextView)findViewById(R.id.dateDisplay);
        		EditText gasto=(EditText)findViewById(R.id.gasto);
        		//EditText ciudad=(EditText)findViewById(R.id.ciudad);
        		EditText rucp=(EditText)findViewById(R.id.rprov);
        		//EditText prov=(EditText)findViewById(R.id.prov);
        		
        		numero.setText(c.getString(c.getColumnIndex("numero")));
        		fecha.setText(c.getString(c.getColumnIndex("fecha")));
        		gasto.setText(c.getString(c.getColumnIndex("total_gasto")));
        		//ciudad.setText(c.getString(c.getColumnIndex("ciudad")));
        		rucp.setText(c.getString(c.getColumnIndex("ruc_proveedor")));
        		//prov.setText(c.getString(c.getColumnIndex("n_proveedor")));
        	}
        	usdbh.close();
        	
		}
		
		public void llenarDeducibles(){
			DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select * from deducible where id_factura='"+id_factura+"'", null);
        	
        	if(c.moveToFirst()){
        		int rows=c.getCount();
        		for(int i=0;i<rows;i++){
        			String categoria=c.getString(c.getColumnIndex("id_categoria"));
        			String total=c.getString(c.getColumnIndex("total"));
        			switch (Integer.parseInt(categoria)) {
						case 1:talimentacion=total;break;
						case 2:teducacion=total;break;
						case 3:tsalud=total;break;
						case 4:tvestimenta=total;break;
						case 5:tvivienda=total;break;					
					}
        			c.moveToNext();
        		}
        	}
        	
        	usdbh.close();
        	double al=Double.parseDouble(talimentacion);
        	double ed=Double.parseDouble(teducacion);
        	double sa=Double.parseDouble(tsalud);
        	double ve=Double.parseDouble(tvestimenta);
        	double vi=Double.parseDouble(tvivienda);
        	double td=al+ed+sa+ve+vi;
        	TextView tded=(TextView)findViewById(R.id.tdeducible);
        	tded.setText(td+"");
		}
	 
	 	public void Save(View view){
	 		String numero=((EditText) findViewById(R.id.numero)).getText().toString();
	 		String fecha=((TextView) findViewById(R.id.dateDisplay)).getText().toString();;
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
    			/*values.put(
    					FacturaProvider.CIUDAD,ciudad);*/
    			values.put(
    					FacturaProvider.RUC_PROVEEDOR,rucp);
    			/*values.put(
    					FacturaProvider.N_PROVEEDOR,prov);*/
    			values.put(
    					FacturaProvider.ID_USUARIO,id_user);
    			
    			String uriString="content://"+FacturaProvider.PROVIDER_NAME+"/"+FacturaProvider.ENTIDAD+"/"+id_factura;
				Uri CONTENT_URI = Uri.parse(uriString);
				getContentResolver().update(CONTENT_URI, values,null,null);
    			
    			guardarDeducibles();
    			Intent dato = new Intent();
		        setResult(android.app.Activity.RESULT_OK,dato );
		        finish();
	 		}
	 		
	 	}
	 	
	 	public void detDeducibles(View view){
	 		Intent i = new Intent(this, NuevoDeducible.class);
	 		i.putExtra("id_user", id_user);
	 		i.putExtra("alimentos", talimentacion);
	 		i.putExtra("educacion", teducacion);
	 		i.putExtra("salud", tsalud);
	 		i.putExtra("vestimenta", tvestimenta);
	 		i.putExtra("vivienda", tvivienda);
        	
		    startActivityForResult(i, 131);
		    
	 	}
	 	
	 	public void guardarDeducibles(){
	 		
	 		Log.d("my tag" ,id_factura+"");
	        
	        if(!(talimentacion.equals("0"))){
	        	deducibleItem(talimentacion,id_factura,"1");
	        }
	        if(!(teducacion.equals("0"))){
	        	deducibleItem(teducacion,id_factura,"2");
	        }
	        if(!(tsalud.equals("0"))){
	        	deducibleItem(tsalud,id_factura,"3");
	        }
	        if(!(tvestimenta.equals("0"))){
	        	deducibleItem(tvestimenta,id_factura,"4");
	        }
	        if(!(tvivienda.equals("0"))){
	        	deducibleItem(tvivienda,id_factura,"5");
	        }
	        
	        
	 	}
	 	
	 	public void deducibleItem(String item,String factura,String categoria){
	 		ContentValues values = new ContentValues();
			String id=idDeducible(categoria);
	 		
			values.put(
					DeducibleProvider.TOTAL,item);
			values.put(
					DeducibleProvider.ID_FACTURA,factura+"");
			values.put(
					DeducibleProvider.ID_CATEGORIA,categoria);
			
			String uriString="content://"+DeducibleProvider.PROVIDER_NAME+"/"+DeducibleProvider.ENTIDAD+"/"+id;
			Uri CONTENT_URI = Uri.parse(uriString);
			getContentResolver().update(CONTENT_URI, values,null,null);
	 	}
	 	
	 	public String idDeducible(String categoria){
	 		String id="0";
	 		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c =  db.rawQuery( "select _id from deducible where id_factura='"+id_factura+"' and id_categoria='"+categoria+"'", null);
        	if(c.moveToFirst()){
        		id=c.getString(c.getColumnIndex("_id"));
        	}
        	usdbh.close();
        	return id;
	 	}
	 	
	 	protected void onActivityResult(int requestCode,int resultCode, Intent pData)            
	    {
	        if ( requestCode == 131 )//Si el código de respuesta es igual al requestCode
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
