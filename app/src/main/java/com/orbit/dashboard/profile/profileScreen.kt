package com.orbit.dashboard.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.orbit.R
import com.orbit.dashboard.base.App
import com.orbit.dashboard.profile.viewModel.profileVM
import com.orbit.other.BlurEffect
import com.orbit.other.CommonText
import com.orbit.other.Cons
import com.orbit.other.CustomDialog
import com.orbit.other.GradientButton
import com.orbit.other.GradientColor
import com.orbit.other.StarsBackground
import com.orbit.other.fieldText

@Composable
fun Profile(navController: NavHostController) {

    val activity = LocalActivity.current

    val viewModel : profileVM = hiltViewModel()
    val loader = viewModel.displayLoader.observeAsState()

    val context = LocalContext.current
    var isGuest by rememberSaveable {mutableStateOf(App.sharedPref.getBoolean(Cons.IS_GUEST, false)) }

    var text by rememberSaveable {mutableStateOf(App.sharedPref.getString(Cons.SPACE_TOKEN, "")) }
    val name by rememberSaveable {mutableStateOf(App.sharedPref.getString(Cons.NAME,"")) }
    val email by rememberSaveable {mutableStateOf(App.sharedPref.getString(Cons.EMAIL,"")) }

    var showdialog by rememberSaveable {mutableStateOf(false) }
    var dialogTitle by rememberSaveable {mutableStateOf("")}
    var dialogMessage by rememberSaveable {mutableStateOf("") }
    var dialogYes by rememberSaveable {mutableStateOf("")  }
    var dialogNo by rememberSaveable {mutableStateOf("")  }

     fun clearDialog(){
        showdialog = false
         dialogTitle = ""
         dialogMessage = ""
         dialogYes = ""
         dialogNo = ""
    }

    var edit by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()


    LaunchedEffect(Unit) {

        viewModel.deleteEvent.collect { message ->

            Toast.makeText(
                activity,
                message,
                Toast.LENGTH_SHORT
            ).show()


            if (message == "Account Deleted Successfully") {

                App.sharedPref.clearAll()
                App.sharedPref.putBoolean(Cons.IS_ONBOARDING_COMPLETE, true)


                navController.navigate(Cons.LOGIN) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }

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

        ){
            if(showdialog){
                    CustomDialog(
                        showDialog = showdialog,
                        title = dialogTitle,
                        message = dialogMessage,
                        yesText = dialogYes,
                        noText = dialogNo,
                        onDismiss = {clearDialog()},
                        onDelete = {
                            if(dialogTitle == "Log Out"){
                                App.sharedPref.clearAll()
                                App.sharedPref.putBoolean(Cons.IS_ONBOARDING_COMPLETE, true)
                                navController.navigate(Cons.LOGIN) {
                                    popUpTo(Cons.MAINSCREEN) {
                                        inclusive = true
                                    }
                                }
                            }
                            else if(dialogTitle == "Delete Account") {

                                viewModel.deleteAccount(email)

//                                navController.navigate(Cons.LOGIN) {
//                                    popUpTo(Cons.MAINSCREEN) {
//                                        inclusive = true
//                                    }
//                                }

                            }
                            clearDialog()

                        },
                    )
            }

                Icon(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(100.dp),
                    painter = painterResource(R.drawable.logo,),
                    contentDescription = "logo",
                    tint = Color.Unspecified)


            if(!isGuest) {
                CommonText(
                    modifier = Modifier.padding(top = 10.dp),
                    name = name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                CommonText(
                    name = email,
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
                fieldText("PLAY & EXPLORE", modifer = Modifier.padding(top = 20.dp))
            }

            navTile("SOLAR SYSTEM","Explore planets in orbit",{navController.navigate(Cons.SOLAR)})
            Spacer(modifier = Modifier.height(10.dp))
            navTile("SOLAR ECLIPSE","Watch the moon cross the sun",{navController.navigate(Cons.SOLAR2)})
            Spacer(modifier = Modifier.height(10.dp))
            navTile("GRAVITY NEBULA","A swirling field of stardust",{navController.navigate(Cons.SOLAR3)})


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
                        1.dp,
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
                                            dialogTitle = "Missing Api key"
                                            dialogMessage = "please provide an Api key before continuing"
                                            dialogNo = ""
                                            dialogYes = "Ok"

                                            return@clickable
                                        }
                                        else {

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
                fieldText("FOLLOW US")
            }

            navTile("Github","Follow on GitHub") { openGithub(context) }
            Spacer(modifier = Modifier.height(10.dp))
            navTile("LinkedIn","Follow on linkedin") {openLinkedIn(context)}

            if(!isGuest) {
                Box(
                    modifier = Modifier
                        .clickable{

                          showdialog = true
                          dialogTitle = "Log Out"
                          dialogMessage = "are you sure you want to logout from your account ?"
                          dialogNo = "No"
                          dialogYes = "Yes"

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
                    )}

                CommonText(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable{
                            showdialog = true
                            dialogTitle = "Delete Account"
                            dialogMessage = "are you sure you want to delete your account ?"
                            dialogNo = "No"
                            dialogYes = "Yes"

                    },
                    name = "Delete Account",
                    fontSize = 13.sp,
                    color = colorResource(R.color.red))
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
                modifier = Modifier.padding(bottom = 10.dp)
                    .clickable{
                        navController.navigate(Cons.SOLAR)
                    },
                name = "Version 1.0",
                fontSize = 13.sp,
                color = colorResource(R.color.dim))

        }
        BlurEffect()
    }
}

@Composable
fun navTile(title: String, subTitle: String, onClick :()->Unit){
    Box(
        modifier = Modifier.fillMaxWidth()
            .clickable{
                onClick()
            }
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp,
                color = colorResource(R.color.app_blue),
                shape = RoundedCornerShape(14.dp))
            .padding(10.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(GradientColor()))
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)

            ) {
                Text(title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = LocalTextStyle.current.copy(
                        lineHeight = 16.sp,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        )
                    ))

                Text(subTitle,
                    color = colorResource(R.color.dim),
                    fontSize = 14.sp)


            }

            Icon(painter = painterResource(R.drawable.ic_arrow),
                contentDescription = "onClick",
                tint = Color.Unspecified)

        }
    }
}

fun openGithub(context: Context) {
    try {

    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(Cons.GITHUB_WEB)
    )
    context.startActivity(intent)
}
catch (e: Exception){
    e.printStackTrace()
    Toast.makeText(context,"Try again later", Toast.LENGTH_SHORT).show()
}
}
fun openLinkedIn(context: Context,) {

    val linkedInAppIntent = Intent(Intent.ACTION_VIEW,Uri.parse(Cons.LINKEDIN_APP))

    try {

        if (linkedInAppIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(linkedInAppIntent)
        } else {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(Cons.LINKEDIN_WEB)
            )

            context.startActivity(webIntent)
        }
    }
    catch (e: Exception){
        e.printStackTrace()
        Toast.makeText(context,"Try again later", Toast.LENGTH_SHORT).show()
    }
}