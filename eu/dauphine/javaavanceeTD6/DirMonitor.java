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
	
	public void toStringPath() throws IOException {
		/*PrefixFilter filtre = new PrefixFilter(10); 
		String res = "";
		Iterator<Path> iterator = Files.newDirectoryStream(this.path, filtre).iterator();
		while(iterator.hasNext()) {
			
			if(!Files.isDirectory(iterator.next()) && filtre.accept(iterator.next()) ) {
				res += iterator.next().toString()+"\n";
				
			}	
		}
		return res;*/
		
		 new MyAction(){

	            @Override
	            public void perform(Path p) throws IOException {
	                long minSize = 1500;
	                Iterator<Path> iterator = Files.newDirectoryStream(path, new DirectoryStream.Filter<Path>(){

	                    @Override
	                    public boolean accept(Path entry) throws IOException {
	                        return entry.toFile().length() >= minSize && entry.endsWith("txt");
	                    }
	                    
	                }).iterator();
	                while (iterator.hasNext()) {
	                    System.out.println(iterator.next());
	                }
	            }
	        };


	        
	}

	public long sizeOfFiles() throws IOException {
		/*long res = 0;
		Iterator<Path> iterator = Files.newDirectoryStream(this.path).iterator();
		while(iterator.hasNext()) {
			if(!Files.isDirectory(iterator.next())) {
				res += iterator.next().toFile().length() ;
			}
		}
		return res;*/
        return new CountSize().sizeOfFiles(path);

	
	}
	

    public File mostRecent() throws IOException {
        /*
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
        return fileMostRecent;*/

    	return new MostRecent().mostRecent(path);
    }

    public class PrefixFilter implements DirectoryStream.Filter<Path> {

        private long size;

        public PrefixFilter(long n) {
            size = n;
        }
        @Override
        public boolean accept(Path entry) throws IOException {
            return entry.toFile().length() >= size;
        }
        
    }
    

    public class CountSize implements MyAction {

        private long size;

        public long sizeOfFiles(Path path) throws IOException {
            this.perform(path);
            return size;
        }

        @Override
        public void perform(Path path) throws IOException {
            long size = 0;

            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for(Path pathList : stream)
                size += pathList.toFile().length();

            this.size = size;
        }
        
    }

    public class MostRecent implements MyAction {

        File file;

        public File mostRecent(Path path) throws IOException {
            this.perform(path);
            return file;
        }

        @Override
        public void perform(Path ppath) throws IOException {
            long modified = 0;
            File file = null;
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for(Path path1 : stream) {
                if(modified == 0) {
                	file = path1.toFile();
                    modified = file.lastModified();
                }
                if(file.lastModified() < modified) {
                	file = path1.toFile();
                    modified = file.lastModified();
                }
            }
            this.file = file;
        }
        
    }
        
	public static void main(String[] args) throws Exception{
		Path path = FileSystems.getDefault().getPath(".");
		
		try {
			DirMonitor dirMonitor = new DirMonitor(path);
			dirMonitor.toStringPath();
			System.out.println(dirMonitor.sizeOfFiles());
			System.out.println(dirMonitor.mostRecent());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Réponse 
	 * Ex 2

		1) Surchargée une méthode  signifie  que nous allons ecrire une nouvelle version de cette méthode pour répondre et l'adapter au besoin de la classe

		2) b) 

		c) Car on a besoin de modifier la variable pour la somme à chaque fois et qu'il n'est pas possible de modifier une variable
		que l'on récupère hors de la classe anonyme (elle doit être final)
	 * 
	 * */

}
