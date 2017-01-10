#Clear Old data
delete from load_unit;
delete from shipping_unit;
delete from invoice_item;
delete from invoice;
delete from shipping_order;
delete from address;

#SU Data
insert into address ( id ,business_name,country_code, email,first_name,last_name, primary_phone_number,city, state_code , zip_code ) values ( 1, 'Cognizant' , 'USA' , 'email@cognizant.com', 'First Name', 'Last Name' , '4791234123' , 'Bentonville', 'AR' , '72712' );
insert into address ( id ,business_name,country_code, email,first_name,last_name, primary_phone_number,city, state_code , zip_code ) values ( 2, 'Cognizant' , 'USA' , 'email@cognizant.com', 'First Name', 'Last Name' , '4791234124' , 'Bentonville', 'AR' , '72712' );
insert into address ( id ,business_name,country_code, email,first_name,last_name, primary_phone_number,city, state_code , zip_code ) values ( 3, 'Cognizant' , 'USA' , 'email@cognizant.com', 'First Name', 'Last Name' , '4791234125' , 'Bentonville', 'AR' , '72712' );
insert into address ( id ,business_name,country_code, email,first_name,last_name, primary_phone_number,city, state_code , zip_code ) values ( 4, 'Cognizant' , 'USA' , 'email@cognizant.com', 'First Name', 'Last Name' , '4791234126' , 'Bentonville', 'AR' , '72712' );
insert into shipping_order ( id , reference_id, billing_address_id , shipper_address_id , delivery_address_id , return_address_id) values ( 1, '0000123456789' , 1, 2, 3, 4 );
insert into shipping_order ( id , reference_id, billing_address_id , shipper_address_id , delivery_address_id , return_address_id) values ( 2, '0000123456789' , 1, 2, 3, 4 );
insert into shipping_order ( id , reference_id, billing_address_id , shipper_address_id , delivery_address_id , return_address_id) values ( 3, '0000123456789' , 1, 2, 3, 4 );
insert into shipping_order ( id , reference_id, billing_address_id , shipper_address_id , delivery_address_id , return_address_id) values ( 4, '0000123456789' , 1, 2, 3, 4 );
insert into shipping_order ( id , reference_id, billing_address_id , shipper_address_id , delivery_address_id , return_address_id) values ( 5, '0000123456789' , 1, 2, 3, 4 );
insert into invoice ( id, sales_tax,shipment_total,shipping_handling,sub_total  ) values ( 1, 2.01 , 20.10 , 5.00 , 15.10 );
insert into invoice ( id, sales_tax,shipment_total,shipping_handling,sub_total  ) values ( 2, 2.01 , 20.10 , 5.00 , 15.10 );
insert into invoice ( id, sales_tax,shipment_total,shipping_handling,sub_total  ) values ( 3, 2.01 , 20.10 , 5.00 , 15.10 );
insert into invoice ( id, sales_tax,shipment_total,shipping_handling,sub_total  ) values ( 4, 2.01 , 20.10 , 5.00 , 15.10 );
insert into invoice ( id, sales_tax,shipment_total,shipping_handling,sub_total  ) values ( 5, 2.01 , 20.10 , 5.00 , 15.10 );
insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 1, 1, 'Item1', 2, 'Mouse' , 9.99 , 19.98 );
insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 1, 2, 'Item2', 2, 'Key Board' , 19.99 , 39.98 );

insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 2, 1, 'Item1', 2, 'Mouse' , 9.99 , 19.98 );
insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 2, 2, 'Item2', 2, 'Key Board' , 19.99 , 39.98 );

insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 3, 1, 'Item1', 2, 'Mouse' , 9.99 , 19.98 );
insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 3, 2, 'Item2', 2, 'Key Board' , 19.99 , 39.98 );

insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 4, 1, 'Item1', 2, 'Mouse' , 9.99 , 19.98 );
insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 4, 2, 'Item2', 2, 'Key Board' , 19.99 , 39.98 );

insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 5, 1, 'Item1', 2, 'Mouse' , 9.99 , 19.98 );
insert into invoice_item ( invoice_id , line_number ,description,quantity,secondary_description, unit_price,total_price  ) values ( 5, 2, 'Item2', 2, 'Key Board' , 19.99 , 39.98 );

insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 1,  '98476984756924001', 1,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 2,  '98476984756924002', 2,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 3,  '98476984756924003', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 4,  '98476984756924004', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 5,  '98476984756924005', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 6,  '98476984756924006', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 7,  '98476984756924007', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 8,  '98476984756924008', 1,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 9,  '98476984756924009', 2,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 10, '98476984756924010', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 11, '98476984756924011', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 12, '98476984756924012', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 13, '98476984756924013', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 14, '98476984756924014', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 15, '98476984756924015', 1,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 16, '98476984756924016', 2,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 17, '98476984756924017', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 18, '98476984756924018', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 19, '98476984756924019', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 20, '98476984756924020', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 21, '98476984756924021', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 22, '98476984756924022', 1,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 23, '98476984756924023', 2,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 24, '98476984756924024', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 25, '98476984756924025', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 26, '98476984756924026', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 27, '98476984756924027', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 28, '98476984756924028', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 29, '98476984756924029', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 30, '98476984756924030', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 31, '98476984756924031', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 32, '98476984756924032', 1,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 33, '98476984756924033', 2,1, 20, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 34, '98476984756924034', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 35, '98476984756924035', 3,2, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 36, '98476984756924036', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 37, '98476984756924037', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 38, '98476984756924038', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 39, '98476984756924039', 4,3, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,status   ) values ( 40, '98476984756924040', 5,4, 21, 'INITIATED' );
insert into shipping_unit ( id, referance_id, invoice_id , order_id, carrier_service_id ,tracking_nbr,status   ) values ( 41, '98476984756924041', 5,4, 21, '672750540831', 'INITIATED' );

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 1, 'Load 1', 'CREATED' );

insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 1 , '98476984756924001');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 1 , '98476984756924002');

#Failure Test
insert into load_unit ( id , reference_id , status ) values ( 2, 'Load 100', 'CREATED' );
insert into load_unit ( id , reference_id , status ) values ( 3, 'Load 101', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 2 , '98476984756924001');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 3 , '98476984756924002');



#Load Data
insert into load_unit ( id , reference_id , status ) values ( 4, 'Load 4', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924019');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924020');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924021');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924022');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924023');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924024');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924025');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924026');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924027');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924028');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924029');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924030');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924031');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924032');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924033');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924034');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924035');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924036');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924037');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924038');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924039');
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 4 , '98476984756924040');






#Load Data
insert into load_unit ( id , reference_id , status ) values ( 6, 'Load 6', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 6 , '98476984756924006');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 7, 'Load 7', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 7 , '98476984756924007');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 8 ,'Load 8', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 8 , '98476984756924008');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 9, 'Load 9', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 9 , '98476984756924009');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 10, 'Load 10', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 10 , '98476984756924010');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 11, 'Load 11', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 11 , '98476984756924011');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 12 ,'Load 12', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 12 , '98476984756924012');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 13, 'Load 13', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 13 , '98476984756924013');

#Load Data
insert into load_unit ( id , reference_id , status ) values ( 14, 'Load 14', 'CREATED' );
insert into load_shipping_unit ( load_id , shipping_unit_ref_id ) values ( 14 , '98476984756924014');

#Shipping Document Data
INSERT INTO document(shipping_unit_id, document_name, document_type, document_format, document_url) VALUES('1000000000001', 'label1', 'LABEL', 'PDF', 'http://localhost:8080/genDocs/');
INSERT INTO document(shipping_unit_id, document_name, document_type, document_format, document_url) VALUES('1000000000001', 'label2', 'LABEL', 'ZPL', 'http://localhost:8080/genDocs/');


