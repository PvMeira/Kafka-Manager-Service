
ALTER TABLE CONFIGURATION ADD URL_CONFLUENT_REST_SERVER varchar2(150) default 'http://localhost:8082' NULL;


ALTER TABLE CONFIGURATION DROP COLUMN KAFKA_HOME_DIRECTORY;