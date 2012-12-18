package synthesis.audiooutput;



/**
 * @author allegrem
 */
public interface AudioOutput {
	
	public void open() throws Exception;
	
	public void close() throws Exception;
	
	public void play(byte[] arr) throws Exception;
	
}
