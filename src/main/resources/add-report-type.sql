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
        900010,
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

INSERT INTO REPORT_PARAMETER_CONSTRAINT (ID, CONSTRAINT_TYPE, VALUE, REPORT_PAREMETER_ID, CREATED_BY, CREATION_TIME)
SELECT SYS_GUID(), 'MINIMUM', '01.01.2018', report_parameter_definition.id, 'SYSTEM', systimestamp
FROM report_parameter_definition where  parameter_type = 'DATE';