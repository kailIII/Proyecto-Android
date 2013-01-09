/**
 * 
 */
package sri.facture;

import sri.facture.bd.DatabaseHelper;

import com.sri.facture.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

/**
 * @author Ricardo X. Campozano
 *
 */
public class ReportesView extends Activity {

	String id_user="0";
	int categoria=0;
	WebView wv;
	String reporte;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beta_view_reportes);
        
        wv =(WebView) findViewById(R.id.webView1);
        
     // Activo JavaScript
        wv.getSettings().setJavaScriptEnabled(true);
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_user = extras.getString("id_user");
            categoria= Integer.parseInt(extras.getString("categoria"));
        }   
        
        switch (categoria) {
			case 1: reporte("1","ALIMENTOS");break;
			case 2: reporte("2","EDUCACION");break;
			case 3: reporte("3","SALUD");break;
			case 4: reporte("4","VESTIMENTA");break;
			case 5: reporte("5","VIVIENDA");break;
			default:Log.d("error", "error generando html"); break;
			
		}
    }
	
	public void reporte(String categoria, String ncategoria){
		String fechas="";
		String table="";
		Double total=0.00;
		
		DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
    	SQLiteDatabase db = usdbh.getWritableDatabase();
    	Cursor c =  db.rawQuery( "select * from factura f, deducible d where f._id=d.id_factura and d.id_categoria='"+categoria+"' and f.id_usuario='"+id_user+"'  order by f.fecha", null);
    	if ( c.moveToFirst() ) {
    		
    		for(int i=0;i<c.getCount();i++){
	    		table+="<td>"+c.getString(c.getColumnIndex("numero"))+"</td>";
	    		table+="<td>"+c.getString(c.getColumnIndex("fecha"))+"</td>";
	    		table+="<td>"+c.getString(c.getColumnIndex("ruc_proveedor"))+"</td>";
	    		table+="<td>"+c.getString(c.getColumnIndex("total"))+"</td>";
	    		
	    		total+=Double.parseDouble(c.getString(c.getColumnIndex("total")));
	    		if(c.isFirst()){
	    			fechas+="Desde "+ c.getString(c.getColumnIndex("fecha"));
	    		}
	    		if(c.isLast()){
	    			fechas+="  hasta "+ c.getString(c.getColumnIndex("fecha"));
	    		}
	    		c.moveToNext();
    		}
    	}
		usdbh.close();		
		reporte="<html xmlns=\"http://www.w3.org/1999/xhtml\">"

		+"<head>"
			+"<title> REPORTE DE ALIMENTOS </title>"
			+"<link rel=\"stylesheet\" type=\"text/css\" href=\"estilo.css\" />"
			
			+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"/>"
			+"<meta name=\"author\"  content=\"SRI Facture\" />"
			+"<meta name=\"Language\" content=\"ES\" />"

		+"</head>"

		+"<body>"
		+"<div id=\"cabezera\">"
			+"<h1>REPORTE DE "+ncategoria+"</h1>"
			+"<h3>"+fechas+"</h3>"
		+"</div>"

		+"<div id=\"tabla\">"
			+"<table>"
			+"<tr>"
				+"<th scope=\"col\">Factura No</th>"
				+"<th scope=\"col\">Fecha</th>"
				+"<th scope=\"col\">Ruc del proveedor</th>"
				+"<th scope=\"col\">Deducible alimentos</th>"
			+"</tr>"
			+table
			+"</table>"
		+"</div>"

		+"<div id=\"total\">"
			+"<table>"
			+"<tr>"
				
				+"<th>Total:</td>"
				+"<td>"+total+"</td>"
			+"</tr>"
			+"</table>"
		+"</div>"

		+"<div id=\"footer\">"
			+"<p>Generado con SRI Facture</p>"
		+"</div>"

		+"</body>";
		
		wv.loadDataWithBaseURL("file:///android_asset/",reporte , "text/html", "utf-8",null);
	}
	
	@Override
	public void onBackPressed() {
		Intent dato = new Intent();
        //dato.putExtra("nombre","valor");//Dato que pasaremos a la actividad principal
        setResult(android.app.Activity.RESULT_OK,dato );
        //Nos devuelve a la actividad principal "ActividadPrincipal"
        finish();
	}
	
	public void Enviar(View view){
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("text/html");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SRI facture.- Reporte");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(reporte));
		startActivity(Intent.createChooser(emailIntent, "Email:"));
	}
}
