package eu.dauphine.javaavanceeTD6;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class DirMonitor {
	private Path path;
	
	public DirMonitor(Path repertoire) throws IOException {
		if(!Files.isReadable(repertoire) && !Files.isDirectory(repertoire)) {
			throw new IOException();
		}
		this.path = repertoire;
	}
	
	public String toStringPath() throws IOException {
		PrefixFilter filtre = new PrefixFilter(10); 
		String res = "";
		Iterator<Path> iterator = Files.newDirectoryStream(this.path).iterator();
		while(iterator.hasNext()) {
			
			if(!Files.isDirectory(iterator.next()) && filtre.accept(iterator.next()) ) {
				res += iterator.next().toString()+"\n";
				
			}	
		}
		return res;
	}

	public long sizeOfFiles() throws IOException {
		long res = 0;
		Iterator<Path> iterator = Files.newDirectoryStream(this.path).iterator();
		while(iterator.hasNext()) {
			if(!Files.isDirectory(iterator.next())) {
				res += iterator.next().toFile().length() ;
			}
		}
		return res;
	}
	

    public File mostRecent() throws IOException {
        long init = 0;
        File fileMostRecent = null;
        DirectoryStream<Path> listDirectoryPath = Files.newDirectoryStream(this.path);
        for(Path path : listDirectoryPath) {
            if(init == 0) {
            	fileMostRecent = path.toFile();
                init = fileMostRecent.lastModified();
            }
            if(fileMostRecent.lastModified() < init) {
            	fileMostRecent = path.toFile();
                init = fileMostRecent.lastModified();
            }
        }
        return fileMostRecent;

        //return new MostRecent().mostRecent(path);
    }

	
	public static void main(String[] args) throws Exception{
		Path path = FileSystems.getDefault().getPath("/Users/rayanprevost");
		
		try {
			DirMonitor dirMonitor = new DirMonitor(path);
			System.out.println(dirMonitor.toStringPath());
			System.out.println(dirMonitor.sizeOfFiles());
			System.out.println(dirMonitor.mostRecent());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
