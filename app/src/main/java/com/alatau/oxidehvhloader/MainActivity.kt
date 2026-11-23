package com.alatau.oxidehvhloader

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF101114)
private val Panel = Color(0xFF1B1D22)
private val Red = Color(0xFFE53945)
private val Muted = Color(0xFF9A9DA6)
private const val GAME_PACKAGE = "com.catsbit.oxidesurvivalisland"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OxideLoaderScreen() }
    }
}

@Composable
private fun OxideLoaderScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("loader_prefs", 0) }
    var overlayPreview by remember { mutableStateOf(prefs.getBoolean("overlay_preview", false)) }
    var sound by remember { mutableStateOf(prefs.getBoolean("sound", true)) }
    var selectedConfig by remember { mutableStateOf(prefs.getString("config", "Default") ?: "Default") }

    MaterialTheme(colorScheme = darkColorScheme(
        primary = Red, background = Bg, surface = Panel,
        onPrimary = Color.White, onBackground = Color.White, onSurface = Color.White
    )) {
        Surface(Modifier.fillMaxSize(), color = Bg) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(48.dp).background(Red, RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                        Text("O", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("OXIDE HVH", fontSize = 21.sp, fontWeight = FontWeight.Black, color = Color.White)
                        Text("MOBILE LOADER  •  v1.0", fontSize = 11.sp, color = Muted, letterSpacing = 1.sp)
                    }
                    Surface(color = Color(0xFF25372C), shape = RoundedCornerShape(50)) {
                        Text("READY", Modifier.padding(horizontal = 11.dp, vertical = 6.dp), color = Color(0xFF8FE0A6), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(18.dp)).padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("ГЛАВНАЯ", color = Red, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
                    Text("Добро пожаловать", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Это самостоятельный помощник. Он не изменяет файлы игры и не вмешивается в игровой процесс.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                    Button(
                        onClick = {
                            val launch = context.packageManager.getLaunchIntentForPackage(GAME_PACKAGE)
                            if (launch != null) context.startActivity(launch)
                            else Toast.makeText(context, "OXIDE не найдена. Установи игру из Google Play.", Toast.LENGTH_LONG).show()
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("ЗАПУСТИТЬ OXIDE", fontWeight = FontWeight.Bold) }
                }

                SectionTitle("ВИЗУАЛЬНЫЕ ИНСТРУМЕНТЫ")
                SettingCard(
                    title = "ESP Preview",
                    subtitle = "Демонстрационный экран для проверки оформления; не показывает игроков в игре.",
                    checked = overlayPreview,
                    onChecked = { overlayPreview = it; prefs.edit().putBoolean("overlay_preview", it).apply() }
                )

                SectionTitle("КОНФИГУРАЦИЯ")
                Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(16.dp)).padding(16.dp)) {
                    Text("Профиль настроек", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Default", "PvP", "Low-end").forEach { name ->
                            FilterChip(
                                selected = selectedConfig == name,
                                onClick = { selectedConfig = name; prefs.edit().putString("config", name).apply() },
                                label = { Text(name) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Red.copy(alpha = .22f), selectedLabelColor = Color.White)
                            )
                        }
                    }
                }

                SectionTitle("ПРИЛОЖЕНИЕ")
                SettingCard("Звуки интерфейса", "Звуковые сигналы приложения", sound, {
                    sound = it; prefs.edit().putBoolean("sound", it).apply()
                })
                Text("Версия 1.0 • Android • OXIDE package: $GAME_PACKAGE", color = Muted, fontSize = 10.sp)
                Text("Примечание: функции WH, Aimbot, Triggerbot, Auto Farm и TP не реализованы. Для интеграции с игрой нужна официально разрешённая поддержка модификаций.", color = Muted, fontSize = 11.sp, lineHeight = 16.sp)
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = Red, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
}

@Composable
private fun SettingCard(title: String, subtitle: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(16.dp)).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Muted, fontSize = 11.sp, lineHeight = 15.sp)
        }
        Switch(checked = checked, onCheckedChange = onChecked, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Red))
    }
}
