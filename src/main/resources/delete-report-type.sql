DELETE FROM REPORT_TYPE_FREQUENCY rtf
WHERE EXISTS(SELECT 1
             FROM REPORT_TYPE rt
             WHERE rt.ID = rtf.REPORT_TYPE_ID AND rt.BUSINESS_ID > 900000);

DELETE FROM ALLOWED_FORMAT_CHANNEL_LINK afcl
WHERE EXISTS(SELECT 1
             FROM REPORT_TYPE rt
             WHERE rt.ID = afcl.REPORT_TYPE_ID AND rt.BUSINESS_ID > 900000);

DELETE FROM REPORT_PARAMETER_DEFINITION rpd
WHERE EXISTS(SELECT 1
             FROM REPORT_TYPE rt
             WHERE rt.ID = rpd.REPORT_TYPE_ID AND rt.BUSINESS_ID > 900000);

DELETE FROM allowed_format_channel_link afml
WHERE EXISTS (
    SELECT 1
    FROM format_channel_link fml
      JOIN format fm ON fm.id = fml.format_id and fm.code in ('SWIFT', 'TXT', 'HTML')
      JOIN channel ch ON ch.id = fml.channel_id
    WHERE afml.FORMAT_CHANNEL_LINK_ID = fml.id );

-- unlink unlink FTP from Report Type
DELETE FROM allowed_format_channel_link afml
WHERE EXISTS (
    SELECT 1
    FROM format_channel_link fml
      JOIN format fm ON fm.id = fml.format_id
      JOIN channel ch ON ch.id = fml.channel_id AND ch.code ='FTP'
    WHERE afml.FORMAT_CHANNEL_LINK_ID = fml.id );

-- unlink FTP from Report Subscription
DELETE  FROM SUBSCRIBED_FORMAT_CHANNEL_LINK sfml
WHERE EXISTS (
    SELECT 1
    FROM format_channel_link fml
      JOIN format fm ON fm.id = fml.format_id
      JOIN channel ch ON ch.id = fml.channel_id AND ch.code ='FTP'
    WHERE sfml.FORMAT_CHANNEL_LINK_ID = fml.id);

-- -- unlink Swift, TXT, HTML from Report Subscription
DELETE FROM SUBSCRIBED_FORMAT_CHANNEL_LINK sfml
WHERE EXISTS (
    SELECT 1
    FROM format_channel_link fml
      JOIN format fm ON fm.id = fml.format_id and fm.code in ('SWIFT', 'TXT', 'HTML')
      JOIN channel ch ON ch.id = fml.channel_id
    WHERE sfml.FORMAT_CHANNEL_LINK_ID = fml.id);

DELETE  FROM  format_channel_link fml
WHERE EXISTS
  (SELECT 1 FROM format fm WHERE fm.id = fml.format_id AND fm.code in ('SWIFT', 'TXT', 'HTML'));

DELETE FROM format_channel_link fml
WHERE EXISTS
  (SELECT 1 FROM CHANNEL ch WHERE ch.id = fml.CHANNEL_ID AND ch.code in ('FTP'));

DELETE FROM REPORT_TYPE where BUSINESS_ID > 900000;
