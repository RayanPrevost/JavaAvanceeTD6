package eu.dauphine.javaavanceeTD6;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class DirMonitor {
	private Path path;
	
	public DirMonitor(Path repertoire) throws Exception {
		if(!Files.isReadable(repertoire) && !Files.isDirectory(repertoire)) {
			throw new Exception("Path not valid");
		}
		this.path = repertoire;
	}
	
	public String toStringPath() throws IOException {
		String res = "";
		Iterator<Path> iterator = Files.newDirectoryStream(this.path).iterator();
		while(iterator.hasNext()) {
			res += iterator.next().toString()+"\n";
			
		}
		return res;
	}

	public long sizeOfFiles() throws IOException {
		long res = 0;
		Iterator<Path> iterator = Files.newDirectoryStream(this.path).iterator();
		while(iterator.hasNext()) {
			res+= iterator.next().getFileName().toFile().length();
		}
		return res;
	}
	
	public static void main(String[] args) throws Exception{
		Path dotPath = FileSystems.getDefault().getPath(".");
		

		try {
			DirMonitor dirMonitor = new DirMonitor(dotPath);
			System.out.println(dirMonitor.toStringPath());
			System.out.println(dirMonitor.sizeOfFiles());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
