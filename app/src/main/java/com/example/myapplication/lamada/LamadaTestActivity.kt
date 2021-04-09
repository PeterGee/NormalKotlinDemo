package com.example.myapplication.lamada

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_lamada_test.*

/**
 * @date 2021/3/17
 * @author qipeng
 * @desc lamada 测试类
 */
class LamadaTestActivity : AppCompatActivity() {

    var list = arrayListOf(1, 2, 3, 4, 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lamada_test)

        val myLamada: (Int) -> Unit = { s: Int -> println(s) }
        // addNumber(10, 11, myLamada)

        // functionInline { println("调用内联函数") }

        // functionInlineTwo({ println("调用内联函数")return},{ println("内联函数中的下一个参数")} )

        // functionThree({ println("this is first inline func")},{ println("second inline func")})
        // functionFour({println("this is first func")return}, { println("this is second func")})
        //  functionFour({ println("调用内联函数")},{ println("内联函数中的下一个参数")})


        list.forEach { index ->
            print(index)
        }


        val text = "山不在高，有仙则名。水不在深，有龙则灵。斯是陋室，惟吾德馨。苔痕上阶绿，草色入帘青。谈笑有鸿儒，" +
                "往来无白丁。可以调素琴，阅金经。无丝竹之乱耳，无案牍之劳形。南阳诸葛庐，西蜀子云亭。孔子云：何陋之有？"
        tvNormal.maxLines = 3
        toggleEllipsize(this,
            tvNormal, 2,
            text,
            "展开全部",
            R.color.color_E3AC62, false)
        tvFolder.text = text
    }

    fun addNumber(a: Int, b: Int, mLamada: (Int) -> Unit) {
        val add = a + b
        mLamada(add)
    }


    inline fun functionInline(mFun: () -> Unit) {
        mFun()
        println("内联函数内代码")
    }

    inline fun functionInlineTwo(myFun: () -> Unit, nxFun: () -> Unit) {
        myFun()
        nxFun()
        println("非局部控制流程")
    }

    inline fun functionThree(crossinline myFun: () -> Unit, nxFun: () -> Unit) {
        myFun()
        nxFun()
        println("code inside inline function")
    }

    inline fun functionFour(myFun: () -> Unit, noinline nxFun: () -> Unit) {
        myFun()
        nxFun()
        println("this is function four")
    }

    /**
     * 设置textView结尾...后面显示的文字和颜色
     * @param context 上下文
     * @param textView textview
     * @param minLines 最少的行数
     * @param originText 原文本
     * @param endText 结尾文字
     * @param endColorID 结尾文字颜色id
     * @param isExpand 当前是否是展开状态
     */
    fun toggleEllipsize(
        context: Context,
        textView: TextView,
        minLines: Int,
        originText: String,
        endText: String,
        endColorID: Int,
        isExpand: Boolean
    ) {
        if (TextUtils.isEmpty(originText)) {
            return
        }
        textView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (isExpand) {
                    textView.text = originText
                } else {
                    val paddingLeft: Int = textView.paddingLeft
                    val paddingRight: Int = textView.paddingRight
                    val paint: TextPaint = textView.paint
                    val moreText: Float = textView.textSize * endText.length
                    val availableTextWidth: Float =
                        (textView.width - paddingLeft - paddingRight) *
                                minLines - moreText
                    val ellipsizeStr: CharSequence = TextUtils.ellipsize(originText, paint,
                        availableTextWidth, TextUtils.TruncateAt.END)
                    if (ellipsizeStr.length < originText.length) {
                        val temp: CharSequence = ellipsizeStr.toString() + endText
                        val ssb = SpannableStringBuilder(temp)
                        ssb.setSpan(ForegroundColorSpan(context.resources
                            .getColor(endColorID)),
                            temp.length - endText.length,
                            temp.length,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                        textView.text = ssb
                    } else {
                        textView.text = originText
                    }
                }

                textView.viewTreeObserver.removeGlobalOnLayoutListener(this)

            }
        })
    }
}