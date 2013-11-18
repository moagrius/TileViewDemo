package com.qozix;

import java.util.HashMap;
import java.util.Map.Entry;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TileViewDemoActivity extends Activity
{

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        final HashMap<Integer, Class<?>> implementations = new HashMap<Integer, Class<?>>();
        implementations.put(R.id.show_image, LargeImageTileViewActivity.class);
        implementations.put(R.id.show_plans, BuildingPlansTileViewActivity.class);
        implementations.put(R.id.show_fiction, FictionalMapTileViewActivity.class);
        implementations.put(R.id.show_map, RealMapTileViewActivity.class);
        implementations.put(R.id.show_map_custom_paths, RealMapCustomPathTileViewActivity.class);

        for (final Entry<Integer, Class<?>> entry : implementations.entrySet())
        {
            final TextView label = (TextView) this.findViewById(entry.getKey());
            label.setTag(entry.getValue());
            label.setOnClickListener(this.labelClickListener);
        }

    }

    private final View.OnClickListener labelClickListener = new View.OnClickListener()
                                                          {
                                                              @Override
                                                              public void onClick(final View v)
                                                              {
                                                                  final Class<?> launchee = (Class<?>) v.getTag();
                                                                  final Intent intent = new Intent(
                                                                                  TileViewDemoActivity.this, launchee);
                                                                  TileViewDemoActivity.this.startActivity(intent);
                                                              }
                                                          };

}
