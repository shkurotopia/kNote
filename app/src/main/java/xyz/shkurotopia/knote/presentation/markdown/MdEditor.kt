package xyz.shkurotopia.knote.presentation.markdown

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.annotation.FontRes
import androidx.annotation.IdRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import java.util.concurrent.Executors

@Composable
fun MdEditor(
    txt: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize : TextUnit = TextUnit . Unspecified,
    textAlign: TextAlign? = TextAlign.Start,
    maxLines: Int = 80,
    style: TextStyle = LocalTextStyle.current,
    onFocusChange: (FocusState) -> Unit,
    textWatcher: TextWatcher,
    @IdRes viewId: Int? = null,
    @FontRes fontResource: Int? = null,
) {
    val defaultColor: Color = LocalContentColor.current.copy(alpha = LocalContentColor.current.alpha)
    val context: Context = LocalContext.current
    val markdownRender: Markwon = remember { createMarkdownRender(context) }
    Box {
        AndroidView(
            modifier = modifier.onFocusChanged { onFocusChange(it) },
            factory = { ctx ->
                createTextEdit(
                    context = ctx,
                    text = txt,
                    color = color,
                    defaultColor = defaultColor,
                    fontSize = fontSize,
                    textAlign = textAlign,
                    maxLines = maxLines,
                    style = style,
                    viewId = viewId,
                    fontResource = fontResource,
                ).apply {
                    addTextChangedListener(textWatcher)

                    addTextChangedListener(
                        MarkwonEditorTextWatcher.withPreRender(
                            createMarkdownEditor(markdownRender),
                            Executors.newCachedThreadPool(),
                            this
                        )
                    )
                }
            }
        )
    }
}

private fun createTextEdit(
    context: Context,
    text: String,
    color: Color = Color.Unspecified,
    defaultColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle,
    @IdRes viewId: Int? = null,
    @FontRes fontResource: Int? = null
): EditText {
    val txtCol = color.takeOrElse { style.color.takeOrElse { defaultColor } }
    val mergedStyle = style.merge(
        TextStyle(
            color =  txtCol,
            fontSize = if (fontSize != TextUnit.Unspecified) fontSize else style.fontSize,
            textAlign = textAlign
        )
    )

    return EditText(context).apply {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        setTextColor(txtCol.toArgb())
        setMaxLines(maxLines)
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, mergedStyle.fontSize.value)

        viewId?.let {id = viewId}

        textAlign?.let { align ->
            textAlignment = when (align) {
                TextAlign.Left, TextAlign.Start -> View.TEXT_ALIGNMENT_TEXT_START
                TextAlign.Right, TextAlign.End -> View.TEXT_ALIGNMENT_TEXT_END
                TextAlign.Center -> View.TEXT_ALIGNMENT_CENTER
                else -> View.TEXT_ALIGNMENT_TEXT_START
            }
        }

        fontResource?.let { font ->
            typeface = ResourcesCompat.getFont(context, font)
        }
        setText(text)
    }
}

private fun createMarkdownRender(
    context: Context,
): Markwon {

    return Markwon.builder(context)
        .usePlugin(StrikethroughPlugin.create())
        .usePlugin(TaskListPlugin.create(context))
        .build()
}

private fun createMarkdownEditor(
    markwon: Markwon
) : MarkwonEditor {
    return MarkwonEditor.create(markwon)
}
