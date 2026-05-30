package com.voicejavaai.core;

import com.voicejavaai.phoneme.PhonemeMapper;
import com.voicejavaai.synth.FormantSynthesizer;
import com.voicejavaai.voice.VoiceConfig;

public class RoboticVoice {
    private static RoboticVoice instance;
    private final FormantSynthesizer synthesizer;
    private final PhonemeMapper phonemeMapper;
    private VoiceConfig config;

    private RoboticVoice() {
        this.config = new VoiceConfig();
        this.synthesizer = new FormantSynthesizer(config);
        this.phonemeMapper = new PhonemeMapper();
    }

    public static synchronized RoboticVoice getInstance() {
        if (instance == null) {
            instance = new RoboticVoice();
        }
        return instance;
    }

    public short[] speak(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new short[0];
        }
        String[] phonemes = phonemeMapper.textToPhonemes(text.toLowerCase().trim());
        return synthesizer.synthesizePhonemes(phonemes);
    }

    public short[] speak(String text, VoiceConfig customConfig) {
        FormantSynthesizer customSynth = new FormantSynthesizer(customConfig);
        String[] phonemes = phonemeMapper.textToPhonemes(text.toLowerCase().trim());
        return customSynth.synthesizePhonemes(phonemes);
    }

    public void setConfig(VoiceConfig config) {
        this.config = config;
        this.synthesizer.setConfig(config);
    }

    public VoiceConfig getConfig() { return config; }
    public int getSampleRate() { return config.getSampleRate(); }
    public long getDurationMs(short[] audio) {
        return (long) audio.length * 1000 / config.getSampleRate();
    }
}
