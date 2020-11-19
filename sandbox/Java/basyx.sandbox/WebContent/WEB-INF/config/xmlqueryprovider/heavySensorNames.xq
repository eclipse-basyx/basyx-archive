for $sensor in fn:doc('database.xml')/sensors/sensor 
where xs:decimal($sensor/weight) gt 10.00
return
  $sensor/name