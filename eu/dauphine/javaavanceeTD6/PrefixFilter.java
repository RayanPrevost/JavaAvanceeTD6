package eu.dauphine.javaavanceeTD6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public class PrefixFilter implements DirectoryStream.Filter<Path> {
	
	private long n;
	
	public PrefixFilter(long n ) {
		this.n = n;
	}
	
	
	@Override
	public boolean accept(Path entry) throws IOException {
		boolean res = false;
		if(entry.toFile().length() >= n) {
			res = true;
		}
		
		return res;
	}

}
