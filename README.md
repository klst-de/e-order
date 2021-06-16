# e-order
development of a jar to read and write [electronic orders](https://fnfe-mpe.org/wp-content/uploads/2021/01/20210413-Final-Press-Release-Order-X-_EN.pdf) conforming to [Order-X](http://fnfe-mpe.org/factur-x/order-x/). 

[Order-X(de)](https://www.ferd-net.de/aktuelles/meldungen/order-x-das-hybridformat-fuer-digitalisierte-auftragsverarbeitung-veroeffentlicht.html) is new franco german standard for electronic orders equivalent to to [Factur-X](http://fnfe-mpe.org/factur-x/factur-x_en/). It is based on [UN/CEFACT](https://en.wikipedia.org/wiki/UN/CEFACT) Supply Chain Reference Data Model ([SCRDM](https://www.unescap.org/sites/default/files/Session%202_SCRDM_UNCEFACT.pdf)).

[SCRDM](https://service.unece.org/trade/uncefact/publication/Supply%20Chain%20Management/CrossIndustrySCRDM/SCRDM/HTML/001.htm) contains Cross-Industry-Order (CIO), a xml syntax for e-order. Both CIO and Cross-Industry-Invoice are supply chain messages with similar structure:

![](src/main/doc/image/CIOvsCII.PNG)

Similar because
- CIO schema is based on SCRDM (128) D19A which was published in 2019
- CII however is based on Version (100) [SCRDM 16B](https://unece.org/trade/uncefact/xml-schemas) released in 2016

### 2021-04-13 : Order-X Version 1.0 published

### 2021-04-19 : jars Version 1.0.x released

- download the [jars](../../releases) and dependents
- see code examples to create or read a xml order
- here some snippets

```java
CoreOrder order = CrossIndustryOrder.getFactory().createOrder
	( BG2_ProcessControl.PROFILE_COMFORT  // or PROFILE_BASIC
	, DocumentNameCode.Order);            // or OrderChange or OrderResponse
order.setId("Order#-1"); // BT-1 Identifier (mandatory)
order.addNote( order.createNote("any unstructured information") );    // (optional)
order.setDocumentCurrency("EUR");
 ...
PostalAddress address = order.createAddress("DE", "12345", "City");
ContactInfo contact = order.createContactInfo("name", "tel", "mail");
order.setSeller("SUPPLIER_NAME", address, contact, "CompanyId", null);
 ...
OrderLine line = order.createOrderLine("1"                            // order line number
		  , new Quantity("C62", new BigDecimal(6))                     // 6 units/C62
		  , new Amount(EUR, new BigDecimal(60.00))                     // line net amount
		  , new UnitPriceAmount(EUR, new BigDecimal(10.00))            // price
		  , "Zeitschrift [...]"                                        // itemName
		  );
line.addNote("textual note with information relevant to the line.");
line.setPartialDeliveryIndicator(true);
line.addStandardIdentifier("1234567890123", GTIN);
line.setSellerAssignedID("987654321");
line.setBuyerAssignedID("654987321");

line.setLineObjectID("id", "schemeID", "AWV");                       // BG.25.BT-128
line.setDescription("description");                                  // BG-31.BT-154
line.addClassificationIdentifier("4047247110051", "EN", null, null); // BG-31.BT-158
line.setCountryOfOrigin("FR");                                       // BG-31.BT-159

// BG-27 0..n LINE ALLOWANCES:
BigDecimal tenPerCent = new BigDecimal(10);
line.addAllowance(new Amount(new BigDecimal(6.00)), new Amount(new BigDecimal(60.00)), tenPerCent);
// BG-28 0..n LINE CHARGES:
AllowancesAndCharges charge = line.createCharge(new Amount(new BigDecimal(6.00)), new Amount(new BigDecimal(60.00)), tenPerCent);
charge.setReasoncode("64");
charge.setReasonText("Special agreement");
line.addAllowanceCharge(charge);

order.addLine(line);

byte[] xml = transformer.marshal(order);  // create Order-X xml
```

