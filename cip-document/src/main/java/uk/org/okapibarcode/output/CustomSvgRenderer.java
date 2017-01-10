/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) 
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *   Contributors:
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) 
 *******************************************************************************/

package uk.org.okapibarcode.output;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.OutputStream;

import uk.org.okapibarcode.backend.Hexagon;
import uk.org.okapibarcode.backend.Symbol;
import uk.org.okapibarcode.backend.TextBox;

/**
 * Renders symbologies to SVG (Scalable Vector Graphics).
 *
 * @author <a href="mailto:rstuart114@gmail.com">Robin Stuart</a>
 * @author Daniel Gredler
 */
public class CustomSvgRenderer implements SymbolRenderer {

    /** The output stream to render to. */
    private final OutputStream out;

    /** The paper (background) color. */
    private final Color paper;

    /** The ink (foreground) color. */
    private final Color ink;

    /**
     * Creates a new SVG renderer.
     *
     * @param out the output stream to render to
     * @param paper the paper (background) color
     * @param ink the ink (foreground) color
     */
    public CustomSvgRenderer(OutputStream out, Color paper, Color ink) {
        this.out = out;
        this.paper = paper;
        this.ink = ink;
    }

    /** {@inheritDoc} */
    @Override
    public void render(Symbol symbol) throws IOException {
    	
    }
    
    public String customRender(Symbol symbol,int height, int width, int type) throws IOException {
        
        double magnification = symbol.getModuleWidth();
        int margin = 1 * (int)magnification;
        int whitespace = 1 * (int)magnification;

        String content = symbol.getContent();
        if(height==0){
        	height = (int) (symbol.getHeight() * magnification) + (2 * margin);
        }
        
        if(width==0){
        	width = (int) (symbol.getWidth() * magnification) + (2 * margin) + (2 * whitespace);
        }
           

        String title;
        if (content == null || content.isEmpty()) {
            title = "OkapiBarcode Generated Symbol";
        } else {
            title = content;
        }

        String fgColour = "black";

        String bgColour = "white";

        StringBuffer writer = new StringBuffer();
        
            // Header
          //  writer.append("<?xml version=\"1.0\" standalone=\"no\"?>\n");
          //  writer.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n");
          //  writer.append("   \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
            writer.append("<svg width=\"").append(width)
                  .append("\" height=\"").append(height)
                  .append("\" version=\"1.1")
                  .append("\" xmlns=\"http://www.w3.org/2000/svg\">\n");
            writer.append("   <desc>").append(title).append("</desc>\n");
            writer.append("   <g id=\"barcode\" fill=\"").append(fgColour).append("\">\n");
            writer.append("      <rect x=\"0\" y=\"0\" width=\"").append(width)
                  .append("\" height=\"").append(height)
                  .append("\" fill=\"").append(bgColour).append("\" />\n");

            // Rectangles
            for (int i = 0; i < symbol.rectangles.size(); i++) {
                Rectangle2D.Double rect = symbol.rectangles.get(i);
                writer.append("      <rect x=\"").append((rect.x * magnification) + margin + whitespace)
                      .append("\" y=\"").append((rect.y * magnification) + margin)
                      .append("\" width=\"").append(rect.width * magnification)
                      .append("\" height=\"").append(rect.height * magnification)
                      .append("\" />\n");
            }

            if(type != 117){
	            // Text
	            for (int i = 0; i < symbol.texts.size(); i++) {
	                TextBox text = symbol.texts.get(i);
	                writer.append("      <text x=\"").append((text.x * magnification) + margin + whitespace)
	                      .append("\" y=\"").append((text.y * magnification) + margin)
	                      .append("\" text-anchor=\"middle\"\n");
	                writer.append("         font-family=\"").append(symbol.getFontName())
	                      .append("\" font-size=\"").append(symbol.getFontSize() * magnification)
	                      .append("\" fill=\"").append(fgColour).append("\">\n");
	                writer.append("         ").append(text.text).append("\n");
	                writer.append("      </text>\n");
	            }
            }

            // Circles
            for (int i = 0; i < symbol.target.size(); i++) {
                Ellipse2D.Double ellipse = symbol.target.get(i);
                String color;
                if ((i & 1) == 0) {
                    color = fgColour;
                } else {
                    color = bgColour;
                }
                writer.append("      <circle cx=\"").append(((ellipse.x + (ellipse.width / 2)) * magnification) + margin + whitespace)
                      .append("\" cy=\"").append(((ellipse.y + (ellipse.width / 2)) * magnification) + margin)
                      .append("\" r=\"").append((ellipse.width / 2) * magnification)
                      .append("\" fill=\"").append(color).append("\" />\n");
            }

            // Hexagons
            for (int i = 0; i < symbol.hexagons.size(); i++) {
                Hexagon hexagon = symbol.hexagons.get(i);
                writer.append("      <path d=\"");
                for (int j = 0; j < 6; j++) {
                    if (j == 0) {
                        writer.append("M ");
                    } else {
                        writer.append("L ");
                    }
                    writer.append((hexagon.pointX[j] * magnification) + margin + whitespace).append(" ")
                          .append((hexagon.pointY[j] * magnification) + margin).append(" ");
                }
                writer.append("Z\" />\n");
            }

            // Footer
            writer.append("   </g>\n");
            writer.append("</svg>\n");
            return writer.toString();
   }
}
