package com.example.myapplication.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @Author qipeng
 * @Date 2023/8/19
 * @Desc
 */
public class CartEvaluator implements TypeEvaluator<PointF> {

    private PointF pointCur;
    private PointF mControlPoint;

    public CartEvaluator(PointF mControlPoint) {

        this.mControlPoint = mControlPoint;
        pointCur = new PointF();
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        // 将二阶贝塞尔曲线的计算公式直接代入即可
        pointCur.x = (1 - fraction) * (1 - fraction) * startValue.x
                + 2 * fraction * (1 - fraction) * mControlPoint.x + fraction * fraction * endValue.x;
        pointCur.y = (1 - fraction) * (1 - fraction) * startValue.y
                + 2 * fraction * (1 - fraction) * mControlPoint.y + fraction * fraction * endValue.y;

        return pointCur;
    }
}
