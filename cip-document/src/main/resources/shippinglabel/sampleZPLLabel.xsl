<?xml version="1.0"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="text" omit-xml-declaration="yes" indent="no" />
	<xsl:template match="/">
		^XA

		^FX
		^CF0,60		
		^FO220,50^FDFast Shipping, Inc.^FS
		^CF0,40
		^FO220,100^FD3000 Shipping Lane^FS
		^FO220,135^FDCaveSprings CA 38102^FS
		^FO220,170^FDUnited States (USA)^FS
		^FO50,250^GB700,1,3^FS

		^FX Second section with recipient address and permit information.
		^CFA,30
		^FO50,300^FDKumar^FS
		^FO50,340^FD100 Main Street^FS
		^FO50,380^FDCaveSprings CA39021^FS
		^FO50,420^FDUnited States (USA)^FS
		^CFA,15
		
		^FX Third section with barcode.
		^BY5,2,270
		^FO175,550^BC^FD1234567890^FS

		^FX Fourth section (the two boxes on the bottom).
		^FO50,900^GB700,250,3^FS
		^FO400,900^GB1,250,3^FS
		^CF0,40
		^FO100,960^FDShipping control^FS
		^FO100,1010^FDREF1 F00B47^FS
		^FO100,1060^FDREF2 BL4H8^FS
		^CF0,190
		^FO485,965^FDCA^FS

		^XZ
	</xsl:template>
</xsl:stylesheet>