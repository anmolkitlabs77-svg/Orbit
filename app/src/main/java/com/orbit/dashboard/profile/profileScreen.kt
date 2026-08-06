package com.orbit.dashboard.profile

import android.app.AlertDialog
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.orbit.R
import com.orbit.dashboard.base.App
import com.orbit.other.BlurEffect
import com.orbit.other.CommonText
import com.orbit.other.Cons
import com.orbit.other.GradientButton
import com.orbit.other.StarsBackground
import com.orbit.other.fieldText

@Composable
fun Profile(navController: NavHostController) {

    var isGuest by rememberSaveable {mutableStateOf(App.sharedPref.getBoolean(Cons.IS_GUEST, false)) }

    var text by rememberSaveable {mutableStateOf(App.sharedPref.getString(Cons.SPACE_TOKEN, "")) }
    var showdialog by rememberSaveable {mutableStateOf(false) }

    var edit by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ){
        StarsBackground()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)

        ) {
            if(showdialog) {
                AlertDialog(
                    onDismissRequest = {
              showdialog = false
                    },
                    title = {
                        Text("Delete Account")
                    },
                    text = {
                        Text("Are you sure you want to delete your account?")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                       showdialog = false
                                // Delete account
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                            showdialog = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }


                Icon(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(100.dp),
                    painter = painterResource(R.drawable.logo,),
                    contentDescription = "logo",
                    tint = Color.Unspecified
                    )


            if(!isGuest) {
                CommonText(
                    modifier = Modifier.padding(top = 10.dp),
                    name = "Test User",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                CommonText(
                    name = "anmol@yopmail.com",
                    color = colorResource(R.color.text_color2),
                    fontSize = 15.sp
                )
            }
            else {
                Box(
                    modifier = Modifier
                    .padding(top = 10.dp, bottom = 5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(colorResource(R.color.black))
                    .border(
                        1.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(50)
                    )
                ) {
                CommonText(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                        name = "GUEST MODE",
                        color = colorResource(R.color.text2_blue),
                        fontSize = 14.sp
                )
                }
                CommonText(
                    name = "Exploring as Guest",
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                GradientButton(
                    text = "Register with Orbit",
                    onClick = {
                        navController.navigate(Cons.REGISTER){
                            popUpTo(Cons.MAINSCREEN){
                                inclusive = true
                            }
                        }
                    },
                    enabled = true
                )
            }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        fieldText("api access", modifer = Modifier.padding(top = 20.dp))
                    }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(14.dp)
                    )

            ) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(14.dp)

                    ) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            enabled = if(edit) true else false,
                            textStyle = TextStyle(
                                color = Color.White),

                            visualTransformation = if (edit) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = colorResource(R.color.fieldbg),
                                unfocusedContainerColor = colorResource(R.color.fieldbg),
                                disabledContainerColor = colorResource(R.color.fieldbg),
                                focusedBorderColor = colorResource(R.color.cyan),
                                unfocusedBorderColor = colorResource(R.color.cyan),
                                focusedTextColor = colorResource(R.color.white),
                                unfocusedTextColor = colorResource(R.color.white),
                                cursorColor = colorResource(R.color.cyan),
                                disabledBorderColor = colorResource(R.color.app_blue),
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                                )
                                .border(1.dp,
                                    color = colorResource(R.color.app_blue),
                                    RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            if(edit) {
                                Icon(
                                    modifier = Modifier.clickable{
                                        if(text.isNullOrEmpty()){
                                            showdialog = true
                                            return@clickable
                                        }
                                        edit = false
                                        App.sharedPref.putString(Cons.SPACE_TOKEN,text)
                                    },
                                    painter = painterResource(R.drawable.ic_save),
                                    contentDescription = "edit",
                                    tint = Color.Unspecified
                                )
                            }
                            else {
                                Icon(
                                    modifier = Modifier.clickable{
                                        edit = true
                                    },
                                    painter = painterResource(R.drawable.ic_edit),
                                    contentDescription = "save",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                    val annotatedText = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = colorResource(R.color.text_color2)
                            )
                        ) {
                            append("Update your API key to keep your data feeds active.Need a new one? ")
                        }

                        // Clickable text
                        pushStringAnnotation(
                            tag = "LINK",
                            annotation = Cons.NASA_GOV
                        )

                        withStyle(
                            SpanStyle(
                                color = colorResource(R.color.cyan),
                            )
                        ) {
                            append("Get your key here ->")
                        }

                        pop()
                    }

                    ClickableText(
                            modifier = Modifier.padding(start = 14.dp, bottom = 14.dp),
                    text = annotatedText,
                    style = TextStyle(
                        fontSize = 13.sp
                    ),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "LINK",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {

                            val link = it.item
                            val title = "Nasa Api"

                            navController.navigate(
                                "webView/${Uri.encode(link)}/${Uri.encode(title)}"
                            )

                        }
                    }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                fieldText("FOLLOW ORBIT")
            }


            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
            ){
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                        )
                        .border(1.dp,
                            color = colorResource(R.color.app_blue),
                            RoundedCornerShape(14.dp))
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp)
                        .size(50.dp),
                    painter = painterResource(R.drawable.ic_github),
                        contentDescription = "github",
                        tint = Color.Unspecified)
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                        )
                        .border(1.dp,
                            color = colorResource(R.color.app_blue),
                            RoundedCornerShape(14.dp))
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp)
                            .size(50.dp),
                        painter = painterResource(R.drawable.ic_insta),
                        contentDescription = "github",
                        tint = Color.Unspecified)
                }
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                        )
                        .border(1.dp,
                            color = colorResource(R.color.app_blue),
                            RoundedCornerShape(14.dp))
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp)
                            .size(50.dp),
                        painter = painterResource(R.drawable.ic_linkedin),
                        contentDescription = "github",
                        tint = Color.Unspecified)
                }
            }


            if(!isGuest) {
                Box(
                    modifier = Modifier
                        .clickable{
                            App.sharedPref.clearAll()
                            App.sharedPref.putBoolean(Cons.IS_ONBOARDING_COMPLETE,true)
                            navController.navigate(Cons.LOGIN){
                                popUpTo(Cons.MAINSCREEN){
                                    inclusive = true
                                }
                            }
                        }
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))

                        .border(
                            1.dp,
                            color = colorResource(R.color.red),
                            shape = RoundedCornerShape(16.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {
                    CommonText(
                        modifier = Modifier.padding(vertical = 10.dp),
                        name = "Log Out",
                        color = colorResource(R.color.red)
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 10.dp)
                    .height(1.dp)
                    .background(color=colorResource(R.color.text_color2))
            )

            Row() {

                CommonText(
                    modifier = Modifier.padding(end = 7.dp)
                        .clickable{
                            val link = Cons.PRIVACY_POLICY_URL
                            val title = "Privacy Policy"

                            navController.navigate(
                                "webView/${Uri.encode(link)}/${Uri.encode(title)}"
                            )
                        },
                    name = "Privacy Policy",
                    fontSize = 13.sp,
                    color = colorResource(R.color.dim)
                    )
                CommonText(
                    modifier = Modifier.padding(end = 7.dp)
                        .clickable{
                            val link = Cons.TERMS_CONDITION_URL
                            val title = "Terms of Service"

                            navController.navigate(
                                "webView/${Uri.encode(link)}/${Uri.encode(title)}"
                            )
                        },
                    name = "Terms & condition",
                    fontSize = 13.sp,
                    color = colorResource(R.color.dim)
                )
            }
            CommonText(
                modifier = Modifier.padding(bottom = 10.dp),
                name = "Version 1.0",
                fontSize = 13.sp,
                color = colorResource(R.color.dim))

        }
        BlurEffect()
    }
}

