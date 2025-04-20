package gh2;

/* Imports the required audio library from the
 * edu.princeton.cs.introcs package. */
import edu.princeton.cs.introcs.StdAudio;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the GuitarString class.
 *  @author Josh Hug
 */
public class TestGuitarString  {

    private static final double C4 = 261.63;
    private static final double D4 = 293.66;
    private static final double E4 = 329.63;
    private static final double F4 = 349.23;
    private static final double G4 = 392.00;
    private static final double A4 = 440.00;
    private static final double B4 = 493.88;

    @Test
    public void playHaruNoHikageRiff() {
        // 模拟前奏吉他riff
        playNote(E4, 0.3);
        playNote(G4, 0.3);
        playNote(A4, 0.6);
        playNote(E4, 0.3);
        playNote(G4, 0.3);
        playNote(B4, 0.6);

        // 加入副歌部分
        playChord(new double[]{E4, G4, B4}, 1.0);
        playChord(new double[]{D4, F4, A4}, 1.0);
    }

    private void playNote(double frequency, double duration) {
        GuitarString string = new GuitarString(frequency);
        string.pluck();

        int samples = (int) (44100 * duration);
        for (int i = 0; i < samples; i++) {
            StdAudio.play(string.sample());
            string.tic();
        }
    }

    private void playChord(double[] frequencies, double duration) {
        GuitarString[] strings = new GuitarString[frequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            strings[i] = new GuitarString(frequencies[i]);
            strings[i].pluck();
        }

        int samples = (int) (44100 * duration);
        for (int i = 0; i < samples; i++) {
            double sample = 0;
            for (GuitarString s : strings) {
                sample += s.sample();
                s.tic();
            }
            StdAudio.play(sample / strings.length); // 平均混合
        }
    }

//    @Test
//    public void testPluckTheAString() {
//        GuitarString aString = new GuitarString(GuitarHeroLite.CONCERT_A);
//        aString.pluck();
//        for (int i = 0; i < 50000; i += 1) {
//            StdAudio.play(aString.sample());
//            aString.tic();
//        }
//    }

    @Test
    public void testSample() {
        GuitarString s = new GuitarString(100);
        assertEquals(0.0, s.sample(), 0.0);
        assertEquals(0.0, s.sample(), 0.0);
        assertEquals(0.0, s.sample(), 0.0);
        s.pluck();
        double sample = s.sample();
        assertNotEquals("After plucking, your samples should not be 0.", 0.0, sample);

        assertEquals("Sample should not change the state of your string.", sample, s.sample(), 0.0);
        assertEquals("Sample should not change the state of your string.", sample, s.sample(), 0.0);
    }


    @Test
    public void testTic() {
        GuitarString s = new GuitarString(100);
        assertEquals(0.0, s.sample(), 0.0);
        assertEquals(0.0, s.sample(), 0.0);
        assertEquals(0.0, s.sample(), 0.0);
        s.pluck();
        double sample1 = s.sample();
        assertNotEquals("After plucking, your samples should not be 0.", 0.0, sample1);

        s.tic();
        assertNotEquals("After tic(), your samples should not stay the same.", sample1, s.sample());
    }


    @Test
    public void testTicCalculations() {
        // Create a GuitarString of frequency 11025, which
        // is a Deque of length 4. 
        GuitarString s = new GuitarString(11025);
        s.pluck();

        // Record the front four values, ticcing as we go.
        double s1 = s.sample();
        s.tic();
        double s2 = s.sample();
        s.tic(); 
        double s3 = s.sample();
        s.tic();
        double s4 = s.sample();

        // If we tic once more, it should be equal to 0.996*0.5*(s1 + s2)
        s.tic();

        double s5 = s.sample();
        double expected = 0.996 * 0.5 * (s1 + s2);

        // Check that new sample is correct, using tolerance of 0.001.
        // See JUnit documentation for a description of how tolerances work
        // for assertEquals(double, double)
        assertEquals("Wrong tic value. Try running the testTic method.", expected, s5, 0.001);
    }
}

