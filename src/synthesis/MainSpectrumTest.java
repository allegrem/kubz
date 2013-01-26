package synthesis;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import synthesis.audiooutput.SpeakersOutput;
import synthesis.exceptions.AudioException;
import synthesis.exceptions.RequireAudioBlocksException;
import synthesis.fmInstruments.FmInstrument;
import synthesis.fmInstruments.WoodInstrument;
import synthesis.soundlab.SLSoundView;
import synthesis.soundlab.SLSpectrumView;
import synthesis.soundlab.SLWindow;

public class MainSpectrumTest extends SLWindow {
	
	public static final int Y_SIZE = 200;

	private static final int X_SIZE = 600;

	private JFrame frmTestSpectrum;

	private byte[] result3s;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainSpectrumTest window = new MainSpectrumTest();
					window.frmTestSpectrum.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainSpectrumTest() {
		initialize();
		
		
		
		FmInstrument instr = new WoodInstrument();
		byte[] soundBytes = null;
		try {
			soundBytes = SynthesisUtilities.computeSound(0f, 1f, instr);
		} catch (RequireAudioBlocksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//looking for the smallest power of two above sound length
		int power2Length = 1;
		while(power2Length < soundBytes.length)
			power2Length *= 2;
		
		//converting byte array to double array
		double[] sound = new double[power2Length];
		for(int i=0; i<soundBytes.length; i++)
			sound[i] = soundBytes[i];
		for(int i=soundBytes.length; i<power2Length; i++) 
			sound[i] = 0;  //add zeros at the end
		
		//compute fourier transform
		FastFourierTransformer fourier = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] result = fourier.transform(sound, TransformType.FORWARD); //PRENDRE fft(double) !!!!! (protected??)
		//never ask me why it works, i dont know!!
//		for (int x = 0; x < result.length/4; x++) {
		System.out.println(result.length);
		for (int x = 0; x < result.length/4; x++) {
//			int y1 = (int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[x].abs())) * Y_SIZE / 7);
//			int y2 = (int) (Y_SIZE - Math.abs(Math.log10(0.0001 + result[result.length/4 + x].abs())) * Y_SIZE / 7);
			if (x < 600 || (x > 1500 && x < 3000)) {
				result[x] = new Complex(0);
				result[result.length/4 + x] = new Complex(0);
				result[result.length - 1 - result.length/4 - x] = new Complex(0);
				result[result.length - x - 1] = new Complex(0);
			}
//			if (y2 > 60)
//				result[result.length/4 + x] = 0;
		}
		
		Complex[] result2 = fourier.transform(result, TransformType.INVERSE);
		result3s = new byte[result2.length]; 
		
		for(int i=0 ; i < result2.length; i++)
			result3s[i] = (byte) result2[i].getReal();
		
		SpeakersOutput speakersOutput = new SpeakersOutput();
		try {
			speakersOutput.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			speakersOutput.play(result3s);
		} catch (AudioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		speakersOutput.close();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestSpectrum = new JFrame();
		frmTestSpectrum.setTitle("Test Spectrum");
		frmTestSpectrum.setBounds(100, 100, 620, 580);
		frmTestSpectrum.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestSpectrum.getContentPane().setLayout(new BoxLayout(frmTestSpectrum.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new SLSpectrumView();
		frmTestSpectrum.getContentPane().add(panel);
		
		SLSoundView panel_1 = new SLSoundView();
		frmTestSpectrum.getContentPane().add(panel_1);
		panel_1.zoomAll(44100);
		
	}

	/* (non-Javadoc)
	 * @see synthesis.soundlab.SLWindow#getLastSound()
	 */
	public byte[] getLastSound() {
		return result3s;
	}
	
	

}
