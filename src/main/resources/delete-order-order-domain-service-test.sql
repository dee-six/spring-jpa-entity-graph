DELETE from  ORDER_ARTIFACT oa
where exists
(select 1 FROM ORDER_STEP os, ORDER_OBJECT oo
    WHERE  oo.id = os.ORDER_OBJECT_ID
           AND  os.ID = oa.ORDER_STEP_ID
           AND  oo.BUSINESS_ID = '1' );


DELETE from  ORDER_STEP_STATUS oss
where exists
(select 1 FROM ORDER_STEP os, ORDER_OBJECT oo
    WHERE oo.id = os.ORDER_OBJECT_ID
          AND os.ID = oss.ORDER_STEP_ID
          AND oo.BUSINESS_ID = '1');

DELETE from  ORDER_PARAMETER_VALUE oss
where exists
(select 1 FROM ORDER_INSTRUCTION oi, ORDER_STEP os, ORDER_OBJECT oo
    WHERE os.ID = oi.ORDER_STEP_ID
          AND oss.ORDER_INSTRUCTION_ID =  oi.ID
          AND oo.id = os.ORDER_OBJECT_ID
          AND oo.BUSINESS_ID = '1')   ;

DELETE from  ORDER_INSTRUCTION oi
where exists
(select 1 FROM ORDER_STEP os, ORDER_OBJECT oo
    WHERE oo.id = os.ORDER_OBJECT_ID
          AND os.ID = oi.ORDER_STEP_ID
          AND oo.BUSINESS_ID = '1');

DELETE FROM ORDER_STEP os WHERE EXISTS
(SELECT 1 FROM ORDER_OBJECT oo
    where oo.id = os.ORDER_OBJECT_ID
          AND oo.BUSINESS_ID = '1');

DELETE FROM ORDER_OBJECT oo
where oo.BUSINESS_ID = '1';
