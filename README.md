# synthetic-human-core-starter

`synthetic-human-core-starter` — это **модульная библиотека на Spring Boot 3**, предназначенная для стандартизации поведения андроидов в системах Weyland-Yutani.  
Стартер обеспечивает **приём, валидацию и исполнение команд**, **аудит действий**, **мониторинг нагрузки** и **единый формат обработки ошибок**.

---

## Функционал

### 1. **Приём и исполнение команд**
- Команды валидируются по описанию, автору и приоритету
- Приоритеты: `CRITICAL` (исполняется немедленно), `COMMON` (добавляется в очередь)
- Очередь реализована на `ThreadPoolExecutor`
- Максимальная ёмкость очереди настраивается

### 2. **Аудит действий**
- Реализована аннотация `@WeylandWatchingYou` для аудита методов
- Поддержка двух режимов:
  - Вывод в консоль
  - Отправка в Kafka Topic
- В аудит включается: имя метода, параметры, результат

### 3. **Мониторинг**
- Публикация метрик через **Micrometer** и **Spring Boot Actuator**
- Доступные метрики:
  - `android.queue.size` — текущее количество задач в очереди
  - `android.queue.capacity` — загруженность очереди
  - `android.commands.completed` — количество выполненных команд по автору

### 4. **Обработка ошибок**
- Все ошибки возвращаются в **едином формате**
- Поддержка:
  - Валидации (`400 Bad Request`)
  - Переполнения очереди (`503 Service Unavailable`)
  - Внутренних ошибок (`500 Internal Server Error`)

---

## Технологии

- **Spring Boot 3.5.4** — основа стартера
- **Spring AOP** — реализация аудита через аннотацию
- **Kafka** — отправка аудит-логов (опционально)
- **ThreadPoolExecutor** — управление очередью команд
- **Micrometer + Actuator** — сбор и экспорт метрик
- **Spring Validation** — валидация входных данных
- **Lombok** — уменьшение boilerplate-кода

---

## 🛠 Как использовать

### 1. Установите стартер в локальный репозиторий

```bash
git clone https://github.com/dmitriina1/syntetic-human-core-starter.git
cd syntetic-human-core-starter
./mvnw clean install
```

### 2. Добавьте зависимость в своё приложение (pom.xml)

```
<dependency>
    <groupId>ru.starter</groupId>
    <artifactId>synthetic-human-core-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 3. Настройте в application.properties или application.yml

```
# Режим аудита: console или kafka
starter.synthetichumancorestarter.audit-mode=console
# Kafka topic (если audit-mode=kafka)
starter.synthetichumancorestarter.kafka-topic=android-audit
# Максимальная ёмкость очереди
starter.synthetichumancorestarter.queue-capacity=100
```

### 4. Создайте контроллер и используй CommandExecutorService

```
@RestController
@RequiredArgsConstructor
public class AndroidController {

    private final CommandExecutorService executor;

    @PostMapping("/command")
    @WeylandWatchingYou
    public String receiveCommand(@RequestBody @Valid Command command) {
        executor.submit(command);
        return "Command accepted";
    }
}
```

### 5. Эндпоинты
- GET /actuator/metrics — список всех метрик
- GET /actuator/metrics/android.queue.size — текущий размер очереди
- GET /actuator/metrics/android.commands.completed?tag=author:Ripley — выполненные команды по автору
