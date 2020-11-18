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
INSERT INTO Report_Type (ID, BUSINESS_ID, NAME, SHORT_NAME, REPORT_KEY, DESCRIPTION, CATEGORY) VALUES (SYS_GUID(), 1, 'TradeDetails', 'TradeDetails', 'POC_TRADEDETAILS', 'POC - Trade Details', 'SMRA');
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'CSV' AND A.cCode = 'WebBox'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'CSV' AND A.cCode = 'FTP'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'PDF' AND A.cCode = 'WebBox'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO allowed_format_channel_link(id, format_channel_link_id, report_type_id) SELECT SYS_GUID(), (select a.id FROM (select fml.id, fm.code fCode, ch.code cCode  from format_channel_link fml join format fm on fm.id =  fml.FORMAT_ID join channel ch on ch.id = fml.CHANNEL_ID) a where A.fCode = 'PDF' AND A.cCode = 'FTP'), rt.id FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_TYPE_FREQUENCY(id, report_type_id, FREQUENCY_TYPE) SELECT SYS_GUID(), rt.ID, 'DAILY' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_TYPE_FREQUENCY(id, report_type_id, FREQUENCY_TYPE) SELECT SYS_GUID(), rt.ID, 'QUARTERLY' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_PARAMETER_DEFINITION(id, NAME, description, PARAMETER_TYPE, report_type_id, SORT_ORDER, PARAMETER_MANDATORY, IS_VISIBLE, PARAMETER_SCOPE) SELECT SYS_GUID(), 'From Date:', 'Start date of the report', 'DATE', rt.id, 1, 1, 1, 'ADHOC' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;
INSERT INTO REPORT_PARAMETER_DEFINITION(id, NAME, description, PARAMETER_TYPE, report_type_id, SORT_ORDER, PARAMETER_MANDATORY, IS_VISIBLE, PARAMETER_SCOPE) SELECT SYS_GUID(), 'To Date:',   'End date of the report',   'DATE', rt.id, 2, 0, 1, 'ADHOC' FROM Report_Type rt WHERE rt.BUSINESS_ID = 1;

INSERT INTO Report_Type (ID,
                         BUSINESS_ID,
                         SHORT_NAME,
                         NAME,
                         REPORT_KEY,
                         DESCRIPTION,
                         CATEGORY,
                         CREATION_TIME,
                         MODIFICATION_TIME)
VALUES (SYS_GUID(),
        '900001',
        'RPRO315',
        'RPRO315',
        'REPO_POSITION_DETAILS',
        'DEMO  only - Reports the security positions and money positions (after Margin transfer) and net exposure based on base currency for all open REPO orders',
        'SMRA',
        SYSTIMESTAMP,
        SYSTIMESTAMP);

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'TXT'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'TXT'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;
INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;

INSERT INTO REPORT_PARAMETER_DEFINITION (id,
                                         NAME,
                                         description,
                                         PARAMETER_KEY,
                                         PARAMETER_TYPE,
                                         PARAMETER_SCOPE,
                                         REPORT_TYPE_ID,
                                         SORT_ORDER,
                                         PARAMETER_MANDATORY,
                                         IS_VISIBLE)
SELECT SYS_GUID(),
       'Trade Settle Date From:',
       'From Trade Settlement Date',
       'FromTradeSettleDate',
       'DATE',
       'ADHOC',
       rt.id,
       1,
       0,
       1
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;

INSERT INTO REPORT_PARAMETER_DEFINITION (id,
                                         NAME,
                                         description,
                                         PARAMETER_KEY,
                                         PARAMETER_TYPE,
                                         PARAMETER_SCOPE,
                                         REPORT_TYPE_ID,
                                         SORT_ORDER,
                                         PARAMETER_MANDATORY,
                                         IS_VISIBLE)
SELECT SYS_GUID(),
       'To:',
       'To Trade Settlement Date',
       'ToTradeSettleDate',
       'DATE',
       'ADHOC',
       rt.id,
       2,
       0,
       1
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;

INSERT INTO REPORT_PARAMETER_DEFINITION (id,
                                         NAME,
                                         description,
                                         PARAMETER_KEY,
                                         PARAMETER_TYPE,
                                         PARAMETER_SCOPE,
                                         REPORT_TYPE_ID,
                                         SORT_ORDER,
                                         PARAMETER_MANDATORY,
                                         IS_VISIBLE)
SELECT SYS_GUID(),
       'Trade Currency:',
       'Trade Currency',
       'TradeCcy',
       'STRING',
       'ADHOC',
       rt.id,
       3,
       0,
       1
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900001;
--
-- Insert REPO_MOVEMENTS - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        '900002',
        'RPRO304',
        'REPO MOVEMENTS',
        'REPO_MOVEMENTS',
        'DEMO  only - List all Repo movements updated during the current day for all Bps',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900002;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900002;

INSERT INTO REPORT_PARAMETER_DEFINITION (id,
                                         NAME,
                                         description,
                                         PARAMETER_KEY,
                                         PARAMETER_TYPE,
                                         PARAMETER_SCOPE,
                                         REPORT_TYPE_ID,
                                         SORT_ORDER,
                                         PARAMETER_MANDATORY,
                                         IS_VISIBLE)
SELECT SYS_GUID(),
       'Trade Settle Date From:',
       'From Trade Settlement Date',
       'FromTradeSettleDate',
       'DATE',
       'ADHOC',
       rt.id,
       1,
       0,
       1
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900002;

--
-- Insert REPO_ORDER_STATUS - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        '900003',
        'REPO_ORDER_STATUS',
        'RPRO301',
        'REPO_ORDER_STATUS',
        'DEMO  only - Reports all accepted,matched,overdue,purchased and repurchased Repo orders for the current day for all Bps',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900003;
INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900003;

INSERT INTO REPORT_PARAMETER_DEFINITION (id,
                                         NAME,
                                         description,
                                         PARAMETER_KEY,
                                         PARAMETER_TYPE,
                                         PARAMETER_SCOPE,
                                         REPORT_TYPE_ID,
                                         SORT_ORDER,
                                         PARAMETER_MANDATORY,
                                         IS_VISIBLE)
SELECT SYS_GUID(),
       'Trade Settle Date From:',
       'From Trade Settlement Date',
       'FromTradeSettleDate',
       'DATE',
       'ADHOC',
       rt.id,
       1,
       0,
       1
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900003;
--
-- Insert Third_party_collateral - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        '900004',
        'RLRO334',
        'Third Party Collateral',
        'Third_party_collateral',
        'DEMO  only - Report of collateral and margin details exchanged from Collateral and Margin provider to Collateral taker and Margin taker in REPO, TSLB and TCM services',
        'TPA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900004;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900004;


--
-- Insert LSFF_Details - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        '900007',
        'LSFF_Details',
        'LSFF Details',
        'LSFF_Details',
        'DEMO  only - Reports relevant data for the limits from the liquidity-shortage financing facility',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'CSV'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900007;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'CSV'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900007;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900007;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900007;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900007;

--
-- Insert Compensation_Details - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        '900008',
        'Compensation_Details',
        'Compensation Details',
        'Compensation_Details',
        'DEMO  only - Reports Compensation details on Security Positions ',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900008;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900008;

--
-- PlanningForecast - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        '900009',
        'PlanningForecast',
        'PlanningForecast',
        'POC_FORECAST',
        'POC - Planning / Forecast',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'CSV'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900009;
INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900009;

--
-- GUI_Exposure_Report - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        900011,
        'ExposureReport',
        'ExposureReport',
        'GUI_Exposure_Report',
        'DEMO Only - Exposure Report',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'CSV'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;
INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'CSV'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'SWIFT'
          AND A.cCode = 'FTP'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'SWIFT'
          AND A.cCode = 'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900011;

--
-- Eligible_Basket - Daily
--
INSERT INTO Report_Type (ID, BUSINESS_ID, SHORT_NAME, NAME, REPORT_KEY, DESCRIPTION, CATEGORY)
VALUES (SYS_GUID(),
        900012,
        'EligibleBasket',
        'EligibleBasket ',
        'Eligible_Basket',
        'DEMO Only - Eligible Basket',
        'SMRA');

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'CSV'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900012;

INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'SWIFT'
          AND A.cCode = 'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900012;
INSERT INTO allowed_format_channel_link (id, format_channel_link_id, report_type_id)
SELECT SYS_GUID(),
       (SELECT a.id
        FROM (SELECT fml.id, fm.code fCode, ch.code cCode
              FROM format_channel_link fml
                     JOIN format fm ON fm.id =
                                       fml.FORMAT_ID
                     JOIN channel ch ON ch.id =
                                        fml.CHANNEL_ID) a
        WHERE A.fCode = 'PDF'
          AND A.cCode =
              'WebBox'),
       rt.id
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900012;

INSERT INTO REPORT_TYPE_FREQUENCY (id, report_type_id, FREQUENCY_TYPE)
SELECT SYS_GUID(), rt.ID, 'DAILY'
FROM Report_Type rt
WHERE rt.BUSINESS_ID = 900012;


-- Insert One report Parameter for each report type
INSERT INTO REPORT_PARAMETER_DEFINITION (id,
                                         NAME,
                                         description,
                                         PARAMETER_KEY,
                                         PARAMETER_TYPE,
                                         PARAMETER_SCOPE,
                                         REPORT_TYPE_ID,
                                         SORT_ORDER,
                                         PARAMETER_MANDATORY,
                                         IS_VISIBLE)
SELECT SYS_GUID(),
       'Trade From',
       'Trade From',
       'FromDate',
       'DATE',
       'ADHOC',
       rt.id,
       1,
       0,
       1
FROM Report_Type rt;


INSERT INTO REPORT_PARAMETER_CONSTRAINT (ID, CONSTRAINT_TYPE, VALUE, REPORT_PAREMETER_ID, CREATED_BY, CREATION_TIME)
SELECT SYS_GUID(), 'MINIMUM', '01.01.2018', report_parameter_definition.id, 'SYSTEM', systimestamp
FROM report_parameter_definition where  parameter_type = 'DATE';

--
-- This file in integration test data to create Report Subscriptions
--
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

Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
SELECT SYS_GUID(), rs.ID, 'TradeDetailsType','RepoDetails', 'STRING', 1, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
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

Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
SELECT SYS_GUID(), rs.ID, 'TradeDetailsType','RepoDetails', 'STRING', 1, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
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

/*

DECLARE
  counter number;
BEGIN
  counter := 10;
  while (counter < 100) loop

    INSERT INTO report_subscription (id, business_id, short_name, name, business_partner_reference, scheduled_task_reference, scheduler_cron_expression,
                                     report_type_id, frequency, time, status, CREATION_TIME, MODIFICATION_TIME, CREATED_BY)
    SELECT SYS_GUID(), counter, 'SubscriptionWithParameter', 'Subscribe for CH900800', 'CH900800', rt.report_key || '_' || '3', 'CRON',
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
                               WHERE rt.ID = rs.REPORT_TYPE_ID AND C.CODE = 'COCO' AND F.CODE = 'CSV')
    FROM REPORT_SUBSCRIPTION rs
    WHERE rs.BUSINESS_ID = counter;

    Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
    SELECT SYS_GUID(), rs.ID, 'SelectedLegalAgreement','SMRA', 'STRING',0, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
    FROM REPORT_SUBSCRIPTION rs
    WHERE rs.BUSINESS_ID = counter;

    Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
    SELECT SYS_GUID(), rs.ID, 'TradeStatus','NOT_CANCELED_NOT_MATURED', 'STRING', 1, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
    FROM REPORT_SUBSCRIPTION rs
    WHERE rs.BUSINESS_ID = counter;

    Insert into subscription_parameter_value (ID,REPORT_SUBSCRIPTION_ID,PARAMETER_NAME,PARAMETER_VALUE,PARAMETER_TYPE,SORT_ORDER,CREATED_BY,CREATION_TIME,MODIFIED_BY,MODIFICATION_TIME)
    SELECT SYS_GUID(), rs.ID, 'TradeDetailsType','RepoDetails', 'STRING', 1, 'deepak', SYSTIMESTAMP, 'deepak', SYSTIMESTAMP
    FROM REPORT_SUBSCRIPTION rs
    WHERE rs.BUSINESS_ID = counter;

    counter := counter + 1;
  end loop;
  COMMIT;
END;
*/