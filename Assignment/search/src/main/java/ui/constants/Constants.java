package ui.constants;

import java.io.File;

public class Constants {

	public static final int TIMEOUTS=30;
	public static final int IMPLECIT_WAIT=10;
	public static final int POLLING_INTERVAL=5;

	
	
	
	
	/**
	 * <h1>getFileFullPath</h1>
	 * 
	 * @author Seth Pal
	 * @param relativeDirPath
	 * @return complete path 
	 */
	public static  String getFullPathOfFile(String relativePath)
	{
		String pathName=null;
		try {
		File fil = new File(relativePath);
		pathName=fil.getAbsolutePath();
		}catch(Exception pex)
		{
			System.out.println(pex);
		}
		return pathName;
	}

	
	
	
}
