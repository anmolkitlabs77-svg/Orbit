package com.orbit.other


import android.text.style.LineHeightSpan
import androidx.compose.foundation.Canvas
import com.orbit.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnit.Companion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(title: String, subtitle: String){

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.black)),

        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.black))
            )
        },

        title = {
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily =  FontFamily(Font(R.font.inter_medium),),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false))
                    )

                Text(subtitle,
                    fontSize = 11.sp,
                    color = colorResource(R.color.text2_blue),
                    fontFamily =  FontFamily(Font(R.font.inter_regular),),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ))
                    )
            }
        }
        )
}

@Composable
fun ErrorCompose(message: String,onClick : ()->Unit){

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 60.dp, start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.text_blue),
                fontSize = 16.sp,
            )

            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(color = colorResource(R.color.bg_black))
                    .clip(RoundedCornerShape(5.dp))
                    .clickable(onClick = onClick)
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(5.dp)

                    )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "settings",
                    fontSize = 16.sp,
                    color = colorResource(R.color.text_color)
                )
            }
        }

    }


}

@Composable
fun BlurEffect(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colorResource(R.color.black),
                        Color.Transparent
                    )
                )
            )
    )
}

@Composable
 fun StarsBackground(starCount: Int = 100) {
    val stars = remember {
        List(starCount) {
            Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 0.5f + 0.3f)
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        stars.forEach { (xPct, yPct, alpha) ->
            drawCircle(
                color = Color(0xFFCFD8FF).copy(alpha = alpha),
                radius = 1.6f,
                center = Offset(size.width * xPct, size.height * yPct)
            )
        }
    }
}
@Composable
fun CommonText(
    name: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,) {
    Text(
        text = name,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight
    )
}

@Composable
fun cyanVioletGradient(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            colorResource( R.color.cyan),
            colorResource(R.color.violet)
        )
    )
}

@Composable
fun GradientButton( text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (enabled) cyanVioletGradient() else SolidColor(colorResource(R.color.line))),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxSize()) {
            CommonText(
                name = text,
                color = if (enabled) colorResource(R.color.bg) else colorResource(R.color.text_color2),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { CommonText(label, color = colorResource(R.color.text_color2), fontSize = 13.sp) },
        leadingIcon = { Icon(leadingIcon, null, tint = colorResource(R.color.cyan)) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Filled.Person else Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = colorResource(R.color.text_color2)
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.fieldbg),
            unfocusedContainerColor = colorResource(R.color.fieldbg),
            disabledContainerColor = colorResource(R.color.fieldbg),
            focusedBorderColor = colorResource(R.color.cyan),
            unfocusedBorderColor = colorResource(R.color.line),
            focusedTextColor = colorResource(R.color.ink),
            unfocusedTextColor = colorResource(R.color.ink),
            cursorColor = colorResource(R.color.cyan)
        )
    )
}

