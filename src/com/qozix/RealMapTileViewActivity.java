package com.qozix;

import java.util.ArrayList;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qozix.tileview.TileView;

public class RealMapTileViewActivity extends TileViewActivity {

	@Override
	public void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );

		// we'll reference the TileView multiple times
		TileView tileView = getTileView();

		// size of the original image at 100% scale
		tileView.setSize( 3090, 2536 );

		// note the order detail levels are added is irrelevant - these start at smallest, then largest, then medium
		tileView.addDetailLevel( 0.25f, "tiles/boston/boston-125-%col%_%row%.jpg", "samples/boston-overview.jpg" );
		tileView.addDetailLevel( 1.0f, "tiles/boston/boston-500-%col%_%row%.jpg", "samples/boston-pedestrian.jpg" );
		tileView.addDetailLevel( 0.5f, "tiles/boston/boston-250-%col%_%row%.jpg", "samples/boston-overview.jpg" );

		// some preferences...
		tileView.setMarkerAnchorPoints( -0.5f, -1.0f );
		tileView.setScaleLimits( 0, 2 );
		tileView.setTransitionsEnabled( false );

		// provide the corner coordinates for relative positioning
		tileView.defineRelativeBounds( 42.379676, -71.094919, 42.346550, -71.040280 );

		// get the default paint and style it.  the same effect could be achieved by passing a custom Paint instnace
		Paint paint = tileView.getPathPaint();
		paint.setShadowLayer( 4, 2, 2, 0x66000000 );
		paint.setPathEffect( new CornerPathEffect( 5 ) );

		// draw some of the points
		tileView.drawPath( points.subList( 0, 12 ) );

		// add markers for all the points
		// i'll pass the coordinate via setTag, so the marker can open a callout and center on itself when clicked
		// but really this could be done by grabbing LayoutParams just as well
		for ( double[] point : points ) {
			// any view will do...
			ImageView marker = new ImageView( this );
			// save the coordinate for centering and callout positioning
			marker.setTag( point );
			// give it a standard marker icon - this indicator points down and is centered, so we'll use appropriate anchors
			marker.setImageResource( R.drawable.maps_marker_blue );
			// on tap show further information about the area indicated
			// this could be done using a MarkerEventListener as well, which would prevent the touch
			// event from being consumed and would not interrupt dragging
			marker.setOnClickListener( markerClickListener );
			// add it to the view tree
			tileView.addMarker( marker, point[0], point[1] );
		}
		
		// let's start off framed to some point where the action is
		frameTo( 42.360025, -71.065663 );

	}

	private View.OnClickListener markerClickListener = new View.OnClickListener() {

		@Override
		public void onClick( View view ) {
			// get reference to the TileView
			TileView tileView = getTileView();
			// we saved the coordinate in the marker's tag
			double[] position = (double[]) view.getTag();
			// lets center the screen to that coordinate
			tileView.slideToAndCenter( position[0], position[1] );
			// create a simple callout
			SampleCallout callout = new SampleCallout( view.getContext() );
			// add it to the view tree at the same position and offset as the marker that invoked it
			tileView.addCallout( callout, position[0], position[1], -0.5f, -1.0f );
			// a little sugar
			callout.transitionIn();
		}
	};

	// a list of points roughly along the freedom trail in Boston
	private ArrayList<double[]> points = new ArrayList<double[]>();
	{
		points.add( new double[] { 42.355503, -71.063883 } );
		points.add( new double[] { 42.358480, -71.063736 } );
		points.add( new double[] { 42.357125, -71.062320 } );
		points.add( new double[] { 42.357420, -71.062025 } );
		points.add( new double[] { 42.358099, -71.060195 } );
		points.add( new double[] { 42.358274, -71.059914 } );
		points.add( new double[] { 42.357797, -71.059639 } );
		points.add( new double[] { 42.357643, -71.058387 } );
		points.add( new double[] { 42.357198, -71.058204 } );
		points.add( new double[] { 42.358769, -71.057806 } );
		points.add( new double[] { 42.358798, -71.057052 } );
		points.add( new double[] { 42.360174, -71.056435 } );
		points.add( new double[] { 42.364047, -71.053808 } );
		points.add( new double[] { 42.366495, -71.054320 } );
		points.add( new double[] { 42.367330, -71.056245 } );
		points.add( new double[] { 42.357571, -71.063247 } );
		points.add( new double[] { 42.358914, -71.065826 } );
		points.add( new double[] { 42.358959, -71.067922 } );
		points.add( new double[] { 42.358601, -71.069754 } );
		points.add( new double[] { 42.357840, -71.070747 } );
		points.add( new double[] { 42.360107, -71.069324 } );
		points.add( new double[] { 42.360217, -71.066614 } );
		points.add( new double[] { 42.360186, -71.065391 } );
		points.add( new double[] { 42.359999, -71.065127 } );
		points.add( new double[] { 42.360025, -71.065663 } );
		points.add( new double[] { 42.349781, -71.046353 } );
	}
}
