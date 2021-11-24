package eu.dauphine.javaavanceeTD6;

import java.io.IOException;
import java.nio.file.Path;

public interface MyAction {
	
	void perform(Path path) throws IOException;

}
