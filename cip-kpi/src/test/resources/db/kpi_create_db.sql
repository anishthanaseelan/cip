DROP TABLE shipment_summary  IF EXISTS;

CREATE TABLE shipment_summary (
  id INTEGER IDENTITY PRIMARY KEY,
  node varchar(20),
  carrier varchar(25) ,
  carrier_svc varchar(25) ,
  rqst_rcvd INTEGER,
  lbl_rqst INTEGER,
  load_rqst INTEGER,
  mfst_rqst INTEGER,
  err_rqst INTEGER,
  pend_load INTEGER,
  pend_mfst INTEGER,
  crt_tmstmp datetime 
);


