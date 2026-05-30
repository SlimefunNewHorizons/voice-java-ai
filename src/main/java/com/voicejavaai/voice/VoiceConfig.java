package com.voicejavaai.voice;

public class VoiceConfig {
    private int sampleRate = 22050;
    private double basePitch = 120.0;
    private double speed = 1.0;
    private double roboticIntensity = 0.8;
    private double volume = 0.7;
    private double vibratoRate = 6.0;
    private double vibratoDepth = 0.02;
    private double formantShift = 1.0;
    private boolean monotone = false;

    public VoiceConfig() {}

    public static VoiceConfig robotic() {
        VoiceConfig c = new VoiceConfig();
        c.basePitch = 100.0;
        c.speed = 0.9;
        c.roboticIntensity = 1.0;
        c.monotone = true;
        c.vibratoRate = 0;
        c.vibratoDepth = 0;
        return c;
    }

    public static VoiceConfig deep() {
        VoiceConfig c = new VoiceConfig();
        c.basePitch = 80.0;
        c.speed = 0.85;
        c.formantShift = 0.8;
        return c;
    }

    public int getSampleRate() { return sampleRate; }
    public double getBasePitch() { return basePitch; }
    public void setBasePitch(double p) { this.basePitch = p; }
    public double getSpeed() { return speed; }
    public void setSpeed(double s) { this.speed = s; }
    public double getRoboticIntensity() { return roboticIntensity; }
    public void setRoboticIntensity(double r) { this.roboticIntensity = r; }
    public double getVolume() { return volume; }
    public void setVolume(double v) { this.volume = v; }
    public double getVibratoRate() { return vibratoRate; }
    public double getVibratoDepth() { return vibratoDepth; }
    public double getFormantShift() { return formantShift; }
    public void setFormantShift(double f) { this.formantShift = f; }
    public boolean isMonotone() { return monotone; }
    public void setMonotone(boolean m) { this.monotone = m; }
}
