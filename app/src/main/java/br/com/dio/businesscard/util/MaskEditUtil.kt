package br.com.dio.businesscard.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

public abstract class MaskEditUtil {

    val FORMAT_CPF = "###.###.###-##"
    val FORMAT_FONE = "(###)####-#####"
    val FORMAT_CEP = "#####-###"
    val FORMAT_DATE = "##/##/####"
    val FORMAT_HOUR = "##:##"

    /**
     * Método que deve ser chamado para realizar a formatação
     *
     * @param ediTxt
     * @param mask
     * @return
     */
    open fun mask(ediTxt: EditText, mask: String): TextWatcher? {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str: String? = this@MaskEditUtil.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str.toString()
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str!!.length > old.length) {
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str!![i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    open fun unmask(s: String): String? {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "").replace("[ ]".toRegex(), "")
            .replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }
}