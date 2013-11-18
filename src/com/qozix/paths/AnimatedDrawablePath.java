package com.qozix.paths;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Path;
import android.graphics.PathEffect;

import com.qozix.tileview.paths.PathManager;
import com.qozix.tileview.paths.drawables.SolidDrawablePath;

public class AnimatedDrawablePath extends SolidDrawablePath
{
    private static final long PATH_ANIMATION_DELAY = 200;

    // parent (or container) view of this path
    PathManager               mPathManager;

    // index of the last paint used
    private int               mPhase               = 20;

    // force redraw after delay (aka animate)
    private final Runnable    mReDrawPath          = new Runnable()
                                                   {
                                                       @Override
                                                       public void run()
                                                       {
                                                           AnimatedDrawablePath.this.mPathManager.invalidate();
                                                           AnimatedDrawablePath.this.mPathManager.postDelayed(this,
                                                                           AnimatedDrawablePath.PATH_ANIMATION_DELAY);
                                                       }
                                                   };

    public AnimatedDrawablePath(final PathManager pathManager)
    {
        super();
        this.mPathManager = pathManager;
    }

    /**
     * Extended draw method that "animates" the line by switching out the Paint's 
     * used to draw the line at specified delay. 
     */
    @Override
    public void draw(final Canvas canvas, final Path translatedPath)
    {
        if (this.mPhase == 20)
        {
            // first run, start the animation
            this.mPathManager.postDelayed(this.mReDrawPath, AnimatedDrawablePath.PATH_ANIMATION_DELAY);
        }

        // define how to draw the line
        final PathEffect effect = new DashPathEffect(new float[]
        { 20, 30 }, this.mPhase);
        this.getPaint().setPathEffect(effect);

        // toggle back and forth
        // this is a simple animation, it certainly can be changed and/or improved
        this.mPhase -= 10;

        canvas.drawPath(translatedPath, this.getPaint());
    }

}
