package com.example.jetpackcomposearchi.other.dialog

import com.example.jetpackcomposearchi.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun selectImage(){
//
//    Column(
//        Modifier.padding(50.dp)
//    ) {
//        Text("Choose Image From",
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Center)
//
//        Row() {
//            Image(painter = painterResource(R.drawable.select_camera),
//                contentDescription = "camera")
//            Image(painter = painterResource(R.drawable.select_gallery),
//                contentDescription = "gallery")
//        }
//    }
//}

@Composable
fun CustomAlertDialog(
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {},
            dismissButton = {},
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Delete item?",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "Are you sure you want to delete this item?",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .clickable { onDismiss() }
                        )

                        Text(
                            text = "Delete",
                            color = Color.Red,
                            modifier = Modifier
                                .clickable { onConfirm() }
                        )
                    }
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Color.White
        )
    }
}
