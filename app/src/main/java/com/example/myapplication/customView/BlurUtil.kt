package com.example.myapplication.customView

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

/**
 * @date 2020/12/8
 * @author qipeng
 * @desc 模糊工具类
 */
object BlurUtil {

    fun blur(mContext: Context, bitmap: Bitmap, radius: Float):Bitmap {
        val overlay = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val rs = RenderScript.create(mContext)
        val overlayAlloc = Allocation.createFromBitmap(rs, bitmap)
        val blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        blur.setRadius(radius)
        blur.setInput(overlayAlloc)
        blur.forEach(overlayAlloc)
        overlayAlloc.copyTo(overlay)
        rs.destroy()
        return overlay
    }

}