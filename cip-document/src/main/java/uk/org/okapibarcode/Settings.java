/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
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
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *******************************************************************************/

package uk.org.okapibarcode;

import com.beust.jcommander.Parameter;
import java.awt.Color;
import uk.org.okapibarcode.backend.HumanReadableLocation;
/**
 *
 * @author <a href="mailto:rstuart114@gmail.com">Robin Stuart</a>
 */
public class Settings {
    
    @Parameter(names = "-cli", description = "Supress GUI loading", required = false)
    private boolean supressGui = false;
    
    @Parameter(names = {"-t", "--types"}, description = "Display table of barcode types", required = false)
    private boolean displayTypes = false;
    
    @Parameter(names = {"-i", "--input"}, description = "Read data from file", required = false)
    private String inputFile = "";
    
    @Parameter(names = {"-o", "--output"}, description = "Write image to file", required = false)
    private String outputFile = "out.png";
    
    @Parameter(names = {"-d", "--data"}, description = "Barcode content", required = false)
    private String inputData = "";
    
    @Parameter(names = {"-b", "--barcode"}, description = "Select barcode type", required = false)
    private int symbolType = 20;
    
    @Parameter(names = "--height", description = "Height of the symbol in multiples of x-dimension", required = false)
    private int symbolHeight = 0;
    
//    @Parameter(names = {"-w", "--whitesp"}, description = "Width of whitespace in multiples of x-dimension", required = false)
//    private int symbolWhiteSpace = 0;
//    
//    @Parameter(names = "--border", description = "Width of border in multiples of x-dimension", required = false)
//    private int symbolBorder = 0;
    
//    @Parameter(names = "--box", description = "Add a box", required = false)
//    private boolean addBox = false;
//    
//    @Parameter(names = "--bind", description = "Add boundary bars", required = false)
//    private boolean addBinding = false;
    
    @Parameter(names = {"-r", "--reverse"}, description = "Reverse colours (white on black)", required = false)
    private boolean reverseColour = false;
    
    @Parameter(names = "--fg", description = "Specify a foreground (ink) colour", required = false)
    private String foregroundColour = "000000";
    
    @Parameter(names = "--bg", description = "Specify a background (paper) colour", required = false)
    private String backgroundColour = "FFFFFF";
    
    @Parameter(names = "--scale", description = "Adjust size of output image", required = false)
    private int symbolScale = 0;
    
    // --directpng, --directeps, --directsvg, --dump
    
//    @Parameter(names = "--rotate", description = "Rotate symbol", required = false)
//    private int rotationAngle = 0;
    
    @Parameter(names = "--cols", description = "Number of columns in PDF417", required = false)
    private int symbolColumns = 0;
    
    @Parameter(names = "--vers", description = "Set QR Code version number", required = false)
    private int symbolVersion = 0;
    
    @Parameter(names = "--secure", description = "Set error correction level", required = false)
    private int symbolECC = 0;
    
    @Parameter(names = "--primary", description = "Add structured primary message", required = false)
    private String primaryData = "";
    
    @Parameter(names = "--mode", description = "Set encoding mode", required = false)
    private int encodeMode = 0;
    
    @Parameter(names = "--gs1", description = "Treat input as GS1 data", required = false)
    private boolean dataGs1Mode = false;
    
    @Parameter(names = "--binary", description = "Treat input as binary data", required = false)
    private boolean dataBinaryMode = false;
    
    @Parameter(names = "--notext", description = "Remove human readable text", required = false)
    private boolean supressHrt = false;
    
    @Parameter(names = "--textabove", description = "Place human readable text above symbol", required = false)
    private boolean superHrt = false;
    
    @Parameter(names = "--square", description = "Force Data Matrix symbols to be square", required = false)
    private boolean makeSquare = false;
    
    @Parameter(names = "--init", description = "Add reader initialisation code", required = false)
    private boolean addReaderInit = false;
    
    // --smalltext
    
    @Parameter(names = "--batch", description = "Treat each line of input as a separate data set", required = false)
    private boolean batchMode = false;
    
    private int height = 0;
    
    private int width = 0;

    /**
     * @return the supressGui
     */
    public boolean isGuiSupressed() {
        return supressGui;
    }
    
    /**
     * @return the displayTypes
     */
    public boolean isDisplayTypes() {
        return displayTypes;
    }

    /**
     * @return the inputFile
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @return the inputData
     */
    public String getInputData() {
        return inputData;
    }

    /**
     * @return the symbolType
     */
    public int getSymbolType() {
        return symbolType;
    }

    /**
     * @return the symbolHeight
     */
    public int getSymbolHeight() {
        return symbolHeight;
    }

//    /**
//     * @return the symbolWhiteSpace
//     */
//    public int getSymbolWhiteSpace() {
//        return symbolWhiteSpace;
//    }

//    /**
//     * @return the symbolBorder
//     */
//    public int getSymbolBorder() {
//        return symbolBorder;
//    }

//    /**
//     * @return the addBox
//     */
//    public boolean isAddBox() {
//        return addBox;
//    }
//
//    /**
//     * @return the addBinding
//     */
//    public boolean isAddBinding() {
//        return addBinding;
//    }

    /**
     * @return the reverseColour
     */
    public boolean isReverseColour() {
        return reverseColour;
    }

    /**
     * @return the foregroundColour
     */
    public Color getForegroundColour() {
        Color inkColour = Color.BLACK;
        String fgColour;
        
        fgColour = foregroundColour.toUpperCase();
        
        if (fgColour.matches("[0-9A-F]+") && (fgColour.length() == 6)) {
            inkColour = Color.decode("0x" + fgColour);
        }
        
        return inkColour;
    }

    /**
     * @return the backgroundColour
     */
    public Color getBackgroundColour() {
        Color paperColour = Color.WHITE;
        String bgColour;
        
        bgColour = backgroundColour.toUpperCase();
        
        if (bgColour.matches("[0-9A-F]+") && (bgColour.length() == 6)) {
            paperColour = Color.decode("0x" + bgColour);
        }
        
        return paperColour;
    }

    /**
     * @return the symbolScale
     */
    public int getSymbolScale() {
        return symbolScale;
    }

//    /**
//     * @return the rotationAngle
//     */
//    public int getRotationAngle() {
//        return rotationAngle;
//    }

    /**
     * @return the symbolColumns
     */
    public int getSymbolColumns() {
        return symbolColumns;
    }

    /**
     * @return the symbolVersion
     */
    public int getSymbolVersion() {
        return symbolVersion;
    }

    /**
     * @return the symbolECC
     */
    public int getSymbolECC() {
        return symbolECC;
    }

    /**
     * @return the primaryData
     */
    public String getPrimaryData() {
        return primaryData;
    }

    /**
     * @return the encodeMode
     */
    public int getEncodeMode() {
        return encodeMode;
    }

    /**
     * @return the dataGs1Mode
     */
    public boolean isDataGs1Mode() {
        return dataGs1Mode;
    }

    /**
     * @return the dataBinaryMode
     */
    public boolean isDataBinaryMode() {
        return dataBinaryMode;
    }

    /**
     * @return the supressHrt
     */
    public HumanReadableLocation getHrtPosition() {
        HumanReadableLocation temp = HumanReadableLocation.BOTTOM;
        
        if(superHrt) {
            temp = HumanReadableLocation.TOP;
        }
        
        if(supressHrt) {
            temp = HumanReadableLocation.NONE;
        }
        
        return temp;
    }

    /**
     * @return the makeSquare
     */
    public boolean isMakeSquare() {
        return makeSquare;
    }

    /**
     * @return the addReaderInit
     */
    public boolean isReaderInit() {
        return addReaderInit;
    }

    /**
     * @return the batchMode
     */
    public boolean isBatchMode() {
        return batchMode;
    }

	public boolean isSupressGui() {
		return supressGui;
	}

	public void setSupressGui(boolean supressGui) {
		this.supressGui = supressGui;
	}

	public boolean isSupressHrt() {
		return supressHrt;
	}

	public void setSupressHrt(boolean supressHrt) {
		this.supressHrt = supressHrt;
	}

	public boolean isSuperHrt() {
		return superHrt;
	}

	public void setSuperHrt(boolean superHrt) {
		this.superHrt = superHrt;
	}

	public boolean isAddReaderInit() {
		return addReaderInit;
	}

	public void setAddReaderInit(boolean addReaderInit) {
		this.addReaderInit = addReaderInit;
	}

	public void setDisplayTypes(boolean displayTypes) {
		this.displayTypes = displayTypes;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public void setInputData(String inputData) {
		this.inputData = inputData;
	}

	public void setSymbolType(int symbolType) {
		this.symbolType = symbolType;
	}

	public void setSymbolHeight(int symbolHeight) {
		this.symbolHeight = symbolHeight;
	}

	public void setReverseColour(boolean reverseColour) {
		this.reverseColour = reverseColour;
	}

	public void setForegroundColour(String foregroundColour) {
		this.foregroundColour = foregroundColour;
	}

	public void setBackgroundColour(String backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	public void setSymbolScale(int symbolScale) {
		this.symbolScale = symbolScale;
	}

	public void setSymbolColumns(int symbolColumns) {
		this.symbolColumns = symbolColumns;
	}

	public void setSymbolVersion(int symbolVersion) {
		this.symbolVersion = symbolVersion;
	}

	public void setSymbolECC(int symbolECC) {
		this.symbolECC = symbolECC;
	}

	public void setPrimaryData(String primaryData) {
		this.primaryData = primaryData;
	}

	public void setEncodeMode(int encodeMode) {
		this.encodeMode = encodeMode;
	}

	public void setDataGs1Mode(boolean dataGs1Mode) {
		this.dataGs1Mode = dataGs1Mode;
	}

	public void setDataBinaryMode(boolean dataBinaryMode) {
		this.dataBinaryMode = dataBinaryMode;
	}

	public void setMakeSquare(boolean makeSquare) {
		this.makeSquare = makeSquare;
	}

	public void setBatchMode(boolean batchMode) {
		this.batchMode = batchMode;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
    
}