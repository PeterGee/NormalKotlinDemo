package com.example.myapplication.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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