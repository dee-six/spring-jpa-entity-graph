--
-- Insert Report Format
--
INSERT  INTO Format (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'CSV', 'CSV', 1);
INSERT  INTO Format (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'PDF', 'PDF', 1);
INSERT  INTO Format (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'TXT', 'TXT', 1);
INSERT  INTO Format (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'HTML', 'HTML', 1);
INSERT  INTO Format (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'SWIFT', 'SWIFT', 1);

--
-- Insert Report Channel
--
INSERT  INTO Channel (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'WebBox', 'WebBox', 1);
INSERT  INTO Channel (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'FTP', 'FTP', 1);
INSERT  INTO Channel (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'CCLINK', 'CC-link', 1);
INSERT  INTO Channel (ID, CODE, NAME, CREATED_BY) VALUES (SYS_GUID(), 'SWIFT', 'Swift Alliance', 1);

--
-- Insert Preconfigured Format Channel
--
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'CSV'), (SELECT ID FROM Channel WHERE CODE = 'WebBox') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'CSV'), (SELECT ID FROM Channel WHERE CODE = 'FTP') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'PDF'), (SELECT ID FROM Channel WHERE CODE = 'WebBox') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'PDF'), (SELECT ID FROM Channel WHERE CODE = 'FTP') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'TXT'), (SELECT ID FROM Channel WHERE CODE = 'WebBox') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'TXT'), (SELECT ID FROM Channel WHERE CODE = 'FTP') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'HTML'), (SELECT ID FROM Channel WHERE CODE = 'WebBox') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'HTML'), (SELECT ID FROM Channel WHERE CODE = 'FTP') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'SWIFT'), (SELECT ID FROM Channel WHERE CODE = 'WebBox') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'SWIFT'), (SELECT ID FROM Channel WHERE CODE = 'FTP') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'SWIFT'), (SELECT ID FROM Channel WHERE CODE = 'CCLINK') FROM dual;
INSERT  INTO format_channel_link(id, format_id, channel_id) SELECT  SYS_GUID(), (SELECT ID FROM Format WHERE CODE = 'SWIFT'), (SELECT ID FROM Channel WHERE CODE = 'SWIFT') FROM dual;



--
-- Trade Details - Daily, Monthly, Quarterly
--
/*
INSERT INTO Report_Type (ID, BUSINESS_ID, NAME, SHORT_NAME, REPORT_KEY, DESCRIPTION, CATEGORY) VALUES (SYS_GUID(), 1, 'TradeDetails', 'TradeDetails', 'POC_TRADEDETAILS', 'POC - Trade Details', 'SMRA');
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'CSV' AND A.cCode = 'WebBox'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'CSV' AND A.cCode = 'FTP'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'PDF' AND A.cCode = 'WebBox'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'PDF' AND A.cCode = 'FTP'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_TYPE_FREQUENCY(id, report_type_id, FREQUENCY_TYPE) SELECT SYS_GUID(), rt.ID, 'DAILY' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_TYPE_FREQUENCY(id, report_type_id, FREQUENCY_TYPE) SELECT SYS_GUID(), rt.ID, 'QUARTERLY' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_PARAMETER_DEFINITION(id, NAME, description, PARAMETER_TYPE, report_type_id, SORT_ORDER, PARAMETER_MANDATORY, IS_VISIBLE, PARAMETER_SCOPE) SELECT SYS_GUID(), 'From Date:', 'Start date of the report', 'DATE', rt.id, 1, 1, 1, 'DATE' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_PARAMETER_DEFINITION(id, NAME, description, PARAMETER_TYPE, report_type_id, SORT_ORDER, PARAMETER_MANDATORY, IS_VISIBLE, PARAMETER_SCOPE) SELECT SYS_GUID(), 'To Date:',   'End date of the report',   'DATE', rt.id, 2, 0, 1, 'DATE' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
*/