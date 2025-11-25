package com.tetyukov.practicum_proj

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack // A better icon for back
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.NightsStay
import com.example.practicum_proj.R
import com.tetyukov.practicum_proj.ui.theme.Practicum_projTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practicum_projTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsScreen(onBackClick = { finish() })
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    var darkTheme by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            SettingsItemWithIcon( //Dark Theme
                text = stringResource(R.string.dark_theme),
                icon = Icons.Filled.NightsStay, // CORRECTED ICON
                showSwitch = true,
                checked = darkTheme,
                onCheckedChange = { darkTheme = it },
                onClick = { darkTheme = !darkTheme }
            )
        }


        item {
            SettingsItemWithIcon( //Share App
                text = stringResource(R.string.share_app),
                icon = Icons.Default.Share,
                showSwitch = false,
                checked = false,
                onCheckedChange = { },
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_message))
                        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Поделиться приложением"))
                }
            )
        }


        item {
            SettingsItemWithIcon( //Tech Support
                text = stringResource(R.string.write_to_support),
                icon = Icons.Default.Email,
                showSwitch = false,
                checked = false,
                onCheckedChange = { },
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${context.getString(R.string.support_email)}")
                        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
                        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.email_body))
                    }
                    context.startActivity(Intent.createChooser(emailIntent, "Выберите почтовое приложение"))
                }
            )
        }


        item {SettingsItemWithIcon( //UserAgreement
            text = stringResource(R.string.user_agreement),
            // Use the correctly imported icon
            icon = Icons.AutoMirrored.Filled.ArrowForwardIos,
            showSwitch = false,
            checked = false,
            onCheckedChange = { },
            onClick = {
                val agreementIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(context.getString(R.string.user_agreement_url))
                }
                context.startActivity(Intent.createChooser(agreementIntent, "Открыть в браузере"))
            }
        )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))


            Button( //Back Button
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад") // Added icon for clarity
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Назад")
            }
        }
    }
}

@Composable
fun SettingsItemWithIcon(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    showSwitch: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // OnClick in all row
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon( //Icon
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 16.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )


        Text( //Text
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )


        if (showSwitch) {
            Switch( //Switcher for DT
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    Practicum_projTheme {
        SettingsScreen(onBackClick = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsDarkPreview() {
    Practicum_projTheme(darkTheme = true) {
        SettingsScreen(onBackClick = {})
    }
}

