DELETE from SUBSCRIBED_FORMAT_CHANNEL_LINK;
delete from SUBSCRIPTION_PARAMETER_VALUE;
DELETE  FROM report_subscription;

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

DELETE  FROM  format_channel_link fml
WHERE EXISTS
(SELECT 1 FROM format fm WHERE fm.id = fml.format_id AND fm.code in ('SWIFT', 'TXT', 'HTML'));

DELETE FROM format_channel_link fml
WHERE EXISTS
(SELECT 1 FROM CHANNEL ch WHERE ch.id = fml.CHANNEL_ID AND ch.code in ('FTP'));
