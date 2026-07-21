package com.example.jetpackcomposearchi.other


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

data class PermissionResult(
    val granted: Boolean,
    val permanentlyDenied: Boolean
)

class PermissionManager(
    private val permissions: List<String>,
    private val launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    private val context: Context,
    private val onResult: (PermissionResult) -> Unit
) {
    fun request() {
        launcher.launch(permissions.toTypedArray())
    }

    fun handleResult(result: Map<String, Boolean>) {
        val granted = result.values.all { it }

        val permanentlyDenied = permissions.any { permission ->
            !androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(
                (context as android.app.Activity),
                permission
            ) && !result[permission]!!
        }

        onResult(PermissionResult(granted, permanentlyDenied))
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}

@Composable
fun rememberPermissionManager(
    permissions: List<String>,
    onResult: (PermissionResult) -> Unit): PermissionManager {
    val context = LocalContext.current

    // Step 1: Create placeholder, so launcher can call handleResult()
    lateinit var permissionManager: PermissionManager

    // Step 2: Create launcher (can now reference the lateinit variable)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        permissionManager.handleResult(result)
    }

    // Step 3: Initialize permissionManager after launcher is ready
    permissionManager = remember {
        PermissionManager(
            permissions = permissions,
            launcher = launcher,
            context = context,
            onResult = onResult
        )
    }

    return permissionManager
}
