package com.kettler.util;

import java.awt.Dimension
import javax.imageio.ImageIO
import javax.imageio.ImageReader
import javax.imageio.stream.FileImageInputStream
import javax.imageio.stream.ImageInputStream
import java.awt.Dimension

class Image {

    static Dimension getImageDimension(final String path) {
	    Dimension dimension = null
	    def matcher = path =~ /.*\.(.*)$/
    	String suffix = matcher[0][1]
	    ImageIO.getImageReadersBySuffix(suffix).each {reader ->
	        try {
	            ImageInputStream stream = new FileImageInputStream(new File(path))
	            reader.setInput(stream)
	            int width = reader.getWidth(reader.getMinIndex())
	            int height = reader.getHeight(reader.getMinIndex())
	            dimension = new Dimension(width, height)
	        } catch (IOException e) {
	            println e.getMessage()
	        } finally {
	            reader.dispose()
	        } 
	    } 
	    return dimension
    }
    
}