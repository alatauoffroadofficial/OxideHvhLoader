# OXIDE HVH Loader — Android source project

Это самостоятельное вспомогательное приложение для Android. Оно содержит тёмный красно-чёрный интерфейс, локальные настройки/профили и кнопку запуска установленной OXIDE: Survival Island.

**Важно:** приложение не внедряется в игру, не читает её память и не реализует WH/ESP по реальным игрокам, Aimbot, Triggerbot, Auto Farm, TP или обход античита. `ESP Preview` — только демонстрационный переключатель интерфейса.

## Сборка APK через GitHub Actions

1. Создайте новый пустой репозиторий GitHub.
2. Распакуйте архив и загрузите в репозиторий всё содержимое папки `OxideHvhLoader` (включая скрытую папку `.github`).
3. Откройте вкладку **Actions** и разрешите запуск workflow, если GitHub попросит.
4. Выберите **Build Android APK** → **Run workflow**.
5. Дождитесь успешного завершения сборки.
6. Откройте завершённый запуск workflow и скачайте artifact `oxide-hvh-loader-debug-apk`.
7. Распакуйте скачанный artifact. Внутри будет `app-debug.apk`. Перешлите его на телефон и установите.

## Сборка локально

Требуются JDK 17, Android SDK Platform 35 и Gradle 8.9. В корне проекта выполните:

```bash
gradle assembleDebug
```

APK появится в `app/build/outputs/apk/debug/app-debug.apk`.

## Совместимость

Минимальная версия Android — API 23. Проект использует `compileSdk 35`. Фактическую работу запуска игры следует проверить на устройстве. Идентификатор приложения OXIDE указан как `com.catsbit.oxidesurvivalisland`.
