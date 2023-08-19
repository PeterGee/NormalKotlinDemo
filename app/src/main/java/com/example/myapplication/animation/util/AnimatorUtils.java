package com.example.myapplication.animation.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @Author qipeng
 * @Date 2023/8/19
 * @Desc
 */
public final class AnimatorUtils {

    public static void doCartAnimator(Activity activity, ImageView imageView,
                                      View cartView, final ViewGroup parentView,
                                      final OnAnimatorListener listener) {
        if (activity == null || imageView == null || cartView == null || parentView == null) return;
        final ImageView goods = new ImageView(activity);
        goods.setPadding(1, 1, 1, 1);
        // 图片切割方式
        goods.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // 获取图片资源
        goods.setImageDrawable(imageView.getDrawable());
        // 设置RelativeLayout容器(这里必须设置RelativeLayout 设置LinearLayout动画会失效)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        // 把动画view添加到动画层
        parentView.addView(goods, params);

        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        parentView.getLocationInWindow(parentLocation);
        int startLoc[] = new int[2];
        imageView.getLocationInWindow(startLoc);
        int endLoc[] = new int[2];
        cartView.getLocationInWindow(endLoc);

        // 开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + imageView.getWidth() / 2;// 动画开始的X坐标
        float startY = startLoc[1] - parentLocation[1] + imageView.getHeight() / 2;//动画开始的Y坐标

        // 商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + cartView.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

        // 计算中间动画的插值坐标，绘制贝塞尔曲线
        Path path = new Path();
        // 移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        // 设置动画时间
        valueAnimator.setDuration(700);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.addUpdateListener(animation -> {
            // 更新动画
            float value = (Float) animation.getAnimatedValue();
            float[] currentPosition = new float[2];
            pathMeasure.getPosTan(value, currentPosition, null);
            goods.setTranslationX(currentPosition[0]);
            goods.setTranslationY(currentPosition[1]);
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                parentView.removeView(goods);
                if (listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public interface OnAnimatorListener {
        void onAnimationEnd(Animator animator);
    }
}
