CREATE TABLE db_connections (

                                id BIGSERIAL PRIMARY KEY,

                                connection_name VARCHAR(100) NOT NULL,

                                host VARCHAR(255) NOT NULL,

                                port INTEGER NOT NULL,

                                database_name VARCHAR(255) NOT NULL,

                                username VARCHAR(255) NOT NULL,

                                encrypted_password TEXT NOT NULL,

                                created_at TIMESTAMP NOT NULL,

                                updated_at TIMESTAMP NOT NULL,

                                user_id BIGINT NOT NULL,

                                CONSTRAINT fk_connection_user
                                    FOREIGN KEY(user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE
);