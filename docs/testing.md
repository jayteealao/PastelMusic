# Testing Guide for PastelMusic

This document describes the testing infrastructure and how to run tests for the PastelMusic Android application.

## Overview

The test suite includes:

| Test Type | Framework | Location | Purpose |
|-----------|-----------|----------|---------|
| Unit Tests | JUnit 4, Turbine, MockK | `app/src/test` | Pure Kotlin logic |
| Robolectric Tests | Robolectric | `app/src/test` | Android tests on JVM |
| Paparazzi Screenshots | Paparazzi | `app/src/test` | JVM-based screenshot tests |
| Roborazzi Screenshots | Roborazzi | `app/src/test` | Robolectric-based screenshots |
| Compose UI Tests | Compose Testing | `app/src/androidTest` | Instrumented UI tests |

## Running Tests Locally

### Unit Tests (JVM)

Run all unit tests including Robolectric tests:

```bash
./gradlew testDebugUnitTest
```

### Paparazzi Screenshot Tests

**Record new golden images:**
```bash
./gradlew recordPaparazziDebug
```

**Verify against golden images:**
```bash
./gradlew verifyPaparazziDebug
```

### Roborazzi Screenshot Tests

**Record new screenshots:**
```bash
./gradlew recordRoborazziDebug
```

**Verify against existing screenshots:**
```bash
./gradlew verifyRoborazziDebug
```

### Instrumented Tests (Requires Device/Emulator)

```bash
./gradlew connectedDebugAndroidTest
```

### Run All Tests

```bash
# JVM tests only (no device needed)
./gradlew testDebugUnitTest verifyPaparazziDebug

# With instrumented tests (device needed)
./gradlew testDebugUnitTest connectedDebugAndroidTest
```

## Test Structure

### Unit Tests (`app/src/test`)

```
app/src/test/java/com/github/jayteealao/pastelmusic/app/
├── domain/
│   ├── SongTest.kt              # Song utility tests
│   ├── PlaybackStateTest.kt     # PlaybackState tests
│   ├── SongRepositoryTest.kt    # Repository tests
│   ├── AlbumRepositoryTest.kt   # Album repository tests
│   └── GetSongsUseCaseTest.kt   # UseCase tests
├── robolectric/
│   ├── ThemeResourcesTest.kt    # Theme/resource tests
│   └── ComposeComponentTest.kt  # Compose on Robolectric
├── screenshots/
│   ├── PlayerCardScreenshotTest.kt  # Paparazzi tests
│   ├── MediaCardScreenshotTest.kt   # Paparazzi tests
│   ├── RoborazziPlayerCardTest.kt   # Roborazzi tests
│   └── RoborazziMediaCardTest.kt    # Roborazzi tests
└── testing/
    ├── TestDispatcherRule.kt    # Coroutine test rule
    ├── FakeRepositories.kt      # Fake implementations
    └── TestData.kt              # Shared test data
```

### Screenshot Snapshots

```
app/src/test/snapshots/
├── paparazzi/     # Paparazzi golden images
└── roborazzi/     # Roborazzi golden images
```

### Instrumented Tests (`app/src/androidTest`)

```
app/src/androidTest/java/com/github/jayteealao/pastelmusic/app/
└── ui/
    ├── PlayerCardInstrumentedTest.kt
    └── MediaCardInstrumentedTest.kt
```

## CI/CD Workflows

### Automatic CI (`android-ci.yml`)

Triggered on every push and pull request:

1. **JVM Tests Job:**
   - Runs unit tests
   - Verifies Paparazzi screenshots
   - Runs lint checks
   - Uploads test reports as artifacts

2. **Instrumented Tests Job:**
   - Runs on Android emulator
   - Executes Compose UI tests
   - Uploads test reports as artifacts

3. **Build Job:**
   - Builds debug APK
   - Uploads APK as artifact

### Screenshot Recording (`screenshot-record.yml`)

Manual workflow to record new golden images:

1. Go to Actions tab
2. Select "Record Screenshots"
3. Choose which screenshots to record
4. Run workflow
5. Download artifacts and commit to repository

## Test Stability Tips

### Deterministic Testing

The test configuration ensures deterministic results:

- **Timezone:** UTC (set via system property)
- **Locale:** en-US (set via system property)
- **Animations:** Disabled in instrumented tests

### Screenshot Test Stability

For consistent screenshots:

1. Use fixed device configurations
2. Avoid dynamic content (dates, timestamps)
3. Use stable preview data from `PreviewSpecs`
4. Ensure fonts are embedded/consistent

### Troubleshooting

**Paparazzi tests fail with image diffs:**
```bash
# View the diff images in build/paparazzi/
# Re-record if changes are intentional:
./gradlew recordPaparazziDebug
```

**Roborazzi tests fail:**
```bash
# Check diff images in build/outputs/roborazzi/
# Re-record if changes are intentional:
./gradlew recordRoborazziDebug
```

**Instrumented tests hang:**
- Ensure emulator is running and connected
- Try: `adb devices` to verify connection
- Check for `AnimationsDisabled` setting in test runner

## Adding New Tests

### Unit Test Template

```kotlin
class MyUseCaseTest {
    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    private lateinit var useCase: MyUseCase

    @Before
    fun setup() {
        useCase = MyUseCase(/* dependencies */)
    }

    @Test
    fun `test case description`() = runTest {
        // Given
        val input = ...

        // When
        val result = useCase(input)

        // Then
        assertThat(result).isEqualTo(expected)
    }
}
```

### Screenshot Test Template (Paparazzi)

```kotlin
class MyComponentScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun myComponent_defaultState() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MyComponent(state = ...)
            }
        }
    }
}
```

### Preview-Driven Testing Pattern

1. Define preview specs in `app/src/main/java/.../ui/preview/PreviewSpecs.kt`
2. Create previews using `@PreviewParameter`
3. Reference the same specs in screenshot tests

This ensures previews and tests share the same data.

## Coverage Areas

| Area | Unit Tests | Robolectric | Paparazzi | Instrumented |
|------|------------|-------------|-----------|--------------|
| Song model | ✅ | - | - | - |
| PlaybackState | ✅ | - | - | - |
| Repositories | ✅ | - | - | - |
| UseCases | ✅ | - | - | - |
| Theme | - | ✅ | - | - |
| PlayerCard | - | ✅ | ✅ | ✅ |
| MediaCard | - | - | ✅ | ✅ |

## Dependencies

Key testing dependencies (see `libs.versions.toml`):

- JUnit 4.13.2
- Kotlinx Coroutines Test 1.7.3
- MockK 1.13.8
- Truth 1.1.5
- Turbine 1.0.0
- Robolectric 4.11.1
- Paparazzi 1.3.1
- Roborazzi 1.7.0
- Compose UI Test 1.4.3
