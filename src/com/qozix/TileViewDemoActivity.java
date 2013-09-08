package com.qozix;

import java.util.HashMap;
import java.util.Map.Entry;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TileViewDemoActivity extends Activity {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );
		
		HashMap<Integer, Class<?>> implementations = new HashMap<Integer, Class<?>>();
		implementations.put( R.id.show_image, LargeImageTileViewActivity.class );
		implementations.put( R.id.show_plans, BuildingPlansTileViewActivity.class );
		implementations.put( R.id.show_fiction, FictionalMapTileViewActivity.class );
		implementations.put( R.id.show_map, RealMapTileViewActivity.class );
		
		for (Entry<Integer, Class<?>> entry : implementations.entrySet()) {
			TextView label = (TextView) findViewById( entry.getKey() );
			label.setTag( entry.getValue() );
			label.setOnClickListener( labelClickListener );
		}
		
	}
	
	private View.OnClickListener labelClickListener = new View.OnClickListener() {
		@Override
		public void onClick( View v ) {
			Class<?> launchee = (Class<?>) v.getTag();
			Intent intent = new Intent( TileViewDemoActivity.this, launchee );
			startActivity( intent );
		}
	};


}
