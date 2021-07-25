package com.vesam.barexamlibrary.utils.image;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager.PageTransformer;

public abstract class BaseTransformer implements PageTransformer {

    protected abstract void onTransform(View view, float position);

    @Override
    public void transformPage(@NonNull View view, float position) {
        onPreTransform(view, position);
        onTransform(view, position);
        onPostTransform();
    }


    private boolean hideOffscreenPages() {
        return true;
    }

    private boolean isPagingEnabled() {
        return false;
    }


    private void onPreTransform(View view, float position) {
        final float width = view.getWidth();

        view.setRotationX(0);
        view.setRotationY(0);
        view.setRotation(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setPivotX(0);
        view.setPivotY(0);
        view.setTranslationY(0);
        view.setTranslationX(isPagingEnabled() ? 0f : -width * position);

        if (hideOffscreenPages()) {
            view.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
        } else {
            view.setAlpha(1f);
        }
    }

    /**
     * Called each {@link #transformPage(View, float)} call after {@link #onTransform(View, float)} is finished.
     */
    private void onPostTransform() {
    }

}