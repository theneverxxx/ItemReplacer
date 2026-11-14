# ItemReplacer

Клиентский мод для **Fabric**, который меняет модели мечей, тотемов, золотых яблок и серверных предметов на кастомные. Меняется только то, что видишь ты, сервер ничего не замечает.

Эта ветка для **Minecraft 1.21.4**.

| Ветка | Minecraft | Java |
|---|---|---|
| [`1.21.4`](https://github.com/theneverxxx/ItemReplacer/tree/1.21.4) | 1.21.4 | 21 |
| [`1.21.11`](https://github.com/theneverxxx/ItemReplacer/tree/1.21.11) | 1.21.11 | 21 |
| [`26.2`](https://github.com/theneverxxx/ItemReplacer/tree/26.2) | 26.2 | 25 |

## Возможности

- **Мечи**: 14 моделей или 5 особых видов (Меч Магистра, Тесак, Кинжал смерти, Золотая катана, Алмазная сабля).
- Можно выбрать, какие мечи заменять: деревянный, каменный, медный, золотой, железный, алмазный, незеритовый, а также «Меч магистра I–X» по названию.
- **Тотем бессмертия**: 14 моделей, включая анимацию срабатывания.
- **Золотое яблоко**: 14 моделей.
- **Серверные предметы**: 17 предметов (ключи, свитки и т. д.), которые сервер делает из палки с `custom_model_data`.
- По желанию замена у других игроков.

## Установка

1. Скачай `itemreplacer-1.0.0+1.21.4.jar` со страницы [релиза v1.0.0-1.21.4](https://github.com/theneverxxx/ItemReplacer/releases/tag/v1.0.0-1.21.4).
2. Поставь [Fabric Loader](https://fabricmc.net/use/) для Minecraft 1.21.4.
3. Положи в папку `mods` [Fabric API](https://modrinth.com/mod/fabric-api) и скачанный jar.
4. [Mod Menu](https://modrinth.com/mod/modmenu) не обязателен, он только добавляет кнопку настроек в список модов.

## Проверка подлинности

Jar в релизах собирает GitHub Actions прямо из этой ветки, когда пушится тег. Руками туда ничего не загружается.

- К каждому релизу приложены `SHA256SUMS.txt` и `SHA512SUMS.txt`. Хеш скачанного файла должен совпасть:

  ```powershell
  (Get-FileHash .\itemreplacer-1.0.0+1.21.4.jar -Algorithm SHA256).Hash
  ```

- Каждый jar подписан аттестацией происхождения (Sigstore). Она доказывает, что файл собран в этом репозитории из конкретного коммита:

  ```bash
  gh attestation verify itemreplacer-1.0.0+1.21.4.jar -R theneverxxx/ItemReplacer
  ```

- Сборка воспроизводимая: если собрать jar из тега у себя (`./gradlew build`), получится тот же SHA-256.

## Настройки

Настройки открываются клавишей **Right Shift** (её можно поменять в «Настройки → Управление») или через Mod Menu.
Экран выглядит как обычные настройки Minecraft:

- Замена предметов: вкл/выкл
- Меч / Вид меча
- Тотем / Золотое яблоко
- У других игроков
- Какие мечи заменять...
- Серверные предметы...

Настройки хранятся в `config/itemreplacer.json`.

## Сборка из исходников

Нужна Java 21+. Если её нет, Gradle скачает её сам.

```bash
git clone -b 1.21.4 https://github.com/theneverxxx/ItemReplacer.git
cd ItemReplacer
./gradlew build
```

На Windows вместо `./gradlew` пиши `gradlew.bat`.

Готовый jar появится в `build/libs/itemreplacer-1.0.0+1.21.4.jar`.

Запустить игру с модом для проверки:

```bash
./gradlew runClient
```

## Структура

```
src/main/java/itemreplacer/
  ItemReplacer.java         логика: какую модель получает предмет
  Models.java               список моделей и опций
  Config.java               config/itemreplacer.json
  ConfigScreen.java         экран настроек
  ModMenuIntegration.java   кнопка в Mod Menu
  compat/                   то, что зависит от версии (клавиша, открытие экрана)
  mixin/                    подмена модели в ItemModelResolver
src/main/resources/assets/itemreplacer/
  items/ models/ textures/  кастомные модели
  lang/                     ru_ru, en_us
```
