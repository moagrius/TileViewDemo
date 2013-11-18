package com.qozix;

import java.util.ArrayList;
import java.util.List;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qozix.paths.AnimatedDrawablePath;
import com.qozix.tileview.TileView;

public class RealMapCustomPathTileViewActivity extends TileViewActivity
{

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        // we'll reference the TileView multiple times
        final TileView tileView = this.getTileView();

        // size of the original image at 100% scale
        tileView.setSize(3090, 2536);

        // note the order detail levels are added is irrelevant - these start at smallest, then largest, then medium
        tileView.addDetailLevel(0.25f, "tiles/boston/boston-125-%col%_%row%.jpg", "samples/boston-overview.jpg");
        tileView.addDetailLevel(1.0f, "tiles/boston/boston-500-%col%_%row%.jpg", "samples/boston-pedestrian.jpg");
        tileView.addDetailLevel(0.5f, "tiles/boston/boston-250-%col%_%row%.jpg", "samples/boston-overview.jpg");

        // some preferences...
        tileView.setMarkerAnchorPoints(-0.5f, -1.0f);
        tileView.setScaleLimits(0, 2);
        tileView.setTransitionsEnabled(false);

        // provide the corner coordinates for relative positioning
        tileView.defineRelativeBounds(42.379676, -71.094919, 42.346550, -71.040280);

        // get the default paint and style it.  the same effect could be achieved by passing a custom Paint instnace
        final Paint paint = tileView.getPathPaint();
        paint.setShadowLayer(4, 2, 2, 0x66000000);
        paint.setPathEffect(new CornerPathEffect(5));

        // draw some of the points
        this.drawAnimatedPaths(this.points.subList(0, 12), paint);

        // add markers for all the points
        // i'll pass the coordinate via setTag, so the marker can open a callout and center on itself when clicked
        // but really this could be done by grabbing LayoutParams just as well
        for (final double[] point : this.points)
        {
            // any view will do...
            final ImageView marker = new ImageView(this);
            // save the coordinate for centering and callout positioning
            marker.setTag(point);
            // give it a standard marker icon - this indicator points down and is centered, so we'll use appropriate anchors
            marker.setImageResource(R.drawable.maps_marker_blue);
            // on tap show further information about the area indicated
            // this could be done using a MarkerEventListener as well, which would prevent the touch
            // event from being consumed and would not interrupt dragging
            marker.setOnClickListener(this.markerClickListener);
            // add it to the view tree
            tileView.addMarker(marker, point[0], point[1]);
        }

        // let's start off framed to some point where the action is
        this.frameTo(42.360025, -71.065663);

    }

    private void drawAnimatedPaths(final List<double[]> positions, final Paint paint)
    {
        final TileView tileView = this.getTileView();

        // translate world co-ords to screen (and scale)
        final List<Point> translatedPoints = tileView.getPositionManager().translate(positions);

        // convert from list of points to path
        final Path path = tileView.getPathManager().getPathFromPoints(translatedPoints);

        // convert to animated path
        final AnimatedDrawablePath animatedPath = new AnimatedDrawablePath(tileView.getPathManager());
        animatedPath.setPaint(paint);
        animatedPath.setPath(path);

        // add path to draw list
        tileView.drawPath(animatedPath);
    }

    private final View.OnClickListener markerClickListener = new View.OnClickListener()
                                                           {

                                                               @Override
                                                               public void onClick(final View view)
                                                               {
                                                                   // get reference to the TileView
                                                                   final TileView tileView = RealMapCustomPathTileViewActivity.this
                                                                                   .getTileView();
                                                                   // we saved the coordinate in the marker's tag
                                                                   final double[] position = (double[]) view.getTag();
                                                                   // lets center the screen to that coordinate
                                                                   tileView.slideToAndCenter(position[0], position[1]);
                                                                   // create a simple callout
                                                                   final SampleCallout callout = new SampleCallout(
                                                                                   view.getContext());
                                                                   // add it to the view tree at the same position and offset as the marker that invoked it
                                                                   tileView.addCallout(callout, position[0],
                                                                                   position[1], -0.5f, -1.0f);
                                                                   // a little sugar
                                                                   callout.transitionIn();
                                                               }
                                                           };

    // a list of points roughly along the freedom trail in Boston
    private final ArrayList<double[]>  points              = new ArrayList<double[]>();
    {
        this.points.add(new double[]
        { 42.355503, -71.063883 });
        this.points.add(new double[]
        { 42.358480, -71.063736 });
        this.points.add(new double[]
        { 42.357125, -71.062320 });
        this.points.add(new double[]
        { 42.357420, -71.062025 });
        this.points.add(new double[]
        { 42.358099, -71.060195 });
        this.points.add(new double[]
        { 42.358274, -71.059914 });
        this.points.add(new double[]
        { 42.357797, -71.059639 });
        this.points.add(new double[]
        { 42.357643, -71.058387 });
        this.points.add(new double[]
        { 42.357198, -71.058204 });
        this.points.add(new double[]
        { 42.358769, -71.057806 });
        this.points.add(new double[]
        { 42.358798, -71.057052 });
        this.points.add(new double[]
        { 42.360174, -71.056435 });
        this.points.add(new double[]
        { 42.364047, -71.053808 });
        this.points.add(new double[]
        { 42.366495, -71.054320 });
        this.points.add(new double[]
        { 42.367330, -71.056245 });
        this.points.add(new double[]
        { 42.357571, -71.063247 });
        this.points.add(new double[]
        { 42.358914, -71.065826 });
        this.points.add(new double[]
        { 42.358959, -71.067922 });
        this.points.add(new double[]
        { 42.358601, -71.069754 });
        this.points.add(new double[]
        { 42.357840, -71.070747 });
        this.points.add(new double[]
        { 42.360107, -71.069324 });
        this.points.add(new double[]
        { 42.360217, -71.066614 });
        this.points.add(new double[]
        { 42.360186, -71.065391 });
        this.points.add(new double[]
        { 42.359999, -71.065127 });
        this.points.add(new double[]
        { 42.360025, -71.065663 });
        this.points.add(new double[]
        { 42.349781, -71.046353 });
    }
}
