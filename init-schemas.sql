CREATE DATABASE IF NOT EXISTS main_schema;

CREATE DATABASE IF NOT EXISTS history_schema;

CREATE DATABASE IF NOT EXISTS appointment_schema;

CREATE DATABASE IF NOT EXISTS notification_schema;

GRANT ALL PRIVILEGES ON main_schema.* TO 'hospidata-user';

GRANT ALL PRIVILEGES ON history_schema.* TO 'hospidata-user';

GRANT ALL PRIVILEGES ON appointment_schema.* TO 'hospidata-user';

GRANT ALL PRIVILEGES ON notification_schema.* TO 'hospidata-user';

FLUSH PRIVILEGES;