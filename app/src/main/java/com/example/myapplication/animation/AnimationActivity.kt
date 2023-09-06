package com.example.myapplication.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.R
import com.example.myapplication.okHttp.util.LogUtil
import kotlinx.android.synthetic.main.activity_animation.*

/**
 * @Author qipeng
 * @Date 2022/9/6
 * @Desc 动画
 */
class AnimationActivity : FragmentActivity() {
    private var showingBack = false
    private var currentAnimator: Animator? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        // valueAnim()
        // objectAnim()
        // animationSet()
        // keyFrameAnim()
        // viewPropertyAnim()
        // propertyValueHolder()
        // directAnim()
        // valueAnimByCode()
        // cardFragment()
        // scaleAnim()
        // springAnim()

        mapTest()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun mapTest() {
        val mapTest=HashMap<String,Int>()
        mapTest["语文"] = 1
        mapTest["数学"] = 2
        mapTest["英语"] = 3
        mapTest["历史"] = 4
        mapTest["政治"] = 5
        mapTest["地理"] = 6

        mapTest.forEach { (k, v) ->
            LogUtil.D("tag","key===$k  value=== $v")
        }
    }

    /**
     * 弹簧动画
     */
    private fun springAnim() {
         SpringAnimation(image_dog,DynamicAnimation.TRANSLATION_X,0f)

        val (animX, animY) = image_dog.let { view1 ->
            SpringAnimation(view1, DynamicAnimation.TRANSLATION_X) to SpringAnimation(
                view1,
                DynamicAnimation.TRANSLATION_Y
            )
        }
        val (anim2X, anim2Y) = image_big.let { view2 ->
            SpringAnimation(view2, DynamicAnimation.TRANSLATION_X) to SpringAnimation(
                view2,
                DynamicAnimation.TRANSLATION_Y
            )
        }

        // 添加监听
        animX.addUpdateListener { _, value, _ ->
            anim2X.animateToFinalPosition(value)
        }
        animY.addUpdateListener { _, value, _ ->
            anim2Y.animateToFinalPosition(value)
        }


    }

    /**
     * 缩放动画
     */
    private fun scaleAnim() {
        image_dog.setOnClickListener {
            zoomImageFromThumb(image_dog, R.drawable.dog02)
        }
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        val expandedImageView: ImageView = findViewById(R.id.image_big)
        expandedImageView.setImageResource(imageResId)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        findViewById<View>(R.id.container)
            .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(
                    ObjectAnimator.ofFloat(
                        expandedImageView,
                        View.Y,
                        startBounds.top,
                        finalBounds.top
                    )
                )
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = 500
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = 500
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }


    private fun cardFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, CardFrontFragment())
            .commit()
        flipCard()
    }

    private fun flipCard() {
        if (showingBack) {
            supportFragmentManager.popBackStack()
            return
        }

        // Flip to the back.

        showingBack = true

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        supportFragmentManager.beginTransaction()

            // Replace the default fragment animations with animator resources
            // representing rotations when switching to the back of the card, as
            // well as animator resources representing rotations when flipping
            // back to the front (e.g. when the system Back button is pressed).
            .setCustomAnimations(
                R.animator.card_flip_right_in,
                R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,
                R.animator.card_flip_left_out
            )

            // Replace any fragments currently in the container view with a
            // fragment representing the next page (indicated by the
            // just-incremented currentPage variable).
            .replace(R.id.container, CardBackFragment())

            // Add this transaction to the back stack, allowing users to press
            // Back to get to the front of the card.
            .addToBackStack(null)

            // Commit the transaction.
            .commit()
    }


    /**
     * A fragment representing the front of the card.
     */
    class CardFrontFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View = inflater.inflate(R.layout.layout_img, container, false)
    }

    /**
     * A fragment representing the back of the card.
     */
    class CardBackFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View = inflater.inflate(R.layout.layout_fragment, container, false)
    }


    private fun valueAnimByCode() {
        (AnimatorInflater.loadAnimator(this, R.animator.value_animator) as ValueAnimator).apply {
            addUpdateListener { updateAnimation ->
                image_dog.translationX = updateAnimation.animatedValue as Float
            }
            start()
        }

    }

    private fun directAnim() {
        image_dog.animate().x(100f).y(100f)
    }

    private fun propertyValueHolder() {
        val pvhX = PropertyValuesHolder.ofFloat("x", 50f)
        val pvhY = PropertyValuesHolder.ofFloat("y", 150f)
        ObjectAnimator.ofPropertyValuesHolder(image_dog, pvhX, pvhY).start()
    }

    private fun viewPropertyAnim() {
        // objectAnimator
        val animX = ObjectAnimator.ofFloat(image_dog, "x", 50f)
        val animY = ObjectAnimator.ofFloat(image_dog, "y", 100f)
        AnimatorSet().apply {
            playTogether(animX, animY)
            start()
        }
    }

    private fun keyFrameAnim() {
        val fk0 = Keyframe.ofFloat(0f, 0f)
        val fk1 = Keyframe.ofFloat(.5f, 360f)
        val fk2 = Keyframe.ofFloat(1f, 0f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", fk0, fk1, fk2)
        ObjectAnimator.ofPropertyValuesHolder(image_dog, pvhRotation).apply {
            duration = 2000
        }
    }

    // animationSet
    private fun animationSet() {
        val translateXAnimator = ObjectAnimator.ofFloat(image_dog, "translationX", 0f, 100f).apply {
            duration = 250
        }
        val animatorSet = AnimatorSet().apply {
            play(translateXAnimator)
        }
        val fadeAnimator = ObjectAnimator.ofFloat(image_dog, "alpha", 1f, 0.5f).apply {
            duration = 250
        }
        val translationY = ObjectAnimator.ofFloat(image_dog, "translationY", 0f, -100f).apply {
            duration = 250
        }
        AnimatorSet().apply {
            play(animatorSet).before(fadeAnimator).with(translationY)
            start()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    LogUtil.D(log = "onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    LogUtil.D(log = "onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator?) {
                    LogUtil.D(log = "onAnimationCancel")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    LogUtil.D(log = "onAnimationRepeat")
                }

            })
        }
    }

    // objectAnimator
    private fun objectAnim() {
        ObjectAnimator.ofFloat(image_dog, "translationX", 100f).apply {
            duration = 1000
            start()
            addUpdateListener { updatedAnimation ->
                image_dog.translationX = updatedAnimation.animatedValue as Float
            }
        }
    }

    // valueAnimator
    private fun valueAnim() {
        ValueAnimator.ofFloat(0f, 200f).apply {
            repeatCount = 2
            duration = 1000
            interpolator = BounceInterpolator()
            start()
            addUpdateListener { updatedAnimation ->
                image_dog.translationX = updatedAnimation.animatedValue as Float
            }
        }
    }
}