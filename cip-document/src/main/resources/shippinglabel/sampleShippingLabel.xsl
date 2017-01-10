<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:okapibarcodegenerator="com.cts.cip.doc.engine.fop.OKAPIBarcodeGenerator"
	exclude-result-prefixes="okapibarcodegenerator">

	<xsl:template match="/">
		<fo:root>
			<xsl:call-template name="document" />
		</fo:root>
	</xsl:template>

	<xsl:template name="document">
		<fo:layout-master-set>
			<fo:simple-page-master master-name="shippingLabel"
				page-height="21cm" page-width="29.7cm" margin-top="0px"
				margin-bottom="0px" margin-left="20px" margin-right="17px">
				<fo:region-body />
			</fo:simple-page-master>
		</fo:layout-master-set>
		<fo:page-sequence master-reference="shippingLabel">
			<xsl:call-template name="body" />
		</fo:page-sequence>
	</xsl:template>

	<xsl:template name="body">
		<fo:flow flow-name="xsl-region-body">
			<xsl:variable name="dataCount" select="count(//*[local-name()='items'])" />
			<xsl:variable name="temp" select="floor(number($dataCount)div 10)" />
			<xsl:variable name="mod" select="number($dataCount) mod 10" />
			<xsl:variable name="pageOne">
				<xsl:number value="1" />
			</xsl:variable>
			<xsl:variable name="zero">
				<xsl:number value="0" />
			</xsl:variable>

			<xsl:variable name="totPages">
				<xsl:choose>
					<xsl:when test="$mod &gt; $zero">
						<xsl:value-of select="$temp+$pageOne" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$temp" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:call-template name="left-side">
				<xsl:with-param name="pageNumber" select="$pageOne" />
				<xsl:with-param name="totalPages" select="$totPages" />
			</xsl:call-template>
			<xsl:call-template name="right-side">
				<xsl:with-param name="pageNumber" select="$pageOne" />
				<xsl:with-param name="totalPages" select="$totPages" />
			</xsl:call-template>
			<xsl:if test="count(//*[local-name()='items']) &gt; 10">
				<fo:block page-break-before="always">
					<xsl:call-template name="left-side">
						<xsl:with-param name="pageNumber" select="($pageOne+1)" />
						<xsl:with-param name="totalPages" select="$totPages" />
					</xsl:call-template>
				</fo:block>
			</xsl:if>
			<xsl:if test="count(//*[local-name()='items']) &gt; 20">
				<fo:block page-break-before="always">
					<xsl:call-template name="left-side">
						<xsl:with-param name="pageNumber" select="($pageOne+2)" />
						<xsl:with-param name="totalPages" select="$totPages" />
					</xsl:call-template>
				</fo:block>
			</xsl:if>
		</fo:flow>
	</xsl:template>

	<xsl:template name="right-side">
		<xsl:param name="pageNumber" />
		<xsl:param name="totalPages" />
		<xsl:variable name="asnNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='shippingUnitBase']/*[local-name()='referenceID']" />
		</xsl:variable>
		<xsl:variable name="maxicode">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='maxiCode']" />
		</xsl:variable>
		<xsl:variable name="zipBarCode">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='zipCode']" />
		</xsl:variable>
		<xsl:variable name="postalBarCode">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='postalBarCode']" />
		</xsl:variable>
		<xsl:variable name="upcTrackingNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='trackingNumber']" />
		</xsl:variable>
		<xsl:variable name="signatureRequired">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='shippingOptions']/*[local-name()='specialServiceType']" />
		</xsl:variable>
		<xsl:variable name="maxiCodeRequired">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='isMaxiCodeReqd']" />
		</xsl:variable>
		<fo:block-container absolute-position="absolute"
			width="4.125in" left="500pt" top="0pt">
			<fo:block font-family="Helvetica" font-size="8pt"
				font-weight="normal">
				<fo:table width="275px">
					<fo:table-column column-width="150px" />
					<fo:table-column column-width="75px" />
					<fo:table-column column-width="50px" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell padding-top="0.1cm" column-width="2cm"
								font-weight="bold">
								<xsl:apply-templates
									select="/*[local-name()='shippingUnit']/*[local-name()='shipperDetails']/*[local-name()='shipperAddress']" />
							</fo:table-cell>
							<fo:table-cell padding-top="0.1cm">
								<fo:block font-size="12pt" font-weight="bold"
									text-align="center">
									<xsl:value-of
										select="/*[local-name()='shippingUnit']/*[local-name()='packageWeight']" />
									<xsl:text>&#xA0;</xsl:text>
									<xsl:value-of
										select="/*[local-name()='shippingUnit']/*[local-name()='packageWeightUOM']" />
								</fo:block>
							</fo:table-cell>
							<fo:table-cell padding-top="0.1cm">
								<fo:block font-size="10pt" font-weight="bold"
									text-align="end">1 OF 1
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:block-container>

		<fo:block-container absolute-position="absolute"
			width="4.125in" left="500pt" top="35pt">
			<fo:block>
				<fo:table width="300px" font-family="Helvetica" font-size="10pt"
					font-weight="normal">
					<fo:table-column column-width="100px" />
					<fo:table-column column-width="200px" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell padding-top="0.7cm" text-align="center"
								width="85px">
								<fo:block font-size="10pt" font-weight="bold">SHIP
								</fo:block>
								<fo:block font-size="10pt" font-weight="bold">TO:
								</fo:block>
							</fo:table-cell>
							<fo:table-cell font-size="10pt" padding-top="0.3cm"
								text-align="left">

								<fo:table>
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell>
												<fo:block text-transform="uppercase">
													<xsl:value-of
														select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='individualName']" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row>
											<fo:table-cell>
												<fo:block text-transform="uppercase">
													<xsl:value-of
														select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='phoneNumber']" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row>
											<fo:table-cell>
												<fo:block text-transform="uppercase">
													<xsl:value-of
														select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='addressLine1']" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row>
											<fo:table-cell>
												<fo:block font-weight="bold" font-size="12pt"
													text-transform="uppercase">
													<xsl:value-of
														select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='city']" />
													,
													<xsl:value-of
														select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='state']" />
													<xsl:text>&#x20;</xsl:text>
													<xsl:value-of
														select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']/*[local-name()='zipCode']" />
												</fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:block-container>


		<fo:block-container absolute-position="absolute"
			width="4in" left="500pt" top="100pt">
			<fo:block>
				<fo:table width="4in">
					<fo:table-column column-width="1.30in" />
					<fo:table-column column-width="0.25in" />
					<fo:table-column column-width="2.45in" />
					<fo:table-body>
						<fo:table-row height="1.1in">
							<fo:table-cell text-align="center" border="1.5pt solid black"
								border-left-style="none">
								<fo:block text-align="start" font-size="48pt"
									font-weight="bold" font-family="Helvetica" padding-top="20px"
									padding-bottom="5px">
									<fo:inline padding-left="30px">
										H
									</fo:inline>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="1pt solid black"
								border-right-style="none">
								<fo:block />
							</fo:table-cell>
							<fo:table-cell border="1pt solid black"
								border-left-style="none" border-right-style="none">
								
								<fo:block>
									<fo:inline  padding-top="30px">
										<fo:block>
											<fo:instream-foreign-object
												content-height="0.60in" content-width="1.8in" scaling="non-uniform">
												<xsl:copy-of
													select="okapibarcodegenerator:generate('CA 90001',20,0,0,'000000','FFFFFF',0,0,0,0,'',0,0,0,1,0,0,0)" />
											</fo:instream-foreign-object>
										</fo:block>
									</fo:inline>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:block-container>
		<xsl:variable name="upcTrackingNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='trackingNumber']" />
		</xsl:variable>
		<fo:block-container absolute-position="absolute"
			width="4in" left="500pt" top="186pt">
			<fo:block>
				<fo:table height="3in">
					<fo:table-column column-width="3.35in" />
					<fo:table-column column-width="0.65in" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell text-align="start" border="4pt solid black"
								border-left-style="none" border-bottom-width="2pt">
								<fo:block font-size="24pt" font-weight="bold"
									font-family="Helvetica">CARRIER GROUND
								</fo:block>
								<fo:block font-size="10pt" font-weight="bold"
									font-family="Helvetica">
									SERIAL #:
									<xsl:choose>
										<xsl:when test="string-length($upcTrackingNumber)=18">
											<xsl:value-of select="substring($upcTrackingNumber,1,2)" />
											<xsl:text>&#xA0;</xsl:text>
											<xsl:value-of select="substring($upcTrackingNumber,3,3)" />
											<xsl:text>&#xA0;</xsl:text>
											<xsl:value-of select="substring($upcTrackingNumber,6,3)" />
											<xsl:text>&#xA0;</xsl:text>
											<xsl:value-of select="substring($upcTrackingNumber,9,2)" />
											<xsl:text>&#xA0;</xsl:text>
											<xsl:value-of select="substring($upcTrackingNumber,11,4)" />
											<xsl:text>&#xA0;</xsl:text>
											<xsl:value-of select="substring($upcTrackingNumber,15,4)" />
											<xsl:text>&#xA0;</xsl:text>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="$upcTrackingNumber" />
										</xsl:otherwise>
									</xsl:choose>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell border="4pt solid black"
								border-left-style="none" border-bottom-width="2pt"
								background-color="#000000">
								<fo:block></fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:block-container>

		<fo:block-container absolute-position="absolute"
			width="4in" left="500pt" top="240pt">
			<fo:block>
				<fo:inline padding-left=".75cm" padding-right="3cm">
					<fo:block>
						<fo:instream-foreign-object
							content-height="1.17in" content-width="3.5in" scaling="non-uniform">
							<xsl:copy-of
								select="okapibarcodegenerator:generate($upcTrackingNumber,20,0,0,'000000','FFFFFF',0,0,0,0,'',0,0,0,1,0,0,0)" />
						</fo:instream-foreign-object>
					</fo:block>
				</fo:inline>
			</fo:block>
		</fo:block-container>
		<fo:block-container absolute-position="absolute"
			width="4in" left="500pt" top="330pt">
			<fo:block border-bottom-width="4pt" width="4in"
				border-bottom-style="solid" border-bottom-color="black" />
		</fo:block-container>

		<fo:block-container absolute-position="absolute"
			width="4in" left="500pt" top="345pt">
			<fo:block font-family="Helvetica">
				<fo:block font-size="8pt" font-weight="bold">BILLING: P/P
				</fo:block>
			</fo:block>
			<fo:block font-family="Helvetica">
				<fo:block font-size="8pt" font-weight="bold">REF
				</fo:block>
			</fo:block>
		</fo:block-container>

		<fo:block-container absolute-position="absolute"
			width="4in" left="500pt" top="368pt">
			<fo:block border-bottom-width="1pt" width="4in"
				border-bottom-style="solid" border-bottom-color="black" />
		</fo:block-container>
	</xsl:template>

	<xsl:template name="left-side">
		<xsl:param name="pageNumber" />
		<xsl:param name="totalPages" />
		<xsl:variable name="asnNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='shippingUnitBase']/*[local-name()='referenceID']" />
		</xsl:variable>
		<xsl:variable name="poNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='poNumber']" />
		</xsl:variable>

		<xsl:variable name="tcNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='trasactionControlNumber']" />
		</xsl:variable>


		<xsl:variable name="orderNumber">
			<xsl:value-of
				select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='orderNumber']" />
		</xsl:variable>
		<fo:block>
			<fo:external-graphic src="url('file:///c:/FOP/Logo.jpg')" />
		</fo:block>
		<fo:block-container absolute-position="absolute"
			width="100px" left="250pt" top="6pt">
			<fo:block text-align="center" font-family="Helvetica"
				font-weight="normal" font-size="6pt">

				<fo:instream-foreign-object
					content-height="scale-to-fit" content-width="60px" scaling="non-uniform">
					<xsl:copy-of
						select="okapibarcodegenerator:generate($poNumber,20,0,0,'000000','FFFFFF',0,0,0,0,'',0,0,0,1,0,0,0)" />
				</fo:instream-foreign-object>
			</fo:block>
		</fo:block-container>
		<fo:block-container absolute-position="absolute"
			width="380px" height="60px" left="0pt" top="70pt" border-style="solid"
			border-width="1pt">
			<fo:table font-family="Helvetica" font-size="8pt"
				font-weight="normal">
				<table-column />
				<table-column />
				<table-column />
				<table-column />
				<table-column />
				<table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell padding-top="5pt" text-align="center">
							<fo:block>Ordered By:</fo:block>
						</fo:table-cell>
						<fo:table-cell padding-top="5pt" text-align="start"
							number-columns-spanned="2">
							<xsl:apply-templates
								select="/*[local-name()='shippingUnit']/*[local-name()='shipperDetails']/*[local-name()='shipperAddress']" />
						</fo:table-cell>
						<fo:table-cell padding-top="5pt">
							<fo:block>Shipped To:</fo:block>
						</fo:table-cell>
						<fo:table-cell padding-top="5pt" text-align="start"
							number-columns-spanned="2">
							<xsl:apply-templates
								select="/*[local-name()='shippingUnit']/*[local-name()='order']/*[local-name()='deliveryToAddress']" />
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block-container>
		<fo:block-container absolute-position="absolute"
			left="30pt" top="140pt">
			<fo:block font-family="Helvetica" font-size="10pt"
				font-weight="normal">Items in this shipment:</fo:block>
		</fo:block-container>
		<fo:block-container absolute-position="absolute"
			width="380px" left="0pt" top="160pt">
			<fo:table border-style="solid" border-width="1pt" width="340px">
				<fo:table-column column-width="18px" border-style="solid"
					border-width="1pt" />
				<fo:table-column column-width="18px" border-style="solid"
					border-width="1pt" />
				<fo:table-column column-width="64px" border-style="solid"
					border-width="1pt" />
				<fo:table-column column-width="175px" border-style="solid"
					border-width="1pt" />
				<fo:table-column column-width="30px" border-style="solid"
					border-width="1pt" />
				<fo:table-column column-width="35px" border-style="solid"
					border-width="1pt" />
				<fo:table-column column-width="40px" border-style="solid"
					border-width="1pt" />
				<fo:table-body font-family="Helvetica" font-weight="normal"
					font-size="8pt">
					<fo:table-row line-height="8pt">
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt">
							<fo:block text-align="center" font-size="7pt">Line</fo:block>
						</fo:table-cell>
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt">
							<fo:block text-align="center" font-size="7pt">QTY</fo:block>
						</fo:table-cell>
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt">
							<fo:block text-align="center" font-size="7pt">Item Number
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt">
							<fo:block text-align="center" font-size="7pt">Description
							</fo:block>
						</fo:table-cell>
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt">
							<fo:block text-align="center" font-size="7pt">VAS</fo:block>
						</fo:table-cell>
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt" font-size="7pt">
							<fo:block text-align="center">Unit Price</fo:block>
						</fo:table-cell>
						<fo:table-cell border-width="1pt" border-style="solid"
							height="12pt" padding-top="5pt" font-size="7pt">
							<fo:block text-align="center">Total Price</fo:block>
						</fo:table-cell>
					</fo:table-row>

					<xsl:variable name="col-limit" select="10" />

					<xsl:variable name="serialCount"
						select="count(//*[local-name()='secondaryDescriptions'])" />

					<xsl:variable name="rows"
						select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='items'][1]/*" />
					<xsl:variable name="cols"
						select=". | following-sibling::*[local-name()='items'][position() &lt; 10]" />

					<xsl:variable name="dataCount" select="count(//*[local-name()='items'])" />

					<xsl:if test="number($pageNumber) = number(1)">
						<xsl:choose>
							<xsl:when test="$serialCount = 0">
								<xsl:for-each
									select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='items'][position() &lt;= $col-limit]">
									<xsl:variable name="row" select="position()" />
									<xsl:call-template name="item-rows" />
								</xsl:for-each>
								<xsl:choose>
									<xsl:when test="number($dataCount) &gt; 10">
										<xsl:call-template name="dummy-rows">
											<xsl:with-param name="rowCount" select="4" />
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="dummy-rows">
											<xsl:with-param name="rowCount" select="15-number($dataCount)" />
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:for-each
									select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='items'][position() &lt;= 6]">
									<xsl:call-template name="serial-item-rows" />
								</xsl:for-each>
								<xsl:call-template name="dummy-rows">
									<xsl:with-param name="rowCount" select="9-number($dataCount)" />
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					<xsl:if test="number($pageNumber) = 2">
						<xsl:for-each
							select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='items'][position() &gt; $col-limit and position() &lt;= 20]">
							<xsl:variable name="row" select="position()" />
							<xsl:call-template name="item-rows" />
						</xsl:for-each>
						<xsl:choose>
							<xsl:when test="number($dataCount)&gt; 20">
								<xsl:call-template name="dummy-rows">
									<xsl:with-param name="rowCount" select="4" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="dummy-rows">
									<xsl:with-param name="rowCount" select="25-number($dataCount)" />
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
					<xsl:if test="number($pageNumber) > 2">
						<xsl:for-each
							select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='items'][position() &gt; 20 and position() &lt;= 30]">
							<xsl:variable name="row" select="position()" />
							<xsl:call-template name="item-rows" />
						</xsl:for-each>
						<xsl:choose>
							<xsl:when test="number($dataCount) &gt; 30">
								<xsl:call-template name="dummy-rows">
									<xsl:with-param name="rowCount" select="4" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="dummy-rows">
									<xsl:with-param name="rowCount" select="34-number($dataCount)" />
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>

				</fo:table-body>
			</fo:table>
		</fo:block-container>
		<fo:block-container width="120px"
			absolute-position="absolute" left="220pt" top="315pt">
			<fo:table font-family="Helvetica" font-weight="normal"
				font-size="8pt">
				<table-column column-width="120px" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">SubTotal
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">Shipping and Handling
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">Sales tax
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">Shipment Total
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block-container>
		<fo:block-container width="20px" absolute-position="absolute"
			left="360pt" top="315pt">
			<fo:table font-family="Helvetica" font-weight="normal"
				font-size="8pt">
				<table-column column-width="20px" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">
								<xsl:value-of
									select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='totalOrderItemsCost']" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">
								<xsl:value-of
									select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='shippingAndHandlingCharges']" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">
								<xsl:value-of
									select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='salesTax']" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="right">
								<xsl:value-of
									select="/*[local-name()='shippingUnit']/*[local-name()='invoiceDetails']/*[local-name()='shipmentTotalCost']" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block-container>
		<fo:block-container absolute-position="absolute"
			width="380px" left="0pt" top="380pt">
			<fo:block text-align="start" font-family="Helvetica"
				font-weight="bold" font-size="12pt">Thank You For Your Order </fo:block>
		</fo:block-container>
		<fo:block-container absolute-position="absolute"
			width="380px" left="0pt" top="400pt">
			<fo:table text-align="start" font-family="Helvetica"
				font-weight="normal" font-size="7pt">
				<table-column column-width="proportional-column-width(1)" />
				<table-column column-width="proportional-column-width(1)" />
				<table-column column-width="proportional-column-width(1)" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block>WANT TO RETURN AN ITEM?
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>QUESTION ABOUT YOUR ORDER?
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell>
							<fo:block>If you aren't satisfied with your order for any
								reason.Please visit www.yourcompany.com/returns.
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block>Please visit www.yourcompany.com/ordertrack for
								details,
								or
								contact us www.yourcompany.com/contact
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block-container>

		<fo:block-container absolute-position="absolute"
			width="50px" left="350pt" top="580pt">
			<fo:block font-size="8pt" font-weight="bold">
				<xsl:value-of select="$pageNumber" />
				OF
				<xsl:value-of select="$totalPages" />
			</fo:block>
		</fo:block-container>
		<xsl:if test="number($pageNumber) = number(1)">
			<fo:block-container reference-orientation="90"
				absolute-position="absolute" width="6in" height="3cm" left="420pt"
				top="100pt">
				<fo:block></fo:block>
			</fo:block-container>
		</xsl:if>
	</xsl:template>

	<xsl:template match="*[local-name()='shipperAddress']">
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='individualName']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='companyName']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='addressLine1']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='addressLine2']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='city']" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="*[local-name()='state']" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="*[local-name()='zipCode']" />
		</fo:block>
	</xsl:template>
	<xsl:template match="*[local-name()='deliveryToAddress']">
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='individualName']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='companyName']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='addressLine1']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='addressLine2']" />
		</fo:block>
		<fo:block text-transform="uppercase">
			<xsl:value-of select="*[local-name()='city']" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="*[local-name()='state']" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="*[local-name()='zipCode']" />
		</fo:block>
	</xsl:template>
	<xsl:template name="dummy-rows">
		<xsl:param name="rowCount" />
		<xsl:if test="$rowCount &gt; 0">
			<fo:table-row line-height="8pt">
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell border-width="1pt" border-style="solid"
					border-top="none" border-bottom="none">
					<fo:block text-align="center">
						<fo:leader />
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
			<xsl:call-template name="dummy-rows">
				<xsl:with-param name="rowCount" select="$rowCount - 1" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template name="item-rows">
		<fo:table-row line-height="8pt">
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='sequenceNumber']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='quantity']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="left">
					<xsl:value-of select="*[local-name()='itemNumber']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="left">
					<xsl:value-of select="*[local-name()='description']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='vasCode']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='unitPrice']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='totalPrice']" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template name="serial-item-rows">
		<fo:table-row line-height="8pt">
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='sequenceNumber']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='quantity']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="left">
					<xsl:value-of select="*[local-name()='itemNumber']" />
				</fo:block>
				<xsl:for-each
					select="*[local-name()='secondaryDescriptions']/*[local-name()='description'][position() &lt;= 1]">
					<fo:block text-align="left" font-size="6pt" font-family="Arial"
						font-weight="normal">
						Serial #(s)
					</fo:block>
				</xsl:for-each>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="left">
					<xsl:value-of select="*[local-name()='description']" />
					<xsl:for-each
						select="*[local-name()='secondaryDescriptions']/*[local-name()='description']">
						<fo:block text-align="left" font-size="6pt" font-family="Arial"
							font-weight="normal">
							<xsl:value-of select="." />
						</fo:block>
					</xsl:for-each>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='vasCode']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='unitPrice']" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell padding-top="2pt">
				<fo:block text-align="center">
					<xsl:value-of select="*[local-name()='totalPrice']" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

</xsl:stylesheet>