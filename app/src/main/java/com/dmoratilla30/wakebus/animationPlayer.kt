package com.dmoratilla30.wakebus

import android.app.Activity
import android.os.Build
import android.transition.Slide
import android.view.Gravity
import android.view.animation.DecelerateInterpolator

class animationPlayer {
    object anim {
        fun setAnimationLeft(myAct: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                val slide = Slide()
                slide.setSlideEdge(Gravity.LEFT)
                slide.setDuration(400)
                slide.setStartDelay(400)
                slide.setInterpolator(DecelerateInterpolator())
                myAct.window.setExitTransition(slide)
                myAct.window.setEnterTransition(slide)
                myAct.window.setReenterTransition(slide)
                myAct.window.setReturnTransition(slide)
            }
        }
        fun setAnimationRight(myAct: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                val slide = Slide()
                slide.setSlideEdge(Gravity.RIGHT)
                slide.setDuration(400)
                //slide.setStartDelay(400)
                slide.setInterpolator(DecelerateInterpolator())
                myAct.window.setExitTransition(slide)
                myAct.window.setEnterTransition(slide)
                myAct.window.setReenterTransition(slide)
                myAct.window.setReturnTransition(slide)
            }
        }
        fun setAnimationTop(myAct: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                val slide = Slide()
                slide.setSlideEdge(Gravity.TOP)
                slide.setDuration(200)
                slide.setStartDelay(200)
                slide.setInterpolator(DecelerateInterpolator())
                myAct.window.setExitTransition(slide)
                myAct.window.setEnterTransition(slide)
                myAct.window.setReenterTransition(slide)
                myAct.window.setReturnTransition(slide)
            }
        }
        fun setAnimationBottom(myAct: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                val slide = Slide()
                slide.setSlideEdge(Gravity.BOTTOM)
                slide.setDuration(400)
                slide.setInterpolator(DecelerateInterpolator())
                myAct.window.setExitTransition(slide)
                myAct.window.setEnterTransition(slide)
                myAct.window.setReenterTransition(slide)
                myAct.window.setReturnTransition(slide)
            }
        }
    }
}