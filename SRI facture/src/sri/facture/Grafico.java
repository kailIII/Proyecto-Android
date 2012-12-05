/**
 * 
 */
package sri.facture;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;

import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


import sri.facture.bd.DatabaseHelper;

import com.sri.facture.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Bundle;

import android.view.Window;
import android.widget.LinearLayout;

/**
 * @author Ricardo X. Campozano
 *	Grafico usando la libreria acharengine
 */
public class Grafico extends Activity {
private GraphicalView mChartView;
	
	String id_user="0";
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.grafico);
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
        	id_user=extras.getString("id_user");                        
        }
    }
	
	protected void onResume() {
	    super.onResume();
	    if (mChartView == null) {
		    LinearLayout layout = (LinearLayout) findViewById(R.id.chart); 
	        
		 // Pie Chart Section Names
	        String[] code = new String[] {
	            "Alimentacion","Educacion","Salud","Vestimenta","Vivienda"
	        };
	        
	        DatabaseHelper usdbh =  new DatabaseHelper(this, "sri-facture.db", null, 1); 	 	  
	    	SQLiteDatabase db = usdbh.getWritableDatabase();
	    	Cursor c =  db.rawQuery( "select sum(d.total) as sum,d.id_categoria from factura f, deducible d where f._id=d.id_factura and f.id_usuario='"+id_user+"'  group by d.id_categoria order by d.id_categoria", null);
	    	Double ali=0.0;
	    	Double edu=0.0;
	    	Double sal=0.0;
	    	Double ves=0.0;
	    	Double viv=0.0;
	    	
	    	if(c.moveToFirst()){
	    		ali=Double.parseDouble(c.getString(c.getColumnIndex("sum")));
	    		c.moveToNext();
	    		edu=Double.parseDouble(c.getString(c.getColumnIndex("sum")));
	    		c.moveToNext();
	    		sal=Double.parseDouble(c.getString(c.getColumnIndex("sum")));
	    		c.moveToNext();
	    		ves=Double.parseDouble(c.getString(c.getColumnIndex("sum")));
	    		c.moveToNext();
	    		viv=Double.parseDouble(c.getString(c.getColumnIndex("sum")));
	    		c.moveToNext();
	    		
	    	}
	    	usdbh.close();
	        // Pie Chart Section Value
	        double[] distribution = { ali, edu, sal, ves, viv } ;
	 
	        // Color of each Pie Chart Sections
	        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.RED, Color.CYAN };
	 
	        // Instantiating CategorySeries to plot Pie Chart
	        CategorySeries distributionSeries = new CategorySeries(" Resumen grafico de Deducibles");
	        for(int i=0 ;i < distribution.length;i++){
	            // Adding a slice with its values and name to the Pie Chart
	            distributionSeries.add(code[i], distribution[i]);
	        }
	 
	        // Instantiating a renderer for the Pie Chart
	        DefaultRenderer defaultRenderer  = new DefaultRenderer();
	        for(int i = 0 ;i<distribution.length;i++){
	            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
	            seriesRenderer.setColor(colors[i]);
	            seriesRenderer.setDisplayChartValues(true);
	            // Adding a renderer for a slice
	            defaultRenderer.addSeriesRenderer(seriesRenderer);
	        }
	 
	        defaultRenderer.setChartTitle("Deducibles ");
	        defaultRenderer.setChartTitleTextSize(20);
	        defaultRenderer.setLabelsColor(Color.BLACK);
	        defaultRenderer.setZoomButtonsVisible(true);
	        
	        mChartView=ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
	        layout.addView(mChartView);
		    
	    }	
	}
}
