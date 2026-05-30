package com.voicejavaai.synth;

import com.voicejavaai.voice.VoiceConfig;

public class FormantSynthesizer {
    private VoiceConfig config;

    private static final double[][] FORMANTS = {
        {800, 1200, 2500},   // a
        {400, 2000, 2800},   // e
        {350, 2500, 3200},   // i
        {450, 1000, 2600},   // o
        {320, 800, 2400},    // u
        {200, 800, 1800},    // b
        {200, 1500, 2500},   // d
        {180, 800, 1600},    // f
        {200, 800, 1600},    // g
        {300, 1800, 2600},   // k
        {300, 1200, 3000},   // l
        {280, 900, 2400},    // m
        {280, 1500, 2500},   // n
        {200, 800, 1800},    // p
        {300, 1500, 2200},   // r
        {300, 1800, 2600},   // s
        {200, 1500, 2500},   // t
        {250, 1800, 2600},   // x
    };

    private static final String[] KEYS = {"a","e","i","o","u","b","d","f","g","k","l","m","n","p","r","s","t","x"};

    public FormantSynthesizer(VoiceConfig config) { this.config = config; }
    public void setConfig(VoiceConfig config) { this.config = config; }

    public short[] synthesizePhonemes(String[] phonemes) {
        if (phonemes == null || phonemes.length == 0) return new short[0];

        int total = 0;
        for (String p : phonemes) total += getDuration(p);

        short[] out = new short[total];
        int off = 0;
        for (String p : phonemes) {
            short[] audio = synthesize(p, getDuration(p));
            System.arraycopy(audio, 0, out, off, audio.length);
            off += audio.length;
        }
        return out;
    }

    private short[] synthesize(String phoneme, int dur) {
        short[] audio = new short[dur];
        if (phoneme.equals("_pause") || phoneme.equals("_silence")) return audio;

        double[] formants = getFormants(phoneme);
        if (formants == null) return audio;

        double pitch = config.getBasePitch();
        double inc = 1.0 / config.getSampleRate();
        double t = 0;

        for (int i = 0; i < dur; i++) {
            t += inc;
            double vib = config.isMonotone() ? 1.0 :
                1.0 + config.getVibratoDepth() * Math.sin(2.0 * Math.PI * config.getVibratoRate() * t);

            double phase = 2.0 * Math.PI * pitch * vib * t;
            double saw = 0;
            for (int h = 1; h <= 8; h++) saw += Math.sin(h * phase) / h;
            saw *= 0.6;

            double sample = 0;
            for (int f = 0; f < 3; f++) {
                double ff = formants[f] * config.getFormantShift();
                sample += Math.sin(2.0 * Math.PI * ff * t) / (f + 1);
            }

            sample *= saw;

            if (config.getRoboticIntensity() > 0) {
                double levels = 1.0 / (config.getRoboticIntensity() * 0.3 + 0.01);
                sample = Math.round(sample * levels) / levels;
            }

            int atk = (int)(config.getSampleRate() * 0.005);
            int rel = (int)(config.getSampleRate() * 0.01);
            double env = 1.0;
            if (i < atk) env = (double) i / atk;
            else if (i > dur - rel) env = (double) (dur - i) / rel;

            sample *= env * config.getVolume();
            audio[i] = (short) Math.max(-32768, Math.min(32767, (int)(sample * 32767)));
        }
        return audio;
    }

    private double[] getFormants(String p) {
        for (int i = 0; i < KEYS.length; i++) {
            if (KEYS[i].equals(p)) return FORMANTS[i];
        }
        return null;
    }

    private int getDuration(String p) {
        double d;
        switch (p) {
            case "_pause": d = 0.15; break;
            case "_silence": d = 0.02; break;
            case "a": case "e": case "i": case "o": case "u": d = 0.12; break;
            case "b": case "d": case "g": case "p": case "t": case "k": d = 0.05; break;
            case "m": case "n": d = 0.08; break;
            case "s": case "x": d = 0.10; break;
            default: d = 0.07;
        }
        return (int)(d * config.getSampleRate() / config.getSpeed());
    }
}
