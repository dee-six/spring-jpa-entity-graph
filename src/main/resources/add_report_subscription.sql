--
-- This file in integration test data to create Report Subscriptions
--
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'CSV' AND A.cCode = 'FTP'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'PDF' AND A.cCode = 'FTP'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;

INSERT INTO report_subscription (id, business_id, short_name, name, business_partner_reference, scheduled_task_reference, scheduler_cron_expression, report_type_id, frequency, time, status, CREATION_TIME, MODIFICATION_TIME, CREATED_BY)
  SELECT SYS_GUID(), 1, 'SubscribeForIntegrationTest', 'Subscribe for CH900800', 'CH900800', rt.report_key || '_' || '1', 'CRON',
    rt.id, 'DAILY', 'MORNING', 'ACTIVE', SYSTIMESTAMP, SYSTIMESTAMP, 'deepak'
  FROM Report_Type rt
  WHERE rt.BUSINESS_ID = 1;

INSERT INTO SUBSCRIBED_FORMAT_CHANNEL_LINK (ID, REPORT_SUBSCRIPTION_ID, FORMAT_CHANNEL_LINK_ID)
  SELECT SYS_GUID(), rs.ID, (SELECT afl.FORMAT_CHANNEL_LINK_ID
                             FROM REPORT_TYPE rt
                               JOIN ALLOWED_FORMAT_CHANNEL_LINK afl ON afl.REPORT_TYPE_ID = rt.id
                               JOIN FORMAT_CHANNEL_LINK fml ON fml.ID = afl.FORMAT_CHANNEL_LINK_ID
                               JOIN FORMAT f ON f.id = fml.FORMAT_ID
                               JOIN CHANNEL c ON C.ID = fml.CHANNEL_ID
                             WHERE rt.ID = rs.REPORT_TYPE_ID AND C.CODE = 'WebBox' AND F.CODE = 'CSV')
  FROM REPORT_SUBSCRIPTION rs
  WHERE rs.BUSINESS_ID = 1;

INSERT INTO report_subscription (id, business_id, short_name, name, business_partner_reference, scheduled_task_reference, scheduler_cron_expression, report_type_id, frequency, time, status, CREATION_TIME, MODIFICATION_TIME, CREATED_BY)
  SELECT SYS_GUID(), 2, 'SubscribeForIntegrationTest', 'Subscribe for CH900800', 'CH900800', rt.report_key || '_' || '2', 'CRON',
    rt.id, 'DAILY', 'MIDDAY', 'ACTIVE', SYSTIMESTAMP, SYSTIMESTAMP, 'deepak'
  FROM Report_Type rt
  WHERE rt.BUSINESS_ID = 1;

INSERT INTO SUBSCRIBED_FORMAT_CHANNEL_LINK (ID, REPORT_SUBSCRIPTION_ID, FORMAT_CHANNEL_LINK_ID)
  SELECT SYS_GUID(), rs.ID, (SELECT afl.FORMAT_CHANNEL_LINK_ID
                             FROM REPORT_TYPE rt
                               JOIN ALLOWED_FORMAT_CHANNEL_LINK afl ON afl.REPORT_TYPE_ID = rt.id
                               JOIN FORMAT_CHANNEL_LINK fml ON fml.ID = afl.FORMAT_CHANNEL_LINK_ID
                               JOIN FORMAT f ON f.id = fml.FORMAT_ID
                               JOIN CHANNEL c ON C.ID = fml.CHANNEL_ID
                             WHERE rt.ID = rs.REPORT_TYPE_ID AND C.CODE = 'FTP' AND F.CODE = 'CSV')
  FROM REPORT_SUBSCRIPTION rs
  WHERE rs.BUSINESS_ID = 2;


INSERT INTO report_subscription (id, business_id, short_name, name, business_partner_reference, scheduled_task_reference, scheduler_cron_expression,
                                 report_type_id, frequency, time, status, CREATION_TIME, MODIFICATION_TIME, CREATED_BY)
  SELECT SYS_GUID(), 3, 'SubscriptionWithParameter', 'Subscribe for CH900800', 'CH900800', rt.report_key || '_' || '3', 'CRON',
    rt.id, 'DAILY', 'MIDDAY', 'ACTIVE', SYSTIMESTAMP, SYSTIMESTAMP, 'deepak'
  FROM Report_Type rt
  WHERE rt.BUSINESS_ID = 1;

INSERT INTO SUBSCRIBED_FORMAT_CHANNEL_LINK (ID, REPORT_SUBSCRIPTION_ID, FORMAT_CHANNEL_LINK_ID)
  SELECT SYS_GUID(), rs.ID, (SELECT afl.FORMAT_CHANNEL_LINK_ID
                             FROM REPORT_TYPE rt
                               JOIN ALLOWED_FORMAT_CHANNEL_LINK afl ON afl.REPORT_TYPE_ID = rt.id
                               JOIN FORMAT_CHANNEL_LINK fml ON fml.ID = afl.FORMAT_CHANNEL_LINK_ID
                               JOIN FORMAT f ON f.id = fml.FORMAT_ID
                               JOIN CHANNEL c ON C.ID = fml.CHANNEL_ID
                             WHERE rt.ID = rs.REPORT_TYPE_ID AND C.CODE = 'FTP' AND F.CODE = 'CSV')
  FROM REPORT_SUBSCRIPTION rs
  WHERE rs.BUSINESS_ID = 3;

Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
SELECT SYS_GUID(), rs.ID, 'SelectedLegalAgreement','SMRA', 'STRING',0, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
  FROM REPORT_SUBSCRIPTION rs
  WHERE rs.BUSINESS_ID = 3;

Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
SELECT SYS_GUID(), rs.ID, 'TradeStatus','NOT_CANCELED_NOT_MATURED', 'STRING', 1, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
  FROM REPORT_SUBSCRIPTION rs
  WHERE rs.BUSINESS_ID = 3;

Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
SELECT SYS_GUID(), rs.ID, 'TradeDetailsType','RepoDetails', 'STRING', 1, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
  FROM REPORT_SUBSCRIPTION rs
  WHERE rs.BUSINESS_ID = 3;
