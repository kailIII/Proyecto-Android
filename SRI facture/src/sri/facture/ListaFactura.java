/**
 * 
 */
package sri.facture;


import sri.facture.bd.DatabaseHelper;
import sri.facture.providers.FacturaProvider;

import com.sri.facture.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * @author Ricardo X. Campozano
 *
 */
public class ListaFactura extends ListActivity {
	
	String id_user="0"; 
	ListView miLista;
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.beta_lista_facturas);
	        
	        
	        Bundle extras = getIntent().getExtras();
	        if(extras!=null){
	            id_user = extras.getString("id_user");
	        }
	        
	        DatabaseHelper usdbh = new DatabaseHelper(this, "sri-facture.db", null, 1);
	        SQLiteDatabase db = usdbh.getWritableDatabase();
	 		
 	        Cursor c;
 	        c =  db.rawQuery( "select * from factura where id_usuario='"+id_user+"';", null);
	 	        
	 		String[] columns = new String[] {
	 				
	 				FacturaProvider.NUMERO,
	 				FacturaProvider.FECHA
	 					 				
	 				 };
	 		int[] views = new int[] {R.id.contactID, R.id.contactName };

	 		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.row, c, columns, views);
	 		miLista=this.getListView();
	 		miLista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 		//this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	 		miLista.setLongClickable(true);
	 		miLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	 		    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
	 		    	
	 		        return onLongListItemClick(v,pos,id);
	 		    }
	 		    
	 		});
	 		miLista.setAdapter(adapter);
	 		usdbh.close();
	        
	    }
	 
	 	public void Add(View view){
	 		Intent i = new Intent(this, NuevaFactura.class);
	 		i.putExtra("id_user", id_user);
	        startActivity(i);
	        finish();
	 	}
	 	
	 	public void Edit(View view){
	 		
	 	}
	 	
	 	public void Delete(View view){
	 		//Obtengo los elementos seleccionados de mi lista
	        SparseBooleanArray seleccionados = miLista.getCheckedItemPositions();
	 
	        if(seleccionados==null || seleccionados.size()==0){
	            //Si no había elementos seleccionados...
	            Toast.makeText(this,"No hay elementos seleccionados",Toast.LENGTH_SHORT).show();
	        }else{
	            //si los había, miro sus valores            
	           	 
	            //Recorro my "array" de elementos seleccionados
	            final int size=seleccionados.size();
	            for (int i=0; i<size; i++) {
	                //Si valueAt(i) es true, es que estaba seleccionado
	                if (seleccionados.valueAt(i)) {
	                    //en keyAt(i) obtengo su posición
	                	Cursor c=(Cursor)miLista.getItemAtPosition(seleccionados.keyAt(i));
	                	
	                    //resultado.append("El elemento "+c.getString(1)+" estaba seleccionado\n");
	                	Log.d("my tag2" ,"yo soy "+c.getString(0));
	                	getContentResolver().delete(Uri.parse("content://sriFacture.proveedor.Factura/factura/"+c.getString(0)),  null, null);
	                    //miLista.refreshDrawableState();
	                    
	                	
	                    Intent in = new Intent(this, ListaFactura.class);
	                    in.putExtra("id_user", id_user);
	                    startActivity(in);
	                    finish();
	                    
	                }
	            }
	            
	        }
	 	}
	 	
	 	protected boolean onLongListItemClick(View v, int pos, long id) {
	 		Cursor c=(Cursor)miLista.getItemAtPosition(pos);
	 		
	 		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
        	SQLiteDatabase db = usdbh.getWritableDatabase();
        	Cursor c2 =  db.rawQuery( "select * from deducible where id_factura='"+c.getString(c.getColumnIndex("_id"))+"'", null);
        	
        	double alimentacion=0;
        	double educacion=0;
        	double salud=0;
        	double vestimenta=0;
        	double vivienda=0;
        	if ( c2.moveToFirst() ) {
        		int rows= c2.getCount();
        		for (int i=0;i<rows;i++){
        			String item=c2.getString(c2.getColumnIndex("id_categoria"));
        			//Log.d("my tag" ,"item "+item);
        			String tot=c2.getString(c2.getColumnIndex("total"));
        			//Log.d("my tag" ,"total item "+tot);
        			Double total=Double.parseDouble(tot);
        			switch (Integer.parseInt(item)) {
						case 1:alimentacion=total;break;
						case 2:educacion=total;break;
						case 3:salud=total;break;
						case 4:vestimenta=total;break;
						case 5:vivienda=total;break;
							
						default:break;
					}
        			c2.moveToNext();
        		}
        	}
        	usdbh.close();
        	
        	//Log.d("my tag" ,"alimentacion "+alimentacion+" de "+c.getString(c.getColumnIndex("_id")));
        	double tded= alimentacion+educacion+salud+vestimenta+vivienda;
        	//Log.d("my tag" ,"total "+tded);
	 		Intent i = new Intent(this, EditFactura.class);
            i.putExtra("id_user", id_user);
            i.putExtra("id_factura", c.getString(c.getColumnIndex("_id")));
            i.putExtra("numero", c.getString(c.getColumnIndex("numero")));
	 		i.putExtra("fecha", c.getString(c.getColumnIndex("fecha")));
	 		i.putExtra("tgasto", c.getString(c.getColumnIndex("total_gasto")));
	 		i.putExtra("ciudad", c.getString(c.getColumnIndex("ciudad")));
	 		i.putExtra("rucp", c.getString(c.getColumnIndex("ruc_proveedor")));
	 		i.putExtra("prov", c.getString(c.getColumnIndex("n_proveedor")));
	 		i.putExtra("ta", alimentacion+"");
 	        i.putExtra("te", educacion+"");
 	        i.putExtra("ts", salud+"");
	        i.putExtra("tve", vestimenta+"");
	        i.putExtra("tvi", vivienda+"");
 	        i.putExtra("tded", tded+"");
            startActivity(i);
            //finish();
	 		return true;
	 	}

}
