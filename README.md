# VoiceJavaAI

A lightweight robotic text-to-speech synthesis engine written in Java using formant synthesis. No external audio or ML dependencies — pure waveform generation from phoneme mapping.



## Features

- **Formant-based speech synthesis** — generates raw 16-bit PCM audio from text
- **Bilingual phoneme dictionaries** — supports Spanish and English word-to-phoneme mapping
- **Configurable voice parameters** — pitch, speed, volume, formant shift, vibrato, and robotic intensity
- **Built-in voice presets** — `VoiceConfig.robotic()` and `VoiceConfig.deep()`
- **Singleton API** — simple `RoboticVoice.getInstance()` access pattern
- **Zero external runtime dependencies** — pure Java, no native libraries or external TTS engines

## Status

This library is **not published on Maven Central**. You must publish it locally or include it as a local dependency.

## Usage

### Add as a local dependency

**Gradle (local JAR or module):**

```groovy
dependencies {
    implementation files('libs/voice-java-ai-1.0.0.jar')
}
```

Or as a Gradle composite build / included module.

**Maven (install to local repository):**

```bash
git clone https://github.com/Chagui68/VoiceJavaAI.git
cd VoiceJavaAI
./gradlew publishToMavenLocal
```

Then in your `pom.xml`:

```xml
<dependency>
    <groupId>com.voicejavaai</groupId>
    <artifactId>voice-java-ai</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Gradle (local Maven repository):**

```groovy
repositories {
    mavenLocal()
}

dependencies {
    implementation 'com.voicejavaai:voice-java-ai:1.0.0'
}
```

### Basic example

```java
import com.voicejavaai.core.RoboticVoice;
import com.voicejavaai.voice.VoiceConfig;

RoboticVoice voice = RoboticVoice.getInstance();

// Speak text — returns raw PCM samples (16-bit, 22050 Hz)
short[] audio = voice.speak("hello friend");

// Use a custom voice config
VoiceConfig config = new VoiceConfig();
config.setBasePitch(90.0);
config.setSpeed(1.2);
config.setRoboticIntensity(1.0);

short[] audioCustom = voice.speak("who are you", config);

// Use built-in presets
short[] deepAudio = voice.speak("i am here", VoiceConfig.deep());
short[] robotAudio = voice.speak("yes i can", VoiceConfig.robotic());
```

### Playing the audio

The `speak()` method returns raw 16-bit signed PCM samples at 22050 Hz. You can play them via `javax.sound.sampled`:

```java
import javax.sound.sampled.*;

void play(short[] samples, int sampleRate) throws Exception {
    byte[] bytes = new byte[samples.length * 2];
    for (int i = 0; i < samples.length; i++) {
        bytes[i * 2]     = (byte) (samples[i] & 0xFF);
        bytes[i * 2 + 1] = (byte) ((samples[i] >> 8) & 0xFF);
    }
    AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
    SourceDataLine line = AudioSystem.getSourceDataLine(format);
    line.open(format);
    line.start();
    line.write(bytes, 0, bytes.length);
    line.drain();
    line.close();
}
```

## API overview

| Class | Description |
|-------|-------------|
| `RoboticVoice` | Singleton facade — `speak(String)`, `speak(String, VoiceConfig)`, `setConfig(VoiceConfig)` |
| `VoiceConfig` | Voice parameters: pitch, speed, volume, robotic intensity, formant shift, vibrato, monotone mode |
| `FormantSynthesizer` | Core synthesis engine — generates audio from phonemes using formant frequencies |
| `PhonemeMapper` | Maps words to phoneme sequences using an internal bilingual dictionary |

### VoiceConfig parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| `sampleRate` | 22050 | Output sample rate in Hz |
| `basePitch` | 120.0 | Fundamental frequency in Hz |
| `speed` | 1.0 | Speech rate multiplier |
| `roboticIntensity` | 0.8 | Quantization level for robotic effect (0 = off) |
| `volume` | 0.7 | Output amplitude (0.0–1.0) |
| `vibratoRate` | 6.0 | Vibrato rate in Hz |
| `vibratoDepth` | 0.02 | Vibrato depth (fraction of pitch) |
| `formantShift` | 1.0 | Formant frequency multiplier |
| `monotone` | false | Disable vibrato for flat pitch |

## Building

```bash
./gradlew build
```

To publish to your local Maven repository:

```bash
./gradlew publishToMavenLocal
```

## License

[MIT](LICENSE)