## To Start:
`mvn clean install`

### Settings

#### BenchmarkMode

- **Throughput**  Пропускная способность Количество выполнений за период, обычно в секунду
- **AverageTime** Среднее время, среднее время на операцию
- **SampleTime**  Время выборки  В тесте время выполнения случайной выборки
- **SingleShotTime**  Расчет затрат времени на каждое выполнение
- All

#### Measurement

Формально измерить количество раундов расчета.

- **iterations** Раунды тестирования
- **time** Продолжительность каждого раунда
- **timeUnit** Единица времени

#### Warmup

Прогрев

#### Fork

Используя аннотацию `@Fork`, мы можем настроить, как происходит выполнение теста: параметр `value` управляет, сколько раз будет выполняться тест производительности,
 а параметр `Warmup` контролирует, сколько раз тест производительности будет выполняться до сбора результатов

#### State

- **Benchmark** Один и тот же тестовый образец разделяет экземпляры между несколькими потоками
- **Group**     Один и тот же поток использует экземпляры в одной группе. Ссылочная аннотация определения группы @Group
- **Thread**    Экземпляры между разными потоками не используются совместно

#### Param

Аннотации на уровне атрибутов, можно использовать для указания нескольких ситуаций для определенного параметра. Он особенно подходит для тестирования производительности функции при различных входных параметрах.

#### Setup

Аннотации на уровне метода, роль этой аннотации заключается в том, что нам нужно сделать некоторые приготовления. Например, инициализация некоторых данных.

#### @TearDown
Аннотации на уровне метода, роль этой аннотации заключается в том, что нам нужно сделать некоторые TearDown, такие как закрытие пулов потоков, подключения к базе данных и т. Д. В основном используются для восстановления ресурсов.

| Benchmark                            | (delay)| Mode  | Cnt      | Score         |  Error  | Units |
|--------------------------------------|--------|-------|----------|---------------|---------|-------|
|StringBenchmark.testConcat            | N/A    | thrpt |  20      |10528,067 �   |2852,083 | ops/s |
|StringBenchmark.testFormat            | N/A    | thrpt |  20      |531,340 �     | 28,709  | ops/s|
|ConcatVsValueOfBenchmark.concat       | N/A    | avgt  |  200     |51,514 �      | 1,219   |ns/op |
|ConcatVsValueOfBenchmark.valueOf      | N/A    | avgt  |  200     |57,925 �      | 4,087   |ns/op |
|ConcatVsValueOfObjectBenchmark.concat | N/A    | avgt  |  200     |282,505 �     | 16,564  |ns/op |
|ConcatVsValueOfObjectBenchmark.valueOf| N/A    | avgt  |  200     |172,316 �     | 5,389   |ns/op |
|ParkNanosBenchmark.parkNanos          |    10  |  avgt |  200     |15955334,180 �| 12022,200 | ns/op|
| ParkNanosBenchmark.parkNanos         |    100 |  avgt |  200     |15962258,330 � | 40286,974 |ns/op |
| ParkNanosBenchmark.parkNanos         |   1000 | avgt  |  200     |17297464,046 � |2241722,585 |ns/op  |
| ParkNanosBenchmark.parkNanos         |  10000 | avgt  |  200     |15951940,438 � | 21966,697 |ns/op  |
| ParkNanosBenchmark.parkNanos         | 1000000|  avgt |  200     | 15939726,045 � | 11349,578| ns/op |
## Links
- [Производительность: нюансы против очевидностей. JDK edition](https://habr.com/ru/post/672146/)
- [Холостые циклы в Java](https://habr.com/ru/post/674116/)